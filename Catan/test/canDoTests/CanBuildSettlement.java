package canDoTests;

import static org.junit.Assert.*;
import map.Map;

import org.junit.Test;

import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class CanBuildSettlement {

	@Test
	public void testMap() {
		Map map = new Map();
		int playerID = 1;
		VertexLocation el = new VertexLocation(new HexLocation(0,0), VertexDirection.East);
		boolean possible = map.canBuildSettlement(playerID, false, el);

		assertTrue(true);

		if(possible) {
			assertTrue(true);
		} else {
			fail("Doesn't work");
		}
		

		//fail("Not yet implemented");
	}


}
