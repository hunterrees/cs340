package model;

import java.util.ArrayList;

import map.Map;
import player.Player;
import shared.ResourceCard;
import shared.definitions.DevCardType;
import shared.definitions.GameState;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import trade.TradeOffer;

public class GameModel {
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
	 * @param playerID
	 * @return
     */
	public boolean acceptTrade(int playerID) {
		Player p = players.get((playerID));

		if(theTrade == null){
			return false;
		}
		
		ArrayList<ResourceType> desired = theTrade.getResourceDesired();

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
		if(tracker.getCurrentTurnPlayerID() != playerID || tracker.getGameStatus() != GameState.discarding) {
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

		if(free) {
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
		if(robberLoc.equals(loc)) {
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


		return map.canRoadBuild(playerID, spot1, spot2);
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


		int numMonument = (players.get(playerID).numNewDevCardRemaining(DevCardType.MONUMENT)+players.get(playerID).numOldDevCardRemaining(DevCardType.MONUMENT));

		if(numMonument + players.get(playerID).getVictoryPoints() >= 10) {
			return true;
		} else {
			return false;
		}
	}

	public Bank getBank() {
		return bank;
	}
	
	public boolean okToPlay(int playerID){
		if(playerID == tracker.getCurrentTurnPlayerID() && tracker.getGameStatus() == GameState.playing){
			return true;
		}else{
			return false;
		}
	}

// Private methods
}
