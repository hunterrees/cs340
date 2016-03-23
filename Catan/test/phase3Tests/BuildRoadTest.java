package phase3Tests;

import client.server.ServerException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import server.commands.moves.BuildRoad;
import server.commands.moves.BuildSettlement;
import shared.definitions.PieceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.GameModel;
import shared.model.Line;
import shared.model.map.Map;
import shared.model.player.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.* ;


/**
 * Created by briantiu on 3/21/16.
 */
public class BuildRoadTest {
    @Test
    public void test() {
        BuildRoad bs = new BuildRoad(0, "{\n" +
                "  \"type\": \"buildRoad\",\n" +
                "  \"playerIndex\": 0,\n" +
                "  \"roadLocation\": {\n" +
                "    \"x\": 0,\n" +
                "    \"y\": 0,\n" +
                "    \"direction\": \"NE\"\n" +
                "  },\n" +
                "  \"free\": \"true\"\n" +
                "}");

        GameModel model = bs.getModel();
        Map map = model.getMap();
        Player p = bs.getModel().getPlayers().get(0);

        try {
            ArrayList<Line> lines = bs.getModel().getLog().getLines();
            int logBefore = lines.size();
            int numRoads = p.getNumRoads();

            bs.setTest(true);
            
            bs.execute();
            int logAfter = bs.getModel().getLog().getLines().size();
            int numRoadsAfter = p.getNumRoads();
            
            
            assertTrue(map.getEdges().get(new EdgeLocation(new HexLocation(0,0), EdgeDirection.NorthEast).getNormalizedLocation()).getPiece() != null);
            assertTrue(map.getEdges().get(new EdgeLocation(new HexLocation(0,0), EdgeDirection.NorthEast).getNormalizedLocation()).getPiece().getPieceType() == PieceType.ROAD);
            assertTrue(logBefore+1 == logAfter); //log added
            assertTrue(numRoads == numRoadsAfter + 1); // player lost a road
            //resources decremented?



        } catch (ServerException e) {
            assertTrue(false);
            e.printStackTrace();
        }


    }

    private JsonObject toJson(String fileName) throws IOException {
        File file = new File(fileName);
        String jsonText = FileUtils.readFileToString(file);
        JsonParser parser = new JsonParser();
        JsonObject json = (JsonObject) parser.parse(jsonText);
        return json;
    }

}
