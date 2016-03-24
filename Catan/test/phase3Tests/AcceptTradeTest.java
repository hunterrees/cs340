package phase3Tests;

import client.server.ServerException;
import org.junit.Test;
import org.junit.internal.ArrayComparisonFailure;
import server.commands.moves.AcceptTrade;
import shared.ResourceCard;
import shared.definitions.GameState;
import shared.definitions.ResourceType;
import shared.model.player.Player;
import shared.model.trade.TradeOffer;

import java.util.ArrayList;

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


        ArrayList<ResourceType> giving = new ArrayList<>();
        ArrayList<ResourceType> receiving = new ArrayList<>();

        TradeOffer trade = new TradeOffer()

        for(int i = 0; i < 1; i++) giving.add(ResourceType.BRICK);
        for(int i = 0; i < 2; i++) giving.add(ResourceType.ORE);
        for(int i = 0; i < 3; i++) giving.add(ResourceType.WOOD);
        for(int i = 0; i < 4; i++) giving.add(ResourceType.WHEAT);
        for(int i = 0; i < 5; i++) giving.add(ResourceType.SHEEP);

        for(int i = 0; i < 5; i++) receiving.add(ResourceType.BRICK);
        for(int i = 0; i < 4; i++) receiving.add(ResourceType.ORE);
        for(int i = 0; i < 3; i++) receiving.add(ResourceType.WOOD);
        for(int i = 0; i < 2; i++) receiving.add(ResourceType.WHEAT);
        for(int i = 0; i < 1; i++) receiving.add(ResourceType.SHEEP);




        try {




            command.getModel().getTracker().setGameStatus(GameState.playing);
            command.execute();






        } catch (ServerException e) {
            e.printStackTrace();
        }

    }
}
