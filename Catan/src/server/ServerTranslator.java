package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import client.server.ServerException;
import client.translators.moves.ResourceList;
import shared.ResourceCard;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.HexType;
import shared.definitions.PieceType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.*;
import shared.model.map.*;
import shared.model.player.*;
import shared.model.trade.TradeOffer;

public class ServerTranslator implements Serializable{
	
	private GameModel model;
	
	public ServerTranslator(GameModel model){
		this.model = model;
	}
	/**
	 * Translates the game model into json and returns it as a string
	 * @return
	 * @throws ServerException 
	 */
	public String translate() throws ServerException{
		try{
			StringBuilder result = new StringBuilder();
			result.append("{");
			result.append(buildBank(model.getBank()));
			result.append(buildChat(model.getChat()));
			result.append(buildLog(model.getLog()));
			result.append(buildMap(model.getMap()));
			result.append(buildPlayers(model.getPlayers()));
			result.append(buildTradeOffer(model.getTradeOffer()));
			result.append(buildTurnTracker(model.getTracker()));
			String version = "\n\"version\":" + model.getVersion() + ",\n";
			String winner = "\"winner\":" + model.getWinner() + "\n}";
			result.append(version);
			result.append(winner);
			return result.toString();
		}catch(Exception e){
			e.printStackTrace();
			throw new ServerException(e.getMessage());
		}
	}
	
	public String buildBank(Bank bank){
		//Starting resources of the bank need to be generated correctly
		StringBuilder result = new StringBuilder();
		String input = "\n\"bank\": {\n\"brick\":" + bank.numResourceRemaining(ResourceType.BRICK);
		result.append(input);
		input = ",\n\"ore\":" + bank.numResourceRemaining(ResourceType.ORE);
		result.append(input);
		input = ",\n\"sheep\":" + bank.numResourceRemaining(ResourceType.SHEEP);
		result.append(input);
		input = ",\n\"wheat\":" + bank.numResourceRemaining(ResourceType.WHEAT);
		result.append(input);
		input = ",\n\"wood\":" + bank.numResourceRemaining(ResourceType.WOOD);
		result.append(input);
		result.append("\n},\n");
		
		input = "\"deck\": {\n\"yearOfPlenty\":" + bank.numDevCardsRemaining(DevCardType.YEAR_OF_PLENTY);
		result.append(input);
		input = ",\n\"monopoly\":" + bank.numDevCardsRemaining(DevCardType.MONOPOLY);
		result.append(input);
		input = ",\n\"monument\":" + bank.numDevCardsRemaining(DevCardType.MONUMENT);
		result.append(input);
		input = ",\n\"soldier\":" + bank.numDevCardsRemaining(DevCardType.SOLDIER);
		result.append(input);
		input = ",\n\"roadBuilding\":" + bank.numDevCardsRemaining(DevCardType.ROAD_BUILD);
		result.append(input);
		result.append("\n},");
		return result.toString();
	}

	public String buildMap(Map map){
		StringBuilder result = new StringBuilder();
		String input = "\n\"map\": {\n";
		result.append(input);
		result.append(buildHexes(map.getHexes()));
		result.append(buildRoads(map.getEdges()));
		result.append(buildPorts(map.getEdgePorts()));
		result.append(buildSettlementsAndCities(map.getVerticies()));
		input = "\"radius\": 3,\n";
		result.append(input);
		input = "\"robber\": {\n";
		result.append(input);
		input = "\"x\":" + map.getRobberLocation().getX() + ",\n";
		result.append(input);
		input = "\"y\":" + map.getRobberLocation().getY() + "\n}\n},\n";
		result.append(input);
		return result.toString();
	}
	
