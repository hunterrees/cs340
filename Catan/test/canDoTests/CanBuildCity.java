package canDoTests;

import static org.junit.Assert.*;
import map.Map;
import map.Vertex;

import org.junit.Test;

import shared.Piece;
import shared.definitions.PieceType;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class CanBuildCity {

	@Test
	public void test() {
		Map map = new Map();
		System.out.println("1");
		Piece p1Settlement = new Piece(PieceType.SETTLEMENT, null, null, 1);
		System.out.println("2");
		Vertex vert = map.getVerticies().get(new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthWest));
		vert.setPiece(p1Settlement);
		map.getVerticies().get(vert.getLocation()).setPiece(p1Settlement);
		System.out.println("3");
		boolean can = map.canBuildCity(1, vert.getLocation());
		System.out.println("4");
		assertTrue(can);
		
		boolean cannot = map.canBuildCity(2, vert.getLocation());
		assertFalse(cannot);
		
		map.getVerticies().get(vert.getLocation()).setPiece(null);
		assertFalse(map.canBuildCity(1, vert.getLocation()));
		
		
	}

}
