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
		System.out.println("Testing can roll number");
		TurnTracker myTurnTracker = new TurnTracker(0, 0, null, 0);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player(1, CatanColor.RED, "player1"));
		myTurnTracker.setCurrentTurnPlayerID(0);
		myTurnTracker.setGameStatus(GameState.rolling);
		GameModel testGameModel = new GameModel(null, null, players, null, null, myTurnTracker, null, null, 0);
		
		//result should return true;
		assertTrue(testGameModel.rollNumber(0));
		//mismatched IDs
		assertFalse(testGameModel.rollNumber(1));
		
		myTurnTracker.setGameStatus(GameState.discarding);
		testGameModel = new GameModel(null, null, players, null, null, myTurnTracker, null, null, 0);
		
		//incorrect phase
		assertFalse(testGameModel.rollNumber(0));
		
		myTurnTracker.setGameStatus(GameState.rolling);
		myTurnTracker.setCurrentTurnPlayerID(1);
		testGameModel = new GameModel(null, null, players, null, null, myTurnTracker, null, null, 0);
		//mismatched IDs
		assertFalse(testGameModel.rollNumber(0));
	}

}

