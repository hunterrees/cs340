
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
import shared.definitions.GameState;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;

public class CanBuyDevCard {

	@Test
	public void test() {
		
		//testing
		TurnTracker myTracker = new TurnTracker(0, 0, GameState.playing, 0);
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
		
		//Test with appropriate Resources
		//
		assertTrue(testGameModel.buyDevCard(0));
		
		players.get(0).getPlayerHand().removeResources(1, ResourceType.SHEEP);
		
		//Test not enough sheep
		assertFalse(testGameModel.buyDevCard(0));
		
		players.get(0).getPlayerHand().addResources(2, ResourceType.SHEEP);
		players.get(0).getPlayerHand().removeResources(1, ResourceType.ORE);
		//Test not enough ore
		assertFalse(testGameModel.buyDevCard(0));
		
		players.get(0).getPlayerHand().addResources(1, ResourceType.ORE);
		players.get(0).getPlayerHand().removeResources(1, ResourceType.WHEAT);
		//test not enough wheat
		assertFalse(testGameModel.buyDevCard(0));
	}

}
