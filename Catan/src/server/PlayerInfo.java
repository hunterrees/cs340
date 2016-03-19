package server;

import shared.definitions.CatanColor;

public class PlayerInfo {
	private int id;
	private String name;
	private String color;
	
	public PlayerInfo()
	{
		setId(-1);
		setName("");
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getColor()
	{
		return color;
	}
	
	public void setColor(CatanColor color)
	{
		switch(color){
		case WHITE: this.color = "white"; break;
		case BLUE: this.color = "blue"; break;
		case ORANGE: this.color = "orange"; break;
		case RED: this.color = "red"; break;
		case YELLOW: this.color = "yellow"; break;
		case PURPLE: this.color = "purple"; break;
		case PUCE: this.color = "puce"; break;
		case BROWN: this.color = "brown"; break;
		case GREEN: this.color = "green"; break;
		}
	}

	@Override
	public int hashCode()
	{
		return 31 * this.id;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final PlayerInfo other = (PlayerInfo) obj;
		
		return this.id == other.id;
	}
}
