package server.commands.moves;

import client.gameManager.GameManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import server.commands.Command;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.GameModel;
import shared.model.map.Map;
import shared.model.player.Player;

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
		
		if(model.getMap().canBuildSettlement(playerIndex, free, vertLoc)){
			
		
		
			if(!free){
				//take resources from player
	
				Player p = GameManager.getInstance().getModel().getPlayers().get(playerIndex);
	
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
	
				}
				
			}
			else{
				model.getMap().placeSettlement(playerIndex, vertLoc);
			}
		}
		
		return null;
	}

}