	public String buildPlayers(ArrayList<Player> players){
		//What do I do if there are less than 4 players?
		//Player pieces need to be generated correclty initially
		StringBuilder result = new StringBuilder();
		String input = "\"players\": [";
		result.append(input);
		for(int i = 0; i < players.size(); i++){
			result.append("\n{\n");
			ResourceList resources = resourceTranslate(players.get(i).getPlayerHand().getResourceCards());
			input = "\"resources\": {\n";
			result.append(input);
			input = "\"brick\":" + resources.getBrick() + ",\n";
			result.append(input);
			input = "\"ore\":" + resources.getOre() + ",\n";
			result.append(input);
			input = "\"sheep\":" + resources.getSheep() + ",\n";
			result.append(input);
			input = "\"wheat\":" + resources.getWheat() + ",\n";
			result.append(input);
			input = "\"wood\":" + resources.getWood() + "\n},\n";
			result.append(input);
			
			input = "\"oldDevCards\": {\n";
			result.append(input);
			input = "\"yearOfPlenty\":" + players.get(i).getPlayerHand().numOldDevCardRemaining(DevCardType.YEAR_OF_PLENTY) + ",\n";
			result.append(input);
			input = "\"monopoly\":" + players.get(i).getPlayerHand().numOldDevCardRemaining(DevCardType.MONOPOLY) + ",\n";
			result.append(input);
			input = "\"soldier\":" + players.get(i).getPlayerHand().numOldDevCardRemaining(DevCardType.SOLDIER) + ",\n";
			result.append(input);
			input = "\"roadBuilding\":" + players.get(i).getPlayerHand().numOldDevCardRemaining(DevCardType.ROAD_BUILD) + ",\n";
			result.append(input);
			input = "\"monument\":" + players.get(i).getPlayerHand().numOldDevCardRemaining(DevCardType.MONUMENT) + "\n},\n";
			result.append(input);
			
			input = "\"newDevCards\": {\n";
			result.append(input);
			input = "\"yearOfPlenty\":" + players.get(i).getPlayerHand().numNewDevCardRemaining(DevCardType.YEAR_OF_PLENTY) + ",\n";
			result.append(input);
			input = "\"monopoly\":" + players.get(i).getPlayerHand().numNewDevCardRemaining(DevCardType.MONOPOLY) + ",\n";
			result.append(input);
			input = "\"soldier\":" + players.get(i).getPlayerHand().numNewDevCardRemaining(DevCardType.SOLDIER) + ",\n";
			result.append(input);
			input = "\"roadBuilding\":" + players.get(i).getPlayerHand().numNewDevCardRemaining(DevCardType.ROAD_BUILD) + ",\n";
			result.append(input);
			input = "\"monument\":" + players.get(i).getPlayerHand().numNewDevCardRemaining(DevCardType.MONUMENT) + "\n},\n";
			result.append(input);
			
			input = "\"roads\":" + players.get(i).numPiecesOfType(PieceType.ROAD) + ",\n";
			result.append(input);
			input = "\"cities\":" + players.get(i).numPiecesOfType(PieceType.CITY) + ",\n";
			result.append(input);
			input = "\"settlements\":" + players.get(i).numPiecesOfType(PieceType.SETTLEMENT) + ",\n";
			result.append(input);
			input = "\"soldiers\":" + players.get(i).getSoldiers() + ",\n";
			result.append(input);
			input = "\"victoryPoints\":" + players.get(i).getVictoryPoints() + ",\n";
			result.append(input);
			input = "\"monuments\":" + players.get(i).getMonuments() + ",\n";
			result.append(input);
			input = "\"playedDevCard\":" + players.get(i).isHasPlayedDevCard() + ",\n";
			result.append(input);
			input = "\"discarded\":" + players.get(i).isHasDiscarded() + ",\n";
			result.append(input);
			input = "\"playerID\":" + players.get(i).getPlayerID() + ",\n";
			result.append(input);
			input = "\"playerIndex\":" + i + ",";
			result.append(input);
			input = "\"name\":\"" + players.get(i).getName() + "\",\n";
			result.append(input);
			input = "\"color\":\"" + getColor(players.get(i).getPlayerColor()) + "\"\n}";
			result.append(input);
			if(i < players.size() - 1){
				result.append(",");
			}else if(players.size() < 4){
				result.append(",");
				for(int j = players.size(); j < 3; j++){
					result.append("null,\n");
				}
				result.append("null\n");
			}
		}
		result.append("\n],");
		return result.toString();
	}
	
