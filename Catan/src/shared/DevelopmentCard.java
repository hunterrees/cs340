package shared;

import shared.definitions.DevCardType;

public class DevelopmentCard 
{
	/**
	 * enum type of development card
	 */
	private DevCardType type;
	
	public DevelopmentCard(DevCardType type)
	{
		this.type = type;
	}
	/**
	 * returns enum type of development card
	 * @return
	 */
	public DevCardType getType()
	{
		return type;
	}
}
