package player;
import java.util.ArrayList;

import map.Port;
import shared.Piece;
import shared.definitions.CatanColor;
import shared.definitions.PortType;
import shared.definitions.ResourceType;

//testing stuff

/*
 * Hey zack i think that the parameters for canBuild____ functions need to change
 * i'm going to pass in the location and if it's a setup or not for canBuildSettlement
 * there also needs to be a setvictorypoint getter
 * brian
 */

public class Player 
{
	/**
	 * roads, settlements and cities owned by player
	 */
	private ArrayList<Piece> playerPieces;
	/**
	 * ports owned by player
	 */
	private ArrayList<Port> playerPorts;
	/**
	 * hand containing cards owned by player
	 */
	private Hand playerHand;
	/**
	 * number of victory points player has
	 */
	private int victoryPoints;
	/**
	 * ID of player
	 */
	private int playerID;
	/**
	 * color of player pieces
	 */
	private String name;
	/**
	 * name of player
	 */
	private CatanColor playerColor;
	/**
	 * initialize pieces arraylist
	 */
	public Player(int playerID, CatanColor playerColor, String name)
	{
		playerPieces = new ArrayList<Piece>();
		this.playerID = playerID;
		this.playerColor = playerColor;
		this.name = name;
	}

	/**
	 * calculate victory points of player
	 * @return
	 */
	public int getVictoryPoints()
	{
		return victoryPoints;
	}
	/**
	 * calculate longest chain of unbroken roads
	 * @return
	 */
	public int getLongestRoadChain()
	{
		return 0;
	}
	/**
	 * check resources required for building road
	 * @return
	 */
	public boolean canBuildRoad()
	{
		if (playerHand.numResourceOfType(ResourceType.BRICK) > 0 && playerHand.numResourceOfType(ResourceType.WOOD) > 0)
		{
			return true;
		}
		return false;
	}
	/**
	 * check resources required for building settlement
	 * @return
	 */
	public boolean canBuildSettlement()
	{
		if 		(playerHand.numResourceOfType(ResourceType.BRICK) > 0 
				&& playerHand.numResourceOfType(ResourceType.WOOD) > 0
				&& playerHand.numResourceOfType(ResourceType.WHEAT) > 0
				&& playerHand.numResourceOfType(ResourceType.SHEEP) > 0)
		{
			return true;
		}
		return false;
	}
	/**
	 * check resources required for building city
	 * @return
	 */
	public boolean canBuildCity()
	{
		if (playerHand.numResourceOfType(ResourceType.WHEAT) > 1 && playerHand.numResourceOfType(ResourceType.ORE) > 2)
		{
			return true;
		}
		return false;
	}
	/**
	 * check resources required for buying development card
	 * @return
	 */
	public boolean canBuyDevCard()
	{
		if (playerHand.numResourceOfType(ResourceType.SHEEP) > 0 
				&& playerHand.numResourceOfType(ResourceType.WHEAT) > 0
				&& playerHand.numResourceOfType(ResourceType.ORE) > 0)
		{
			return true;
		}
		return false;
	}
	public boolean hasPort(PortType type)
	{
		for (int i = 0; i < playerPorts.size(); i++)
		{
			if (playerPorts.get(i).equals(type))
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * return all pieces owned by player
	 * @return
	 */
	public ArrayList<Piece> getPlayerPieces()
	{
		return playerPieces;
	}
	/**
	 * return player hand class
	 * @return
	 */
	public Hand getPlayerHand()
	{
		return playerHand;
	}
	/**
	 * returns name of the player
	 * @return
	 */
	public String getName()
	{
		return name;
	}
	public int getPlayerID()
	{
		return playerID;
	}
	public CatanColor getPlayerColor()
	{
		return playerColor;
	}
}
