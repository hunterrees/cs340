package client.translators.moves;

import client.translators.GenericTranslator;

public class MovesSendChatTranslator extends GenericTranslator{
	
	private String type;
	private int playerIndex;
	private String content;

	public MovesSendChatTranslator(int playerIndex, String content) {
		this.type = "sendChat";
		this.playerIndex = playerIndex;
		this.content = content;
	}
	
	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
