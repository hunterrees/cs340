package client.translators.moves;

import client.translators.GenericTranslator;

public class MovesFinishTurnTranslator extends GenericTranslator {

	private String type;
	private int playerIndex;
	
	public MovesFinishTurnTranslator(int playerIndex) {
		super();
		this.type = "finishTurn";
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
