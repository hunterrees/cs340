package phase3Tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;

import client.server.ServerException;
import client.translators.moves.MovesMonopolyTranslator;
import client.translators.moves.MovesOfferTradeTranslator;
import client.translators.moves.MovesYearOfPlentyTranslator;
import server.ServerManager;
import server.commands.moves.Monopoly;
import server.commands.moves.OfferTrade;
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

public class OfferTradeTest {

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
	
		int index = ServerManager.getInstance().createGame(createGame(GameState.playing));
		
		ArrayList<ResourceType> resourceGive = new ArrayList<ResourceType>();
		resourceGive.add(ResourceType.BRICK);
		ArrayList<ResourceType> resourceGet = new ArrayList<ResourceType>();
		resourceGet.add(ResourceType.ORE);
		MovesOfferTradeTranslator testTranslator = new MovesOfferTradeTranslator(0, resourceGive, resourceGet, 1);
		String json = testTranslator.translate();
		
		OfferTrade myOfferTrade = new OfferTrade(index, json);
		try {
			myOfferTrade.execute();
			//check that the trade offer isn't null
			assert(ServerManager.getInstance().getGame(index).getTradeOffer() != null);
			//check that the input resource is correct
			assert(ServerManager.getInstance().getGame(index).getTradeOffer().getResourceDesired().contains(ResourceType.ORE));
			//check that the output resource is correct
			assert(ServerManager.getInstance().getGame(index).getTradeOffer().getResourceDesired().contains(ResourceType.BRICK));
			
			//testing
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			fail("year of plenty threw an exception");
			e.printStackTrace();
		}
		
	}

}