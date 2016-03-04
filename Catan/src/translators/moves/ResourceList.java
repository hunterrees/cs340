package translators.moves;

import shared.definitions.ResourceType;

public class ResourceList {
	
	private int brick;
	private int ore;
	private int sheep;
	private int wheat;
	private int wood;

	public ResourceList(int brick, int ore, int sheep, int wheat, int wood) {
		super();
		this.brick = brick;
		this.ore = ore;
		this.sheep = sheep;
		this.wheat = wheat;
		this.wood = wood;
	}
	
	public void clear(){
		brick = 0;
		ore = 0;
		sheep = 0;
		wheat = 0;
		wood = 0;
	}
	
	public int getBrick() {
		return brick;
	}

	public void setBrick(int brick) {
		this.brick = brick;
	}

	public int getOre() {
		return ore;
	}

	public void setOre(int ore) {
		this.ore = ore;
	}

	public int getSheep() {
		return sheep;
	}

	public void setSheep(int sheep) {
		this.sheep = sheep;
	}

	public int getWheat() {
		return wheat;
	}

	public void setWheat(int wheat) {
		this.wheat = wheat;
	}

	public int getWood() {
		return wood;
	}

	public void setWood(int wood) {
		this.wood = wood;
	}
	
	public int size(){
		return brick + wood + wheat + ore + sheep;
	}
	
	public void add(ResourceType type){
		switch(type){
		case BRICK: brick++; break;
		case WOOD: wood++; break;
		case WHEAT: wheat++; break;
		case SHEEP: sheep++; break;
		case ORE: ore++; break;
		}
	}
	
	public void remove(ResourceType type){
		switch(type){
		case BRICK: brick--; break;
		case WOOD: wood--; break;
		case WHEAT: wheat--; break;
		case SHEEP: sheep--; break;
		case ORE: ore--; break;
		}
	}
	
	public int get(ResourceType type){
		switch(type){
		case BRICK: return brick;
		case WOOD: return wood;
		case WHEAT: return wheat;
		case SHEEP: return sheep;
		case ORE: return ore;
		}
		return -1;
	}
}
