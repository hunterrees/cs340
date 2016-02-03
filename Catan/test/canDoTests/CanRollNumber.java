package canDoTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.GameModel;
import model.TurnTracker;
import player.Player;
import shared.definitions.CatanColor;
import shared.definitions.GameState;

public class CanRollNumber {

	@Test
	public void test() {
		TurnTracker myTurnTracker = new TurnTracker();
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player(1, CatanColor.RED, "player1"));
		myTurnTracker.setCurrentTurnPlayerID(0);
		myTurnTracker.setGameStatus(GameState.rolling);
		GameModel testGameModel = new GameModel(null, players, null, null, myTurnTracker);
		
		//result should return true;
		assertTrue(testGameModel.rollNumber(0));
		//mismatched IDs
		assertFalse(testGameModel.rollNumber(1));
		
		myTurnTracker.setGameStatus(GameState.discarding);
		testGameModel = new GameModel(null, players, null, null, myTurnTracker);
		
		//incorrect phase
		assertFalse(testGameModel.rollNumber(0));
		
		myTurnTracker.setGameStatus(GameState.rolling);
		myTurnTracker.setCurrentTurnPlayerID(1);
		testGameModel = new GameModel(null, players, null, null, myTurnTracker);
		//mismached IDs
		assertFalse(testGameModel.rollNumber(0));
	}

}
