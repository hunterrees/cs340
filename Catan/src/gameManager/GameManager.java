package gameManager;

//THIS is a test to see if a committ works
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
	 * Singleton class that controls calls to the model and server
	 */
	public GameManager MANAGER = new GameManager();
	private GameModel model;
	private ServerInterface server;
	
	private GameManager(){
	}
	
	public GameModel getModel() {
		return model;
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
	/**
	 * Updates game model based on new info from Game
	 * @param model New model to update the game
	 */
	public void setGameModel(GameModel model){
		this.model = model;
	}{}
	/**
	 * Validates users credentials and logs them into the Game
	 * @param username
	 * @param password
	 */
	public void userLogin(String username, String password)throws GameException{}
	/**
	 * Creates new player account and logs them into the Game
	 * @param username
	 * @param password
	 */
	public void userRegister(String username, String password)throws GameException{}
	/**
	 * Lists all the games in progress
	 */
	public void listGames()throws GameException{}
	/**
	 * Creates a new game based on given rules
	 * @param name Name of Game
	 * @param randomTiles Random set up of tiles
	 * @param randomNumbers Random numbering of tiles
	 * @param randomPorts Random placement of ports
	 */
	public void createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts)throws GameException{}
	/**
	 * 	Adds a player to game
	 * @param gameID ID of game to join
	 * @param color Desired color of player
	 */
	public void joinGame(int gameID, String color)throws GameException{}
	/**
	 * Gets the model from the Game, compares to versionID
	 * @param versionID Current versionID of the client
	 */
	public GameModel getModel(int versionID)throws GameException{return null;}
	/**
	 * Adds a new AI to the game
	 * @param AIname Name of AI to add
	 */
	public void addAI(String AIname)throws GameException{}
	/**
	 * Lists the possible AIs to add
	 */
	public void listAIs()throws GameException{}
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
	public void rollNumber(int playerID, int numberRolled)throws GameException{}
	/**
	 * Robs a player and moves robber
	 * @param playerID Player robbing
	 * @param victimID Player being robbed
	 * @param newRobberLocation New location of the robber piece
	 */
	public void robPlayer(int playerID, int victimID, HexLocation newRobberLocation)throws GameException{}
	/**
	 * Ends the turn of the player
	 * @param playerID ID of player finished their turn
	 */
	public void finishTurn(int playerID)throws GameException{}
	/**
	 * Adds a development card to given player
	 * @param playerID ID of player buying card
	 */
	public void buyDevCard(int playerID)throws GameException{}
	/**
	 * Plays a year of plenty card, adds resource to Player
	 * @param playerID ID of player playing the card
	 * @param resource1 First resource to receive
	 * @param resource2 Second resource to receive
	 */
	public void yearOfPlenty(int playerID, ResourceType resource1, ResourceType resource2)throws GameException{}
	/**
	 * Plays a road building card, places two roads at given edges
	 * @param playerID ID of player playing the card
	 * @param roadLocation1 First location to build road
	 * @param roadLocation2 Second location to build road
	 */
	public void roadBuilding(int playerID, EdgeLocation roadLocation1, EdgeLocation roadLocation2)throws GameException{}
	/**
	 * Plays a knight card, basically robbing player
	 * @param playerID Player robbing
	 * @param victimID Player bing robbed
	 * @param newRobberLocation New location of robber piece
	 */
	public void knight(int playerID, int victimID, HexLocation newRobberLocation)throws GameException{}
	/**
	 * Plays a monopoly card, gives player all of the given resource in play
	 * @param playerID ID of player playing the card
	 * @param resource Resource to monopolize
	 */
	public void monopoly(int playerID, ResourceType resource)throws GameException{}
	/**
	 * Plays a monument card, gives player a victory point
	 * @param playerID ID of player playing the card
	 */
	public void monument(int playerID)throws GameException{}
	/**
	 * Builds a road at given location, won't change resources if during setUp
	 * @param playerID ID of player building road
	 * @param roadLocation Location to build road
	 * @param setUp True if road is being built during initial set up
	 */
	public void buildRoad(int playerID, EdgeLocation roadLocation, boolean setUp)throws GameException{}
	/**
	 * Builds a settlement at given locaiton, won't change resources if during setUp
	 * @param playerID ID of player building settlement
	 * @param vertexLocation Location to build settlement
	 * @param setUp True if settlement is being built during initial set up
	 */
	public void buildSettlment(int playerID, VertexLocation vertexLocation, boolean setUp)throws GameException{}
	/**
	 * Builds a city at given locaiton
	 * @param playerID ID of player building city
	 * @param vertexLocation Location to build city
	 */
	public void buildCity(int playerID, VertexLocation vertexLocation)throws GameException{}
	/**
	 * Sends a trade offer from one player to another
	 * @param playerID ID of player sending trade
	 * @param resourceGive resouces offered
	 * @param resourceReceive resources to get from receiver
	 * @param receiverID ID of player being offered trade
	 */
	public void offerTrade(int playerID, ArrayList<ResourceType> resourceGive,  ArrayList<ResourceType> resourceReceive, int receiverID)throws GameException{}
	/**
	 * Used to accept or reject a trade offer
	 * @param playerID ID of player accepting or rejecting trade
	 * @param accept True if player accepted trade
	 */
	public void acceptTrade(int playerID, boolean accept)throws GameException{}
	/**
	 * Used to execute trades involving ports
	 * @param playerID ID of player trading
	 * @param ratio Ratio of trade(ie for 3:1 the value of ratio would be 3)
	 * @param inputResource Resource player gives
	 * @param outputResource Resource player receives
	 */
	public void maritimeTrade(int playerID, int ratio, ResourceType inputResource, ResourceType outputResource)throws GameException{}
	/**
	 * Used to discard cards
	 * @param playerID ID of player discarding cards
	 * @param resources Resources to discard
	 */
	public void discardCards(int playerID, ArrayList<ResourceType> resources)throws GameException{}
	/**
	 * Checks if you can build a settlement
	 * @param playerID ID of player
	 * @param location Location to build
	 * @param setUp True if during set-up
	 * @throws GameException Throws when you can't build
	 */
	public void canBuildSettlement(int playerID, VertexLocation location, boolean setUp)throws GameException{}
	/**
	 * Checks if you can build a city
	 * @param playerID ID of player
	 * @param location Location to build
	 * @throws GameException Throws when you can't build
	 */
	public void canBuildCity(int playerID, VertexLocation location) throws GameException{}
	/**
	 * Checks if you can build a road
	 * @param playerID ID of player
	 * @param location Location to build
	 * @param setUp True if set-up
	 * @throws GameException Throws when you can't build
	 */
	public void canBuildRoad(int playerID, EdgeLocation location, boolean setUp) throws GameException{}
}
