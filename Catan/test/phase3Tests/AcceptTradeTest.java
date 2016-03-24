package phase3Tests;

import client.server.ServerException;
import org.junit.Test;
import server.commands.moves.AcceptTrade;
import shared.definitions.GameState;
import shared.definitions.ResourceType;
import shared.model.player.Player;
import shared.model.trade.TradeOffer;

import static org.junit.Assert.* ;


/**
 * Created by Brian on 16/3/23.
 */
public class AcceptTradeTest {
    @Test
    public void test() {
        String json = "";

        AcceptTrade command = new AcceptTrade(0, json);

        Player p1 = command.getModel().getPlayers().get(0);
        p1.getPlayerHand().addResources(10, ResourceType.BRICK);
        p1.getPlayerHand().addResources(10, ResourceType.ORE);
        p1.getPlayerHand().addResources(10, ResourceType.WOOD);
        p1.getPlayerHand().addResources(10, ResourceType.WHEAT);
        p1.getPlayerHand().addResources(10, ResourceType.SHEEP);

        Player p2 = command.getModel().getPlayers().get(1);
        p2.getPlayerHand().addResources(10, ResourceType.BRICK);
        p2.getPlayerHand().addResources(10, ResourceType.ORE);
        p2.getPlayerHand().addResources(10, ResourceType.WOOD);
        p2.getPlayerHand().addResources(10, ResourceType.WHEAT);
        p2.getPlayerHand().addResources(10, ResourceType.SHEEP);


        TradeOffer trade = new TradeOffer(0, 1, null, null);





        try {




            command.getModel().getTracker().setGameStatus(GameState.playing);
            command.execute();






        } catch (ServerException e) {
            e.printStackTrace();
        }

    }
}
