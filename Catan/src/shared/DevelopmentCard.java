package shared;

import java.io.Serializable;

import shared.definitions.DevCardType;

public class DevelopmentCard implements Serializable
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
