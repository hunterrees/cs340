package client.translators.moves;

import client.translators.GenericTranslator;

public class MovesBuyDevCardTranslator extends GenericTranslator {
	
	private String type;
	private int playerIndex;
	
	public MovesBuyDevCardTranslator(int playerIndex) {
		super();
		this.type = "buyDevCard";
		this.playerIndex = playerIndex;
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
}
