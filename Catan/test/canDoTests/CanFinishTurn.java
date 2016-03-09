
package canDoTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import shared.definitions.CatanColor;
import shared.definitions.GameState;
import shared.model.GameModel;
import shared.model.TurnTracker;
import shared.model.player.Player;

public class CanFinishTurn {

	@Test
	public void test() {
		System.out.println("Testing can finish turn");
		TurnTracker myTurnTracker = new TurnTracker(0, 0, GameState.playing, 0);
		myTurnTracker.setCurrentTurnPlayerID(0);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player(1, CatanColor.RED, "player1"));
		players.add(new Player(2, CatanColor.RED, "player1"));
		players.add(new Player(3, CatanColor.RED, "player1"));
		GameModel testGameModel = new GameModel(null, null, players, null, null, myTurnTracker, null, null, 0);
		assertTrue(testGameModel.finishTurn(0));
		assertFalse(testGameModel.finishTurn(1));
		myTurnTracker.setCurrentTurnPlayerID(1);
		assertTrue(testGameModel.finishTurn(1));
		assertFalse(testGameModel.finishTurn(0));
	}

}