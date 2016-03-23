package phase3Tests;

import client.server.ServerException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import server.commands.moves.BuildSettlement;
import server.commands.moves.BuyDevCard;
import shared.definitions.PieceType;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.Bank;
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
public class BuyDevCardTest {
    @Test
    public void test() {

        String json = "{\n" +
                "  \"type\": \"buyDevCard\",\n" +
                "  \"playerIndex\": 0\n" +
                "}";
        BuyDevCard command = new BuyDevCard(0, json);

        Player p = command.getModel().getPlayers().get(0);
        Bank b = command.getModel().getBank();

        try {
            ArrayList<Line> lines = command.getModel().getLog().getLines();
            int logBefore = lines.size();


            int before = p.getPlayerHand().getNewDevelopmentCards().size();
            int bankBefore = b.getDevelopmentCards().size();

            command.setTest(true);
            command.execute();
            int logAfter = command.getModel().getLog().getLines().size();

            int after = p.getPlayerHand().getNewDevelopmentCards().size();
            int bankAfter = b.getDevelopmentCards().size();


            assertTrue(before + 1 == after);
            assertTrue(bankBefore - 1 == bankAfter);
            assertTrue(logBefore+1 == logAfter);



        } catch (ServerException e) {
            e.printStackTrace();
        }


    }


}
