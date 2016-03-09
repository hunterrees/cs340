package client.translators.moves;

import client.translators.GenericTranslator;

public class MovesRollNumberTranslator extends GenericTranslator {
	
	private String type;
	private int playerIndex;
	private int number;
	
	public MovesRollNumberTranslator(int playerIndex, int number) {
		super();
		this.type = "rollNumber";
		this.playerIndex = playerIndex;
		this.number = number;
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
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
}
