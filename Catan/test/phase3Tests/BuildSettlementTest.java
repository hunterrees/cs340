 package phase3Tests;
  
 import client.server.ServerException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

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
   * Created by briantiu on 3/21/16.sdf
   */
  public class BuildSettlementTest {
      @Test
      public void test() {

         BuildSettlement bs = new BuildSettlement(0, "{\n" +
                 "  \"type\": \"buildSettlement\",\n" +
                 "  \"playerIndex\": 0,\n" +
                 "  \"vertexLocation\": {\n" +
                 "    \"x\": 0,\n" +
                 "    \"y\": 0,\n" +
                 "    \"direction\": \"NE\"\n" +
                 "  },\n" +
                 "  \"free\": true\n" +
                 "}");
 
         GameModel model = bs.getModel();
         Map map = model.getMap();
         Player p = bs.getModel().getPlayers().get(0);
         
         try {
        	 ArrayList<Line> lines = bs.getModel().getLog().getLines();
             int logBefore = lines.size();
             int numSettlements = p.getNumSettlements();
             int before = p.getVictoryPoints();
             int wood = p.getPlayerHand().numResourceOfType(ResourceType.WOOD);
             
             bs.setTest(true);
             bs.execute();
 
             int after = p.getVictoryPoints();
             int logAfter = bs.getModel().getLog().getLines().size();
             
             int numSettlementsAfter = p.getNumSettlements();
             int woodAfter = p.getPlayerHand().numResourceOfType(ResourceType.WOOD);
             
             assertTrue(map.getVerticies().get(new VertexLocation(new HexLocation(0,0), VertexDirection.NorthEast).getNormalizedLocation()).getPiece() != null);
             assertTrue(map.getVerticies().get(new VertexLocation(new HexLocation(0,0), VertexDirection.NorthEast).getNormalizedLocation()).getPiece().getPieceType() == PieceType.SETTLEMENT);
             assertTrue(before + 1 == after); // VP +1
             assertTrue(logBefore+1 == logAfter); //Added something to the log
             assertTrue(numSettlements == numSettlementsAfter + 1); //settlement was removed
            // assertTrue(wood == woodAfter + 1); //wood resource removed but it was free so dont check this...
  
  
         } catch (ServerException e) {
             assertTrue(false);
             e.printStackTrace();
         }
 
 
     }
 
    
     
 
  }