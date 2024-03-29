package shared.model;

import java.io.Serializable;
import java.util.ArrayList;

import shared.ResourceCard;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.GameState;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.map.Map;
import shared.model.player.Player;
import shared.model.trade.TradeOffer;

public class GameModel implements Serializable {
	private Map map;
	private Bank bank;
	private ArrayList<Player> players;
	private HexLocation robberLoc;
	private int version;
	private TradeOffer theTrade;
	private TurnTracker tracker;
	private Chat chat;
	private Log log;
	private int winner;
	private String title;

// Constructor
	public GameModel(Map map, Bank bank, ArrayList<Player> players, HexLocation robberLoc, TradeOffer theTrade,
						TurnTracker tracker, Log log, Chat chat, int winner) {
		this.tracker = tracker;
		this.log = log;
		this.chat = chat;
		this.winner = winner;
		this.map = map;
		this.robberLoc = robberLoc;
		this.theTrade = theTrade;
		this.bank = bank;
		this.players = players;
		this.version = 0;
	}

// Public methods

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	/**
	 *
	 * @param
	 * @return
     */
	public void checkVictory()
	{
		
		for (int i = 0; i < 4; i++)
		{
			if (players.get(i).getVictoryPoints() >= 10)
			{
				winner = i;
			}
		}

	}

	public boolean acceptTrade(int playerID) {
		Player p = players.get((playerID));

		if(theTrade == null){
			return false;
		}
		
		ArrayList<ResourceType> desired = theTrade.getResourceOffered();

		int wood = 0;
		int brick = 0;
		int sheep = 0;
		int wheat = 0;
		int ore = 0;

		for(ResourceType rt : desired) {
			switch(rt) {
				case WOOD: wood++; break;
				case BRICK: brick++; break;
				case SHEEP: sheep++; break;
				case WHEAT: wheat++; break;
				case ORE: ore++; break;
				default: System.out.println("Error! ResourceType doesn't exist!");
			}
		}	
		
		if(p.numResourceRemaining(ResourceType.WOOD) >= wood &&
				p.numResourceRemaining(ResourceType.BRICK) >= brick &&
				p.numResourceRemaining(ResourceType.SHEEP) >= sheep &&
				p.numResourceRemaining(ResourceType.WHEAT) >= wheat &&
				p.numResourceRemaining( ResourceType.ORE) >= ore) {
			return true;
		}
		return false;
	}

	/**
	 * Returns a boolean indicating whether or not you can discard the given cards
	 * @param toDiscard
	 * @return
     */
	public boolean discardCards(int playerID, ArrayList<ResourceType> toDiscard) {
		Player p = players.get((playerID));
		if(tracker.getGameStatus() != GameState.discarding) {
			return false;
		}

		ArrayList<ResourceCard> currentHand = players.get(playerID).getPlayerHand().getResourceCards();

		if(currentHand.size() <= 7) {
			return false;
		}

		int woodD = 0;
		int brickD = 0;
		int woolD = 0;
		int wheatD = 0;
		int oreD = 0;


		for(ResourceType rt : toDiscard) {
			switch(rt) {
				case WOOD: woodD++; break;
				case BRICK: brickD++; break;
				case SHEEP: woolD++; break;
				case WHEAT: wheatD++; break;
				case ORE: oreD++; break;
				default: System.out.println("Error! The resource type doesn't exist!");
			}
		}

		if(p.numResourceRemaining(ResourceType.WOOD) < woodD ||
				p.numResourceRemaining(ResourceType.BRICK) < brickD ||
				p.numResourceRemaining(ResourceType.SHEEP) < woolD ||
				p.numResourceRemaining(ResourceType.WHEAT) < wheatD ||
				p.numResourceRemaining( ResourceType.ORE) < oreD) {
			return false;
		}

		return true;
	}

	/**
	 *
	 * @param playerID
	 * @return
     */
	public boolean rollNumber(int playerID) {
		if(tracker.getCurrentTurnPlayerID() != playerID || tracker.getGameStatus() != GameState.rolling) {
			return false;
		}
		return true;
	}

	public TurnTracker getTracker() {
		return tracker;
	}

	public void setTracker(TurnTracker tracker) {
		this.tracker = tracker;
	}

