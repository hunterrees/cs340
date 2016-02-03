package translators;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import map.Map;
import model.Bank;
import model.Chat;
import model.GameModel;
import model.Log;
import model.TurnTracker;
import player.Player;
import shared.DevelopmentCard;
import shared.ResourceCard;
import shared.definitions.DevCardType;
import shared.definitions.GameState;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import trade.TradeOffer;

/**
 * This class should parse through the model that is returned
 */
public class ModelTranslator {
	
	public ModelTranslator(){
	
	};
	
	//takes in an object, casts it a JsonObject then parses through it
	//What do I do with the log and chat and winner?
	public GameModel getModelfromJSON(String json){
		Gson gson = new Gson();
		JsonObject root = gson.fromJson(json, JsonObject.class);
		JsonObject deckJson = root.getAsJsonObject("deck");
		
		JsonObject chatJson = root.getAsJsonObject("chat");
		Chat chat = buildChat(chatJson);
		
		JsonObject logJson = root.getAsJsonObject("log");
		Log log = buildLog(logJson);
		
		JsonObject mapJson = root.getAsJsonObject("map");
		Map map = buildMap(mapJson);
		
		JsonObject bankJson = root.getAsJsonObject("bank");
		Bank bank = buildBank(deckJson, bankJson);
		
		JsonObject playersJson = root.getAsJsonObject("players");
		ArrayList<Player> players = buildPlayers(playersJson);
		
		JsonObject turnTrackerJson = root.getAsJsonObject("turnTracker");
		TurnTracker turnTracker = buildTurnTracker(turnTrackerJson);
		
		JsonObject tradeOfferJson = root.getAsJsonObject("tradeOffer");
		TradeOffer tradeOffer = null;
		if(tradeOfferJson != null){
			tradeOffer = buildTradeOffer(tradeOfferJson);
		}
		
		JsonPrimitive winnerJson = root.getAsJsonPrimitive("winner");
		int winner = winnerJson.getAsInt();
		JsonPrimitive versionJson = root.getAsJsonPrimitive("version");
		int version = versionJson.getAsInt();
		
		HexLocation robber = null; //how to get the robber location?
		
		GameModel model = new GameModel(map, bank, players, robber, tradeOffer, turnTracker, log, chat, winner);
		model.setVersion(version);
		return model;
	};
	
	public Bank buildBank(JsonObject deckJson, JsonObject bankJson){
		ArrayList<DevelopmentCard> devCards = buildDevCards(deckJson);
		ArrayList<ResourceCard> resources = buildResources(bankJson);
		Bank bank = new Bank(resources, devCards);
		return bank;
	}
	
	public ArrayList<ResourceCard> buildResources(JsonObject bankJson){
		JsonPrimitive brickJson = bankJson.getAsJsonPrimitive("brick");
		int brick = brickJson.getAsInt();
		
		JsonPrimitive woodJson = bankJson.getAsJsonPrimitive("wood");
		int wood = woodJson.getAsInt();
		
		JsonPrimitive sheepJson = bankJson.getAsJsonPrimitive("sheep");
		int sheep = sheepJson.getAsInt();
		
		JsonPrimitive wheatJson = bankJson.getAsJsonPrimitive("wheat");
		int wheat = wheatJson.getAsInt();
		
		JsonPrimitive oreJson = bankJson.getAsJsonPrimitive("ore");
		int ore = oreJson.getAsInt();
		
		ArrayList<ResourceCard> resources = new ArrayList<ResourceCard>();
		addResources(resources, new ResourceCard(ResourceType.BRICK), brick);
		addResources(resources, new ResourceCard(ResourceType.WOOD), wood);
		addResources(resources, new ResourceCard(ResourceType.SHEEP), sheep);
		addResources(resources, new ResourceCard(ResourceType.WHEAT), wheat);
		addResources(resources, new ResourceCard(ResourceType.ORE), ore);
		return resources;
	}
	
	public ArrayList<DevelopmentCard> buildDevCards(JsonObject deckJson){
		JsonPrimitive yearOfPlentyJson = deckJson.getAsJsonPrimitive("yearOfPlenty");
		int yearOfPlenty = yearOfPlentyJson.getAsInt();
		
		JsonPrimitive monopolyJson = deckJson.getAsJsonPrimitive("monopoly");
		int monopoly = monopolyJson.getAsInt();
		
		JsonPrimitive soldierJson = deckJson.getAsJsonPrimitive("soldier");
		int soldier = soldierJson.getAsInt();
		
		JsonPrimitive roadBldgJson = deckJson.getAsJsonPrimitive("roadBuilding");
		int roadBuilding = roadBldgJson.getAsInt();
		
		JsonPrimitive monumentJson = deckJson.getAsJsonPrimitive("monument");
		int monument = monumentJson.getAsInt();
		
		ArrayList<DevelopmentCard> devCards = new ArrayList<DevelopmentCard>();
		addDevCards(devCards, new DevelopmentCard(DevCardType.YEAR_OF_PLENTY), yearOfPlenty);
		addDevCards(devCards, new DevelopmentCard(DevCardType.MONOPOLY), monopoly);
		addDevCards(devCards, new DevelopmentCard(DevCardType.SOLDIER), soldier);
		addDevCards(devCards, new DevelopmentCard(DevCardType.ROAD_BUILD), roadBuilding);
		addDevCards(devCards, new DevelopmentCard(DevCardType.MONUMENT), monument);
		return devCards;
	}
	
	private void addResources(ArrayList<ResourceCard> resources, ResourceCard resourceToAdd, int amount){
		for(int i = 0; i < amount; i++){
			resources.add(resourceToAdd);
		}
	}
	
	private void addDevCards(ArrayList<DevelopmentCard> devCards, DevelopmentCard devToAdd, int amount){
		for(int i = 0; i < amount; i++){
			devCards.add(devToAdd);
		}
	}
	
	public Map buildMap(JsonObject mapJson){
		return null;
	}
	
	public ArrayList<Player> buildPlayers(JsonObject playersJson){
		return null;
	}
	
	public TurnTracker buildTurnTracker(JsonObject turnTrackerJson){
		JsonPrimitive statusJson = turnTrackerJson.getAsJsonPrimitive("status");
		String statusString = statusJson.getAsString();
		GameState status;
		if(statusString.equals("Playing")){
			status = GameState.playing;
		}else if(statusString.equals("Rolling")){
			status = GameState.rolling;
		}else if(statusString.equals("Robbing")){
			status = GameState.robbing;
		}else{
			status = GameState.discarding;
		}
		
		JsonPrimitive currentTurnJson = turnTrackerJson.getAsJsonPrimitive("currentTurn");
		int currentTurn = currentTurnJson.getAsInt();
		
		JsonPrimitive longestRoadJson = turnTrackerJson.getAsJsonPrimitive("longestRoad");
		int longestRoad = longestRoadJson.getAsInt();
		
		JsonPrimitive largestArmyJson = turnTrackerJson.getAsJsonPrimitive("largestArmy");
		int largestArmy = largestArmyJson.getAsInt();
		TurnTracker turnTracker = new TurnTracker(longestRoad, largestArmy, status, currentTurn);
		return turnTracker;
	}
	
	public TradeOffer buildTradeOffer(JsonObject tradeOfferJson){
		return null;
	}
	
	public Log buildLog(JsonObject logJson){
		return null;
	}
	
	public Chat buildChat(JsonObject chatJson){
		
		return null;
	}
}
