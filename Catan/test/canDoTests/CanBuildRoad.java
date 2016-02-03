package canDoTests;

import map.Map;
import map.Vertex;
import org.junit.Test;
import shared.Piece;
import shared.definitions.PieceType;
import shared.locations.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CanBuildRoad {

    @Test
    public void testMap() {
        Map map = new Map();

        //
        boolean possible;
        int playerID = 1;
        EdgeLocation el = new EdgeLocation(new HexLocation(0,0), EdgeDirection.NorthEast).getNormalizedLocation();


        // Testing an empty map on non setup mode
        assertFalse(map.canBuildRoad(playerID, false, el));

        // Testing an empty map on setup mode
        assertFalse(map.canBuildRoad(playerID, true, el));

        // Blank map with road next to settlement on non setup mode
        Vertex spot = map.getVerticies().get(new VertexLocation(new HexLocation(0,0), VertexDirection.East).getNormalizedLocation());
        Piece house = new Piece(PieceType.SETTLEMENT,null, null, 1);

        spot.setPiece(house);

        assertTrue(map.canBuildRoad(playerID, false, el));


        // Blank map with road next to settlement on setup mode
        assertTrue(map.canBuildRoad(playerID, false, el));



    }


}
