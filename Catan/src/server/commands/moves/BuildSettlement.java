package server.commands.moves;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import server.commands.Command;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.GameModel;

public class BuildSettlement extends Command {
	
	
	VertexLocation vertLoc;
	int playerIndex;
	boolean free;
	String type;

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
		vertLoc = new VertexLocation(new HexLocation(x, y), vertDir);
		
		JsonPrimitive primFree = root.getAsJsonPrimitive("free");
		free = primFree.getAsBoolean();
		
	}

	/**
	 * Preconditions: The settlement location is valid: 
	 * (open, not on water, follows the two-away rule, connected to friendly road unless setup), 
	 * you have the required resources (1 wood, 1 brick, 1 sheep, 1 wheat, 1 settlement).
	 * Postconditions: The player loses the resources for building a settlement (wood, brick, sheep, wheat)
	 * The settlement is placed on the map in the specified location.
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		
		
		model.getMap().placeSettlement(playerIndex, vertLoc);
		if(!free){
			//take resources from player
			
		}
		
		return null;
	}

}
