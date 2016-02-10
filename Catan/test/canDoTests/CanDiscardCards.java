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
import shared.locations.HexLocation;

public class CanDiscardCards {

	@Test
	public void test() {
		System.out.println("Testing can discard cards");
		TurnTracker myTurnTracker = new TurnTracker(0, 0, GameState.discarding, 0);
		myTurnTracker.setCurrentTurnPlayerID(0);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player(1, CatanColor.RED, "player1"));
		ArrayList<ResourceCard> deckToSet = new ArrayList<ResourceCard>();
		deckToSet.add(new ResourceCard(ResourceType.SHEEP));
		deckToSet.add(new ResourceCard(ResourceType.SHEEP));
		deckToSet.add(new ResourceCard(ResourceType.SHEEP));
		deckToSet.add(new ResourceCard(ResourceType.WHEAT));
		deckToSet.add(new ResourceCard(ResourceType.ORE));
		deckToSet.add(new ResourceCard(ResourceType.WHEAT));
		deckToSet.add(new ResourceCard(ResourceType.ORE));
		deckToSet.add(new ResourceCard(ResourceType.ORE));
		players.get(0).getPlayerHand().setResourceCards(deckToSet);
		players.add(new Player(2, CatanColor.RED, "player1"));
		players.add(new Player(3, CatanColor.RED, "player1"));
		players.add(new Player(4, CatanColor.RED, "player1"));
		HexLocation robberLocation = new HexLocation(2,3);
		
		GameModel testGameModel = new GameModel(null, null, players, robberLocation, null, myTurnTracker, null, null, 0);
		
		//cards to discard
		ArrayList<ResourceType> cardsToDiscard = new ArrayList<ResourceType>();
		cardsToDiscard.add((ResourceType.SHEEP));
		cardsToDiscard.add((ResourceType.SHEEP));
		//trying to discard to out of 3 sheep
		assertTrue(testGameModel.discardCards(0,cardsToDiscard));
		cardsToDiscard.add((ResourceType.ORE));
		cardsToDiscard.add((ResourceType.ORE));
		cardsToDiscard.add((ResourceType.ORE));
		cardsToDiscard.add((ResourceType.ORE));
		//trying to discard 4 out of 3 sheep
		assertFalse(testGameModel.discardCards(0,cardsToDiscard));
		cardsToDiscard = new ArrayList<ResourceType>();
		cardsToDiscard.add((ResourceType.BRICK));
		cardsToDiscard.add((ResourceType.SHEEP));
		players.get(0).getPlayerHand().removeResources(1, ResourceType.BRICK);
		//less than 7 cards in hand
		assertFalse(testGameModel.discardCards(0, cardsToDiscard));
	}

}

