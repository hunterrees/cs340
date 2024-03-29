package server.commands.moves;

import client.gameManager.GameManager;
import client.server.ServerException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import server.ServerManager;
import server.ServerTranslator;
import server.commands.Command;
import shared.Piece;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.GameModel;
import shared.model.Line;
import shared.model.player.Player;

public class BuildCity extends Command {

	VertexLocation vertLoc;
	int playerIndex;
	
	String type;
	private boolean test = false;

	public BuildCity(int gameID, String json) {
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

		

	}

	/**
	 * Preconditions: The city location is where you already have a settlement.
	 * You have the required resources (2 wheat, 3 ore, 1 city).
	 * Postconditions: You lost all of the required resources.
	 * The city is on the map at the specified location.
	 * You got one settlement back.
	 */
	@Override
	public Object execute() throws ServerException {
		// TODO Auto-generated method stub
		if(gameID != -1){
			this.model = ServerManager.getInstance().getGame(gameID);
		}
		Player p = model.getPlayers().get(playerIndex);

		if(model.canBuildCity(playerIndex, vertLoc) || test){
			// Build the city
			model.getMap().placeCity(playerIndex, vertLoc);

			// Remove the resources
			p.getPlayerHand().removeResources(2, ResourceType.WHEAT);
			p.getPlayerHand().removeResources(3, ResourceType.ORE);

			// Get a settlement back
			
			p.removePiece(PieceType.CITY);
			p.addPiece(PieceType.SETTLEMENT);
			Line line = new Line(p.getName(), p.getName() + " built a city");
			model.getLog().addLine(line);

			//model.getTracker().setGameStatus(blah);
			p.addVictoryPoint();
			model.checkVictory();
		}



		
		model.updateVersionNumber();
		ServerTranslator temp = new ServerTranslator(model);
		return temp.translate();
	}

	/**
	 * @return the test
	 */
	public boolean isTest() {
		return test;
	}

	/**
	 * @param test the test to set
	 */
	public void setTest(boolean test) {
		this.test = test;
	}
	
	
	

}
