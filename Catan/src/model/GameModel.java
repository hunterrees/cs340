package model;

import java.util.ArrayList;

import map.Map;
import player.Player;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public class GameModel {
	
	private int version;
	private Map map;
	private Bank bank;
	private ArrayList<Player> players;
	
	
// Construtor
	/**
	 * The only constructor in this class
	 */
	public GameModel() {
		
	}
	
// Public methods
	
	/**
	 * Plays the game
	 */
	public void playGame() {
		
	}
	
	/**
	 * Returns true if building a settlement is valid
	 * @param playerId
	 * @param loc
	 * @return
	 */
	public boolean canBuildSettlement(int playerId, VertexLocation loc, boolean setup){
		return false;
	}
	
	/**
	 * Returns true if building a city is valid
	 * @param playerId
	 * @param loc
	 * @return
	 */
	public boolean canBuildCity(int playerId, VertexLocation loc) {
		return false;
	}
	
	/**
	 * Returns true if building a road is valid
	 * @param playerId
	 * @param loc
	 * @return
	 */
	public boolean canBuildRoad(int playerId, EdgeLocation loc) {
		return false;
	}
	
	/**
	 * Returns true if trading with another player is valid
	 * @param playerId
	 * @param playerId
	 * @return
	 */
	public boolean canMaritimeTrade(int playerId, int playerId2) {
		return false;
	}
	
	/**
	 * Returns true if trading with the bank is valid
	 * @param playerId
	 * @return
	 */
	public boolean canDomesticTrade(int playerId) {
		return false;
	}
	
	
// Private methods	
	/**
	 * Runs the beginning setup of the game
	 */
	private void startSetUp() {
		
	}
	
	/**
	 * Player take's his/her turn
	 * @param playerId
	 */
	private void takeTurn(int playerId) {
		
	}
	
	/**
	 * Incriments the turn
	 */
	private void updateTurn() {
		version ++;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}


	
// Getters and setters
	/**
	 * @return the map
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map map) {
		this.map = map;
	}

	/**
	 * @return the bank
	 */
	public Bank getBank() {
		return bank;
	}

	/**
	 * @param bank the bank to set
	 */
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
	/**
	 * @return the players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	
// Server methods
	/**
	 * Rolls the dice
	 * @param playerID
	 * @param numberRolled
	 * @
	 */
	public void rollNumber(int playerID, int numberRolled) {
		
	}
	
	/**
	 * Robs a player and moves robber
	 * @param playerID Player robbing
	 * @param victimID Player being robbed
	 * @param newRobberLocation New location of the robber piece
	 */
	public void robPlayer(int playerID, int victimID, HexLocation newRobberLocation) {
		
	}
	
	/**
	 * Ends the turn of the player
	 * @param playerID ID of player finished their turn
	 */
	public void finishTurn(int playerID) {
		
	}
	
	/**
	 * Adds a development card to given player
	 * @param playerID ID of player buying card
	 */
	public void buyDevCard(int playerID) {
		
	}
	
	/**
	 * Plays a year of plenty card, adds resource to Player
	 * @param playerID ID of player playing the card
	 * @param resource1 First resource to receive
	 * @param resource2 Second resource to receive
	 */
	public void yearOfPlenty(int playerID, ResourceType resource1, ResourceType resource2) {
		
	}
	
	/**
	 * Plays a road building card, places two roads at given edges
	 * @param playerID ID of player playing the card
	 * @param roadLocation1 First location to build road
	 * @param roadLocation2 Second location to build road
	 */
	public void roadBuilding(int playerID, EdgeLocation roadLocation1, EdgeLocation roadLocation2) {
		
	}
	
	/**
	 * Plays a knight card, basically robbing player
	 * @param playerID Player robbing
	 * @param victimID Player bing robbed
	 * @param newRobberLocation New location of robber piece
	 */
	public void knight(int playerID, int victimID, HexLocation newRobberLocation) {
		
	}
	
	/**
	 * Plays a monopoly card, gives player all of the given resource in play
	 * @param playerID ID of player playing the card
	 * @param resource Resource to monopolize
	 */
	public void monopoly(int playerID, ResourceType resource) {
		
	}
	
	/**
	 * Plays a monument card, gives player a victory point
	 * @param playerID ID of player playing the card
	 */
	public void monument(int playerID) {
		
	}
	
	/**
	 * Builds a road at given location, won't change resources if during setUp
	 * @param playerID ID of player building road
	 * @param roadLocation Location to build road
	 * @param setUp True if road is being built during initial set up
	 */
	public void buildRoad(int playerID, EdgeLocation roadLocation, boolean setUp) {
		
	}
	
	/**
	 * Builds a settlement at given locaiton, won't change resources if during setUp
	 * @param playerID ID of player building settlement
	 * @param vertexLocation Location to build settlement
	 * @param setUp True if settlement is being built during initial set up
	 */
	public void buildSettlment(int playerID, VertexLocation vertexLocation, boolean setUp) {
		
	}
	
	/**
	 * Builds a city at given locaiton
	 * @param playerID ID of player building city
	 * @param vertexLocation Location to build city
	 */
	public void buildCity(int playerID, VertexLocation vertexLocation) {
		
	}
	
	/**
	 * Sends a trade offer from one player to another
	 * @param playerID ID of player sending trade
	 * @param resourceGive resouces offered
	 * @param resourceReceive resources to get from receiver
	 * @param receiverID ID of player being offered trade
	 */
	public void offerTrade(int playerID, ArrayList<ResourceType> resourceGive,
			ArrayList<ResourceType> resourceReceive, 
			int receiverID) {
		
	}
	
	/**
	 * Used to accept or reject a trade offer
	 * @param playerID ID of player accepting or rejecting trade
	 * @param accept True if player accepted trade
	 */
	public void acceptTrade(int playerID, boolean accept) {
		
	}
	
	/**
	 * Used to execute trades involving ports
	 * @param playerID ID of player trading
	 * @param ratio Ratio of trade(ie for 3:1 the value of ratio would be 3)
	 * @param inputResource Resource player gives
	 * @param outputResource Resource player receives
	 */
	public void maritimeTrade(int playerID, int ratio, ResourceType inputResource, ResourceType outputResource) {
		
	}
	
}
