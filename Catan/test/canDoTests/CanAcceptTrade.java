package canDoTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.Bank;
import model.GameModel;
import model.TurnTracker;
import player.Player;
import shared.ResourceCard;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import trade.TradeOffer;

public class CanAcceptTrade {
//tests
	@Test
	//testing
	public void test() {
		
		TurnTracker myTracker = new TurnTracker(0, 0, null, 0);
		myTracker.setCurrentTurnPlayerID(0);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player(1, CatanColor.RED, "player1"));
		ArrayList<ResourceCard> deckToSet = new ArrayList<ResourceCard>();
		deckToSet.add(new ResourceCard(ResourceType.SHEEP));
		deckToSet.add(new ResourceCard(ResourceType.WHEAT));
		deckToSet.add(new ResourceCard(ResourceType.ORE));
		players.get(0).getPlayerHand().setResourceCards(deckToSet);
		players.add(new Player(2, CatanColor.RED, "player1"));
		players.add(new Player(3, CatanColor.RED, "player1"));
		players.add(new Player(4, CatanColor.RED, "player1"));
		HexLocation robberLocation = new HexLocation(2,3);
		Bank myBank = new Bank();
		myBank.generateStartingDevelopmentCards();
		GameModel testGameModel = new GameModel(null, myBank, players, robberLocation, null, myTracker, null, null, 0);
		//there is no trade offer
		assertFalse(testGameModel.acceptTrade(0));
		ArrayList<ResourceType> cardsToTrade = new ArrayList<ResourceType>();
		cardsToTrade.add(ResourceType.SHEEP);
		cardsToTrade.add(ResourceType.ORE);
		TradeOffer myTradeOffer = new TradeOffer(0,1,cardsToTrade, cardsToTrade);
		testGameModel = new GameModel(null, myBank, players, robberLocation, myTradeOffer, myTracker, null, null, 0);
		assertTrue(testGameModel.acceptTrade(0));
		assertFalse(testGameModel.acceptTrade(1));
		cardsToTrade = new ArrayList<ResourceType>();
		cardsToTrade.add(ResourceType.SHEEP);
		cardsToTrade.add(ResourceType.BRICK);
		myTradeOffer.setResourceOffered(cardsToTrade);
		testGameModel = new GameModel(null, myBank, players, robberLocation, myTradeOffer, myTracker, null, null, 0);
		assertFalse(testGameModel.acceptTrade(0));
	}

}
