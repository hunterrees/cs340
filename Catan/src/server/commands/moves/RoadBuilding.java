package server.commands.moves;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import server.ServerTranslator;
import server.commands.Command;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.model.GameModel;
import shared.model.Line;
import shared.model.player.Player;

public class RoadBuilding extends Command {

	String type;
	int playerIndex;
	EdgeLocation edgeLoc1;
	EdgeLocation edgeLoc2;


	public RoadBuilding(int gameID, String json) {
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

		// Spot1's stuff
		JsonObject jsonLoc = root.getAsJsonObject("spot1");
		JsonPrimitive primX = jsonLoc.getAsJsonPrimitive("x");
		int x = primX.getAsInt();
		JsonPrimitive primY = jsonLoc.getAsJsonPrimitive("y");
		int y = primY.getAsInt();
		JsonPrimitive primDir = jsonLoc.getAsJsonPrimitive("direction");
		String dir = primDir.getAsString();
		EdgeDirection edgeDir1;
		//make vertLoc
		switch(dir){
			case "NW" : edgeDir1 = EdgeDirection.NorthWest; break;
			case "NE" : edgeDir1 = EdgeDirection.NorthEast; break;
			case "N" : edgeDir1 = EdgeDirection.North; break;
			case "SE" : edgeDir1 = EdgeDirection.SouthEast; break;
			case "SW" : edgeDir1 = EdgeDirection.SouthWest; break;
			case "S" : edgeDir1 = EdgeDirection.South; break;
			default : edgeDir1 = null; break;
		}
		edgeLoc1 = new EdgeLocation(new HexLocation(x, y), edgeDir1).getNormalizedLocation();


		// Spot2's stuff
		jsonLoc = root.getAsJsonObject("spot2");
		primX = jsonLoc.getAsJsonPrimitive("x");
		x = primX.getAsInt();
		primY = jsonLoc.getAsJsonPrimitive("y");
		y = primY.getAsInt();
		primDir = jsonLoc.getAsJsonPrimitive("direction");
		dir = primDir.getAsString();
		EdgeDirection edgeDir2;
		//make vertLoc
		switch(dir){
			case "NW" : edgeDir2 = EdgeDirection.NorthWest; break;
			case "NE" : edgeDir2 = EdgeDirection.NorthEast; break;
			case "N" : edgeDir2 = EdgeDirection.North; break;
			case "SE" : edgeDir2 = EdgeDirection.SouthEast; break;
			case "SW" : edgeDir2 = EdgeDirection.SouthWest; break;
			case "S" : edgeDir2 = EdgeDirection.South; break;
			default : edgeDir2 = null; break;
		}
		edgeLoc2 = new EdgeLocation(new HexLocation(x, y), edgeDir2).getNormalizedLocation();


	}

	/**
	 * Preconditions: The first road location (spot1) is connected to one of your roads.
		*				The second road location (spot2) is connected to one of your roads or to the first
		*				road location (spot1)
		*				Neither road location is on water
		*				You have at least two unused roads
	 * Postconditions: Usable roads in hand of player is reduced by 2.
	 * 					The roads are built in the specified locations 
	 * 					If applicable longest road is given to the player.

	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		Player p = model.getPlayers().get(playerIndex);


		Line line = new Line(p.getName(), p.getName() + " played road building");
		model.getLog().addLine(line);

		//model.getTracker().setGameStatus(blah);

		ServerTranslator temp = new ServerTranslator(model);
		return temp.translate();
	}

}
