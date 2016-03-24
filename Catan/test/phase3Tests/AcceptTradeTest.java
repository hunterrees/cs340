package phase3Tests;

import client.server.ServerException;
import org.junit.Test;
import server.commands.moves.AcceptTrade;
import shared.definitions.GameState;

import static org.junit.Assert.* ;


/**
 * Created by Brian on 16/3/23.
 */
public class AcceptTradeTest {
    @Test
    public void test() {
        String json = "";

        AcceptTrade command = new AcceptTrade(0, json);







        try {




            command.getModel().getTracker().setGameStatus(GameState.playing);
            command.execute();






        } catch (ServerException e) {
            e.printStackTrace();
        }

    }
}
