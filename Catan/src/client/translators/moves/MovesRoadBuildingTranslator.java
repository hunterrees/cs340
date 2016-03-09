package client.translators.moves;

import client.translators.GenericTranslator;
import shared.locations.EdgeDirection;
import shared.locations.HexLocation;

public class MovesRoadBuildingTranslator extends GenericTranslator {
	
	private String type;
	private int playerIndex;
	private EdgeLocation spot1;
	private EdgeLocation spot2;
	
	public MovesRoadBuildingTranslator(int playerIndex, HexLocation location1, EdgeDirection direction1,
			HexLocation location2, EdgeDirection direction2) {
		super();
		this.type = "Road_Building";
		this.playerIndex = playerIndex;
		this.spot1 = new EdgeLocation(location1, direction1);
		this.spot2 = new EdgeLocation(location2, direction2);
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

	public EdgeLocation getSpot1() {
		return spot1;
	}

	public void setSpot1(EdgeLocation spot1) {
		this.spot1 = spot1;
	}

	public EdgeLocation getSpot2() {
		return spot2;
	}

	public void setSpot2(EdgeLocation spot2) {
		this.spot2 = spot2;
	}
}
