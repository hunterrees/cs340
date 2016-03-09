package client.translators.moves;

import client.translators.GenericTranslator;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;

public class MovesBuildCityTranslator extends GenericTranslator {
	
	private String type;
	private int playerIndex;
	private VertexLocation vertexLocation;
	
	public MovesBuildCityTranslator(int playerIndex, HexLocation location, VertexDirection direction) {
		super();
		this.type = "buildCity";
		this.playerIndex = playerIndex;
		this.vertexLocation = new VertexLocation(location, direction);
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
}
