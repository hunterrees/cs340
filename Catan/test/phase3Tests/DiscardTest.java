package phase3Tests;

import client.server.ServerException;
import org.junit.Test;
import server.commands.moves.DiscardCards;
import shared.definitions.GameState;
import shared.definitions.ResourceType;
import shared.model.player.Player;

import static org.junit.Assert.* ;


/**
 * Created by Brian on 16/3/23.
 */
public class DiscardTest {

    @Test
    public void test() {
        System.out.println("Testing Discard Command");

        String json = "{\n" +
                "  \"type\": \"discardCards\",\n" +
                "  \"playerIndex\": 0,\n" +
                "  \"discardedCards\": {\n" +
                "    \"brick\": 1,\n" +
                "    \"ore\": 2,\n" +
                "    \"sheep\": 3,\n" +
                "    \"wheat\": 4,\n" +
                "    \"wood\": 5\n" +
                "  }\n" +
                "}";

        DiscardCards command = new DiscardCards(0, json);

        Player p = command.getModel().getPlayers().get(0);
        p.getPlayerHand().addResources(10, ResourceType.BRICK);
        p.getPlayerHand().addResources(10, ResourceType.ORE);
        p.getPlayerHand().addResources(10, ResourceType.WOOD);
        p.getPlayerHand().addResources(10, ResourceType.WHEAT);
        p.getPlayerHand().addResources(10, ResourceType.SHEEP);


        try {
            int beforeBrick = p.getPlayerHand().numResourceOfType(ResourceType.BRICK);
            int beforeOre= p.getPlayerHand().numResourceOfType(ResourceType.ORE);;
            int beforeSheep= p.getPlayerHand().numResourceOfType(ResourceType.SHEEP);;
            int beforeWheat= p.getPlayerHand().numResourceOfType(ResourceType.WHEAT);;
            int beforeWood= p.getPlayerHand().numResourceOfType(ResourceType.WOOD);;

            command.getModel().getTracker().setGameStatus(GameState.discarding);
            command.execute();

            int afterBrick= p.getPlayerHand().numResourceOfType(ResourceType.BRICK);;
            int afterOre= p.getPlayerHand().numResourceOfType(ResourceType.ORE);;
            int afterSheep= p.getPlayerHand().numResourceOfType(ResourceType.SHEEP);;
            int afterWheat= p.getPlayerHand().numResourceOfType(ResourceType.WHEAT);;
            int afterWood= p.getPlayerHand().numResourceOfType(ResourceType.WOOD);;

            assertTrue(beforeBrick -1== afterBrick);
            assertTrue(beforeOre -2== afterOre);
            assertTrue(beforeSheep -3== afterSheep);
            assertTrue(beforeWheat -4== afterWheat);
            assertTrue(beforeWood -5== afterWood);



        } catch (ServerException e) {
            e.printStackTrace();
        }
    }
}
