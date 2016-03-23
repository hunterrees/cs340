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
import shared.model.map.Map;

import java.io.File;
import java.io.IOException;

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

        try {
            bs.setTest(true);

            bs.execute();

            assertTrue(map.getEdges().get(new EdgeLocation(new HexLocation(0,0), EdgeDirection.NorthEast).getNormalizedLocation()).getPiece() != null);
            assertTrue(map.getEdges().get(new EdgeLocation(new HexLocation(0,0), EdgeDirection.NorthEast).getNormalizedLocation()).getPiece().getPieceType() == PieceType.ROAD);



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
