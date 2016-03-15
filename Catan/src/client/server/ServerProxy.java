package client.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import client.gameManager.GameManager;
import client.translators.ModelTranslator;
import client.translators.game.*;
import client.translators.games.*;
import client.translators.moves.*;
import client.translators.user.*;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.GameModel;

/**
 * 
 * See ServerInterface Javadoc for details of methods
 *
 */
public class ServerProxy implements ServerInterface {

	private String userCookie;
	private String gameCookie;
	private String fullCookie;
	private ModelTranslator translator;
	private static String SERVER_HOST;
	private static int SERVER_PORT; //given server runs on port 8081
	private static String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	
	public ServerProxy(String host, int port){
		SERVER_HOST = host;
		SERVER_PORT = port;
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
		translator = new ModelTranslator();
	}
	
	@SuppressWarnings("deprecation")
	private String post(String urlPath, String json) throws ServerException{
		StringBuffer result = new StringBuffer();
		try{
			URL url = new URL(URL_PREFIX + urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			if(urlPath == "/user/login" || urlPath == "/user/register"){
				connection.connect();
				DataOutputStream output = new DataOutputStream(connection.getOutputStream());
				output.writeBytes(json);
				if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
					throw new ServerException(String.format("doPost failed: %s (http code %d) %s",
							urlPath, connection.getResponseCode(), connection.getResponseMessage()));
				}
				userCookie = connection.getHeaderField("Set-cookie");
				userCookie = userCookie.replace(";Path=/;", "");
				String cookie = userCookie.replace("catan.user=", "");
				String jsonCookieString = URLDecoder.decode(cookie);
				Gson gson = new Gson();
				JsonObject jsonCookie = gson.fromJson(jsonCookieString, JsonObject.class);
				JsonPrimitive nameJson = jsonCookie.getAsJsonPrimitive("name");
				String name = nameJson.getAsString();
				JsonPrimitive idJson = jsonCookie.getAsJsonPrimitive("playerID");
				int id = idJson.getAsInt();
				
				GameManager.getInstance().getPlayerInfo().setId(id);
				GameManager.getInstance().getPlayerInfo().setName(name);
			}else if(urlPath == "/games/create"){
				connection.setRequestProperty("Cookie", userCookie);
				connection.connect();
				DataOutputStream output = new DataOutputStream(connection.getOutputStream());
				output.writeBytes(json);
				if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
					throw new ServerException(String.format("doPost failed: %s (http code %d) %s",
							urlPath, connection.getResponseCode(), connection.getResponseMessage()));
				}
			}else if(urlPath == "/games/join"){
				connection.setRequestProperty("Cookie", userCookie);
				connection.connect();
				DataOutputStream output = new DataOutputStream(connection.getOutputStream());
				output.writeBytes(json);
				if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
					throw new ServerException(String.format("doPost failed: %s (http code %d) %s",
							urlPath, connection.getResponseCode(), connection.getResponseMessage()));
				}
				gameCookie = connection.getHeaderField("Set-cookie");
				gameCookie = gameCookie.replace(";Path=/;", "");
				fullCookie = userCookie + "; " + gameCookie;
			}else{
				if(fullCookie == null){
					throw new ServerException("Haven't logged in/and or joined game");
				}
				connection.setRequestProperty("Cookie", fullCookie);
				connection.connect();
				DataOutputStream output = new DataOutputStream(connection.getOutputStream());
				output.writeBytes(json);
				if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
					throw new ServerException(String.format("doPost failed: %s (http code %d) %s",
							urlPath, connection.getResponseCode(), connection.getResponseMessage()));
				}
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			while((inputLine = in.readLine()) != null){
				result.append(inputLine);
			}
			in.close();
		}catch(Exception e){
			e.printStackTrace();
			throw new ServerException(String.format("doPost failed: %s", e.getMessage()), e);
		}
		return result.toString();
	}
	
	private String get(String urlPath) throws ServerException{
		StringBuffer result = new StringBuffer();
		try{
			URL url = new URL(URL_PREFIX + urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			if(urlPath != "/games/list"){
				connection.setRequestProperty("Cookie", fullCookie);
			}
			connection.connect();
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				while((inputLine = in.readLine()) != null){
					result.append(inputLine);
				}
				in.close();
			}else{
				throw new ServerException(String.format("doGet failed: %s (http code %d)",
						urlPath, connection.getResponseCode()));
			}
		}catch(Exception e){
			throw new ServerException(String.format("doGet failed: %s", e.getMessage()), e);
		}
		return result.toString();
	}
	
	@Override
	public void userLogin(String username, String password) throws ServerException {
		UserTranslator user = new UserTranslator(username, password);
		String json = user.translate();
		try{
			post("/user/login", json);
		}catch(ServerException e){
			throw e;
		}
	}
	
	@Override
	public void userRegister(String username, String password) throws ServerException {
		UserTranslator user = new UserTranslator(username, password);
		String json = user.translate();
		try{
			post("/user/register", json);
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public String listGames() throws ServerException {
		try{
			return get("/games/list");
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public String createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts) throws ServerException {
		GamesCreateTranslator create = new GamesCreateTranslator(randomTiles, randomNumbers, randomPorts, name);
		String json = create.translate();
		try{
			return post("/games/create", json);
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public void joinGame(int playerID, String color) throws ServerException {
		GamesJoinTranslator join = new GamesJoinTranslator(playerID, color);
		String json = join.translate();
		try{
			post("/games/join", json);
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public GameModel getModel(int versionID) throws ServerException {
		try{
			StringBuilder result = new StringBuilder();
			result.append("/game/model?version=");
			result.append(versionID);
			String json = get(result.toString());
			if(json == "true"){
				return null;
			}
			return translator.getModelfromJSON(json);
		}catch(ServerException e){
			throw e;
		}
		
	}

	@Override
	public void addAI(String AIname) throws ServerException {
		GameAddAITranslator ai = new GameAddAITranslator(AIname);
		String json = ai.translate();
		try{
			post("/game/addAI", json);
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public String[] listAIs() throws ServerException {
		try{
			String json = get("/game/listAI");
			GameListAITranslator ai = new GameListAITranslator(json);
			return ai.getAIs();
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public GameModel sendChat(int playerID, String content) throws ServerException {
		MovesSendChatTranslator chat = new MovesSendChatTranslator(playerID, content);
		String json = chat.translate();
		try{
			String model = post("/moves/sendChat", json);
			return translator.getModelfromJSON(model);
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public GameModel rollNumber(int playerID, int numberRolled) throws ServerException {
		MovesRollNumberTranslator roll = new MovesRollNumberTranslator(playerID, numberRolled);
		String json = roll.translate();
		try{
			String model = post("/moves/rollNumber", json);
			return translator.getModelfromJSON(model);
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public GameModel robPlayer(int playerID, int victimID, HexLocation newRobberLocation) throws ServerException {
		MovesRobPlayerTranslator rob = new MovesRobPlayerTranslator(playerID, victimID, newRobberLocation);
		String json = rob.translate();
		try{
			String model = post("/moves/robPlayer", json);
			return translator.getModelfromJSON(model);
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public GameModel finishTurn(int playerID) throws ServerException {
		MovesFinishTurnTranslator finish = new MovesFinishTurnTranslator(playerID);
		String json = finish.translate();
		try{
			String model = post("/moves/finishTurn", json);
			return translator.getModelfromJSON(model);
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public GameModel buyDevCard(int playerID) throws ServerException {
		MovesBuyDevCardTranslator dev = new MovesBuyDevCardTranslator(playerID);
		String json = dev.translate();
		try{
			String model = post("/moves/buyDevCard", json);
			return translator.getModelfromJSON(model);
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public GameModel yearOfPlenty(int playerID, ResourceType resource1, ResourceType resource2) throws ServerException {
		MovesYearOfPlentyTranslator year = new MovesYearOfPlentyTranslator(playerID, resource1, resource2);
		String json = year.translate();
		try{
			String model = post("/moves/Year_of_Plenty", json);
			return translator.getModelfromJSON(model);
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public GameModel roadBuilding(int playerID, EdgeLocation roadLocation1, EdgeLocation roadLocation2) throws ServerException {
		MovesRoadBuildingTranslator roadBuilding = new MovesRoadBuildingTranslator(playerID, roadLocation1.getHexLoc(), roadLocation1.getDir(),
				roadLocation2.getHexLoc(), roadLocation2.getDir());
		String json = roadBuilding.translate();
		try{
			String model = post("/moves/Road_Building", json);
			return translator.getModelfromJSON(model);
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public GameModel knight(int playerID, int victimID, HexLocation newRobberLocation) throws ServerException {
		MovesSoldierTranslator soldier = new MovesSoldierTranslator(playerID, victimID, newRobberLocation);
		String json = soldier.translate();
		try{
			String model = post("/moves/Soldier", json);
			return translator.getModelfromJSON(model);
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public GameModel monopoly(int playerID, ResourceType resource) throws ServerException {
		MovesMonopolyTranslator monopoly = new MovesMonopolyTranslator(playerID, resource);
		String json = monopoly.translate();
		try{
			String model = post("/moves/Monopoly", json);
			return translator.getModelfromJSON(model);
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public GameModel monument(int playerID) throws ServerException {
		MovesMonumentTranslator monument = new MovesMonumentTranslator(playerID);
		String json = monument.translate();
		try{
			String model = post("/moves/Monument", json);
			return translator.getModelfromJSON(model);
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public GameModel buildRoad(int playerID, EdgeLocation roadLocation, boolean setUp) throws ServerException {
		MovesBuildRoadTranslator road = new MovesBuildRoadTranslator(playerID, roadLocation.getHexLoc(), roadLocation.getDir(), setUp);
		String json = road.translate();
		try{
			String model = post("/moves/buildRoad", json);
			return translator.getModelfromJSON(model);
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public GameModel buildSettlment(int playerID, VertexLocation vertexLocation, boolean setUp) throws ServerException {
		MovesBuildSettlementTranslator settlement = new MovesBuildSettlementTranslator(playerID, vertexLocation.getHexLoc(), 
				vertexLocation.getDir(), setUp);
		String json = settlement.translate();
		try{
			String model = post("/moves/buildSettlement", json);
			return translator.getModelfromJSON(model);
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public GameModel buildCity(int playerID, VertexLocation vertexLocation) throws ServerException {
		MovesBuildCityTranslator city = new MovesBuildCityTranslator(playerID, vertexLocation.getHexLoc(), vertexLocation.getDir());
		String json = city.translate();
		try{
			String model =post("/moves/buildCity", json);
			return translator.getModelfromJSON(model);
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public GameModel acceptTrade(int playerID, boolean accept) throws ServerException {
		MovesAcceptTradeTranslator acceptTrade = new MovesAcceptTradeTranslator(playerID, accept);
		String json = acceptTrade.translate();
		try{
			String model = post("/moves/acceptTrade", json);
			return translator.getModelfromJSON(model);
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public GameModel maritimeTrade(int playerID, int ratio, ResourceType inputResource, ResourceType outputResource) throws ServerException {
		MovesMaritimeTradeTranslator maritime = new MovesMaritimeTradeTranslator(playerID, ratio, inputResource, outputResource);
		String json = maritime.translate();
		try{
			String model = post("/moves/maritimeTrade", json);
			return translator.getModelfromJSON(model);
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public GameModel discardCards(int playerID, ArrayList<ResourceType> resources) throws ServerException {
		MovesDiscardCardsTranslator discard = new MovesDiscardCardsTranslator(playerID, resources);
		String json = discard.translate();
		try{
			String model = post("/moves/discardCards", json);
			return translator.getModelfromJSON(model);
		}catch(ServerException e){
			throw e;
		}
	}

	@Override
	public GameModel offerTrade(int playerID, ArrayList<ResourceType> resourceGive, ArrayList<ResourceType> resourceReceive,
			int receiverID) throws ServerException {
		MovesOfferTradeTranslator offer = new MovesOfferTradeTranslator(playerID, resourceGive, resourceReceive, receiverID);
		String json = offer.translate();
		try{
			String model = post("/moves/offerTrade", json);
			return translator.getModelfromJSON(model);
		}catch(ServerException e){
			throw e;
		}
	}

}
