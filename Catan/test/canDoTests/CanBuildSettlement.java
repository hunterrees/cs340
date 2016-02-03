package canDoTests;

import map.Map;
import org.junit.Test;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CanBuildSettlement {

    @Test
    public void testMap() {
        assertTrue(true);

        // Testing building a settlement on an open map
        Map map = new Map();
        int playerID = 1;
        VertexLocation el = new VertexLocation(new HexLocation(0, 0), VertexDirection.East).getNormalizedLocation();
        boolean possible = map.canBuildSettlement(playerID, true, el);


        if (possible) {
            assertTrue(true);
        } else {
            fail("Doesn't work");
        }

        // Building it next to another settlement

        // Building it on another settlement

        // Building it next to another road

        // Building it at the side of the board

    }


}
