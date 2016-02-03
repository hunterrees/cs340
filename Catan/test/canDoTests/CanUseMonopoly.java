package canDoTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.GameModel;
import model.TurnTracker;
import player.Player;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.locations.HexLocation;

public class CanUseMonopoly {

	@Test
	public void test() {
		TurnTracker myTracker = new TurnTracker();
		myTracker.setCurrentTurnPlayerID(0);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player(1, CatanColor.RED, "player1"));
		
		
		players.get(0).getPlayerHand().addOldDevelopmentCard(DevCardType.MONOPOLY);
		HexLocation robberLocation = new HexLocation(2,3);
		GameModel testGameModel = new GameModel(null, players, robberLocation, null, myTracker);
		//5 old and 5 new
		assertTrue(testGameModel.monopoly(0));
		players = new ArrayList<Player>();
		players.add(new Player(1, CatanColor.RED, "player1"));
		testGameModel = new GameModel(null, players, robberLocation, null, myTracker);
		assertFalse(testGameModel.monopoly(0));
	}

}
