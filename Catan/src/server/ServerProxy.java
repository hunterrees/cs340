package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import translators.user.*;
import translators.games.*;
import translators.ModelTranslator;
import translators.game.*;
import translators.moves.*;
import model.GameModel;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

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
	}
	
	private void post(String urlPath, String json) throws ServerException{
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
		}catch(Exception e){
			throw new ServerException(String.format("doPost failed: %s", e.getMessage()), e);
		}
	}
	
	private String get(String urlPath) throws ServerException{
		StringBuffer result = new StringBuffer();
		try{
			URL url = new URL(URL_PREFIX + urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setRequestProperty("Cookie:", fullCookie);
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
	public void userLogin(String username, String password) {
		UserTranslator user = new UserTranslator(username, password);
		String json = user.translate();
		try{
			post("/user/login", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void userRegister(String username, String password) {
		UserTranslator user = new UserTranslator(username, password);
		String json = user.translate();
		try{
			post("/user/register", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}

	@Override
	public String listGames() {
		try{
			return get("/games/list");
		}catch(ServerException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts) {
		GamesCreateTranslator create = new GamesCreateTranslator(randomTiles, randomNumbers, randomPorts, name);
		String json = create.translate();
		try{
			post("/games/create", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}

	@Override
	public void joinGame(int playerID, String color) {
		GamesJoinTranslator join = new GamesJoinTranslator(playerID, color);
		String json = join.translate();
		try{
			post("/games/join", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}

	@Override
	public GameModel getModel(int versionID) {
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
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addAI(String AIname) {
		GameAddAITranslator ai = new GameAddAITranslator(AIname);
		String json = ai.translate();
		try{
			post("/game/addAI", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}

	@Override
	public String listAIs() {
		try{
			return get("/game/listAI");
		}catch(ServerException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void sendChat(int playerID, String content) {
		MovesSendChatTranslator chat = new MovesSendChatTranslator(playerID, content);
		String json = chat.translate();
		try{
			post("/moves/sendChat", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}

	@Override
	public void rollNumber(int playerID, int numberRolled) {
		MovesRollNumberTranslator roll = new MovesRollNumberTranslator(playerID, numberRolled);
		String json = roll.translate();
		try{
			post("/moves/rollNumber", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}

	@Override
	public void robPlayer(int playerID, int victimID, HexLocation newRobberLocation) {
		MovesRobPlayerTranslator rob = new MovesRobPlayerTranslator(playerID, victimID, newRobberLocation);
		String json = rob.translate();
		try{
			post("/moves/robPlayer", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}

	@Override
	public void finishTurn(int playerID) {
		MovesFinishTurnTranslator finish = new MovesFinishTurnTranslator(playerID);
		String json = finish.translate();
		try{
			post("/moves/finishTurn", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}

	@Override
	public void buyDevCard(int playerID) {
		MovesBuyDevCardTranslator dev = new MovesBuyDevCardTranslator(playerID);
		String json = dev.translate();
		try{
			post("/moves/buyDevCard", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}

	@Override
	public void yearOfPlenty(int playerID, ResourceType resource1, ResourceType resource2) {
		MovesYearOfPlentyTranslator year = new MovesYearOfPlentyTranslator(playerID, resource1, resource2);
		String json = year.translate();
		try{
			post("/moves/Year_of_Plenty", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}

	@Override
	public void roadBuilding(int playerID, EdgeLocation roadLocation1, EdgeLocation roadLocation2) {
		MovesRoadBuildingTranslator roadBuilding = new MovesRoadBuildingTranslator(playerID, roadLocation1.getHexLoc(), roadLocation1.getDir(),
				roadLocation2.getHexLoc(), roadLocation2.getDir());
		String json = roadBuilding.translate();
		try{
			post("/moves/Road_Building", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}

	@Override
	public void knight(int playerID, int victimID, HexLocation newRobberLocation) {
		MovesSoldierTranslator soldier = new MovesSoldierTranslator(playerID, victimID, newRobberLocation);
		String json = soldier.translate();
		try{
			post("/moves/Soldier", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}

	@Override
	public void monopoly(int playerID, ResourceType resource) {
		MovesMonopolyTranslator monopoly = new MovesMonopolyTranslator(playerID, resource);
		String json = monopoly.translate();
		try{
			post("/moves/Monopoly", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}

	@Override
	public void monument(int playerID) {
		MovesMonumentTranslator monument = new MovesMonumentTranslator(playerID);
		String json = monument.translate();
		try{
			post("/moves/Monument", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}

	@Override
	public void buildRoad(int playerID, EdgeLocation roadLocation, boolean setUp) {
		MovesBuildRoadTranslator road = new MovesBuildRoadTranslator(playerID, roadLocation.getHexLoc(), roadLocation.getDir(), setUp);
		String json = road.translate();
		try{
			post("/moves/buildRoad", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}

	@Override
	public void buildSettlment(int playerID, VertexLocation vertexLocation, boolean setUp) {
		MovesBuildSettlementTranslator settlement = new MovesBuildSettlementTranslator(playerID, vertexLocation.getHexLoc(), 
				vertexLocation.getDir(), setUp);
		String json = settlement.translate();
		try{
			post("/moves/buildSettlement", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}

	@Override
	public void buildCity(int playerID, VertexLocation vertexLocation) {
		MovesBuildCityTranslator city = new MovesBuildCityTranslator(playerID, vertexLocation.getHexLoc(), vertexLocation.getDir());
		String json = city.translate();
		try{
			post("/moves/buildCity", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}

	@Override
	public void acceptTrade(int playerID, boolean accept) {
		MovesAcceptTradeTranslator acceptTrade = new MovesAcceptTradeTranslator(playerID, accept);
		String json = acceptTrade.translate();
		try{
			post("/moves/acceptTrade", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}

	@Override
	public void maritimeTrade(int playerID, int ratio, ResourceType inputResource, ResourceType outputResource) {
		MovesMaritimeTradeTranslator maritime = new MovesMaritimeTradeTranslator(playerID, ratio, inputResource, outputResource);
		String json = maritime.translate();
		try{
			post("/moves/maritimeTrade", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}

	@Override
	public void discardCards(int playerID, ArrayList<ResourceType> resources) {
		MovesDiscardCardsTranslator discard = new MovesDiscardCardsTranslator(playerID, resources);
		String json = discard.translate();
		try{
			post("/moves/discardCards", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}

	@Override
	public void offerTrade(int playerID, ArrayList<ResourceType> resourceGive, ArrayList<ResourceType> resourceReceive,
			int receiverID) {
		MovesOfferTradeTranslator offer = new MovesOfferTradeTranslator(playerID, resourceGive, resourceReceive, receiverID);
		String json = offer.translate();
		try{
			post("/moves/offerTrade", json);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}

}
