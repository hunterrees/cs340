package translators;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import map.Edge;
import map.Map;
import map.Port;
import map.TerrainHex;
import map.Vertex;
import model.Bank;
import model.Chat;
import model.GameModel;
import model.Line;
import model.Log;
import model.TurnTracker;
import player.Player;
import shared.DevelopmentCard;
import shared.ResourceCard;
import shared.definitions.DevCardType;
import shared.definitions.GameState;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
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
		 HashMap<HexLocation, TerrainHex> hexes = new HashMap<HexLocation, TerrainHex>();
		 //HashMap<EdgeLocation, Edge> edges = new HashMap<EdgeLocation, Edge>();
		// HashMap<VertexLocation, Vertex> verticies = new HashMap<VertexLocation, Vertex>();
		 HashMap<VertexLocation, Port> ports = new HashMap<VertexLocation, Port>();
		 
		 JsonArray hexArray = mapJson.getAsJsonArray("hexes");
		 for (int i = 0; i < hexArray.size(); i++){
			 TerrainHex hex = parseHex((JsonObject)hexArray.get(i));
			 hexes.put(hex.getLocation(), hex);
		 }
		 
		 
		 JsonArray portArray = mapJson.getAsJsonArray("ports");
		 for (int i = 0; i < portArray.size(); i++){
			 Port port = parsePort((JsonObject)portArray.get(i));
			 ports.put(port.getLoc(), port);
			
		 }
		 Map map = new Map(hexes, ports);
		 
		 
		 JsonArray roadArray = mapJson.getAsJsonArray("roads");
		 for (int i = 0; i < roadArray.size(); i++){
			 parseAndAddRoad((JsonObject)roadArray.get(i), map);
		 }
		 
		 JsonArray settlementArray = mapJson.getAsJsonArray("settlements");
		 for (int i = 0; i < settlementArray.size(); i++){
			 parseAndAddSettlement((JsonObject)settlementArray.get(i), map);
		 }
		 
		 JsonArray cityArray = mapJson.getAsJsonArray("cities");
		 for (int i = 0; i < cityArray.size(); i++){
			 parseAndAddCity((JsonObject)cityArray.get(i), map);
		 }
		
		
		
		
		
		return null;
	}
	
	public TerrainHex parseHex(JsonObject jsonHex){
		int x; 
		int y;
		String resource;
		int num;
		HexType type;
		
		JsonObject location = jsonHex.getAsJsonObject("location");
		JsonPrimitive myX = location.getAsJsonPrimitive("x");
		x = myX.getAsInt();
		JsonPrimitive myY = location.getAsJsonPrimitive("y");
		y = myY.getAsInt();
		
		JsonPrimitive myResource = jsonHex.getAsJsonPrimitive("resource");
		resource = myResource.getAsString();
		JsonPrimitive myNum = jsonHex.getAsJsonPrimitive("number");
		num = myNum.getAsInt();
		if (resource == null){
			resource = "desert";
		}
		switch(resource){
		
		case "brick": type = HexType.BRICK; break;
		case "wood" : type = HexType.WOOD; break;
		case "wheat" : type = HexType.WHEAT; break;
		case "sheep" : type = HexType.SHEEP; break;
		case "ore" : type = HexType.ORE; break;
		default : type = HexType.DESERT; break;
		}
		
		HexLocation loc = new HexLocation(x, y);
		TerrainHex hex = new TerrainHex(loc, type, num);
		
		
		
		return hex;
		
	}
	public Port parsePort(JsonObject jsonPort){
		return null;
	}
	public void parseAndAddRoad(JsonObject jsonRoad, Map map){
		
	}
	public void parseAndAddSettlement(JsonObject jsonSettlement, Map map){
		
		
		
		
		
		
	}
	public void parseAndAddCity(JsonObject jsonCity, Map map){
		
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
		JsonPrimitive senderJson = tradeOfferJson.getAsJsonPrimitive("sender");
		int sender = senderJson.getAsInt();
		
		JsonPrimitive receiverJson = tradeOfferJson.getAsJsonPrimitive("receiver");
		int receiver = receiverJson.getAsInt();
		
		JsonObject offerJson = tradeOfferJson.getAsJsonObject("offer");
		JsonPrimitive brickJson = offerJson.getAsJsonPrimitive("brick");
		int brick = brickJson.getAsInt();
		
		JsonPrimitive woodJson = offerJson.getAsJsonPrimitive("wood");
		int wood = woodJson.getAsInt();
		
		JsonPrimitive sheepJson = offerJson.getAsJsonPrimitive("sheep");
		int sheep = sheepJson.getAsInt();
		
		JsonPrimitive wheatJson = offerJson.getAsJsonPrimitive("wheat");
		int wheat = wheatJson.getAsInt();
		
		JsonPrimitive oreJson = offerJson.getAsJsonPrimitive("ore");
		int ore = oreJson.getAsInt();
		
		ArrayList<ResourceType> resourcesOffered = new ArrayList<ResourceType>();
		ArrayList<ResourceType> resourcesDesired = new ArrayList<ResourceType>();
		addTradeResources(resourcesOffered, resourcesDesired, ResourceType.BRICK, brick);
		addTradeResources(resourcesOffered, resourcesDesired, ResourceType.WOOD, wood);
		addTradeResources(resourcesOffered, resourcesDesired, ResourceType.SHEEP, sheep);
		addTradeResources(resourcesOffered, resourcesDesired, ResourceType.WHEAT, wheat);
		addTradeResources(resourcesOffered, resourcesDesired, ResourceType.ORE, ore);
		
		TradeOffer tradeOffer = new TradeOffer(sender, receiver, resourcesOffered, resourcesDesired);
		return tradeOffer;
	}
	
	private void addTradeResources(ArrayList<ResourceType> offer, ArrayList<ResourceType> receive, ResourceType type, int amount){
		if(amount > 0){
			for(int i = 0; i < amount; i++){
				offer.add(type);
			}
		}else if(amount < 0){
			amount = -1 * amount;
			for(int i = 0; i < amount; i++){
				receive.add(type);
			}
		}
	}
	
	public Log buildLog(JsonObject logJson){
		ArrayList<Line> lines = new ArrayList<Line>();
		JsonArray linesJson = logJson.getAsJsonArray("lines");
		for(int i = 0; i < linesJson.size(); i++){
			JsonObject lineJson = (JsonObject) linesJson.get(i);
			JsonPrimitive sourceJson = lineJson.getAsJsonPrimitive("source");
			String source = sourceJson.getAsString();
			JsonPrimitive messageJson = lineJson.getAsJsonPrimitive("message");
			String message = messageJson.getAsString();
			lines.add(new Line(source, message));
		}
		Log log = new Log(lines);
		return log;
	}
	
	public Chat buildChat(JsonObject chatJson){
		ArrayList<Line> lines = new ArrayList<Line>();
		JsonArray linesJson = chatJson.getAsJsonArray("lines");
		for(int i = 0; i < linesJson.size(); i++){
			JsonObject lineJson = (JsonObject) linesJson.get(i);
			JsonPrimitive sourceJson = lineJson.getAsJsonPrimitive("source");
			String source = sourceJson.getAsString();
			JsonPrimitive messageJson = lineJson.getAsJsonPrimitive("message");
			String message = messageJson.getAsString();
			lines.add(new Line(source, message));
		}
		Chat chat = new Chat(lines);
		return chat;
	}
}
