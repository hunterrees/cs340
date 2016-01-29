package translators.moves;

import translators.GenericTranslator;

public class MovesBuildSettlementTranslator extends GenericTranslator {
	
	private String type;
	private int playerIndex;
	private VertexLocation vertexLocation;
	private boolean free;
	
	public MovesBuildSettlementTranslator(int playerIndex, VertexLocation vertexLocation, boolean free) {
		super();
		this.type = "buildSettlement";
		this.playerIndex = playerIndex;
		this.vertexLocation = vertexLocation;
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
