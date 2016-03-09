package client.translators.moves;

import client.translators.GenericTranslator;

public class MovesAcceptTradeTranslator extends GenericTranslator {
	
	private String type;
	private int playerIndex;
	private boolean willAccept;
	
	public MovesAcceptTradeTranslator(int playerIndex, boolean willAccept) {
		super();
		this.type = "acceptTrade";
		this.playerIndex = playerIndex;
		this.willAccept = willAccept;
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

	public boolean isWillAccept() {
		return willAccept;
	}

	public void setWillAccept(boolean willAccept) {
		this.willAccept = willAccept;
	}
}
