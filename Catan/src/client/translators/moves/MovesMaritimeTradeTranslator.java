package client.translators.moves;

import client.translators.GenericTranslator;
import shared.definitions.ResourceType;

public class MovesMaritimeTradeTranslator extends GenericTranslator {
	
	private String type;
	private int playerIndex;
	private int ratio;
	private String inputResource;
	private String outputResource;
	
	public MovesMaritimeTradeTranslator(int playerIndex, int ratio, ResourceType inputResource,
			ResourceType outputResource) {
		super();
		this.type = "maritimeTrade";
		this.playerIndex = playerIndex;
		this.ratio = ratio;
		this.inputResource = resourceTranslate(inputResource);
		this.outputResource = resourceTranslate(outputResource);
	}
	
	private String resourceTranslate(ResourceType type){
		switch(type){
			case WOOD: return "wood";
			case BRICK: return "brick";
			case ORE: return "ore";
			case WHEAT: return "wheat";
			case SHEEP: return "sheep";
		}
		return "";
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

	public int getRatio() {
		return ratio;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}

	public String getInputResource() {
		return inputResource;
	}

	public void setInputResource(String inputResource) {
		this.inputResource = inputResource;
	}

	public String getOutputResource() {
		return outputResource;
	}

	public void setOutputResource(String outputResource) {
		this.outputResource = outputResource;
	}
}