	/**
	 * Returns a boolean whether or not the player can build a road at the given location
	 * @param free
	 * @param loc
     * @return
     */
	public boolean canBuildRoad(int playerID, boolean free, EdgeLocation loc) {
		if(!okToPlay(playerID)){
			return false;
		}

		boolean canBuild;
		if(map.canBuildRoad(playerID, free, loc)) {
			canBuild = true;
		} else {
			canBuild = false;
		}

		if(!canBuild) {
			return false;
		}

		if(!free  && ((tracker.getGameStatus() != GameState.firstRound) || (tracker.getGameStatus() != GameState.secondRound))) {
			if (!players.get(playerID).canBuildRoad()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns a boolean whether or not the player can build a road at the given location
	 * @param free
	 * @param loc
     * @return
     */
	public boolean canBuildSettlement(int playerID, boolean free, VertexLocation loc) {
		if(!okToPlay(playerID)) {
			return false;
		}
		boolean canBuild;
		if(map.canBuildSettlement(playerID, free, loc)) {
			canBuild = true;
		} else {
			canBuild = false;
		}

		if(!canBuild) {
			return false;
		}

		if(free) {
			if (!players.get(playerID).canBuildSettlement()) {
				return false;
			}
		}

		return true;
	}

	/**
	 *
	 * @param playerID
	 * @param loc
     * @return
     */
	public boolean canBuildCity(int playerID, VertexLocation loc) {
		if(!okToPlay(playerID)) {
			return false;
		}
		boolean canBuild;
		if(map.canBuildCity(playerID, loc)) {
			canBuild = true;
		} else {
			canBuild = false;
		}

		if(!canBuild) {
			return false;
		}


		if (!players.get(playerID).canBuildCity()) {
			return false;
		}


		return true;
	}

	public boolean offerTrade(int playerID, ArrayList<ResourceType> resourceTypes) {
		if (!okToPlay(playerID))
		{
			return false;
		}
		//Get how many of each resource being offered
		int wood = 0;
		int brick = 0;
		int sheep = 0;
		int wheat = 0;
		int ore = 0;

		for(ResourceType rt : resourceTypes) {
			switch(rt) {
				case WOOD: wood++; break;
				case BRICK: brick++; break;
				case SHEEP: sheep++; break;
				case WHEAT: wheat++; break;
				case ORE: ore++; break;
				default: System.out.println("Error! ResourceType doesn't exist!");
			}
		}	
		Player p = players.get(playerID);
		if(p.numResourceRemaining(ResourceType.WOOD) >= wood &&
				p.numResourceRemaining(ResourceType.BRICK) >= brick &&
				p.numResourceRemaining(ResourceType.SHEEP) >= sheep &&
				p.numResourceRemaining(ResourceType.WHEAT) >= wheat &&
				p.numResourceRemaining( ResourceType.ORE) >= ore) {
			return true;
		}
		return false;
	}

	public int maritimeTrade(int playerID, ResourceType inputType) {
		if(!okToPlay(playerID)){
			return -1;
		}
		return(players.get(playerID).maritimeTradeRatio(inputType));
	}


	/**
	 * Returns a boolean indicating whether robbing a player with givin location and victimID is valid
	 * @param loc
	 * @param victimID
     * @return
     */
	public boolean robPlayer(HexLocation loc, int victimID) {
		if(robberLoc == loc) {
			return false;
		}
		if (map.getHexes().get(loc).getType() == HexType.WATER)
		{
			return false;
		}
		if(victimID != -1) {
			if(players.get(victimID).getPlayerHand().getResourceCards().size() == 0) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns a boolean indicating if the turn finished or not
	 * @return
     */
	public boolean finishTurn(int playerID) {
		if(okToPlay(playerID)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns a boolean whether or not the player can buy a development card
	 * @return
     */
	public boolean buyDevCard(int playerID) {
		if(!okToPlay(playerID)) {
			return false;
		}

		if(bank.getDevelopmentCards().size() == 0) {
			return false;
		}
		
		return players.get(playerID).canBuyDevCard();
	
	}

	/**
	 * Returns a boolean whether or not a robber can be placed at the new hex location
	 * @param loc
	 * @param victimID
     * @return
     */
	public boolean soldier(int playerID, HexLocation loc, int victimID) {
		if(!okToPlay(playerID) || players.get(playerID).isHasPlayedDevCard()) {
			return false;
		}
		
		if(players.get(playerID).numOldDevCardRemaining(DevCardType.SOLDIER) == 0) {
			return false;
		}
		
	

		return robPlayer(loc,victimID);
	}

	/**
	 * Returns a boolean whether or not a player can play the road building card
	 * @param type1
	 * @param type2
     * @return
     */
	public boolean yearOfPlenty(int playerID, ResourceType type1, ResourceType type2) {
		if(!okToPlay(playerID) || players.get(playerID).isHasPlayedDevCard()) {
			return false;
		}

		if(players.get(playerID).numOldDevCardRemaining(DevCardType.YEAR_OF_PLENTY) == 0) {
			return false;
		}

		ArrayList<ResourceCard> temp = bank.getResourceCards();

		int one = 0;
		int two = 0;

		for(ResourceCard rc : temp) {
			if(rc.getType() == type1) {
				one++;
			}
			if(rc.getType() == type2) {
				two++;
			}
		}

		if(type1 != type2) {
			if (one == 0 || two == 0) {
				return false;
			}
		} else {
			if (one == 0 || one == 1) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns a boolean returning whether or not a road buliding card can be used at the two given locations
	 * @param spot1
	 * @param spot2
     * @return
     */
	public boolean roadBuilding(int playerID, EdgeLocation spot1, EdgeLocation spot2) {
		if(!okToPlay(playerID) || players.get(playerID).isHasPlayedDevCard()) {
			return false;
		}

		if(players.get(playerID).numOldDevCardRemaining(DevCardType.ROAD_BUILD) == 0) {
			return false;
		}


		return true;
	}

	/**
	 *
	 * @param playerID
	 * @return
     */
	public boolean monopoly(int playerID) {
		if(!okToPlay(playerID) || players.get(playerID).isHasPlayedDevCard()) {
			return false;
		}

		if (players.get(playerID).numOldDevCardRemaining(DevCardType.MONOPOLY) == 0) {
			return false;
		}
		return true;
	}


	/**
	 * Returns a boolean whether or not the victory points can be played or not
	 * @return
     */
	public boolean monument(int playerID) {
		if(!okToPlay(playerID) || players.get(playerID).isHasPlayedDevCard()) {
			return false;
		}

		if(players.get(playerID).numOldDevCardRemaining(DevCardType.MONUMENT)+players.get(playerID).numNewDevCardRemaining(DevCardType.MONUMENT) == 0) {
			return false;
		}

		return true;
	}

	public Bank getBank() {
		return bank;
	}
	
	public boolean okToPlay(int playerID){
		if(playerID == tracker.getCurrentTurnPlayerID() && (tracker.getGameStatus() == GameState.playing ||
				tracker.getGameStatus() == GameState.firstRound ||
				tracker.getGameStatus() == GameState.secondRound)){
			return true;
		}else{
			return false;
		}
	}
	
	public CatanColor getColor(int playerIndex){
		return players.get(playerIndex).getPlayerColor();
	}
	public String getName(int playerIndex){
		return players.get(playerIndex).getName();
	}
	public GameState getGameState()
	{
		return tracker.getGameStatus();
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public int getCurrentPlayerIndex() {
		return tracker.currentTurnPlayerID;
	}

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

	public TradeOffer getTradeOffer()
	{
		return theTrade;
	}
	
	public void setTradeOffer(TradeOffer tradeToSet)
	{
		theTrade = tradeToSet;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void updateVersionNumber()
	{
		version++;
	}

	public void updateLongestRoad() {
		if(tracker.getLongestRoadplayerID() == -1){
			for(int i = 0; i < players.size(); i++){
				if(players.get(i).getNumRoads() <= 10){
					players.get(i).addVictoryPoint();
					players.get(i).addVictoryPoint();
					tracker.setLongestRoadplayerID(i);
					return;
				}
			}
		}else{
			int currentLongest = tracker.getLongestRoadplayerID();
			for(int i = 0; i < players.size(); i++){
				if((i != currentLongest) && (players.get(i).getNumRoads() < players.get(currentLongest).getNumRoads())){
					players.get(i).addVictoryPoint();
					players.get(i).addVictoryPoint();
					players.get(currentLongest).removeVictoryPoint();
					players.get(currentLongest).removeVictoryPoint();
					tracker.setLongestRoadplayerID(i);
					return;
				}
			}
		}
	}

	public void updateLargestArmy() {
		if(tracker.getLargestArmyPlayerID() == -1){
			for(int i = 0; i < players.size(); i++){
				if(players.get(i).getSoldiers() >= 3){
					players.get(i).addVictoryPoint();
					players.get(i).addVictoryPoint();	
					tracker.setLargestArmyPlayerID(i);
					return;
				}
			}
		}else{
			int currentLargest = tracker.getLargestArmyPlayerID();
			for(int i = 0; i < players.size(); i++){
				if((i != currentLargest) && (players.get(i).getSoldiers() > players.get(currentLargest).getSoldiers())){
					players.get(i).addVictoryPoint();
					players.get(i).addVictoryPoint();
					players.get(currentLargest).removeVictoryPoint();
					players.get(currentLargest).removeVictoryPoint();
					tracker.setLargestArmyPlayerID(i);
					return;
				}
			}
		}

	}

	public HexLocation getRobberLoc() {
		return robberLoc;
	}

	public void setRobberLoc(HexLocation robberLoc) {
		this.robberLoc = robberLoc;
	}




// Private methods
}
