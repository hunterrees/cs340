package phase3Tests;

import client.server.ServerException;
import org.junit.Test;
import server.commands.moves.FinishTurn;
import shared.definitions.GameState;

import static org.junit.Assert.*;


/**
 * Created by Brian on 16/3/23.
 */
public class FinishTurnTest {
    @Test
    public void test() {
        String json = "{\n" +
                "  \"type\": \"finishTurn\",\n" +
                "  \"playerIndex\": 0\n" +
                "}";
        FinishTurn command = new FinishTurn(0, json);


        try {
            int beforeTurn = command.getModel().getTracker().getCurrentTurnPlayerID();
            command.getModel().getTracker().setGameStatus(GameState.firstRound);
            command.execute();
            int afterTurn = command.getModel().getTracker().getCurrentTurnPlayerID();

            assertTrue(beforeTurn != afterTurn);

        } catch (ServerException e) {
            e.printStackTrace();
        }

    }
}
