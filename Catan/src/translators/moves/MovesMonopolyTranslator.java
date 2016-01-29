package translators.moves;

import translators.GenericTranslator;

public class MovesMonopolyTranslator extends GenericTranslator {
	
	private String type;
	private int playerIndex;
	
	public MovesMonopolyTranslator(int playerIndex) {
		super();
		this.type = "Monopoly";
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
