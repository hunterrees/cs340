package serverTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import map.Edge;
import map.Map;
import map.TerrainHex;
import map.Vertex;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Bank;
import model.Chat;
import model.Log;
import model.TurnTracker;
import shared.definitions.DevCardType;
import shared.definitions.GameState;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import trade.TradeOffer;
import translators.ModelTranslator;

public class InitializeModel {

	ModelTranslator translator = new ModelTranslator();

	@Test
	public void bankTest() throws IOException {
		JsonObject json = toJson("translatorTests/bankJson.txt");
		JsonObject bankJson = json.getAsJsonObject("bank");
		JsonObject deckJson = json.getAsJsonObject("deck");
		Bank bank = translator.buildBank(deckJson, bankJson);

		int brick = bank.numResourceRemaining(ResourceType.BRICK);
		assertTrue(brick == 24);
		int wood = bank.numResourceRemaining(ResourceType.WOOD);
		assertTrue(wood == 20);
		int sheep = bank.numResourceRemaining(ResourceType.SHEEP);
		assertTrue(sheep == 21);
		int wheat = bank.numResourceRemaining(ResourceType.WHEAT);
		assertTrue(wheat == 27);
		int ore = bank.numResourceRemaining(ResourceType.ORE);
		assertTrue(ore == 25);

		int yearOfPlenty = bank.numDevCardsRemaining(DevCardType.YEAR_OF_PLENTY);
		assertTrue(yearOfPlenty == 2);
		int monopoly = bank.numDevCardsRemaining(DevCardType.MONOPOLY);
		assertTrue(monopoly == 2);
		int soldier = bank.numDevCardsRemaining(DevCardType.SOLDIER);
		assertTrue(soldier == 14);
		int roadBuilding = bank.numDevCardsRemaining(DevCardType.ROAD_BUILD);
		assertTrue(roadBuilding == 2);
		int monument = bank.numDevCardsRemaining(DevCardType.MONUMENT);
		assertTrue(monument == 4);
	}

	@Test
	public void mapTest() throws IOException {
		JsonObject json = toJson("translatorTests/mapJson.txt");
		JsonObject mapJson = json.getAsJsonObject("map");
		Map map = translator.buildMap(mapJson);

		// Testing hexes
		int numHexes = map.getHexes().size();
		assertTrue(numHexes == 37);

		int numWater = 0;

		int numWood = 0;

		int numBrick = 0;

		int numWool = 0;

		int numWheat = 0;

		int numOre = 0;

		int numDesert = 0;

		for (TerrainHex hex : map.getHexes().values()) {
			switch (hex.getType()) {
				case WOOD:
					numWood++;
					break;
				case BRICK:
					numBrick++;
					break;
				case SHEEP:
					numWool++;
					break;
				case WHEAT:
					numWheat++;
					break;
				case ORE:
					numOre++;
					break;
				case WATER:
					numWater++;
					break;
				case DESERT:
					numDesert++;
					break;
				default:
					System.out.println("Error! The hex type doesn't exist!");
			}
		}
		assertTrue(numWood == 4);
		assertTrue(numBrick == 3);
		assertTrue(numWool == 4);
		assertTrue(numWheat == 4);
		assertTrue(numOre == 3);
		assertTrue(numWater == 18);
		assertTrue(numDesert == 1);



		// Testing settlements and cities
		int numSettlements = 0;

		int numCities = 0;

		for (Vertex v : map.getVerticies().values()) {
			if(v.getPiece() != null) {
				if(v.getPiece().getPieceType() == PieceType.SETTLEMENT) {
					numSettlements++;
				} else if(v.getPiece().getPieceType() == PieceType.CITY) {
					numCities++;
				} else {
					System.out.println("Error! Building type doesn't exist");
				}
			}
		}

		assertTrue(numSettlements == 9);
		assertTrue(numCities == 0);


		// Testing roads
		int numRoads = 0;

		for (Edge e : map.getEdges().values()) {
			if(e.getPiece() != null) {
				numRoads++;
			}
		}
		assertTrue(numRoads == 9);
	}

	@Test
	public void chatTest() throws IOException{
		JsonObject json = toJson("translatorTests/chatJson.txt");
		JsonObject chatJson = json.getAsJsonObject("chat");
		Chat chat = translator.buildChat(chatJson);

		assertTrue(chat.getLines().size() == 1);
		String source = chat.getLines().get(0).getSource();
		String message = chat.getLines().get(0).getMessage();
		assertTrue(message.equals("test"));
		assertTrue(source.equals("Sam"));
	}

	@Test
	public void logTest() throws IOException{
		JsonObject json = toJson("translatorTests/logJson.txt");
		JsonObject logJson = json.getAsJsonObject("log");
		Log log = translator.buildLog(logJson);

		assertTrue(log.getLines().size() == 4);
		for(int i = 0; i < log.getLines().size(); i++){
			String source = log.getLines().get(i).getSource();
			assertTrue(source.equals("Mark"));
		}
		String message = log.getLines().get(0).getMessage();
		assertTrue(message.equals("Mark built a settlement"));
	}

	@Test
	public void tradeOfferTest() throws IOException{
		JsonObject json = toJson("translatorTests/tradeOfferJson.txt");
		JsonObject tradeOfferJson = json.getAsJsonObject("tradeOffer");
		TradeOffer tradeOffer = translator.buildTradeOffer(tradeOfferJson);

		int receiver = tradeOffer.getAcceptingPlayerID();
		assertTrue(receiver == 1);
		int sender = tradeOffer.getRequestingPlayerID();
		assertTrue(sender == 0);
		ArrayList<ResourceType> resourcesOffered = tradeOffer.getResourceOffered();
		assertTrue(resourcesOffered.size() == 1);
		ArrayList<ResourceType> resourcesDesired = tradeOffer.getResourceDesired();
		assertTrue(resourcesDesired.size() == 2);
	}

	@Test
	public void turnTrackerTest() throws IOException{
		JsonObject json = toJson("translatorTests/turnTrackerJson.txt");
		JsonObject turnTrackerJson = json.getAsJsonObject("turnTracker");
		TurnTracker turnTracker = translator.buildTurnTracker(turnTrackerJson);

		int currentTurn = turnTracker.getCurrentTurnPlayerID();
		assertTrue(currentTurn == 1);
		int longestRoad = turnTracker.getLongestRoadplayerID();
		assertTrue(longestRoad == -1);
		int largestArmy = turnTracker.getLargestArmyPlayerID();
		assertTrue(largestArmy == -1);
		GameState state = turnTracker.getGameStatus();
		assertTrue(state == GameState.rolling);
	}

	private JsonObject toJson(String fileName) throws IOException{
		File file = new File(fileName);
		String jsonText = FileUtils.readFileToString(file);
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(jsonText);
		return json;
	}


}
