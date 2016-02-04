package canDoTests;

import map.Edge;
import map.Map;
import map.Vertex;
import org.junit.Test;
import shared.Piece;
import shared.definitions.PieceType;
import shared.locations.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CanBuildSettlement {

    @Test
    public void testMap() {
        assertTrue(true);

        // Testing building a settlement on an open map on setup mode
        Map map = new Map();
        
        int playerID = 1;
        VertexLocation el = new VertexLocation(new HexLocation(0, 0), VertexDirection.East).getNormalizedLocation();
        assertTrue(map.canBuildSettlement(playerID, true, el));


        // Building a settlement on an open map not on setup mode
        assertFalse(map.canBuildSettlement(playerID, false, el));

        // Building it next to another settlement
        Vertex spot = map.getVerticies().get(new VertexLocation(new HexLocation(0,0), VertexDirection.East).getNormalizedLocation());
        Piece house = new Piece(PieceType.SETTLEMENT,null, null, 1);

        spot.setPiece(house);
        el = new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthEast).getNormalizedLocation();
        assertFalse(map.canBuildSettlement(1, true, el));

        // Building it on another settlement
        el = new VertexLocation(new HexLocation(0, 0), VertexDirection.East).getNormalizedLocation();
        assertFalse(map.canBuildSettlement(1, true, el));

        // Building it next to own road on non setup mode
        Piece road = new Piece(PieceType.ROAD, null, null, 1);
        Edge spot1 = map.getEdges().get(new EdgeLocation(new HexLocation(0,0), EdgeDirection.South).getNormalizedLocation());
        spot1.setPiece(road);
        el = new VertexLocation(new HexLocation(0, 0), VertexDirection.SouthWest).getNormalizedLocation();
        assertTrue(map.canBuildSettlement(1, true, el));

        // Building it next to another player's road on non setup mode
        road = new Piece(PieceType.ROAD, null, null, 2);
        //Edge spot2 = map.getEdges().get(new EdgeLocation(new HexLocation(0,0), VertexDirection.SouthWest).getNormalizedLocation());
        spot1.setPiece(road);
        el = new VertexLocation(new HexLocation(0, 0), VertexDirection.SouthWest).getNormalizedLocation();
        assertTrue(map.canBuildSettlement(1, true, el));


        // Building it at the side of the board
        VertexLocation loc1 = new VertexLocation(new HexLocation(0, 3), VertexDirection.East);
        assertFalse(map.canBuildSettlement(1, true, loc1));
        
        VertexLocation loc2 = new VertexLocation(new HexLocation(-1, 3), VertexDirection.East);
        assertTrue(map.canBuildSettlement(1, true, loc2));
        
        VertexLocation loc3 = new VertexLocation(new HexLocation(3, 0), VertexDirection.NorthEast);
        assertFalse(map.canBuildSettlement(1, true, loc3));

    }


}
