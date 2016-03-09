package client.translators.moves;

import java.util.ArrayList;

import client.translators.GenericTranslator;
import shared.definitions.ResourceType;
/***** Order for ResourceList constructor: brick, ore, sheep, wheat, wood*****/
public class MovesOfferTradeTranslator extends GenericTranslator {
	
	private String type;
	private int playerIndex;
	private ResourceList offer;
	private int receiver;
	
	public MovesOfferTradeTranslator(int playerIndex, ArrayList<ResourceType> resourceGive, ArrayList<ResourceType> resourceReceive, int receiver) {
		super();
		this.type = "offerTrade";
		this.playerIndex = playerIndex;
		this.offer = resourceTranslate(resourceGive, resourceReceive);
		this.receiver = receiver;
	}
	
	private ResourceList resourceTranslate(ArrayList<ResourceType> resourceGive, ArrayList<ResourceType> resourceReceive){
		int wood = 0;
		int brick = 0;
		int ore = 0;
		int wheat = 0;
		int sheep = 0;
		
		for(int i = 0; i < resourceGive.size(); i++){
			switch(resourceGive.get(i)){
				case WOOD: wood--; break;
				case BRICK: brick--; break;
				case ORE: ore--; break;
				case WHEAT: wheat--; break;
				case SHEEP: sheep--; break;
			}
		}
		
		for(int i = 0; i < resourceReceive.size(); i++){
			switch(resourceReceive.get(i)){
				case WOOD: wood++; break;
				case BRICK: brick++; break;
				case ORE: ore++; break;
				case WHEAT: wheat++; break;
				case SHEEP: sheep++; break;
			}
		}
		return new ResourceList(brick, ore, sheep, wheat, wood);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public ResourceList getOffer() {
		return offer;
	}

	public void setOffer(ResourceList offer) {
		this.offer = offer;
	}

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}
}
