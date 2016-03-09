package canDoTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import shared.ResourceCard;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.GameState;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.model.GameModel;
import shared.model.TurnTracker;
import shared.model.map.Map;
import shared.model.map.TerrainHex;
import shared.model.player.Player;

public class CanUseSoldier {

	@Test
	public void test() {
		System.out.println("Testing can use soldier");
		TurnTracker myTracker = new TurnTracker(0, 0, GameState.playing, 0);
		HashMap<HexLocation, TerrainHex> hexes = new HashMap<HexLocation, TerrainHex>();
		hexes.put(new HexLocation(10,10), new TerrainHex(new HexLocation(10,10), HexType.WATER, 6));
		hexes.put(new HexLocation(1,1), new TerrainHex(new HexLocation(1,1), HexType.BRICK, 6));
		hexes.put(new HexLocation(2,3), new TerrainHex(new HexLocation(2,3), HexType.ORE, 6));
		Map myMap = new Map();
		myMap.setHexes(hexes);
		myTracker.setCurrentTurnPlayerID(1);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player(1, CatanColor.RED, "player1"));
		players.add(new Player(2, CatanColor.RED, "player2"));
		ArrayList<ResourceCard> deckToSet = new ArrayList<ResourceCard>();
		deckToSet.add(new ResourceCard(ResourceType.SHEEP));
		deckToSet.add(new ResourceCard(ResourceType.WHEAT));
		deckToSet.add(new ResourceCard(ResourceType.ORE));
		players.get(0).getPlayerHand().setResourceCards(deckToSet);
		players.get(1).getPlayerHand().addOldDevelopmentCard(DevCardType.SOLDIER);
		players.get(1).setHasPlayedDevCard(false);
		HexLocation robberLocation = new HexLocation(2,3);
		GameModel testGameModel = new GameModel(myMap, null, players, robberLocation, null, myTracker, null, null, 0);
		HexLocation placeToRob = (new HexLocation(1,1));
		//test with new loction
		assertTrue(testGameModel.robPlayer(placeToRob, 0));
		
		
		assertTrue(testGameModel.soldier(1, placeToRob, 0));
		//removing the soldier card
		players = new ArrayList<Player>();
		players.add(new Player(1, CatanColor.RED, "player1"));
		players.add(new Player(2, CatanColor.RED, "player2"));
		deckToSet = new ArrayList<ResourceCard>();
		deckToSet.add(new ResourceCard(ResourceType.SHEEP));
		deckToSet.add(new ResourceCard(ResourceType.WHEAT));
		deckToSet.add(new ResourceCard(ResourceType.ORE));
		players.get(0).getPlayerHand().setResourceCards(deckToSet);
		players.get(1).setHasPlayedDevCard(false);
		testGameModel = new GameModel(myMap, null, players, robberLocation, null, myTracker, null, null, 0);
		assertFalse(testGameModel.soldier(1, placeToRob, 0));
		
		placeToRob = new HexLocation(2,3);
		
		assertFalse(testGameModel.robPlayer(placeToRob, 0));
		placeToRob = new HexLocation(10,10);
		//test trying to rob water
		assertFalse(testGameModel.robPlayer(placeToRob, 0));
		//test with old location
		placeToRob = new HexLocation(2,3);
		assertFalse(testGameModel.robPlayer(placeToRob, 0));
		
		deckToSet = new ArrayList<ResourceCard>();
		players.get(0).getPlayerHand().setResourceCards(deckToSet);
		testGameModel = new GameModel(myMap, null, players, robberLocation, null, myTracker, null, null, 0);
		//test with no no resources to steal
		placeToRob = new HexLocation(1,1);
		assertFalse(testGameModel.robPlayer(placeToRob, 0));
		
	}

}
