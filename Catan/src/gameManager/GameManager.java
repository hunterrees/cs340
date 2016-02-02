package gameManager;

import java.util.ArrayList;

import model.GameException;
import model.GameModel;
import server.ServerException;
import server.ServerInterface;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public class GameManager {
	/**
	 * Controls calls to the model and server
	 */
	private GameModel model;
	private ServerInterface server;
	
	public GameManager(){
	}
	
	public void setModel(GameModel model) {
		this.model = model;
	}
	public ServerInterface getServer() {
		return server;
	}
	public void setServer(ServerInterface server) {
		this.server = server;
	}
	public GameModel getModel(){
		return model;
	}
	/**
	 * Updates game model based on new info from Game
	 * @param model New model to update the game
	 */
	public void setGameModel(GameModel model){
		this.model = model;
	}
	/**
	 * Validates users credentials and logs them into the Game
	 * @param username
	 * @param password
	 */
	public void userLogin(String username, String password)throws GameException{
		if(username != null && password != null){
			try{
				server.userLogin(username, password);
			}catch(ServerException e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * Creates new player account and logs them into the Game
	 * @param username
	 * @param password
	 */
	public void userRegister(String username, String password)throws GameException{
		if(username != null && password != null){
			try{
				server.userRegister(username, password);
			}catch(ServerException e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * Lists all the games in progress
	 */
	public String listGames()throws GameException{
		try{
			return server.listGames();
		}catch(ServerException e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Creates a new game based on given rules
	 * @param name Name of Game
	 * @param randomTiles Random set up of tiles
	 * @param randomNumbers Random numbering of tiles
	 * @param randomPorts Random placement of ports
	 */
	public void createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts)throws GameException{
		if(name != null){
			try{
				server.createGame(name, randomTiles, randomNumbers, randomPorts);
			}catch(ServerException e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * 	Adds a player to game
	 * @param gameID ID of game to join
	 * @param color Desired color of player
	 */
	public void joinGame(int gameID, String color)throws GameException{
		try{
			server.joinGame(gameID, color);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}
	/**
	 * Gets the model from the Game, compares to versionID
	 * @param versionID Current versionID of the client
	 */
	public GameModel getNewModel()throws GameException{
		try{
			return server.getModel(model.getVersion());
		}catch(ServerException e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Adds a new AI to the game
	 * @param AIname Name of AI to add
	 */
	public void addAI(String AIname)throws GameException{
		try{
			server.addAI(AIname);
		}catch(ServerException e){
			e.printStackTrace();
		}
	}
	/**
	 * Lists the possible AIs to add
	 */
	public String listAIs()throws GameException{
		try{
			return server.listAIs();
		}catch(ServerException e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Sends chat message
	 * @param playerID ID of player sending the message
	 * @param content Content of the message
	 */
	public void sendChat(int playerID, String content)throws GameException{
		try {
			server.sendChat(playerID, content);
		} catch (ServerException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Rolls the dice at the beginning of the turn
	 * @param playerID ID of player rolling
	 * @param numberRolled Number rolled
	 */
	public void rollNumber(int playerID, int numberRolled)throws GameException{
		if(model.rollNumber(playerID)){
			try{
				server.rollNumber(playerID, numberRolled);
			}catch(ServerException e){
				e.printStackTrace();
			}
		}else{
			throw new GameException("Not your turn to roll");
		}
	}
	/**
	 * Robs a player and moves robber
	 * @param playerID Player robbing
	 * @param victimID Player being robbed
	 * @param newRobberLocation New location of the robber piece
	 */
	public void robPlayer(int playerID, int victimID, HexLocation newRobberLocation)throws GameException{
		if(model.robPlayer(newRobberLocation, victimID)){
			try{
				server.robPlayer(playerID, victimID, newRobberLocation);
			}catch(ServerException e){
				e.printStackTrace();
			}
		}else{
			throw new GameException();
		}
	}
	/**
	 * Ends the turn of the player
	 * @param playerID ID of player finished their turn
	 */
	public void finishTurn(int playerID)throws GameException{
		if(model.finishTurn(playerID)){
			try{
				server.finishTurn(playerID);
			}catch(ServerException e){
				e.printStackTrace();
			}
		}else{
			throw new GameException();
		}
	}
	/**
	 * Adds a development card to given player
	 * @param playerID ID of player buying card
	 */
	public void buyDevCard(int playerID)throws GameException{
		if(model.buyDevCard(playerID)){
			try{
				server.buyDevCard(playerID);
			}catch(ServerException e){
				e.printStackTrace();
			}
		}else{
			throw new GameException();
		}
	}
	/**
	 * Plays a year of plenty card, adds resource to Player
	 * @param playerID ID of player playing the card
	 * @param resource1 First resource to receive
	 * @param resource2 Second resource to receive
	 */
	public void yearOfPlenty(int playerID, ResourceType resource1, ResourceType resource2)throws GameException{
		if(model.yearOfPlenty(playerID, resource1, resource2)){
			try{
				server.yearOfPlenty(playerID, resource1, resource2);
			}catch(ServerException e){
				e.printStackTrace();
			}
		}else{
			throw new GameException();
		}
	}
	/**
	 * Plays a road building card, places two roads at given edges
	 * @param playerID ID of player playing the card
	 * @param roadLocation1 First location to build road
	 * @param roadLocation2 Second location to build road
	 */
	public void roadBuilding(int playerID, EdgeLocation roadLocation1, EdgeLocation roadLocation2)throws GameException{
		if(model.roadBuilding(playerID, roadLocation1, roadLocation2)){
			try{
				server.roadBuilding(playerID, roadLocation1, roadLocation2);
			}catch(ServerException e){
				e.printStackTrace();
			}
		}else{
			throw new GameException();
		}
	}
	/**
	 * Plays a knight card, basically robbing player
	 * @param playerID Player robbing
	 * @param victimID Player bing robbed
	 * @param newRobberLocation New location of robber piece
	 */
	public void knight(int playerID, int victimID, HexLocation newRobberLocation)throws GameException{
		if(model.soldier(playerID, newRobberLocation,victimID)){
			try{
				server.knight(playerID, victimID, newRobberLocation);
			}catch(ServerException e){
				e.printStackTrace();
			}
		}else{
			throw new GameException();
		}
	}
	/**
	 * Plays a monopoly card, gives player all of the given resource in play
	 * @param playerID ID of player playing the card
	 * @param resource Resource to monopolize
	 */
	public void monopoly(int playerID, ResourceType resource)throws GameException{
		if(model.monopoly(playerID)){
			try{
				server.monopoly(playerID, resource);
			}catch(ServerException e){
				e.printStackTrace();
			}
		}else{
			throw new GameException();
		}
	}
	/**
	 * Plays a monument card, gives player a victory point
	 * @param playerID ID of player playing the card
	 */
	public void monument(int playerID)throws GameException{
		if(model.monument(playerID)){
			try{
				server.monument(playerID);
			}catch(ServerException e){
				e.printStackTrace();
			}
		}else{
			throw new GameException();
		}
	}
	/**
	 * Builds a road at given location, won't change resources if during setUp
	 * @param playerID ID of player building road
	 * @param roadLocation Location to build road
	 * @param setUp True if road is being built during initial set up
	 */
	public void buildRoad(int playerID, EdgeLocation roadLocation, boolean setUp)throws GameException{
		if(model.canBuildRoad(playerID, setUp, roadLocation)){
			try{
				server.buildRoad(playerID, roadLocation, setUp);
			}catch(ServerException e){
				e.printStackTrace();
			}
		}else{
			throw new GameException();
		}
	}
	/**
	 * Builds a settlement at given location, won't change resources if during setUp
	 * @param playerID ID of player building settlement
	 * @param vertexLocation Location to build settlement
	 * @param setUp True if settlement is being built during initial set up
	 */
	public void buildSettlment(int playerID, VertexLocation vertexLocation, boolean setUp)throws GameException{
		if(model.canBuildSettlement(playerID, setUp, vertexLocation)){
			try{
				server.buildSettlment(playerID, vertexLocation, setUp);
			}catch(ServerException e){
				e.printStackTrace();
			}
		}else{
			throw new GameException();
		}
	}
	/**
	 * Builds a city at given locaiton
	 * @param playerID ID of player building city
	 * @param vertexLocation Location to build city
	 */
	public void buildCity(int playerID, VertexLocation vertexLocation)throws GameException{
		if(model.canBuildCity(playerID, vertexLocation)){
			try{
				server.buildCity(playerID, vertexLocation);
			}catch(ServerException e){
				throw new GameException();
			}
		}
	}
	/**
	 * Sends a trade offer from one player to another
	 * @param playerID ID of player sending trade
	 * @param resourceGive resouces offered
	 * @param resourceReceive resources to get from receiver
	 * @param receiverID ID of player being offered trade
	 */
	public void offerTrade(int playerID, ArrayList<ResourceType> resourceGive,  ArrayList<ResourceType> resourceReceive, int receiverID)throws GameException{
		if(model.offerTrade(playerID, resourceGive)){
			try{
				server.offerTrade(playerID, resourceGive, resourceReceive, receiverID);
			}catch(ServerException e){
				throw new GameException();
			}
		}
	}
	/**
	 * Used to accept or reject a trade offer
	 * @param playerID ID of player accepting or rejecting trade
	 * @param accept True if player accepted trade
	 */
	public void acceptTrade(int playerID, boolean accept)throws GameException{
		if(!accept || model.acceptTrade(playerID)){
			try{
				server.acceptTrade(playerID, accept);
			}catch(ServerException e){
				e.printStackTrace();
			}
		}else{
			throw new GameException("Doesn't have the necessary resources");
		}
	}
	/**
	 * Used to execute trades involving ports
	 * @param playerID ID of player trading
	 * @param inputResource Resource player gives
	 * @param outputResource Resource player receives
	 */
	public void maritimeTrade(int playerID, ResourceType inputResource, ResourceType outputResource)throws GameException{
		int ratio = model.maritimeTrade(playerID, inputResource);
		if(ratio != -1){
			try{
				server.maritimeTrade(playerID, ratio, inputResource, outputResource);
			}catch(ServerException e){
				e.printStackTrace();
			}
		}else{
			throw new GameException();
		}
	}

	/**
	 * Used to discard cards
	 * @param playerID ID of player discarding cards
	 * @param resources Resources to discard
	 */
	public void discardCards(int playerID, ArrayList<ResourceType> resources)throws GameException{
		if(model.discardCards(playerID, resources)){
			try{
				server.discardCards(playerID, resources);
			}catch(ServerException e){
				e.printStackTrace();
			}
		}else{
			throw new GameException();
		}
	}
	/**
	 * Checks if you can build a settlement
	 * @param playerID ID of player
	 * @param location Location to build
	 * @param setUp True if during set-up
	 * @throws GameException Throws when you can't build
	 */
	public boolean canBuildSettlement(int playerID, VertexLocation location, boolean setUp){
		return model.canBuildSettlement(playerID, setUp, location);
	}
	/**
	 * Checks if you can build a city
	 * @param playerID ID of player
	 * @param location Location to build
	 * @throws GameException Throws when you can't build
	 */
	public boolean canBuildCity(int playerID, VertexLocation location){
		return model.canBuildCity(playerID, location);
	}
	/**
	 * Checks if you can build a road
	 * @param playerID ID of player
	 * @param location Location to build
	 * @param setUp True if set-up
	 * @throws GameException Throws when you can't build
	 */
	public boolean canBuildRoad(int playerID, EdgeLocation location, boolean setUp){
		return model.canBuildRoad(playerID, setUp, location);
	}
}
