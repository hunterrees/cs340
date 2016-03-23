package phase3Tests;

import client.server.ServerException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import server.commands.moves.BuildCity;
import server.commands.moves.BuildSettlement;
import shared.locations.VertexLocation;
import shared.model.GameModel;
import shared.model.map.Map;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.* ;


/**
 * Created by briantiu on 3/21/16.
 */
public class BuildCityTest {
    @Test
    public void test() {
        BuildCity bs = new BuildCity(0, "   add json file here     ");

        GameModel model = bs.getModel();
        Map map = model.getMap();

        try {
            bs.execute();

            //assertTrue("test if the settlement is in the correct place");



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

