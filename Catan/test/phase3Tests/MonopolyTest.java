package phase3Tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;

import client.server.ServerException;
import client.translators.moves.MovesMonopolyTranslator;
import server.ServerManager;
import server.commands.moves.Monopoly;
import shared.definitions.CatanColor;
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

public class MonopolyTest {

	private void setUpGame(GameModel myModel, ResourceType type)
	{
		for (int i = 0; i < 4; i++)
		{
			myModel.getPlayers().get(i).getPlayerHand().addResources(2, type);
		}
	}
	private GameModel createGame(GameState gameState)
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
		setUpGame(myModel, ResourceType.WOOD);
		return myModel;
	}
	@Test
	public void test() {
	
		int index = ServerManager.getInstance().createGame(createGame(GameState.playing));
		int index2 = ServerManager.getInstance().createGame(createGame(GameState.rolling));
		MovesMonopolyTranslator testTranslator = new MovesMonopolyTranslator(0, ResourceType.WOOD);
		String json = testTranslator.translate();
		if (ServerManager.getInstance().getGame(index).getTracker() == null)
		{
			System.out.println("the tracker is null");
		}
		else
		{
			System.out.println(ServerManager.getInstance().getGame(index).getTracker().getGameStatus().toString());
		}
		
		Monopoly myMonopoly = new Monopoly(index, json);
		Monopoly myMonopoly2 = new Monopoly(index2, json);
		try {
			myMonopoly.execute();
			assert(ServerManager.getInstance().getGame(index).getPlayers().get(0).getPlayerHand().numResourceOfType(ResourceType.WOOD) == 8);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			fail("monopoly threw an exception");
			e.printStackTrace();
		}
		try {
			myMonopoly.execute();
			assert(ServerManager.getInstance().getGame(index).getPlayers().get(0).getPlayerHand().numResourceOfType(ResourceType.WOOD) == 0);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			fail("monopoly threw an exception");
			e.printStackTrace();
		}
		
		
	}

}