package phase3Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import server.commands.moves.RollNumber;
import shared.definitions.GameState;
import shared.definitions.ResourceType;
import shared.model.GameModel;
import shared.model.map.Map;
import shared.model.player.Player;
import client.server.ServerException;
import client.translators.moves.MovesRollNumberTranslator;

public class RollNumberTest {

	@Test
	public void test6() {
		String json = (new MovesRollNumberTranslator(0, 6)).translate();
		
		RollNumber rn = new RollNumber(0, json);
		
		GameModel model = rn.getModel();
        Map map = model.getMap();
        Player p = rn.getModel().getPlayers().get(0);
        
        int wheat = p.getPlayerHand().numResourceOfType(ResourceType.WHEAT);
        
        try {
			rn.execute();
		} catch (ServerException e) {
			// TODO Auto-generated catch blocks
			e.printStackTrace();
		}
        
        int wheatAfter = p.getPlayerHand().numResourceOfType(ResourceType.WHEAT);
        
        assertTrue(wheat == wheatAfter - 1);
        
      /*  String json = (new MovesRollNumberTranslator(0, 7)).translate();
		
		RollNumber rn = new RollNumber(0, json);
		
		GameModel model = rn.getModel();
        Map map = model.getMap();
        Player p = rn.getModel().getPlayers().get(0);
        
        
        
        try {
			rn.execute();
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
        assertTrue(model.getGameState() == GameState.robbing);*/
		
		
		
	}
	

}
