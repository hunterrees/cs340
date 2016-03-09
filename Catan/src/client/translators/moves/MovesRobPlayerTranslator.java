package client.translators.moves;

import client.translators.GenericTranslator;
import shared.locations.HexLocation;

public class MovesRobPlayerTranslator extends GenericTranslator {
	
	private String type;
	private int playerIndex;
	private int victimIndex;
	private HexLocation location;
	
	public MovesRobPlayerTranslator(int playerIndex, int victimIndex, HexLocation location) {
		super();
		this.type = "robPlayer";
		this.playerIndex = playerIndex;
		this.victimIndex = victimIndex;
		this.location = location;
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

	public int getVictimIndex() {
		return victimIndex;
	}

	public void setVictimIndex(int victimIndex) {
		this.victimIndex = victimIndex;
	}

	public HexLocation getLocation() {
		return location;
	}

	public void setLocation(HexLocation location) {
		this.location = location;
	}
}
