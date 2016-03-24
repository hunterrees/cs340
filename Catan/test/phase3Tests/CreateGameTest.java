package phase3Tests;

import client.server.ServerException;
import org.junit.Test;
import server.commands.games.CreateGame;
import shared.locations.HexLocation;
import shared.model.map.Map;
import shared.model.map.TerrainHex;

import static org.junit.Assert.* ;


/**
 * Created by Brian on 16/3/22.
 */
public class CreateGameTest {



    @Test
    public void test() {
    	System.out.println("Testing CreateGame Command");
        String json = "{\n" +
                "  \"randomTiles\": true,\n" +
                "  \"randomNumbers\": true,\n" +
                "  \"randomPorts\": true,\n" +
                "  \"name\": testGame\n" +
                "}";

        CreateGame command = new CreateGame(0, json);

        try {
            command.execute();

            Map map = command.getModel().getMap();

            assertTrue(map != null);

            int water = 0;
            int desert = 0;
            int wood = 0;
            int brick = 0;
            int sheep = 0;
            int wheat = 0;
            int ore = 0;

            HexLocation desertLoc = null;

            for(java.util.Map.Entry<HexLocation, TerrainHex> entry : map.getHexes().entrySet()) {
                switch(entry.getValue().getType()) {
                    case WATER: water++;  assertTrue(entry.getValue().getNumber() == -1); break;
                    case DESERT: desert++; assertTrue(entry.getValue().getNumber() < 1); desertLoc = entry.getKey();  break;
                    case WOOD: wood++;     assertTrue(entry.getValue().getNumber() > 1);   break;
                    case BRICK: brick++;   assertTrue(entry.getValue().getNumber() > 1);   break;
                    case SHEEP: sheep++;   assertTrue(entry.getValue().getNumber() > 1);   break;
                    case WHEAT: wheat++;   assertTrue(entry.getValue().getNumber() > 1);   break;
                    case ORE: ore++;       assertTrue(entry.getValue().getNumber() > 1);   break;
                    default:               assertTrue(false);
                }


            }
            assertTrue(water == 18);
            assertTrue(desert == 1);
            assertTrue(wood == 4);
            assertTrue(brick == 3);
            assertTrue(sheep == 4);
            assertTrue(wheat == 4);
            assertTrue(ore == 3);

            HexLocation robberLoc = map.getRobberLocation();
            assertTrue(robberLoc.equals(desertLoc));

        } catch (ServerException e) {
            e.printStackTrace();
        }

    }
}