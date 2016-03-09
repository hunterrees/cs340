package client.translators.games;

import client.translators.GenericTranslator;

public class GamesJoinTranslator 
	extends GenericTranslator {
	
	private int id;
	private String color;
	
	public GamesJoinTranslator(int id, String color) {
		super();
		this.id = id;
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
