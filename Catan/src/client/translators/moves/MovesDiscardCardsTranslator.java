package client.translators.moves;

import java.util.ArrayList;

import client.translators.GenericTranslator;
import shared.definitions.ResourceType;

public class MovesDiscardCardsTranslator extends GenericTranslator {
	
	private String type;
	private int playerIndex;
	private ResourceList discardedCards;
	
	public MovesDiscardCardsTranslator(int playerIndex, ArrayList<ResourceType> discardedCards) {
		super();
		this.type = "discardCards";
		this.playerIndex = playerIndex;
		this.discardedCards = resourceTranslate(discardedCards);
	}
	
	private ResourceList resourceTranslate(ArrayList<ResourceType> resources){
		int wood = 0;
		int brick = 0;
		int ore = 0;
		int wheat = 0;
		int sheep = 0;
		
		for(int i = 0; i < resources.size(); i++){
			switch(resources.get(i)){
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

	public ResourceList getDiscardedCards() {
		return discardedCards;
	}

	public void setDiscardedCards(ResourceList discardedCards) {
		this.discardedCards = discardedCards;
	}
}
