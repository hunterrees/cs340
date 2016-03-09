package client.gameManager;

import java.util.ArrayList;

import client.data.PlayerInfo;
import client.server.ServerException;
import client.server.ServerInterface;
import client.server.ServerPoller;
import client.server.ServerProxy;
import client.translators.games.GamesListGamesTranslator;
import shared.definitions.CatanColor;
import shared.definitions.GameState;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.GameException;
import shared.model.GameModel;

import java.util.Observable;

import client.data.GameInfo;

public class GameManager extends Observable{
	/**
	 * Controls calls to the model and server
	 */
	private GameModel model;
	private ServerInterface server;
	private static GameManager manager = null;
	private PlayerInfo playerInfo;
	private boolean startingUp;
	private ServerPoller poller = null;
	private boolean gameEnd;
	private int gameID;

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	protected GameManager(){
		playerInfo = new PlayerInfo();
	}
	
	protected GameManager(GameModel model, ServerInterface server){
		this.model = model;
		this.server = server;
	}

	public static GameManager getInstance(){
		if(manager == null){
			manager = new GameManager();
			manager.startingUp = true;
			manager.gameEnd = false;
		}
		return manager;
	}
	public synchronized void setModel(GameModel model) {
		this.model = model;
		this.setChanged();
		try{
			notifyObservers();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void startPoller(){
		poller = new ServerPoller(server, this);
		gameEnd = false;
	}
	
	public void stopPoller(){
		model = null;
		gameEnd = true;
		poller.stop();
	}
	
	public void gameEndNofity(){
		this.setChanged();
		try{
			notifyObservers();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public boolean isGameEnd() {
		return gameEnd;
	}

	public void setGameEnd(boolean gameEnd) {
		this.gameEnd = gameEnd;
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
	public synchronized void setGameModel(GameModel model){
		this.model = model;
		this.setChanged();
		try{
			notifyObservers();
		}catch(Exception e){
			e.printStackTrace();
		}
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
				throw new GameException(e.getMessage());
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
				throw new GameException(e.getMessage());
			}
		}
	}
	/**
	 * Lists all the games in progress
	 */
	public GameInfo[] listGames()throws GameException{
		try{
			String json = server.listGames();
			GamesListGamesTranslator list = new GamesListGamesTranslator(json);
			return list.getGameInfo();
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
	public String createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts)throws GameException{
		if(name != null){
			try{
				return server.createGame(name, randomTiles, randomNumbers, randomPorts);
			}catch(ServerException e){
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 	Adds a player to game
	 * @param gameID ID of game to join
	 * @param color Desired color of player
	 */
	public void joinGame(int gameID, String color)throws GameException{
		try{
			startingUp = true;
			server.joinGame(gameID, color);
			GameModel newModel = server.getModel(-1);
			setModel(newModel);
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
			if(startingUp){
				return server.getModel(-1);
			}
			return server.getModel(model.getVersion());
		}catch(ServerException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void waitingModel()throws GameException{
		try{
			GameModel newModel = server.getModel(-1);
			setModel(newModel);
		}catch(ServerException e){
			e.printStackTrace();
		}
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
	public String[] listAIs()throws GameException{
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
			GameModel newModel = server.sendChat(playerID, content);
			setModel(newModel);
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
				GameModel newModel = server.rollNumber(playerID, numberRolled);
				setModel(newModel);
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
				GameModel newModel = server.robPlayer(playerID, victimID, newRobberLocation);
				setModel(newModel);
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
				GameModel newModel = server.finishTurn(playerID);
				setModel(newModel);
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
				GameModel newModel = server.buyDevCard(playerID);
				setModel(newModel);
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
				GameModel newModel = server.yearOfPlenty(playerID, resource1, resource2);
				setModel(newModel);
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
				GameModel newModel = server.roadBuilding(playerID, roadLocation1, roadLocation2);
				setModel(newModel);
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
				GameModel newModel = server.knight(playerID, victimID, newRobberLocation);
				setModel(newModel);
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
				GameModel newModel = server.monopoly(playerID, resource);
				setModel(newModel);
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
				GameModel newModel = server.monument(playerID);
				setModel(newModel);
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
			try{
				GameModel newModel = server.buildRoad(playerID, roadLocation, setUp);
				setModel(newModel);
			}catch(ServerException e){
				e.printStackTrace();
			}

	}
	/**
	 * Builds a settlement at given location, won't change resources if during setUp
	 * @param playerID ID of player building settlement
	 * @param vertexLocation Location to build settlement
	 * @param setUp True if settlement is being built during initial set up
	 */
	public void buildSettlment(int playerID, VertexLocation vertexLocation, boolean setUp)throws GameException{
		/*if(model.canBuildSettlement(playerID, setUp, vertexLocation)){
			try{
				GameModel newModel = server.buildSettlment(playerID, vertexLocation, setUp);
				setModel(newModel);
			}catch(ServerException e){
				e.printStackTrace();
			}
		}else{
			throw new GameException();
		}*/
			try{
				GameModel newModel = server.buildSettlment(playerID, vertexLocation, setUp);
				setModel(newModel);
			}catch(ServerException e){
				e.printStackTrace();
			}
		
	}
	/**
	 * Builds a city at given location
	 * @param playerID ID of player building city
	 * @param vertexLocation Location to build city
	 */
	public void buildCity(int playerID, VertexLocation vertexLocation)throws GameException{
			try{
				GameModel newModel = server.buildCity(playerID, vertexLocation);
				setModel(newModel);
			}catch(ServerException e){
				throw new GameException();
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
		if(model.offerTrade(playerID, resourceReceive)){
			try{
				GameModel newModel = server.offerTrade(playerID, resourceGive, resourceReceive, receiverID);
				setModel(newModel);
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
				GameModel newModel = server.acceptTrade(playerID, accept);
				setModel(newModel);
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
				GameModel newModel = server.maritimeTrade(playerID, ratio, inputResource, outputResource);
				setModel(newModel);
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
				GameModel newModel = server.discardCards(playerID, resources);
				setModel(newModel);
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

	public boolean isMyTurn() {
		if(playerInfo.getPlayerIndex() == model.getCurrentPlayerIndex()) {
			return true;
		} else {
			return false;
		}
	}

	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}

	public CatanColor getColor(int playerIndex){
		return model.getColor(playerIndex);
	}
	public String getPlayerName(int playerIndex){
		return model.getName(playerIndex);
	}
	public GameState getGameState()
	{
		return model.getGameState();
	}
	public int getCurrentPlayerIndex()
	{
		return model.getCurrentPlayerIndex();
	}


	public void setPlayerInfo(PlayerInfo playerInfo) {
		this.playerInfo = playerInfo;
	}

	public boolean isStartingUp() {
		return startingUp;
	}

	public void setStartingUp(boolean startingUp) {
		this.startingUp = startingUp;
	}
}
