package canDoTests;

import static org.junit.Assert.*;

import map.Map;
import org.junit.Test;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

public class CanBuildSettlement {

	@Test
	public void testMap() {
		Map map = new Map();
		int playerID = 1;
		EdgeLocation el = new EdgeLocation(new HexLocation(0,0), EdgeDirection.North);


		fail("Not yet implemented");
	}


}
