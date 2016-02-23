package server;
import java.util.ArrayList;

import model.GameModel;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
/**
 * 
 * Server Interface is implemented by Server Proxy and MockServerProxy in order to interact with actual Server
 * on each method the ServerInterface will call the Translator to serialize components into JSON to send to server
 */
public interface ServerInterface {
	/**
	 * Validates users credentials and logs them into the server
	 * @param username
	 * @param password
	 */
	public void userLogin(String username, String password)throws ServerException;
	/**
	 * Creates new player account and logs them into the server
	 * @param username
	 * @param password
	 */
	public void userRegister(String username, String password)throws ServerException;
	/**
	 * Lists all the games in progress
	 */
	public String listGames()throws ServerException;
	/**
	 * Creates a new game based on given rules
	 * @param name Name of Game
	 * @param randomTiles Random set up of tiles
	 * @param randomNumbers Random numbering of tiles
	 * @param randomPorts Random placement of ports
	 */
	public void createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts)throws ServerException;
	/**
	 * 	Adds a player to game
	 * @param gameID ID of game to join
	 * @param color Desired color of player
	 */
	public void joinGame(int gameID, String color)throws ServerException;
	/**
	 * Gets the model from the server, compares to versionID
	 * @param versionID Current versionID of the client
	 */
	public GameModel getModel(int versionID)throws ServerException;
	/**
	 * Adds a new AI to the game
	 * @param AIname Name of AI to add
	 */
	public void addAI(String AIname)throws ServerException;
	/**
	 * Lists the possible AIs to add
	 */
	public String[] listAIs()throws ServerException;
	/**
	 * Sends chat message
	 * @param playerID ID of player sending the message
	 * @param content Content of the message
	 */
	public GameModel sendChat(int playerID, String content)throws ServerException;
	/**
	 * Rolls the dice at the beginning of the turn
	 * @param playerID ID of player rolling
	 * @param numberRolled Number rolled
	 */
	public GameModel rollNumber(int playerID, int numberRolled)throws ServerException;
	/**
	 * Robs a player and moves robber
	 * @param playerID Player robbing
	 * @param victimID Player being robbed
	 * @param newRobberLocation New location of the robber piece
	 */
	public GameModel robPlayer(int playerID, int victimID, HexLocation newRobberLocation)throws ServerException;
	/**
	 * Ends the turn of the player
	 * @param playerID ID of player finished their turn
	 */
	public GameModel finishTurn(int playerID)throws ServerException;
	/**
	 * Adds a development card to given player
	 * @param playerID ID of player buying card
	 */
	public GameModel buyDevCard(int playerID)throws ServerException;
	/**
	 * Plays a year of plenty card, adds resource to Player
	 * @param playerID ID of player playing the card
	 * @param resource1 First resource to receive
	 * @param resource2 Second resource to receive
	 */
	public GameModel yearOfPlenty(int playerID, ResourceType resource1, ResourceType resource2)throws ServerException;
	/**
	 * Plays a road building card, places two roads at given edges
	 * @param playerID ID of player playing the card
	 * @param roadLocation1 First location to build road
	 * @param roadLocation2 Second location to build road
	 */
	public GameModel roadBuilding(int playerID, EdgeLocation roadLocation1, EdgeLocation roadLocation2)throws ServerException;
	/**
	 * Plays a knight card, basically robbing player
	 * @param playerID Player robbing
	 * @param victimID Player bing robbed
	 * @param newRobberLocation New location of robber piece
	 */
	public GameModel knight(int playerID, int victimID, HexLocation newRobberLocation)throws ServerException;
	/**
	 * Plays a monopoly card, gives player all of the given resource in play
	 * @param playerID ID of player playing the card
	 * @param resource Resource to monopolize
	 */
	public GameModel monopoly(int playerID, ResourceType resource)throws ServerException;
	/**
	 * Plays a monument card, gives player a victory point
	 * @param playerID ID of player playing the card
	 */
	public GameModel monument(int playerID)throws ServerException;
	/**
	 * Builds a road at given location, won't change resources if during setUp
	 * @param playerID ID of player building road
	 * @param roadLocation Location to build road
	 * @param setUp True if road is being built during initial set up
	 */
	public GameModel buildRoad(int playerID, EdgeLocation roadLocation, boolean setUp)throws ServerException;
	/**
	 * Builds a settlement at given locaiton, won't change resources if during setUp
	 * @param playerID ID of player building settlement
	 * @param vertexLocation Location to build settlement
	 * @param setUp True if settlement is being built during initial set up
	 */
	public GameModel buildSettlment(int playerID, VertexLocation vertexLocation, boolean setUp)throws ServerException;
	/**
	 * Builds a city at given locaiton
	 * @param playerID ID of player building city
	 * @param vertexLocation Location to build city
	 */
	public GameModel buildCity(int playerID, VertexLocation vertexLocation)throws ServerException;
	/**
	 * Sends a trade offer from one player to another
	 * @param playerID ID of player sending trade
	 * @param resourceGive resouces offered
	 * @param resourceReceive resources to get from receiver
	 * @param receiverID ID of player being offered trade
	 */
	public GameModel offerTrade(int playerID, ArrayList<ResourceType> resourceGive,  ArrayList<ResourceType> resourceReceive, int receiverID)throws ServerException;
	/**
	 * Used to accept or reject a trade offer
	 * @param playerID ID of player accepting or rejecting trade
	 * @param accept True if player accepted trade
	 */
	public GameModel acceptTrade(int playerID, boolean accept)throws ServerException;
	/**
	 * Used to execute trades involving ports
	 * @param playerID ID of player trading
	 * @param ratio Ratio of trade(ie for 3:1 the value of ratio would be 3)
	 * @param inputResource Resource player gives
	 * @param outputResource Resource player receives
	 */
	public GameModel maritimeTrade(int playerID, int ratio, ResourceType inputResource, ResourceType outputResource)throws ServerException;
	/**
	 * Used to discard cards
	 * @param playerID ID of player discarding cards
	 * @param resources Resources to discard
	 */
	public GameModel discardCards(int playerID, ArrayList<ResourceType> resources)throws ServerException;
};
