package canDoTests;

import static org.junit.Assert.*;

import org.junit.Test;

import shared.Piece;
import shared.definitions.PieceType;
import shared.locations.*;
import shared.model.map.Edge;
import shared.model.map.Map;
import shared.model.map.Vertex;


public class CanUseRoadBuilder {

	@Test
	public void test() {
		System.out.println("Testing can use road builder");
		Map map = new Map();

		// Tesing it building the roads next to own settlements
		Vertex spot = map.getVerticies().get(new VertexLocation(new HexLocation(0,0), VertexDirection.East).getNormalizedLocation());
		spot.setPiece(new Piece(PieceType.SETTLEMENT, null, null, 1));

		EdgeLocation el1 = new EdgeLocation(new HexLocation(0,0), EdgeDirection.NorthEast).getNormalizedLocation();
		EdgeLocation el2 = new EdgeLocation(new HexLocation(0,0), EdgeDirection.North).getNormalizedLocation();

		assertTrue(map.canRoadBuild(1, el1, el2));
		assertTrue(map.canRoadBuild(1, el2, el1));


		// Building them not next to own settlement
		EdgeLocation el3 = new EdgeLocation(new HexLocation(0,0), EdgeDirection.South).getNormalizedLocation();
		EdgeLocation el4 = new EdgeLocation(new HexLocation(0,0), EdgeDirection.SouthWest).getNormalizedLocation();

		assertFalse(map.canRoadBuild(1, el3, el4));
		assertFalse(map.canRoadBuild(1, el4, el3));

		// Building them next to own road
		EdgeLocation el5 = new EdgeLocation(new HexLocation(0,0), EdgeDirection.South).getNormalizedLocation();
		EdgeLocation el6 = new EdgeLocation(new HexLocation(0,0), EdgeDirection.SouthWest).getNormalizedLocation();

		Edge edgeSpot = map.getEdges().get(new EdgeLocation(new HexLocation(0,0), EdgeDirection.NorthWest).getNormalizedLocation());
		edgeSpot.setPiece(new Piece(PieceType.ROAD, null, null, 1));
		assertTrue(map.canRoadBuild(1, el5, el6));
		assertTrue(map.canRoadBuild(1, el6, el5));



		// Building them next to another player's roads
		assertFalse(map.canRoadBuild(2, el5, el6));
		assertFalse(map.canRoadBuild(2, el6, el5));


		// Building them next to another player's settlement
		assertFalse(map.canRoadBuild(2, el1, el2));
		assertFalse(map.canRoadBuild(2, el2, el1));
		
		// Building one next to settlement then another next to a road
		assertTrue(map.canRoadBuild(1, el6, el1));
		assertTrue(map.canRoadBuild(1, el1, el6));
		
		Vertex vert = map.getVerticies().get(new VertexLocation(new HexLocation(0,0), VertexDirection.SouthWest).getNormalizedLocation());
		vert.setPiece(new Piece(PieceType.SETTLEMENT, null, null, 3));
		
		//Building in valid place but also next to enemy settlement
		assertTrue(map.canRoadBuild(1, el6, el1));
		assertTrue(map.canRoadBuild(1, el1, el6));
		
		//Building through enemy settlement
		assertFalse(map.canRoadBuild(1, el5, el6));
		assertFalse(map.canRoadBuild(1, el6, el5));
		
		
		Vertex vert2 = map.getVerticies().get(new VertexLocation(new HexLocation(1,1), VertexDirection.SouthEast).getNormalizedLocation());
		vert2.setPiece(new Piece(PieceType.SETTLEMENT, null, null, 1));
		
		EdgeLocation el7 = new EdgeLocation(new HexLocation(1,1), EdgeDirection.South).getNormalizedLocation();
		EdgeLocation el8 = new EdgeLocation(new HexLocation(1,1), EdgeDirection.SouthEast).getNormalizedLocation();
		
		assertTrue(map.canRoadBuild(1, el7, el8));
		assertTrue(map.canRoadBuild(1, el8, el7));
		
		
		
		
	}

}
