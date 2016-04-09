package phase3Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import client.server.ServerException;
import server.ServerManager;
import server.commands.moves.RoadBuilding;
import shared.definitions.PieceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.model.GameModel;
import shared.model.Line;
import shared.model.map.Edge;
import shared.model.map.Map;
import shared.model.player.Player;

import java.util.ArrayList;

public class RoadBuildTest {

	
	@Test
	public void test() {
		ServerManager.getInstance().testing();
		System.out.println("Testing RoadBuild Command");
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
        Player p = rb.getModel().getPlayers().get(0);
        
        try {
			ArrayList<Line> lines = rb.getModel().getLog().getLines();
			int logBefore = lines.size();
			int numRoads = p.getNumRoads();

			rb.setTest(true);
			rb.execute();
			int logAfter = rb.getModel().getLog().getLines().size();
			int numRoadsAfter = p.getNumRoads();

			Edge edge = map.getEdges().get(new EdgeLocation(new HexLocation(0,0), EdgeDirection.NorthWest).getNormalizedLocation());
			assertTrue(edge.getPiece() != null);
            assertTrue(edge.getPiece().getPieceType() == PieceType.ROAD);
			Edge edge2 = map.getEdges().get(new EdgeLocation(new HexLocation(1,1), EdgeDirection.SouthEast).getNormalizedLocation());
            assertTrue(edge2.getPiece() != null);
            assertTrue(edge2.getPiece().getPieceType() == PieceType.ROAD);
			assertTrue(logBefore+1 == logAfter); //actually logs a message
			assertTrue(numRoads == numRoadsAfter + 2); //removed 2 roads from player

		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
