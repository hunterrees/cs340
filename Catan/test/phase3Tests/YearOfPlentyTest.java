package phase3Tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;

import client.server.ServerException;
import client.translators.moves.MovesMonopolyTranslator;
import client.translators.moves.MovesYearOfPlentyTranslator;
import server.ServerManager;
import server.commands.moves.Monopoly;
import server.commands.moves.YearOfPlenty;
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

public class YearOfPlentyTest {

	private void setUpGame(GameModel myModel, ResourceType type)
	{
		for (int i = 0; i < 4; i++)
		{
			myModel.getPlayers().get(i).getPlayerHand().addResources(2, type);
			myModel.getPlayers().get(i).getPlayerHand().addOldDevelopmentCard(DevCardType.YEAR_OF_PLENTY);
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
		ServerManager.getInstance().testing();
		System.out.println("Testing YearOfPlenty Command");
		int index = ServerManager.getInstance().createGame(createGame(GameState.playing));
		int index2 = ServerManager.getInstance().createGame(createGame(GameState.rolling));
		MovesYearOfPlentyTranslator testTranslator = new MovesYearOfPlentyTranslator(0, ResourceType.WOOD, ResourceType.BRICK);
		String json = testTranslator.translate();
		
		
		YearOfPlenty myMonopoly = new YearOfPlenty(index, json);
		YearOfPlenty myMonopoly2 = new YearOfPlenty(index2, json);
		try {
			myMonopoly.execute();
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			fail("year of plenty threw an exception");
			e.printStackTrace();
		}
		assert(ServerManager.getInstance().getGame(index).getPlayers().get(0).getPlayerHand().numResourceOfType(ResourceType.WOOD) == 3
				&& ServerManager.getInstance().getGame(index).getPlayers().get(0).getPlayerHand().numResourceOfType(ResourceType.BRICK) == 1);
		try {
			myMonopoly.execute();
			fail("shouldn't be able to use 2 dev cards in 1 turn");
		} catch (ServerException e) {
		}
		try {
			myMonopoly2.execute();
			fail("year of plenty threw an exception");
		} catch (ServerException e) {
		}
		
		
	}

}