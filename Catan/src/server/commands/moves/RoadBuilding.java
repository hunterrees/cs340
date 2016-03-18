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
		edgeLoc1 = new EdgeLocation(new HexLocation(x, y), edgeDir).getNormalizedLocation();



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


		Line line = new Line(p.getName(), p.getName() + " bought a development card");
		model.getLog().addLine(line);

		//model.getTracker().setGameStatus(blah);

		ServerTranslator temp = new ServerTranslator(model);
		return temp.translate();
	}

}
