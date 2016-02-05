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
import shared.Piece;
import shared.ResourceCard;
import shared.definitions.*;
import shared.locations.*;
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
			parsePort((JsonObject) portArray.get(i), ports);
			// ports.put(port.getLoc(), port);
			
		 }
		 Map map = new Map(hexes, ports);
		 
		 
		 JsonArray roadArray = mapJson.getAsJsonArray("roads");
		 for (int i = 0; i < roadArray.size(); i++){
			 System.out.println(i);
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
		
		
		
		
		
		return map;
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
		if(myResource != null) {
			resource = myResource.getAsString();
		} else {
			resource = "desert";
		}
		JsonPrimitive myNum = jsonHex.getAsJsonPrimitive("number");
		if(!resource.equals("desert")) {
			num = myNum.getAsInt();
		} else num = -1;

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

	public void parsePort(JsonObject jsonPort, HashMap<VertexLocation,Port> ports){
		// Get the information out

		JsonObject location= jsonPort.getAsJsonObject("location");
		JsonPrimitive myX = location.getAsJsonPrimitive("x");
		JsonPrimitive myY = location.getAsJsonPrimitive("y");

		JsonPrimitive myDirection = jsonPort.getAsJsonPrimitive("direction");
		JsonPrimitive myRatio = jsonPort.getAsJsonPrimitive("ratio");
		int ratio = myRatio.getAsInt();

		JsonPrimitive myResource = null;
		String resource = null;

		if(ratio != 3) {
			myResource = jsonPort.getAsJsonPrimitive("resource");
			resource = myResource.getAsString();

		} else {
			resource = "blah";
		}


		int x = myX.getAsInt();
		int y = myY.getAsInt();
		String direction = myDirection.getAsString();


		// Create java objects from the values
		PortType type = null;
		switch(resource) {
			case "wood": type = PortType.WOOD; break;
			case "brick": type = PortType.BRICK; break;
			case "sheep": type = PortType.SHEEP; break;
			case "wheat": type = PortType.WHEAT; break;
			case "ore": type = PortType.ORE; break;
			default: type = PortType.THREE;
		}

/*		VertexDirection vl = null;
		switch(direction) {
			case "NW": vl = VertexDirection.NorthWest; break;
			case "NE": vl = VertexDirection.NorthEast; break;
			case "E": vl = VertexDirection.East; break;
			case "SE": vl = VertexDirection.SouthEast; break;
			case "SW": vl = VertexDirection.SouthWest; break;
			case "W": vl = VertexDirection.West; break;
			default: System.out.println("Error! The EdgeLocation doesn't exist!");
		}*/

		VertexDirection vd1 = null;
		VertexDirection vd2 = null;

		switch(direction) {
			case "N": vd1 = VertexDirection.NorthWest;
					vd2 = VertexDirection.NorthEast;
					break;
			case "NE": vd1 = VertexDirection.NorthEast;
					vd2 = VertexDirection.East;
					break;
			case "SE": vd1 = VertexDirection.East;
					vd2 = VertexDirection.SouthEast;
					break;
			case "S": vd1 = VertexDirection.SouthEast;
					vd2 = VertexDirection.SouthWest;
					break;
			case "SW": vd1 = VertexDirection.SouthWest;
					vd2 = VertexDirection.West;
					break;
			case "NW": vd1 = VertexDirection.West;
					vd2 = VertexDirection.NorthWest;
					break;
			default: System.out.println("Error! The direction doesn't exist!");
		}


		Port port1 = new Port(type);
		port1.setLoc(new VertexLocation(new HexLocation(x,y), vd1));

		Port port2 = new Port(type);
		port2.setLoc(new VertexLocation(new HexLocation(x,y), vd2));

		ports.put(port1.getLoc(), port1);
		ports.put(port2.getLoc(), port2);






		//return port;
	}
	public void parseAndAddRoad(JsonObject jsonRoad, Map map){
		JsonPrimitive myID = jsonRoad.getAsJsonPrimitive("owner");
		JsonObject location = jsonRoad.getAsJsonObject("location");

		JsonPrimitive myX = location.getAsJsonPrimitive("x");
		JsonPrimitive myY = location.getAsJsonPrimitive("y");
		JsonPrimitive myDirection = location.getAsJsonPrimitive("direction");


		int playerID = myID.getAsInt();
		int x = myX.getAsInt();
		int y = myY.getAsInt();
		String direction = myDirection.getAsString();

		EdgeDirection ed = null;
		switch(direction) {
			case "N": ed = EdgeDirection.North; break;
			case "NE": ed = EdgeDirection.North; break;
			case "SE": ed = EdgeDirection.North; break;
			case "S": ed = EdgeDirection.North; break;
			case "SW": ed = EdgeDirection.North; break;
			case "NW": ed = EdgeDirection.North; break;
			default: System.out.println("Error! EdgeDirection doesn't exist");
		}


		Piece road = new Piece(PieceType.ROAD,null,null,playerID);

		Edge edge = map.getEdges().get(new EdgeLocation(new HexLocation(x,y),ed).getNormalizedLocation());
		edge.setPiece(road);



//////////////////
		if(edge.getPiece() == null) {
			System.out.println("should never come here");
		}

		int numRoads = 0;

		for (Edge e : map.getEdges().values()) {
			if(e.getPiece() != null) {
				numRoads++;
			}
		}System.out.println("numRoads = " + numRoads);
///////////////
	}
	//here
	public void parseAndAddSettlement(JsonObject jsonSettlement, Map map){
		int playerID;
		int x; 
		int y;
		String direction;
		VertexDirection dir;
		PieceType type = PieceType.SETTLEMENT;
		
		
		JsonPrimitive pID = jsonSettlement.getAsJsonPrimitive("owner");
		playerID = pID.getAsInt();
		
		JsonObject location = jsonSettlement.getAsJsonObject("location");
		JsonPrimitive myX = location.getAsJsonPrimitive("x");
		x = myX.getAsInt();
		JsonPrimitive myY = location.getAsJsonPrimitive("y");
		y = myY.getAsInt();
		
		JsonPrimitive myDirection = location.getAsJsonPrimitive("direction");
		direction = myDirection.getAsString();
		
		switch (direction){
		case "NW": dir = VertexDirection.NorthWest; break;
		case "NE": dir = VertexDirection.NorthEast; break;
		case "W": dir = VertexDirection.West; break;
		case "E": dir = VertexDirection.East; break;
		case "SW": dir = VertexDirection.SouthWest; break;
		case "SE": dir = VertexDirection.SouthEast; break;
		default: dir = null;
		}
		
		HexLocation hexLoc = new HexLocation(x, y);
		VertexLocation vertLoc = new VertexLocation(hexLoc, dir).getNormalizedLocation();
		map.getVerticies().get(vertLoc).setPiece(new Piece(type, null, null, playerID));

		



	}
	public void parseAndAddCity(JsonObject jsonCity, Map map){
		int playerID;
		int x; 
		int y;
		String direction;
		VertexDirection dir;
		PieceType type = PieceType.CITY;
		
		
		JsonPrimitive pID = jsonCity.getAsJsonPrimitive("owner");
		playerID = pID.getAsInt();
		
		JsonObject location = jsonCity.getAsJsonObject("location");
		JsonPrimitive myX = location.getAsJsonPrimitive("x");
		x = myX.getAsInt();
		JsonPrimitive myY = location.getAsJsonPrimitive("y");
		y = myY.getAsInt();
		
		JsonPrimitive myDirection = jsonCity.getAsJsonPrimitive("direction");
		direction = myDirection.getAsString();
		
		switch (direction){
		case "NW": dir = VertexDirection.NorthWest; break;
		case "NE": dir = VertexDirection.NorthEast; break;
		case "W": dir = VertexDirection.West; break;
		case "E": dir = VertexDirection.East; break;
		case "SW": dir = VertexDirection.SouthWest; break;
		case "SE": dir = VertexDirection.SouthEast; break;
		default: dir = null;
		}
		
		HexLocation hexLoc = new HexLocation(x, y);
		VertexLocation vertLoc = new VertexLocation(hexLoc, dir).getNormalizedLocation();
		map.getVerticies().get(vertLoc).setPiece(new Piece(type, null, null, playerID));
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
				receive.add(type);
			}
		}else if(amount < 0){
			amount = -1 * amount;
			for(int i = 0; i < amount; i++){
				offer.add(type);
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
