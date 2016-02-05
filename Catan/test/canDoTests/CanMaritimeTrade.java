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
import shared.definitions.PortType;
import shared.definitions.ResourceType;

public class CanMaritimeTrade {

	@Test
	public void test() {
		TurnTracker myTurnTracker = new TurnTracker(0, 0, GameState.playing, 0);
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
		
		assertTrue(testGameModel.maritimeTrade(0, ResourceType.SHEEP) == 4);
		players.get(0).addPort(PortType.SHEEP);
		players.get(0).getPlayerHand().removeResources(1, ResourceType.SHEEP);
		assertTrue(testGameModel.maritimeTrade(0, ResourceType.SHEEP) == 2);
		players.get(0).removePort(PortType.SHEEP);
		players.get(0).addPort(PortType.THREE);
		assertTrue(testGameModel.maritimeTrade(0, ResourceType.SHEEP) == 3);
		players.get(0).getPlayerHand().removeResources(2, ResourceType.SHEEP);
		assertTrue(testGameModel.maritimeTrade(0, ResourceType.SHEEP) == -1);
	}

}

