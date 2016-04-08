package shared;

import java.io.Serializable;

import shared.definitions.ResourceType;

public class ResourceCard implements Serializable
{
	/**
	 * enum type of resource
	 */
	private ResourceType type;
	/**
	 * sets enum type of resource card
	 * @param type
	 */
	public ResourceCard(ResourceType type)
	{
		this.type = type;
	}
	/**
	 * returns enum type of resource
	 * @return
	 */
	public ResourceType getType()
	{
		return type;
	}
	
}
