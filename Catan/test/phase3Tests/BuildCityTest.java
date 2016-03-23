package phase3Tests;

import client.server.ServerException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import server.commands.moves.BuildCity;
import server.commands.moves.BuildSettlement;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
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
public class BuildCityTest {
    @Test
    public void test() {
        String json = "{\n" +
                "  \"type\": \"buildCity\",\n" +
                "  \"playerIndex\": 0,\n" +
                "  \"vertexLocation\": {\n" +
                "    \"x\": 0,\n" +
                "    \"y\": 0,\n" +
                "    \"direction\": \"SE\"\n" +
                "  }\n" +
                "}";
        BuildCity bs = new BuildCity(0, json);

        GameModel model = bs.getModel();
        Map map = model.getMap();
        Player p = bs.getModel().getPlayers().get(0);
        p.getPlayerHand().addResources(4, ResourceType.ORE);
        p.getPlayerHand().addResources(4, ResourceType.WHEAT);
// sas
        try {
            ArrayList<Line> lines = bs.getModel().getLog().getLines();
            int logBefore = lines.size();
            int numCities = p.getNumCities();
            int numSettlements = p.getNumSettlements();

            int before = p.getVictoryPoints();
            
            int ore = p.getPlayerHand().numResourceOfType(ResourceType.ORE);
            int wheat = p.getPlayerHand().numResourceOfType(ResourceType.WHEAT);
            
            
            bs.setTest(true);
            bs.execute();
            
            int after = p.getVictoryPoints();
            int logAfter = bs.getModel().getLog().getLines().size();
            int numCitiesAfter = p.getNumCities();
            int numSettlementsAfter = p.getNumSettlements();
            int oreAfter = p.getPlayerHand().numResourceOfType(ResourceType.ORE);
            int wheatAfter = p.getPlayerHand().numResourceOfType(ResourceType.WHEAT);
            
            assertTrue(map.getVerticies().get(new VertexLocation(new HexLocation(0,0), VertexDirection.SouthEast).getNormalizedLocation()).getPiece() != null);
            assertTrue(map.getVerticies().get(new VertexLocation(new HexLocation(0,0), VertexDirection.SouthEast).getNormalizedLocation()).getPiece().getPieceType() == PieceType.CITY);
            assertTrue(before + 1 == after); // VP +1
            assertTrue(logBefore+1 == logAfter); //Added something to the log
            assertTrue(numCities == numCitiesAfter + 1); //city removed from playerpiece list
            assertTrue(numSettlements == numSettlementsAfter - 1); //settlement was added
            assertTrue(ore == oreAfter + 3); //3 ore removed from hand
            assertTrue(wheat == wheatAfter + 2); //2 wheat removed from hand



        } catch (ServerException e) {
            assertTrue(false);
            e.printStackTrace();
        }


    }

 


}

