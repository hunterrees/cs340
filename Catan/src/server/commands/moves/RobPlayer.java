package server.commands.moves;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import server.commands.Command;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.GameModel;

public class RobPlayer extends Command {

	String type;
	int playerIndex;
	int victimIndex;
	HexLocation hexLoc;


	public RobPlayer(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}



	private void translate(String json){
		Gson gson = new Gson();
		JsonObject root;
		try{
			root = gson.fromJson(json, JsonObject.class);
		}catch(Exception e){
			return;
		}

		JsonPrimitive primType = root.getAsJsonPrimitive("playerIndex");
		type = primType.getAsString();

		JsonPrimitive primPlayerIndex = root.getAsJsonPrimitive("playerIndex");
		playerIndex = primPlayerIndex.getAsInt();

		JsonPrimitive primVictimIndex = root.getAsJsonPrimitive("victimIndex");
		victimIndex = primVictimIndex.getAsInt();

		JsonObject jsonLoc = root.getAsJsonObject("location");
		JsonPrimitive primX = jsonLoc.getAsJsonPrimitive("x");
		int x = primX.getAsInt();
		JsonPrimitive primY = jsonLoc.getAsJsonPrimitive("y");
		int y = primY.getAsInt();

		hexLoc = new HexLocation(x, y);



	}


	/**
	 * Preconditions: The robber is being moved to a different location
	 * 					The location the robber being moved to is legal
	 * 					If a player is being robbed, the player being robbed has resource cards
	 * Postconditions: The robber is moved to the new location
	 * 					A resource card is moved from the hand of the player being robbed to the hand of the robber.
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
