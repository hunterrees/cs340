package client.translators.moves;

import client.translators.GenericTranslator;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;

public class MovesBuildSettlementTranslator extends GenericTranslator {
	
	private String type;
	private int playerIndex;
	private VertexLocation vertexLocation;
	private boolean free;
	
	public MovesBuildSettlementTranslator(int playerIndex, HexLocation location, VertexDirection direction, boolean free) {
		super();
		this.type = "buildSettlement";
		this.playerIndex = playerIndex;
		this.vertexLocation = new VertexLocation(location, direction);
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
	public VertexLocation getVertexLocation() {
		return vertexLocation;
	}
	public void setVertexLocation(VertexLocation vertexLocation) {
		this.vertexLocation = vertexLocation;
	}
	public boolean isFree() {
		return free;
	}
	public void setFree(boolean free) {
		this.free = free;
	}
}
