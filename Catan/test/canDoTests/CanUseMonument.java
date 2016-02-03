/*
package canDoTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.GameModel;
import model.TurnTracker;
import player.Player;
import shared.DevelopmentCard;
import shared.ResourceCard;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;

public class CanUseMonument {

	@Test
	public void test() {
		TurnTracker myTracker = new TurnTracker();
		myTracker.setCurrentTurnPlayerID(0);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player(1, CatanColor.RED, "player1"));
		
		for (int i = 0; i < 4; i++)
		{
			players.get(0).getPlayerHand().addNewDevelopmentCard(DevCardType.MONUMENT);
		}
		for (int i = 0; i < 4; i++)
		{
			players.get(0).getPlayerHand().addOldDevelopmentCard(DevCardType.MONUMENT);
		}
		HexLocation robberLocation = new HexLocation(2,3);
		GameModel testGameModel = new GameModel(null, players, robberLocation, null, myTracker);
		//5 old and 5 new
		assertTrue(testGameModel.monument(0));
	}

}
*/
