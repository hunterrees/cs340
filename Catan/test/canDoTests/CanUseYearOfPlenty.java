/*
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
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;

public class CanUseYearOfPlenty {

	@Test
	public void test() {
		TurnTracker myTracker = new TurnTracker();
		myTracker.setCurrentTurnPlayerID(0);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player(1, CatanColor.RED, "player1"));
		players.get(0).getPlayerHand().addOldDevelopmentCard(DevCardType.YEAR_OF_PLENTY);
		HexLocation robberLocation = new HexLocation(2,3);
		
		GameModel testGameModel = new GameModel(null, players, robberLocation, null, myTracker);
		
		//testing with same resourcetype
		assertTrue(testGameModel.yearOfPlenty(0, ResourceType.ORE, ResourceType.ORE));
		//testing with different resourcetypes
		assertTrue(testGameModel.yearOfPlenty(0, ResourceType.ORE, ResourceType.WHEAT));
		
		//making bank with limited resources
		ArrayList<ResourceCard> resourceDeck = new ArrayList<ResourceCard>();
		resourceDeck.add(new ResourceCard(ResourceType.ORE));
		resourceDeck.add(new ResourceCard(ResourceType.ORE));
		resourceDeck.add(new ResourceCard(ResourceType.WOOD));
		resourceDeck.add(new ResourceCard(ResourceType.WHEAT));
		Bank.BANK.setResources(resourceDeck);
		assertTrue(testGameModel.yearOfPlenty(0, ResourceType.ORE, ResourceType.ORE));
		assertTrue(testGameModel.yearOfPlenty(0, ResourceType.ORE, ResourceType.WOOD));
		assertFalse(testGameModel.yearOfPlenty(0, ResourceType.WOOD, ResourceType.WOOD));
		assertFalse(testGameModel.yearOfPlenty(0, ResourceType.SHEEP, ResourceType.ORE));
	}

}
*/
