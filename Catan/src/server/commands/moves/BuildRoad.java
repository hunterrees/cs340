package server.commands.moves;

import client.server.ServerException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import server.ServerTranslator;
import server.commands.Command;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.GameModel;
import shared.model.Line;
import shared.model.player.Player;

public class BuildRoad extends Command {

	private int playerIndex;
	private EdgeLocation edgeLoc;
	private boolean free;
	private String type;
	private boolean test = false;
	
	public BuildRoad(int gameID, String json) {
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
		
		JsonObject jsonLoc = root.getAsJsonObject("roadLocation");
		JsonPrimitive primX = jsonLoc.getAsJsonPrimitive("x");
		int x = primX.getAsInt();
		JsonPrimitive primY = jsonLoc.getAsJsonPrimitive("y");
		int y = primY.getAsInt();
		JsonPrimitive primDir = jsonLoc.getAsJsonPrimitive("direction");
		String dir = primDir.getAsString();
		EdgeDirection edgeDir; 
		//make vertLoc
		switch(dir){
			case "NW" : edgeDir = EdgeDirection.NorthWest; break;
			case "NE" : edgeDir = EdgeDirection.NorthEast; break;
			case "N" : edgeDir = EdgeDirection.North; break;
			case "SE" : edgeDir = EdgeDirection.SouthEast; break;
			case "SW" : edgeDir = EdgeDirection.SouthWest; break;
			case "S" : edgeDir = EdgeDirection.South; break;
			default : edgeDir = null; break;
		}
		edgeLoc = new EdgeLocation(new HexLocation(x, y), edgeDir).getNormalizedLocation();
		
		JsonPrimitive primFree = root.getAsJsonPrimitive("free");
		free = primFree.getAsBoolean();
		
	}
	
	/**
	 * Preconditions: The road location is open
	 * 					The road is connected to another road owned by the players
	 * 					The build location is not on water
	 * 					The player has the required resources (1 wood, 1 brick, 1 road)
	 * 					If it's setup round the location must be adjacent to a settlement owned by the player
	 * 					with no adjacent road
	 * Postconditions: The resources required to build a road are lost
	 * 					The road is placed in the specified location
	 * 					If applicable, longest road is given to the player.
	 */
	@Override
	public Object execute() throws ServerException {
		// TODO Auto-generated method stub
		Player p = model.getPlayers().get(playerIndex);

		if(model.canBuildRoad(playerIndex, free, edgeLoc) || test){
			// Place it on the map
			model.getMap().placeRoad(playerIndex, edgeLoc);

			// Remove the correct resources
			p.getPlayerHand().removeResources(1, ResourceType.WOOD);
			p.getPlayerHand().removeResources(1, ResourceType.BRICK);
			
			Line line = new Line(p.getName(), p.getName() + " built a road");
			model.getLog().addLine(line);
			
			p.removePiece(PieceType.ROAD);
			
		}


		

		//model.getTracker().setGameStatus(blah);

		ServerTranslator temp = new ServerTranslator(model);
		return temp.translate();
	}

	public void setTest(boolean test) {
		this.test = test;
	}
}
