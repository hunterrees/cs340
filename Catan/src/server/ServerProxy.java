package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import translators.user.*;
import translators.games.*;
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
	private int gameID;
	private static String SERVER_HOST;
	private static int SERVER_PORT;
	private static String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	
	public ServerProxy(String host, int port){
		SERVER_HOST = host;
		SERVER_PORT = port;
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	}
	
	@Override
	public void userLogin(String username, String password) {
		UserTranslator user = new UserTranslator(username, password);
		String json = user.translate();

	}
	
	private Object post(String urlPath, String json) throws ServerException{
		try{
			URL url = new URL(URL_PREFIX + urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.connect();
			//add cookie to all of the subsequent server calls
			//send object receive response (only to get cookie on login and id on join game)
		}catch(Exception e){
			throw new ServerException(String.format("doPost failed: %s", e.getMessage()), e);
		}
		return null;
	}
	
	//add a get method like the one from ClientCommunicator for 240
	//returns a byte array, turn into an object, then pass that object to the translator

	@Override
	public void userRegister(String username, String password) {
		UserTranslator user = new UserTranslator(username, password);
		String json = user.translate();
		
	}

	@Override
	public void listGames() {
		

	}

	@Override
	public void createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts) {
		GamesCreateTranslator create = new GamesCreateTranslator(randomTiles, randomNumbers, randomPorts, name);
		String json = create.translate();

	}

	@Override
	public void joinGame(int playerID, String color) {
		GamesJoinTranslator join = new GamesJoinTranslator(playerID, color);
		String json = join.translate();

	}

	@Override
	public GameModel getModel() {
		return null;
		// TODO Auto-generated method stub

	}

	@Override
	public void addAI(String AIname) {
		GameAddAITranslator ai = new GameAddAITranslator(AIname);
		String json = ai.translate();

	}

	@Override
	public void listAIs() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendChat(int playerID, String content) {
		MovesSendChatTranslator chat = new MovesSendChatTranslator(playerID, content);
		String json = chat.translate();

	}

	@Override
	public void rollNumber(int playerID, int numberRolled) {
		MovesRollNumberTranslator roll = new MovesRollNumberTranslator(playerID, numberRolled);
		String json = roll.translate();

	}

	@Override
	public void robPlayer(int playerID, int victimID, HexLocation newRobberLocation) {
		MovesRobPlayerTranslator rob = new MovesRobPlayerTranslator(playerID, victimID, newRobberLocation);
		String json = rob.translate();

	}

	@Override
	public void finishTurn(int playerID) {
		MovesFinishTurnTranslator finish = new MovesFinishTurnTranslator(playerID);
		String json = finish.translate();

	}

	@Override
	public void buyDevCard(int playerID) {
		MovesBuyDevCardTranslator dev = new MovesBuyDevCardTranslator(playerID);
		String json = dev.translate();

	}

	@Override
	public void yearOfPlenty(int playerID, ResourceType resource1, ResourceType resource2) {
		MovesYearOfPlentyTranslator year = new MovesYearOfPlentyTranslator(playerID, resource1, resource2);
		String json = year.translate();

	}

	@Override
	public void roadBuilding(int playerID, EdgeLocation roadLocation1, EdgeLocation roadLocation2) {
		MovesRoadBuildingTranslator roadBuilding = new MovesRoadBuildingTranslator(playerID, roadLocation1.getHexLoc(), roadLocation1.getDir(),
				roadLocation2.getHexLoc(), roadLocation2.getDir());
		String json = roadBuilding.translate();

	}

	@Override
	public void knight(int playerID, int victimID, HexLocation newRobberLocation) {
		MovesSoldierTranslator soldier = new MovesSoldierTranslator(playerID, victimID, newRobberLocation);
		String json = soldier.translate();

	}

	@Override
	public void monopoly(int playerID, ResourceType resource) {
		MovesMonopolyTranslator monopoly = new MovesMonopolyTranslator(playerID, resource);
		String json = monopoly.translate();
	}

	@Override
	public void monument(int playerID) {
		MovesMonumentTranslator monument = new MovesMonumentTranslator(playerID);
		String json = monument.translate();
		
	}

	@Override
	public void buildRoad(int playerID, EdgeLocation roadLocation, boolean setUp) {
		MovesBuildRoadTranslator road = new MovesBuildRoadTranslator(playerID, roadLocation.getHexLoc(), roadLocation.getDir(), setUp);
		String json = road.translate();

	}

	@Override
	public void buildSettlment(int playerID, VertexLocation vertexLocation, boolean setUp) {
		MovesBuildSettlementTranslator settlement = new MovesBuildSettlementTranslator(playerID, vertexLocation.getHexLoc(), 
				vertexLocation.getDir(), setUp);
		String json = settlement.translate();
		
	}

	@Override
	public void buildCity(int playerID, VertexLocation vertexLocation) {
		MovesBuildCityTranslator city = new MovesBuildCityTranslator(playerID, vertexLocation.getHexLoc(), vertexLocation.getDir());
		String json = city.translate();

	}

	@Override
	public void acceptTrade(int playerID, boolean accept) {
		MovesAcceptTradeTranslator acceptTrade = new MovesAcceptTradeTranslator(playerID, accept);
		String json = acceptTrade.translate();

	}

	@Override
	public void maritimeTrade(int playerID, int ratio, ResourceType inputResource, ResourceType outputResource) {
		MovesMaritimeTradeTranslator maritime = new MovesMaritimeTradeTranslator(playerID, ratio, inputResource, outputResource);
		String json = maritime.translate();

	}

	@Override
	public void discardCards(int playerID, ArrayList<ResourceType> resources) {
		MovesDiscardCardsTranslator discard = new MovesDiscardCardsTranslator(playerID, resources);
		String json = discard.translate();

	}

	@Override
	public void offerTrade(int playerID, ArrayList<ResourceType> resourceGive, ArrayList<ResourceType> resourceReceive,
			int receiverID) {
		MovesOfferTradeTranslator offer = new MovesOfferTradeTranslator(playerID, resourceGive, resourceReceive, receiverID);
		String json = offer.translate();
		
	}

}
