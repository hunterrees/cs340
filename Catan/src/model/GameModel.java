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
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public class GameModel {
	private Map map;
	private Bank bank;
	private ArrayList<Player> players;
	private int playerIndex;
	private HexLocation robberLoc;
	private String type;
	private int version;

// Constructor
	public GameModel(Map map, int playerIndex, ArrayList<Player> players, HexLocation robberLoc) {
		players = new ArrayList<Player>();

		this.map = map;
		this.bank = Bank.BANK;
		this.playerIndex = playerIndex;
		this.robberLoc = robberLoc;

		this.players = players;
		//players.add(new Player(0, CatanColor.RED , "Player1"));
		//players.add(new Player(1, CatanColor.GREEN, "Player2"));
		//players.add(new Player(2, CatanColor.ORANGE, "Player3"));
		//players.add(new Player(3, CatanColor.BLUE, "Player4"));
	}

// Public methods

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * Returns a boolean whether or not the players accepted the trade
	 * @param accept
	 * @return
     */
	public boolean acceptTrade(boolean accept) {
		return accept;
	}

	/**
	 * Returns a boolean indicating whether or not you can discard the given cards
	 * @param toDiscard
	 * @return
     */
	public boolean discardCards(ArrayList<ResourceCard> toDiscard) {
		ArrayList<ResourceCard> currentHand = players.get(playerIndex).getPlayerHand().getResourceCards();

		if(currentHand.size() <= 7) {
			return false;
		}

		int woodH = 0;
		int brickH = 0;
		int woolH = 0;
		int wheatH = 0;
		int oreH = 0;

		int woodD = 0;
		int brickD = 0;
		int woolD = 0;
		int wheatD = 0;
		int oreD = 0;


		for(ResourceCard rc : toDiscard) {
			switch(rc.getType()) {
				case WOOD: woodD++; break;
				case BRICK: brickD++; break;
				case SHEEP: woolD++; break;
				case WHEAT: wheatD++; break;
				case ORE: oreD++; break;
				default: System.out.println("Error! The resource type doesn't exist!");
			}
		}

		if(woodH < woodD || brickH < brickD ||
				woolH < woolD || wheatH < wheatD ||
				oreH < oreD) {
			return false;
		}

		return true;
	}

	/**
	 * Returns a boolean whether or not the roll was valid
	 * @param number
	 * @return
     */
	public boolean rollNumber(int number) {
		if(2 <= number && number <= 12) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns a boolean whether or not the player can build a road at the given location
	 * @param free
	 * @param loc
     * @return
     */
	public boolean buildRoad(boolean free, EdgeLocation loc) {
		boolean canBuild;
		if(map.canBuildRoad(playerIndex, free, loc)) {
			canBuild = true;
		} else {
			canBuild = false;
		}

		if(!canBuild) {
			return false;
		}

		if(free) {
			if (!players.get(playerIndex).canBuildRoad()) {
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
	public boolean buildSettlement(boolean free, VertexLocation loc) {

		boolean canBuild;
		if(map.canBuildSettlement(playerIndex, free, loc)) {
			canBuild = true;
		} else {
			canBuild = false;
		}

		if(!canBuild) {
			return false;
		}

		if(free) {
			if (!players.get(playerIndex).canBuildSettlement()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns a boolean whether or not the player can build a city at the given location
	 * @param free
	 * @param loc
     * @return
     */
	public boolean buildCity(boolean free, VertexLocation loc) {
		boolean canBuild;
		if(map.canBuildCity(playerIndex, loc)) {
			canBuild = true;
		} else {
			canBuild = false;
		}

		if(!canBuild) {
			return false;
		}

		if(free) {
			if (!players.get(playerIndex).canBuildCity()) {
				return false;
			}
		}

		return true;	}

	public boolean offerTrade() {
		return false;
	}

	public boolean maritimeTrade(int ratio, ArrayList<ResourceCard> giving, ArrayList<ResourceCard> getting) {
		// Determine if the player has enough resources or not
		if(giving.size() != ratio) {
			return false;
		}

		int woodH = 0;
		int brickH = 0;
		int woolH = 0;
		int wheatH = 0;
		int oreH = 0;
		for(ResourceCard rc : players.get(playerIndex).getPlayerHand().getResourceCards()) {
			switch(rc.getType()) {
				case WOOD: woodH++; break;
				case BRICK: brickH++; break;
				case SHEEP: woolH++; break;
				case WHEAT: wheatH++; break;
				case ORE: oreH++; break;
				default: System.out.println("Error! The resource type doesn't exist!");
			}
		}

		int woodG = 0;
		int brickG = 0;
		int woolG = 0;
		int wheatG = 0;
		int oreG = 0;;
		for(ResourceCard rc : giving) {
			switch(rc.getType()) {
				case WOOD: woodG++; break;
				case BRICK: brickG++; break;
				case SHEEP: woolG++; break;
				case WHEAT: wheatG++; break;
				case ORE: oreG++; break;
				default: System.out.println("Error! The resource type doesn't exist!");
			}
		}

		if(woodH < woodG || brickH < brickG ||
				woolH < woolG || wheatH < wheatG ||
				oreH < oreG) {
			return false;
		}

		// Check if the right number of resources is correct based on the ports the user has
		switch(ratio) {
			case 2:
				if(players.get(playerIndex).hasPortType(giving.get(0))) {
					return true;
				} else {
					return false;
				}
			case 3:
				if(players.get(playerIndex).hasPortType(giving.get(0))) {
					return true;
				} else {
					return false;
				}
			case 4: return true;
			default: return false;
		}

		// Make the trade
		// Don't know if this code goes here or not

	}

	
	public boolean domesticTradeOffer(ResourceType type, int numOf)
	{
		if (players.get(playerIndex).numResourceRemaining(type) < numOf)
		{
			return false;
		}
		return true;
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
	public boolean finishTurn() {
		return true;
	}

	/**
	 * Returns a boolean whether or not the player can buy a development card
	 * @return
     */
	public boolean buyDevCard() {
		if(bank.getDevelopmentCards().size() == 0) {
			return false;
		}
		
		return players.get(playerIndex).canBuyDevCard();
	
	}

	/**
	 * Returns a boolean whether or not a robber can be placed at the new hex location
	 * @param loc
	 * @param victimID
     * @return
     */
	public boolean soldier(HexLocation loc, int victimID) {
		return robPlayer(loc,victimID);
	}

	/**
	 * Returns a boolean whether or not a player can play the road building card
	 * @param type1
	 * @param type2
     * @return
     */
	public boolean yearOfPlenty(ResourceType type1, ResourceType type2) {
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
	public boolean roadBuilding(EdgeLocation spot1, EdgeLocation spot2) {
		return map.roadBuilding(spot1, spot2);
	}

	/**
	 * Returns a boolean whether or not the monopoly is valid
	 * @param type
	 * @return
     */
	public boolean monopoly(ResourceType type) {

		return true;
	}

	/**
	 * Returns a boolean whether or not the victory points can be played or not
	 * @return
     */
	public boolean monument() {
		ArrayList<DevelopmentCard> cards = players.get(playerIndex).getPlayerHand().getDevelopmentCards();
		int count = 0;

		for(DevelopmentCard dc : cards) {
			if(dc.getType() == DevCardType.MONUMENT) {
				count++;
			}
		}
		int difference = 10 - count;

		if(difference >= players.get(playerIndex).getVictoryPoints()) {
			return true;
		} else {
			return false;
		}
	}

// Private methods
}
