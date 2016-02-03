package player;
import java.util.ArrayList;

import shared.DevelopmentCard;
import shared.ResourceCard;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;


public class Hand 
{
	/**
	 * Development cards owned by player
	 */
	private ArrayList<DevelopmentCard> oldDevelopmentCards;
	private ArrayList<DevelopmentCard> newDevelopmentCards;
	/**
	 * Resource cards owned by player
	 */
	private ArrayList<ResourceCard> resourceCards;
	/**
	 * Knights owned by player
	 */
	private int knights;
	public Hand()
	{
		oldDevelopmentCards = new ArrayList<DevelopmentCard>();
		newDevelopmentCards = new ArrayList<DevelopmentCard>();
		resourceCards = new ArrayList<ResourceCard>();
	}
	/**
	 * returns number of total resources owned by player
	 * @return
	 */
	public int getNumResources()
	{
		return resourceCards.size();
	}
	/**
	 * returns number of development cards owned by player
	 * @return
	 */
	public int getNumDevCards()
	{
		return oldDevelopmentCards.size();
	}
	
	/**
	 * Add resource of type and amount to player hand
	 * @param amount
	 * @param type
	 */
	public void addResources(int amount, ResourceType type)
	{
		for (int i = 0; i < amount; i++)
		{
			resourceCards.add(new ResourceCard(type));
		}
	}
	/**
	 * Remove resources of type and amount from player hand
	 * @param amount
	 * @param type
	 */
	public boolean removeResources(int amount, ResourceType type)
	{
		if (amount > numResourceOfType(type))
		{
			return false;
		}
		
		for (int i = 0; i < amount; i++)
		{
			boolean found = false;
			int j = 0;
			while (!found)
			{
				if (resourceCards.get(j).getType().equals(type))
				{
					resourceCards.remove(j);
					found = true;
				}
				j++;
			}
		}
		return true;
	}
	/**
	 * returns number of resources of specified type in player hand
	 * @param type
	 * @return
	 */
	public int numResourceOfType(ResourceType type)
	{
		int numRemaining = 0;
		for (int i = 0; i < resourceCards.size(); i++)
		{
			if (resourceCards.get(i).getType().equals(type))
			{
				numRemaining++;
			}
		}
		return numRemaining;
	}
	/**
	 * Add development card of type to player hand
	 * @param type
	 */
	public void addNewDevelopmentCard(DevCardType type)
	{
		newDevelopmentCards.add(new DevelopmentCard(type));
	}
	public void addOldDevelopmentCard(DevCardType type)
	{
		oldDevelopmentCards.add(new DevelopmentCard(type));
	}
	/**
	 * remove development card of specified type from player hand
	 * @param type
	 */
	boolean removeOldDevelopmentCard(DevCardType type)
	{
		if (numOldDevCardRemaining(type) < 1)
		{
			return false;
		}
		boolean cardRemoved = false;
		int i = 0;
		while (!cardRemoved)
		{
			if (oldDevelopmentCards.get(i).getType().equals(type))
			{
				oldDevelopmentCards.remove(i);
				cardRemoved = true;
			}
			i++;
		}
		return true;
	}
	boolean removeNewDevelopmentCard(DevCardType type)
	{
		if (numNewDevCardRemaining(type) < 1)
		{
			return false;
		}
		boolean cardRemoved = false;
		int i = 0;
		while (!cardRemoved)
		{
			if (newDevelopmentCards.get(i).getType().equals(type))
			{
				newDevelopmentCards.remove(i);
				cardRemoved = true;
			}
			i++;
		}
		return true;
	}
	public int numOldDevCardRemaining(DevCardType type)
	{
		int numRemaining = 0;
		for (int i = 0; i < oldDevelopmentCards.size(); i++)
		{
			if (oldDevelopmentCards.get(i).getType() == type)
			{
				numRemaining++;
			}
		}
		return numRemaining;
	}
	public int numNewDevCardRemaining(DevCardType type)
	{
		int numRemaining = 0;
		for (int i = 0; i < newDevelopmentCards.size(); i++)
		{
			if (newDevelopmentCards.get(i).getType() == type)
			{
				numRemaining++;
			}
		}
		return numRemaining;
	}
	/**
	 * returns number of knights owned by player
	 * @return
	 */
	public int getArmySize()
	{
		return knights;
	}
	/**
	 * returns development cards owned by player
	 * @return
	 */
	public ArrayList<DevelopmentCard> getOldDevelopmentCards()
	{
		return oldDevelopmentCards;
	}
	public ArrayList<DevelopmentCard> getNewDevelopmentCards()
	{
		return newDevelopmentCards;
	}
	/**
	 * returns resource cards owned by player
	 * @return
	 */
	public ArrayList<ResourceCard> getResourceCards()
	{
		return resourceCards;
	}
	public void setResourceCards(ArrayList<ResourceCard> cardsToSet)
	{
		resourceCards = cardsToSet;
	}
}
