package phase3Tests;

import client.server.ServerException;
import org.junit.Test;

import server.ServerManager;
import server.commands.moves.MaritimeTrade;
import shared.definitions.GameState;
import shared.definitions.ResourceType;
import shared.model.player.Player;

import static org.junit.Assert.*;


/**
 * Created by Brian on 16/3/23.
 */
public class MaritimeTradeTest {
    @Test
    public void test() {
    	System.out.println("Testing Maritime Trade Command");
    	ServerManager.getInstance().resetManager();
        String json = "{\n" +
                "  \"type\": \"maritimeTrade\",\n" +
                "  \"playerIndex\": 0,\n" +
                "  \"ratio\": 3,\n" +
                "  \"inputResource\": \"brick\",\n" +
                "  \"outputResource\": \"wood\"\n" +
                "}";

        MaritimeTrade command = new MaritimeTrade(0, json);

        Player p = command.getModel().getPlayers().get(0);
        p.getPlayerHand().addResources(3, ResourceType.BRICK);

        try {
            int playerBeforeBrick = p.getPlayerHand().numResourceOfType(ResourceType.BRICK);
            int playerBeforeWood = p.getPlayerHand().numResourceOfType(ResourceType.WOOD);
            int bankBeforeBrick = command.getModel().getBank().numResourceRemaining(ResourceType.BRICK);
            int bankBeforeWood = command.getModel().getBank().numResourceRemaining(ResourceType.WOOD);

            command.getModel().getTracker().setGameStatus(GameState.playing);
            command.execute();

            int playerAfterBrick = p.getPlayerHand().numResourceOfType(ResourceType.BRICK);
            int playerAfterWood = p.getPlayerHand().numResourceOfType(ResourceType.WOOD);
            int bankAfterBrick = command.getModel().getBank().numResourceRemaining(ResourceType.BRICK);
            int bankAfterWood = command.getModel().getBank().numResourceRemaining(ResourceType.WOOD);

            assertTrue(playerBeforeBrick - 3 == playerAfterBrick);
            assertTrue(playerBeforeWood + 1 == playerAfterWood);

            assertTrue(bankBeforeBrick + 3 == bankAfterBrick);
            assertTrue(bankBeforeWood - 1 == bankAfterWood);


        } catch (ServerException e) {
            e.printStackTrace();
        }
    }
}
