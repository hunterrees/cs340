package model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import map.Map;
import map.Port;
import player.Player;
import shared.DevelopmentCard;
import shared.ResourceCard;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.GameState;
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

// Constructor
	public GameModel(Map map, ArrayList<Player> players, HexLocation robberLoc, TradeOffer theTrade,
						TurnTracker tracker) {
		this.tracker = tracker;

		this.map = map;
		this.bank = Bank.BANK;
		this.robberLoc = robberLoc;
		this.theTrade = theTrade;

		this.players = players;
	}

// Public methods

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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
		return false;
	}

	/**
	 * Returns a boolean whether or not the player can build a road at the given location
	 * @param free
	 * @param loc
     * @return
     */
	public boolean canBuildRoad(int playerID, boolean free, EdgeLocation loc) {
		if(tracker.getCurrentTurnPlayerID() != playerID) {
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
		if(tracker.getCurrentTurnPlayerID() != playerID) {
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
		if(tracker.getCurrentTurnPlayerID() != playerID) {
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
		return false;
	}

	public int maritimeTrade(int playerID, ResourceType inputType) {
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
		if(victimID != -1) {
			if(players.get(victimID).getPlayerHand().getResourceCards().size() == 0) {
				return false;
			}
		} else {
			return false;
		}

		return true;
	}

	/**
	 * Returns a boolean indicating if the turn finished or not
	 * @return
     */
	public boolean finishTurn(int playerID) {
		if(tracker.getCurrentTurnPlayerID() == playerID) {
			return true;
		}

		return false;
	}

	/**
	 * Returns a boolean whether or not the player can buy a development card
	 * @return
     */
	public boolean buyDevCard(int playerID) {
		if(tracker.getCurrentTurnPlayerID() != playerID) {
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
		if(tracker.getCurrentTurnPlayerID() != playerID) {
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
		if(tracker.getCurrentTurnPlayerID() != playerID) {
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
		if(tracker.getCurrentTurnPlayerID() != playerID) {
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
		if (tracker.getCurrentTurnPlayerID() != playerID) {
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
		if(tracker.getCurrentTurnPlayerID() != playerID) {
			return false;
		}

		if(players.get(playerID).numOldDevCardRemaining(DevCardType.MONUMENT)+players.get(playerID).numNewDevCardRemaining(DevCardType.MONUMENT) == 0) {
			return false;
		}




		int difference = 10 - (players.get(playerID).numNewDevCardRemaining(DevCardType.MONOPOLY)+players.get(playerID).numOldDevCardRemaining(DevCardType.MONOPOLY));

		if(difference >= players.get(playerID).getVictoryPoints()) {
			return true;
		} else {
			return false;
		}
	}

// Private methods
}
