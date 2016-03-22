package phase3Tests;

import org.junit.Test;
import server.commands.moves.BuildSettlement;
import shared.locations.VertexLocation;
import shared.model.map.Map;

/**
 * Created by briantiu on 3/21/16.
 */
public class BuildSettlementTest {
    @Test
    public void test() {
        Map map = new Map();

        BuildSettlement bs = new BuildSettlement(1, makejsonandputithere);

        bs.execute();

        map.getVerticies().get(new VertexLocation())
    }



}
