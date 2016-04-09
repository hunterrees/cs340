package phase3Tests;

import static org.junit.Assert.*;

import java.awt.event.MouseAdapter;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;

import client.server.ServerException;
import client.translators.moves.MovesFinishTurnTranslator;
import client.translators.moves.MovesMonopolyTranslator;
import server.ServerManager;
import server.commands.moves.FinishTurn;
import server.commands.moves.Monopoly;
import server.commands.moves.Monument;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.GameState;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.model.Bank;
import shared.model.Chat;
import shared.model.GameModel;
import shared.model.Line;
import shared.model.Log;
import shared.model.TurnTracker;
import shared.model.map.Map;
import shared.model.player.Player;

public class MonumentTest {

	private void setUpGame(GameModel myModel, DevCardType type, int oldOrNew)
	{
		if (oldOrNew == 0)
		{
			for (int i = 0; i < 4; i++)
			{
				myModel.getPlayers().get(i).getPlayerHand().addNewDevelopmentCard(type);;
			}
		}
		else
		{
			for (int i = 0; i < 4; i++)
			{
				myModel.getPlayers().get(i).getPlayerHand().addOldDevelopmentCard(type);;
			}
		}
	}
	private GameModel createGame(GameState gameState, DevCardType type, int oldOrNew)
	{
		ArrayList<Player> myPlayers= new ArrayList<Player>();
		myPlayers.add(new Player(0, CatanColor.RED, "bob"));
		myPlayers.add(new Player(1, CatanColor.BLUE, "jed"));
		myPlayers.add(new Player(2, CatanColor.YELLOW, "dan"));
		myPlayers.add(new Player(3, CatanColor.GREEN, "ron"));
		Bank myBank = new Bank();
		ArrayList<Line> myLines = new ArrayList<Line>();
		myLines.add(new Line("", ""));
		Log myLog = new Log(myLines);
		Chat myChat = new Chat(myLines);
		Map myMap = new Map();
		HexLocation robberLocation = new HexLocation(2,2);
		TurnTracker myTurnTracker = new TurnTracker(-1, -1, gameState, 0);
		GameModel myModel = new GameModel(myMap, myBank, myPlayers, robberLocation, null, myTurnTracker, myLog, myChat, -1);
		setUpGame(myModel, type, oldOrNew);
		return myModel;
	}
	@Test
	public void test() {
		ServerManager.getInstance().testing();
		System.out.println("Testing Monument Command");
		int index = ServerManager.getInstance().createGame(createGame(GameState.playing, DevCardType.MONUMENT, 0));
		int index2 = ServerManager.getInstance().createGame(createGame(GameState.rolling, DevCardType.MONUMENT, 0));
	    int index3 = ServerManager.getInstance().createGame(createGame(GameState.playing, DevCardType.MONUMENT, 1));
		MovesMonopolyTranslator testTranslator = new MovesMonopolyTranslator(0, ResourceType.WOOD);
		String json = testTranslator.translate();
		
		Monument myMonopoly = new Monument(index, json);
		Monument myMonopoly2 = new Monument(index2, json);
		Monument myMonopoly3 = new Monument(index3, json);
		try {
			//myMonopoly.getModel().getTracker().setGameStatus(GameState.playing);
			myMonopoly.execute();
			assert(ServerManager.getInstance().getGame(index).getPlayers().get(0).getVictoryPoints() == 1);
			assert(ServerManager.getInstance().getGame(index).getPlayers().get(0).getPlayerHand().numOldDevCardRemaining(DevCardType.MONUMENT) == 0);
			//should be able to monument multiple times
			myMonopoly.execute();
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			fail("monument threw an exception");
			e.printStackTrace();
		}
		try {
			myMonopoly2.execute();
			fail("monument threw an exception");
		} catch (ServerException e) {
		}
		
		try {
			myMonopoly3.execute();
			assert(ServerManager.getInstance().getGame(index).getPlayers().get(0).getVictoryPoints() == 2);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			fail("monument threw an exception");
			e.printStackTrace();
		}
	}

}