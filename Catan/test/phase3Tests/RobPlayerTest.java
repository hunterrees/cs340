package phase3Tests;

import static org.junit.Assert.*;

import client.server.ServerException;
import org.junit.Test;
import server.commands.moves.RobPlayer;
import shared.Piece;
import shared.ResourceCard;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.map.Map;
import shared.model.map.Vertex;
import shared.model.player.Player;

import java.util.ArrayList;

/**
 * Created by Brian on 16/3/23.
 */
public class RobPlayerTest {
    @Test
    public void test() {
        String json = "{\n" +
                "  \"type\": \"robPlayer\",\n" +
                "  \"playerIndex\": 0,\n" +
                "  \"victimIndex\": 1,\n" +
                "  \"location\": {\n" +
                "    \"x\": 0,\n" +
                "    \"y\": 0\n" +
                "  }\n" +
                "}";

        RobPlayer command = new RobPlayer(0, json);

        Player p1 = command.getModel().getPlayers().get(0);
        Player p2 = command.getModel().getPlayers().get(1);

        ArrayList<ResourceCard> p1Cards = p1.getPlayerHand().getResourceCards();
        ArrayList<ResourceCard> p2Cards = p2.getPlayerHand().getResourceCards();

        p1.getPlayerHand().addResources(1, ResourceType.BRICK);
        p2.getPlayerHand().addResources(1, ResourceType.BRICK);


        Map map = command.getModel().getMap();

        Vertex loc = map.getVerticies().get(new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthEast).getNormalizedLocation());
        loc.setPiece(new Piece(PieceType.SETTLEMENT, null, null, 1));


        try {
            int p1Before = p1Cards.size();
            int p2Before = p2Cards.size();

            command.execute();

            int p1After = p1Cards.size();
            int p2After = p2Cards.size();

            assertTrue(p1Before == p1After - 1);
            assertTrue(p2Before == p2After + 1);


        } catch (ServerException e) {
            e.printStackTrace();
        }


    }
}