	private String getColor(CatanColor color){
		switch(color){
			case WHITE: return "white";
			case BLUE: return "blue";
			case ORANGE: return "orange";
			case RED: return "red";
			case YELLOW: return "yellow";
			case PURPLE: return "purple";
			case PUCE: return "puce";
			case BROWN: return "brown";
			case GREEN: return "green";
			default: return null;
		}
	}
	
	private ResourceList resourceTranslate(ArrayList<ResourceCard> resources){
		int wood = 0;
		int brick = 0;
		int ore = 0;
		int wheat = 0;
		int sheep = 0;
		
		for(int i = 0; i < resources.size(); i++){
			switch(resources.get(i).getType()){
				case WOOD: wood++; break;
				case BRICK: brick++; break;
				case ORE: ore++; break;
				case WHEAT: wheat++; break;
				case SHEEP: sheep++; break;
			}
		}
		return new ResourceList(brick, ore, sheep, wheat, wood);
	}
	
	public String buildTradeOffer(TradeOffer offer){
		if(offer == null){
			return "";
		}
		StringBuilder result = new StringBuilder();
		String input = "\"tradeOffer\": {\n";
		result.append(input);
		input = "\"sender\":" + offer.getRequestingPlayerID() + ",\n";
		result.append(input);
		input = "\"receiver\":" + offer.getAcceptingPlayerID() + ",\n";
		result.append(input);
		ResourceList resources = resourceTranslate(offer.getResourceOffered(), offer.getResourceDesired());
		input = "\"offer\": {\n";
		result.append(input);
		input = "\"brick\":" + resources.getBrick() + ",\n";
		result.append(input);
		input = "\"ore\":" + resources.getOre() + ",\n";
		result.append(input);
		input = "\"sheep\":" + resources.getSheep() + ",\n";
		result.append(input);
		input = "\"wheat\":" + resources.getWheat() + ",\n";
		result.append(input);
		input = "\"wood\":" + resources.getWood() + "\n} \n},";
		result.append(input);
		return result.toString();
	}
	
	private ResourceList resourceTranslate(ArrayList<ResourceType> resourceGive, ArrayList<ResourceType> resourceReceive){
		int wood = 0;
		int brick = 0;
		int ore = 0;
		int wheat = 0;
		int sheep = 0;
		
		for(int i = 0; i < resourceGive.size(); i++){
			switch(resourceGive.get(i)){
				case WOOD: wood--; break;
				case BRICK: brick--; break;
				case ORE: ore--; break;
				case WHEAT: wheat--; break;
				case SHEEP: sheep--; break;
			}
		}
		
		for(int i = 0; i < resourceReceive.size(); i++){
			switch(resourceReceive.get(i)){
				case WOOD: wood++; break;
				case BRICK: brick++; break;
				case ORE: ore++; break;
				case WHEAT: wheat++; break;
				case SHEEP: sheep++; break;
			}
		}
		return new ResourceList(brick, ore, sheep, wheat, wood);
	}
	
	public String buildTurnTracker(TurnTracker tracker){
		StringBuilder result = new StringBuilder();
		String input = "\n\"turnTracker\": {\n";
		result.append(input);
		input = "\"status\": \"" + tracker.statusToString() + "\",";
		result.append(input);
		input = "\n\"currentTurn\":" + tracker.getCurrentTurnPlayerID() + ",";
		result.append(input);
		input = "\n\"longestRoad\":" + tracker.getLongestRoadplayerID() + ",";
		result.append(input);
		input = "\n\"largestArmy\":" + tracker.getLargestArmyPlayerID() + "\n},";
		result.append(input);
		return result.toString();
	}
	
	public String buildChat(Chat chat){
		StringBuilder result = new StringBuilder();
		result.append("\n\"chat\": { \n\"lines\": [ \n");
		ArrayList<Line> lines = chat.getLines();
		for(int i = 0; i < lines.size() - 1; i++){
			if(lines.size() > 0){
				String input = "{\"source\":\"" + lines.get(i).getSource() + "\",";
				result.append(input);
				input = "\"message\":\"" + lines.get(i).getMessage() + "\"},\n";
				result.append(input);
			}
		}
		if(lines.size() > 0){
			String input = "{\"source\":\"" + lines.get(lines.size() - 1).getSource() + "\",";
			result.append(input);
			input = "\"message\":\"" + lines.get(lines.size() - 1).getMessage() + "\"}\n ] },";
			result.append(input);
		}else{
			result.append("] \n},");
		}
		return result.toString();
	}
	
