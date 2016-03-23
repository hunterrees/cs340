package phase3Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import client.server.ServerException;
import server.commands.moves.RoadBuilding;
import shared.definitions.PieceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.model.GameModel;
import shared.model.map.Edge;
import shared.model.map.Map;

public class RoadBuildTest {

	
	@Test
	public void test() {
	
		String json = "{\n"+
		        "  \"type\": \"Road_Building\",\n"+
		        "  \"playerIndex\": 0,\n"+
		        "  \"spot1\": {\n"+
		        "    \"x\": 0,\n"+
		        "    \"y\": 0,\n"+
		        "    \"direction\": \"NW\"\n"+
		        "  },\n"+
		        "  \"spot2\": {\n"+
		        "    \"x\": 1,\n"+
		        "    \"y\": 1,\n"+
		        "    \"direction\": \"SE\"\n"+
		        "  }\n"+
		        "}";
		
		RoadBuilding rb = new RoadBuilding(0, json);
		GameModel model = rb.getModel();
        Map map = model.getMap();
        
        try {
			rb.execute();
			
			Edge edge = map.getEdges().get(new EdgeLocation(new HexLocation(0,0), EdgeDirection.NorthWest).getNormalizedLocation());
			assertTrue(edge.getPiece() != null);
            assertTrue(edge.getPiece().getPieceType() == PieceType.ROAD);
			Edge edge2 = map.getEdges().get(new EdgeLocation(new HexLocation(1,1), EdgeDirection.SouthEast).getNormalizedLocation());
            assertTrue(edge2.getPiece() != null);
            assertTrue(edge2.getPiece().getPieceType() == PieceType.ROAD);
            
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
