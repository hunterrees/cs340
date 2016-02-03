package canDoTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.GameModel;
import model.TurnTracker;
import player.Player;
import shared.ResourceCard;
import shared.definitions.CatanColor;
import shared.definitions.GameState;
import shared.definitions.ResourceType;

public class CanOfferTrade {

	@Test
	public void test() {
		TurnTracker myTurnTracker = new TurnTracker();
		myTurnTracker.setGameStatus(GameState.playing);
		myTurnTracker.setCurrentTurnPlayerID(0);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player(1, CatanColor.RED, "player1"));
		ArrayList<ResourceCard> deckToSet = new ArrayList<ResourceCard>();
		deckToSet.add(new ResourceCard(ResourceType.SHEEP));
		deckToSet.add(new ResourceCard(ResourceType.SHEEP));
		deckToSet.add(new ResourceCard(ResourceType.SHEEP));
		deckToSet.add(new ResourceCard(ResourceType.SHEEP));
		deckToSet.add(new ResourceCard(ResourceType.WHEAT));
		deckToSet.add(new ResourceCard(ResourceType.ORE));
		players.get(0).getPlayerHand().setResourceCards(deckToSet);
		
		GameModel testGameModel = new GameModel(null, players, null, null, myTurnTracker);
		ArrayList<ResourceType> resourceTypes = new ArrayList<ResourceType>();
		resourceTypes.add(ResourceType.SHEEP);
		resourceTypes.add(ResourceType.SHEEP);
		resourceTypes.add(ResourceType.SHEEP);
		//Withing trade limits
		assertTrue(testGameModel.offerTrade(0, resourceTypes));
		resourceTypes.add(ResourceType.ORE);
		//should still work with two different resources
		assertTrue(testGameModel.offerTrade(0, resourceTypes));
		resourceTypes.add(ResourceType.SHEEP);
		resourceTypes.add(ResourceType.SHEEP);
		//Outside trade limits
		assertFalse(testGameModel.offerTrade(0, resourceTypes));
	}

}
