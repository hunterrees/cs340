package client.translators.moves;

import client.translators.GenericTranslator;
import shared.locations.EdgeDirection;
import shared.locations.HexLocation;

public class MovesBuildRoadTranslator extends GenericTranslator {
	
	private String type;
	private int playerIndex;
	private EdgeLocation roadLocation;
	private boolean free;
	
	public MovesBuildRoadTranslator(int playerIndex, HexLocation location, EdgeDirection direction, boolean free) {
		super();
		this.type = "buildRoad";
		this.playerIndex = playerIndex;
		this.roadLocation = new EdgeLocation(location, direction);
		this.free = free;
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
	public EdgeLocation getRoadLocation() {
		return roadLocation;
	}
	public void setRoadLocation(EdgeLocation roadLocation) {
		this.roadLocation = roadLocation;
	}
	public boolean isFree() {
		return free;
	}
	public void setFree(boolean free) {
		this.free = free;
	}
}
