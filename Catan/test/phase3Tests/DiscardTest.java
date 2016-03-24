package phase3Tests;

import client.server.ServerException;
import org.junit.Test;
import server.commands.moves.DiscardCards;
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

        try {
            int beforeBrick = p.getPlayerHand().numResourceOfType(ResourceType.BRICK);
            int beforeOre= p.getPlayerHand().numResourceOfType(ResourceType.ORE);;
            int beforeSheep= p.getPlayerHand().numResourceOfType(ResourceType.SHEEP);;
            int beforeWheat= p.getPlayerHand().numResourceOfType(ResourceType.WHEAT);;
            int beforeWood= p.getPlayerHand().numResourceOfType(ResourceType.WOOD);;

            command.execute();

            int afterBrick= p.getPlayerHand().numResourceOfType(ResourceType.BRICK);;
            int afterOre= p.getPlayerHand().numResourceOfType(ResourceType.ORE);;
            int afterSheep= p.getPlayerHand().numResourceOfType(ResourceType.SHEEP);;
            int afterWheat= p.getPlayerHand().numResourceOfType(ResourceType.WHEAT);;
            int afterWood= p.getPlayerHand().numResourceOfType(ResourceType.WOOD);;
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }
}