	public String buildLog(Log log){
		StringBuilder result = new StringBuilder();
		result.append("\n\"log\": { \n\"lines\": [ \n");
		ArrayList<Line> lines = log.getLines();
		for(int i = 0; i < lines.size() - 1; i++){
			if(lines.size() > 0){
				String input = "{\"source\":\"" + lines.get(i).getSource() + "\",";
				result.append(input);
				input = "\"message\":\"" + lines.get(i).getMessage() + "\"},\n";
				result.append(input);
			}
		}
		if(lines.size() > 0){
			String input = "{\"source\":\"" + lines.get(lines.size() - 1).getSource() + "\",";
			result.append(input);
			input = "\"message\":\"" + lines.get(lines.size() - 1).getMessage() + "\"}\n ] },";
			result.append(input);
		}else{
			result.append("] \n},");
		}
		return result.toString();
	}
	
	public String buildHexes(HashMap<HexLocation, TerrainHex> hexes){
		StringBuilder result = new StringBuilder();
		String input = "\"hexes\": [\n";
		result.append(input);
		int i = hexes.entrySet().size();
		for(Entry<HexLocation, TerrainHex> hex : hexes.entrySet()){
			i--;
			if(hex.getValue().getType() != HexType.WATER){
				result.append("{\n");
				if(hex.getValue().getType() != HexType.DESERT){
					input = "\"resource\": \"" + resourceType(hex.getValue().getType()) + "\",\n";
					result.append(input);
				}
				input = "\"location\": {\n";
				result.append(input);
				input = "\"x\":" + hex.getValue().getLocation().getX() + ",\n";
				result.append(input);
				input = "\"y\":" + hex.getValue().getLocation().getY() + "\n}";
				result.append(input);
				if(hex.getValue().getType() != HexType.DESERT){
					input = ",\n\"number\":" + hex.getValue().getNumber();
					result.append(input);
				}
				if(i > 0){
					result.append("\n},\n");
				}else{
					result.append("\n}\n],");
				}
			}
		}
		return result.toString();
	}
	
	public String resourceType(HexType type){
		switch(type){
			case BRICK: return "brick";
			case WOOD: return "wood";
			case WHEAT: return "wheat";
			case ORE: return "ore";
			case SHEEP: return "sheep";
			default: return "";
		}
	}
	
	public String buildPorts(HashMap<EdgeLocation, PortType> ports){
		StringBuilder result = new StringBuilder();
		String input = "\"ports\": [\n";
		result.append(input);
		int i = ports.entrySet().size();
		for(Entry<EdgeLocation, PortType> port : ports.entrySet()){
			i--;
			result.append("{\n");
			if(port.getValue() != PortType.THREE){
				input = "\"ratio\":2,\n";
				result.append(input);
				input = "\"resource\": \"" + portType(port.getValue()) + "\",\n";
				result.append(input);
			}else{
				input = "\"ratio\":3,\n";
				result.append(input);
			}
			input = "\"direction\": \"" + direction(port.getKey().getNormalizedLocation().getDir()) + "\",\n";
			result.append(input);
			input = "\"location\": {\n";
			result.append(input);
			input = "\"x\":" + port.getKey().getNormalizedLocation().getHexLoc().getX() + ",\n";
			result.append(input);
			input = "\"y\":" + port.getKey().getNormalizedLocation().getHexLoc().getY() + "\n}";
			result.append(input);
			if(i > 0){
				result.append("\n},\n");
			}else{
				result.append("\n}\n],");
			}
		}
		return result.toString();
	}
	
	public String direction(VertexDirection direction){
		switch(direction){
			case NorthWest: return "NW";
			case NorthEast: return "NE";
			case SouthWest: return "SW";
			case SouthEast: return "SE";
			case West: return "W";
			case East: return "E";
			default: return "";
		}
	}
	
	public String direction(EdgeDirection direction){
		switch(direction){
			case NorthWest: return "NW";
			case NorthEast: return "NE";
			case SouthWest: return "SW";
			case SouthEast: return "SE";
			case North: return "N";
			case South: return "S";
			default: return "";
		}
	}
	
