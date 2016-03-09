package client.translators.game;

import client.translators.GenericTranslator;

public class GameAddAITranslator extends GenericTranslator {

	private String AIType;

	public GameAddAITranslator(String aIType) {
		super();
		AIType = aIType;
	}

	public String getAIType() {
		return AIType;
	}

	public void setAIType(String aIType) {
		AIType = aIType;
	}
}
