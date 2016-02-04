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
		//map.getVerticies().get(vert.getLocation()).setPiece(p1Settlement);s
		System.out.println("3");
		
		//can build on valid location
		boolean can = map.canBuildCity(1, vert.getLocation());
		System.out.println("4");
		assertTrue(can);
		
		//valid location but wrong player
		boolean cannot = map.canBuildCity(2, vert.getLocation());
		assertFalse(cannot);
		
		//previously good location but doesnt have a settlement anymore
		map.getVerticies().get(vert.getLocation()).setPiece(null);
		assertFalse(map.canBuildCity(1, vert.getLocation()));
		
		//place settlement at south direction's normalized location
		Vertex vert2 = map.getVerticies().get(new VertexLocation(new HexLocation(0, 0), VertexDirection.SouthWest).getNormalizedLocation());
		vert2.setPiece(p1Settlement);
		assertTrue(map.canBuildCity(1, vert2.getLocation()));
		
		//can call canBuildCity on a non normalized vertex
		Vertex vert3 = map.getVerticies().get(new VertexLocation(new HexLocation(-1, 1), VertexDirection.East));
		assertTrue(map.canBuildCity(1, vert3.getLocation()));
		
		//can call canBuildCity on a non normalized vertex
		Vertex vert4 = map.getVerticies().get(new VertexLocation(new HexLocation(0, 1), VertexDirection.NorthWest));
		assertTrue(map.canBuildCity(1, vert4.getLocation()));
		
		//piece was not actually placed in the non normalized vertex location
		Vertex vert5 = map.getVerticies().get(new VertexLocation(new HexLocation(0, 0), VertexDirection.SouthWest));
		assertTrue(vert5.getPiece() == null);
		
		
	}

}
