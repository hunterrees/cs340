package phase3Tests;

import client.server.ServerException;
import org.junit.Test;
import org.junit.internal.ArrayComparisonFailure;

import server.ServerManager;
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
    	ServerManager.getInstance().testing();
        System.out.println("Testing AcceptTrade Command");
        String json = "{\n" +
                "  \"type\": \"acceptTrade\",\n" +
                "  \"playerIndex\": \"0\",\n" +
                "  \"willAccept\": \"true\"\n" +
                "}";

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


        for(int i = 0; i < 1; i++) giving.add(ResourceType.BRICK);
        for(int i = 0; i < 2; i++) giving.add(ResourceType.ORE);
        for(int i = 0; i < 3; i++) giving.add(ResourceType.SHEEP);
        for(int i = 0; i < 4; i++) giving.add(ResourceType.WHEAT);
        for(int i = 0; i < 5; i++) giving.add(ResourceType.WOOD);

        for(int i = 0; i < 5; i++) receiving.add(ResourceType.BRICK);
        for(int i = 0; i < 4; i++) receiving.add(ResourceType.ORE);
        for(int i = 0; i < 3; i++) receiving.add(ResourceType.SHEEP);
        for(int i = 0; i < 2; i++) receiving.add(ResourceType.WHEAT);
        for(int i = 0; i < 1; i++) receiving.add(ResourceType.WOOD);

        TradeOffer trade = new TradeOffer(0, 1, giving, receiving);
        command.getModel().setTradeOffer(trade);



        try {

            int beforeBrick1 = p1.getPlayerHand().numResourceOfType(ResourceType.BRICK);
            int beforeOre1 = p1.getPlayerHand().numResourceOfType(ResourceType.ORE);;
            int beforeSheep1 = p1.getPlayerHand().numResourceOfType(ResourceType.SHEEP);;
            int beforeWheat1 =  p1.getPlayerHand().numResourceOfType(ResourceType.WHEAT);;
            int beforeWood1 = p1.getPlayerHand().numResourceOfType(ResourceType.WOOD);;

            int beforeBrick2 = p2.getPlayerHand().numResourceOfType(ResourceType.BRICK);
            int beforeOre2= p2.getPlayerHand().numResourceOfType(ResourceType.ORE);;
            int beforeSheep2= p2.getPlayerHand().numResourceOfType(ResourceType.SHEEP);;
            int beforeWheat2= p2.getPlayerHand().numResourceOfType(ResourceType.WHEAT);;
            int beforeWood2= p2.getPlayerHand().numResourceOfType(ResourceType.WOOD);;


            command.getModel().getTracker().setGameStatus(GameState.playing);
            command.execute();

            int afterBrick1= p1.getPlayerHand().numResourceOfType(ResourceType.BRICK);;
            int afterOre1= p1.getPlayerHand().numResourceOfType(ResourceType.ORE);;
            int afterSheep1= p1.getPlayerHand().numResourceOfType(ResourceType.SHEEP);;
            int afterWheat1= p1.getPlayerHand().numResourceOfType(ResourceType.WHEAT);;
            int afterWood1= p1.getPlayerHand().numResourceOfType(ResourceType.WOOD);;

            int afterBrick2= p2.getPlayerHand().numResourceOfType(ResourceType.BRICK);;
            int afterOre2= p2.getPlayerHand().numResourceOfType(ResourceType.ORE);;
            int afterSheep2= p2.getPlayerHand().numResourceOfType(ResourceType.SHEEP);;
            int afterWheat2= p2.getPlayerHand().numResourceOfType(ResourceType.WHEAT);;
            int afterWood2= p2.getPlayerHand().numResourceOfType(ResourceType.WOOD);;

            assertTrue(beforeBrick1-5+1 == afterBrick1);
            assertTrue(beforeOre1-4+2 == afterOre1);
            assertTrue(beforeSheep1-3+3 == afterSheep1);
            assertTrue(beforeWheat1-2+4 == afterWheat1);
            assertTrue(beforeWood1-1+5 == afterWood1);

            assertTrue(beforeBrick2-1+5 == afterBrick2);
            assertTrue(beforeOre2-2+4 == afterOre2);
            assertTrue(beforeSheep2-3+3 == afterSheep2);
            assertTrue(beforeWheat2-4+2 == afterWheat2);
            assertTrue(beforeWood2-5+1 == afterWood2);






        } catch (ServerException e) {
            e.printStackTrace();
        }

    }
}
