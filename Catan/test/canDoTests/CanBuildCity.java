package canDoTests;

import static org.junit.Assert.*;

import org.junit.Test;

import shared.Piece;
import shared.definitions.PieceType;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.map.Map;
import shared.model.map.Vertex;

public class CanBuildCity {

	@Test
	public void test() {
		System.out.println("Testing can build city");
		Map map = new Map();
		Piece p1Settlement = new Piece(PieceType.SETTLEMENT, null, null, 1);
		Vertex vert = map.getVerticies().get(new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthWest));
		vert.setPiece(p1Settlement);
		//map.getVerticies().get(vert.getLocation()).setPiece(p1Settlement);

		assertTrue(map.getHexes().size() == 37);

		//can build on valid location
		boolean can = map.canBuildCity(1, vert.getLocation());
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
