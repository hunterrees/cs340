
package canDoTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import shared.ResourceCard;
import shared.definitions.CatanColor;
import shared.definitions.GameState;
import shared.definitions.ResourceType;
import shared.model.GameModel;
import shared.model.TurnTracker;
import shared.model.player.Player;

public class CanOfferTrade {

	@Test
	public void test() {
		System.out.println("Testing can offer trade");
		TurnTracker myTurnTracker = new TurnTracker(0, 0, null, 0);
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
		
		GameModel testGameModel = new GameModel(null, null, players, null, null, myTurnTracker, null, null, 0);
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