	public String portType(PortType type){
		switch(type){
			case WHEAT: return "wheat";
			case WOOD: return "wood";
			case BRICK: return "brick";
			case ORE: return "ore";
			case SHEEP: return "sheep";
			default: return "";
		}
	}
	
	public String buildRoads(HashMap<EdgeLocation, Edge> edges){
		StringBuilder result = new StringBuilder();
		String input = "\"roads\": [\n";
		result.append(input);
		ArrayList<Edge> roadEdges = new ArrayList<Edge>();
		for(Entry<EdgeLocation, Edge> edge : edges.entrySet()){
			if(edge.getValue().getPiece() != null){
				roadEdges.add(edge.getValue());
			}
		}
		for(int i = 0; i < roadEdges.size(); i++){
			result.append("{\n");
			input = "\"owner\":" + roadEdges.get(i).getPiece().getPlayerID() + ",\n";
			result.append(input);
			input = "\"location\": {\n";
			result.append(input);
			input = "\"direction\": \"" + direction(roadEdges.get(i).getLocation().getNormalizedLocation().getDir()) + "\",\n";
			result.append(input);
			input = "\"x\":" + roadEdges.get(i).getLocation().getNormalizedLocation().getHexLoc().getX()+ ",\n";
			result.append(input);
			input = "\"y\":" + roadEdges.get(i).getLocation().getNormalizedLocation().getHexLoc().getY() + "\n}";
			result.append(input);
			if(i < roadEdges.size() - 1){
				result.append("},\n");
			}else{
				result.append("}\n");
			}
		}
		result.append("],");
		return result.toString();
	}
	
	public String buildSettlementsAndCities(HashMap<VertexLocation, Vertex> verticies){
		StringBuilder result = new StringBuilder();
		ArrayList<Vertex> settlements = new ArrayList<Vertex>();
		ArrayList<Vertex> cities = new ArrayList<Vertex>();
		for(Entry<VertexLocation, Vertex> vertex : verticies.entrySet()){
			if(vertex.getValue().getPiece()!= null){
				if(vertex.getValue().getPiece().getPieceType() == PieceType.SETTLEMENT){
					settlements.add(vertex.getValue());
				}else{
					cities.add(vertex.getValue());
				}
			}
		}
		String input = "\"settlements\": [\n";
		result.append(input);
		for(int i = 0; i < settlements.size(); i++){
			result.append("{\n");
			input = "\"owner\":" + settlements.get(i).getPiece().getPlayerID() + ",\n";
			result.append(input);
			input = "\"location\": {\n";
			result.append(input);
			input = "\"direction\": \"" + direction(settlements.get(i).getLocation().getNormalizedLocation().getDir()) + "\",\n";
			result.append(input);
			input = "\"x\":" + settlements.get(i).getLocation().getNormalizedLocation().getHexLoc().getX()+ ",\n";
			result.append(input);
			input = "\"y\":" + settlements.get(i).getLocation().getNormalizedLocation().getHexLoc().getY() + "\n}";
			result.append(input);
			if(i < settlements.size() - 1){
				result.append("\n},\n");
			}else{
				result.append("\n}\n");
			}
		}
		result.append("],\n");
		input = "\"cities\": [\n";
		result.append(input);
		for(int i = 0; i < cities.size(); i++){
			result.append("{\n");
			input = "\"owner\":" + cities.get(i).getPiece().getPlayerID() + ",\n";
			result.append(input);
			input = "\"location\": {\n";
			result.append(input);
			input = "\"direction\": \"" + direction(cities.get(i).getLocation().getNormalizedLocation().getDir()) + "\",\n";
			result.append(input);
			input = "\"x\":" + cities.get(i).getLocation().getNormalizedLocation().getHexLoc().getX()+ ",\n";
			result.append(input);
			input = "\"y\":" + cities.get(i).getLocation().getNormalizedLocation().getHexLoc().getY() + "\n}";
			result.append(input);
			if(i < cities.size() - 1){
				result.append("\n},\n");
			}else{
				result.append("\n}\n");
			}
		}
		result.append("],\n");
		return result.toString();
	}
}
