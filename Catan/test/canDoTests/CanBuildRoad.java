package canDoTests;

import map.Edge;
import map.Map;
import map.TerrainHex;
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
    	System.out.println("Testing can build road");
        Map map = new Map();

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

        // Building roads on illegal vertices on water hexes
        EdgeLocation spot2 = new EdgeLocation(new HexLocation(0,3), EdgeDirection.South).getNormalizedLocation();
        assertFalse(map.canBuildRoad(playerID, false, spot2));

        spot2 = new EdgeLocation(new HexLocation(-2,3), EdgeDirection.SouthWest).getNormalizedLocation();
        assertFalse(map.canBuildRoad(playerID, false, spot2));

        spot2 = new EdgeLocation(new HexLocation(2,1), EdgeDirection.SouthEast).getNormalizedLocation();
        assertFalse(map.canBuildRoad(playerID, false, spot2));

        spot2 = new EdgeLocation(new HexLocation(0,-3), EdgeDirection.North).getNormalizedLocation();
        assertFalse(map.canBuildRoad(playerID, false, spot2));

        TerrainHex bottomHex = map.getHexes().get(new HexLocation(0,2));
        Vertex bottomVertex = map.getVerticies().get(new VertexLocation(bottomHex.getLocation(),VertexDirection.SouthEast).getNormalizedLocation());
        bottomVertex.setPiece(new Piece(PieceType.SETTLEMENT, null, null, 1));

        Edge bottomEdge = map.getEdges().get(new EdgeLocation(bottomHex.getLocation(), EdgeDirection.SouthEast).getNormalizedLocation());
        assertTrue(map.canBuildRoad(1, true, new EdgeLocation(bottomHex.getLocation(), EdgeDirection.SouthEast)));

    }


}
