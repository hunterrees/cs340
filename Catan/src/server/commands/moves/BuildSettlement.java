package server.commands.moves;

import client.gameManager.GameManager;
import client.server.ServerException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import server.ServerTranslator;
import server.commands.Command;
import shared.definitions.GameState;
import shared.definitions.HexType;
import shared.definitions.PieceType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.GameModel;
import shared.model.Line;
import shared.model.map.Map;
import shared.model.map.Port;
import shared.model.map.TerrainHex;
import shared.model.map.Vertex;
import shared.model.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class BuildSettlement extends Command {
	
	
	VertexLocation vertLoc;
	int playerIndex;
	boolean free;
	String type;
	private boolean test = false;

	public BuildSettlement(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
		translate(json);
	}
	
	private void translate(String json){
		Gson gson = new Gson();
		JsonObject root;
		try{
			root = gson.fromJson(json, JsonObject.class);
		}catch(Exception e){
			return;
		}
		
		JsonPrimitive typePrim = root.getAsJsonPrimitive("type");
		type = typePrim.getAsString();
		
		JsonPrimitive primIndex = root.getAsJsonPrimitive("playerIndex");
		playerIndex = primIndex.getAsInt();
		
		JsonObject jsonLoc = root.getAsJsonObject("vertexLocation");
		JsonPrimitive primX = jsonLoc.getAsJsonPrimitive("x");
		int x = primX.getAsInt();
		JsonPrimitive primY = jsonLoc.getAsJsonPrimitive("y");
		int y = primY.getAsInt();
		JsonPrimitive primDir = jsonLoc.getAsJsonPrimitive("direction");
		String dir = primDir.getAsString();
		VertexDirection vertDir; 
		//make vertLoc
		switch(dir){
			case "NW" : vertDir = VertexDirection.NorthWest; break;
			case "NE" : vertDir = VertexDirection.NorthEast; break;
			case "E" : vertDir = VertexDirection.East; break;
			case "SE" : vertDir = VertexDirection.SouthEast; break;
			case "SW" : vertDir = VertexDirection.SouthWest; break;
			case "W" : vertDir = VertexDirection.West; break;
			default : vertDir = null; break;
		}
		vertLoc = new VertexLocation(new HexLocation(x, y), vertDir).getNormalizedLocation();
		
		JsonPrimitive primFree = root.getAsJsonPrimitive("free");
		free = primFree.getAsBoolean();
		
	}

	public void addPort(VertexLocation loc) {
		HashMap<VertexLocation, Port> ports = model.getMap().getPorts();

		for(java.util.Map.Entry<VertexLocation, Port> entry : ports.entrySet()) {
			if(loc.equals(entry.getKey().getNormalizedLocation())) {
				model.getPlayers().get(playerIndex).addPort(entry.getValue().getType());

				return;
			}
		}
	}

	public void giveStartingResources(Player p){
		HashMap<VertexLocation, Vertex> verticies = model.getMap().getVerticies();
		HashMap<HexLocation, TerrainHex> hexes = model.getMap().getHexes();
		
		
		
		for(java.util.Map.Entry<VertexLocation, Vertex> entry : verticies.entrySet()) {
			if(entry.getKey().getNormalizedLocation().equals(vertLoc)){
				HexLocation hexLoc = entry.getKey().getHexLoc();
				HexType type = hexes.get(hexLoc).getType();
				
				switch(type){
					case WOOD: p.getPlayerHand().addResources(1, ResourceType.WOOD); break;
					case WHEAT: p.getPlayerHand().addResources(1, ResourceType.WHEAT); break;
					case BRICK: p.getPlayerHand().addResources(1, ResourceType.BRICK); break;
					case ORE: p.getPlayerHand().addResources(1, ResourceType.ORE); break;
					case SHEEP: p.getPlayerHand().addResources(1, ResourceType.SHEEP); break;
					default: break;
				}
				
			}
		}
	}



	/**
	 * Preconditions: The settlement location is valid: 
	 * (open, not on water, follows the two-away rule, connected to friendly road unless setup), 
	 * you have the required resources (1 wood, 1 brick, 1 sheep, 1 wheat, 1 settlement).
	 * Postconditions: The player loses the resources for building a settlement (wood, brick, sheep, wheat)
	 * The settlement is placed on the map in the specified location.
	 */
	@Override
	public Object execute() throws ServerException {
		// TODO Auto-generated method stub
		Player p = model.getPlayers().get(playerIndex);

		if(model.getMap().canBuildSettlement(playerIndex, free, vertLoc) || test){
			
		
		
			if(!free){
				//take resources from player
	

				// Check if user has enough resources
				boolean enough;
				if(p.getPlayerHand().numResourceOfType(ResourceType.WOOD) > 0 &&
						p.getPlayerHand().numResourceOfType(ResourceType.BRICK) > 0 &&
						p.getPlayerHand().numResourceOfType(ResourceType.SHEEP) > 0 &&
						p.getPlayerHand().numResourceOfType(ResourceType.WHEAT) > 0) {
					enough = true;
				} else {
					enough = false;
				}
	
				// Remove the resources
				if(enough) {
					p.getPlayerHand().removeResources(1, ResourceType.WOOD);
					p.getPlayerHand().removeResources(1, ResourceType.BRICK);
					p.getPlayerHand().removeResources(1, ResourceType.SHEEP);
					p.getPlayerHand().removeResources(1, ResourceType.WHEAT);
					model.getMap().placeSettlement(playerIndex, vertLoc);
	
					Line line = new Line(p.getName(), p.getName() + " built a settlement");
					model.getLog().addLine(line);

					//model.getTracker().setGameStatus(blah);
					p.addVictoryPoint();
					p.removePiece(PieceType.SETTLEMENT);
				}
				
			}
			else{
				model.getMap().placeSettlement(playerIndex, vertLoc);
				Line line = new Line(p.getName(), p.getName() + " built a settlement");
				model.getLog().addLine(line);

				//model.getTracker().setGameStatus(blah);
				p.addVictoryPoint();
				p.removePiece(PieceType.SETTLEMENT);
				if(model.getGameState() == GameState.secondRound){
					giveStartingResources(p);
				}
			}
			addPort(vertLoc);
		}

		model.checkVictory();

		ServerTranslator temp = new ServerTranslator(model);
		model.updateVersionNumber();
		return temp.translate();
	}

	public void setTest(boolean test) {
		this.test = test;
	}
}
