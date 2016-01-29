package trade;

import shared.definitions.ResourceType;

public class DomesticTrade 
{
	/**
	 * type of resource being recieved by player taking action
	 */
	private ResourceType typeRecieve;
	/**
	 * type of resource being sent by player taking action
	 */
	private ResourceType typeSend;
	/**
	 * player initiating this trade
	 */
	private int actionPlayerID;
	/**
	 * player being traded with
	 */
	private int targetPlayerID;
	/**
	 * sets up necessary variables for domestic trade
	 * @param typeRecieve
	 * @param typeSend
	 */
	public DomesticTrade(ResourceType typeRecieve, ResourceType typeSend,int actionPlayerID,int targetPlayerID)
	{
		this.typeRecieve = typeRecieve;
		this.typeSend = typeSend;
		this.actionPlayerID = actionPlayerID;
		this.targetPlayerID = targetPlayerID;
	}
	/**
	 * determines whether player can trade with bank
	 */
	public boolean canTrade(ResourceType typeLose, ResourceType typeRecieve, int actionPlayer, int targetPlayer)
	{
		return true;
	}
	
}
