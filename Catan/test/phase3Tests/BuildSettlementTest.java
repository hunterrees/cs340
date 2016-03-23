 package phase3Tests;
  
 import client.server.ServerException;
 import com.google.gson.JsonObject;
 import com.google.gson.JsonParser;
 import org.apache.commons.io.FileUtils;
  import org.junit.Test;
  import server.commands.moves.BuildSettlement;
 import shared.locations.HexLocation;
 import shared.locations.VertexDirection;
  import shared.locations.VertexLocation;
  import shared.model.GameModel;
  import shared.model.map.Map;
  
 import java.io.File;
 import java.io.IOException;
 
 import static org.junit.Assert.* ;
 
 
  /**
   * Created by briantiu on 3/21/16.
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
 
         try {
             bs.setTest(true);
             bs.execute();
 
             assertTrue(map.getVerticies().get(new VertexLocation(new HexLocation(0,0), VertexDirection.NorthEast).getNormalizedLocation()).getPiece() != null);
  
  
  
         } catch (ServerException e) {
             assertTrue(false);
             e.printStackTrace();
         }
 
 
     }
 
    
     
 
  }