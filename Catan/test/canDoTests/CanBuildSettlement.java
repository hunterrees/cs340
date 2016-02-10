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

public class CanBuildSettlement {

    @Test
    public void testMap() {
    	System.out.println("Testing can build settlement");

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

        VertexLocation loc4 = new VertexLocation(new HexLocation(-3, 3), VertexDirection.NorthWest);
        assertFalse(map.canBuildSettlement(1, true, loc4));

        VertexLocation loc5 = new VertexLocation(new HexLocation(3, 0), VertexDirection.NorthWest);
        assertTrue(map.canBuildSettlement(1, true, loc5));

        VertexLocation loc6 = new VertexLocation(new HexLocation(3, -1), VertexDirection.SouthEast);
        assertFalse(map.canBuildSettlement(1, true, loc6));

        VertexLocation loc7 = new VertexLocation(new HexLocation(0, -3), VertexDirection.NorthEast);
        assertFalse(map.canBuildSettlement(1, true, loc7));

        // Building it on the side of the board not on set up mode
        VertexLocation loc8 = new VertexLocation(new HexLocation(3, 0), VertexDirection.NorthWest);
        assertFalse(map.canBuildSettlement(1, false, loc8));

        VertexLocation loc9 = new VertexLocation(new HexLocation(3, -1), VertexDirection.SouthEast);
        assertFalse(map.canBuildSettlement(1, false, loc9));

        VertexLocation loc10 = new VertexLocation(new HexLocation(0, -3), VertexDirection.NorthEast);
        assertFalse(map.canBuildSettlement(1, false, loc10));

        // Build it next to own road
        Piece road1 = new Piece(PieceType.ROAD, null, null, 1);
        EdgeLocation spot4 = new EdgeLocation(new HexLocation(3,0),EdgeDirection.NorthWest).getNormalizedLocation();
        map.getEdges().get(spot4).setPiece(road1);

        VertexLocation loc11 = new VertexLocation(new HexLocation(3, 0), VertexDirection.NorthWest);
        assertTrue(map.canBuildSettlement(1, false, loc11));

        spot.setPiece(null);
        // Test every direction
        VertexLocation loc12 = new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthEast);
        assertTrue(map.canBuildSettlement(1, true, loc12));

        VertexLocation loc13 = new VertexLocation(new HexLocation(0, 0), VertexDirection.East);
        assertTrue(map.canBuildSettlement(1, true, loc13));

        VertexLocation loc14 = new VertexLocation(new HexLocation(0, 0), VertexDirection.SouthEast);
        assertTrue(map.canBuildSettlement(1, true, loc14));

        VertexLocation loc15 = new VertexLocation(new HexLocation(0, 0), VertexDirection.SouthWest);
        assertTrue(map.canBuildSettlement(1, true, loc15));

        VertexLocation loc16 = new VertexLocation(new HexLocation(0, 0), VertexDirection.West);
        assertTrue(map.canBuildSettlement(1, true, loc16));

        VertexLocation loc17 = new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthWest);
        assertTrue(map.canBuildSettlement(1, true, loc17));


    }


}
