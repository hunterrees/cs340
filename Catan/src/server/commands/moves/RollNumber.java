package server.commands.moves;

import client.gameManager.GameManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import server.ServerTranslator;
import server.commands.Command;
import shared.definitions.GameState;
import shared.definitions.HexType;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.GameModel;
import shared.model.Line;
import shared.model.map.Map;
import shared.model.map.TerrainHex;
import shared.model.map.Vertex;
import shared.model.player.Player;

import java.util.ArrayList;

public class RollNumber extends Command{

	Map map = model.getMap();

	String type;
	int numRolled;
	int playerIndex;

	public RollNumber(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
		translate(json);
	}


	public void translate(String json) {
		Gson gson = new Gson();
		JsonObject root;
		try{
			root = gson.fromJson(json, JsonObject.class);
		}catch(Exception e){
			return;
		}

		JsonPrimitive typePrim = root.getAsJsonPrimitive("type");
		type = typePrim.getAsString();

		JsonPrimitive myPlayerIndex = root.getAsJsonPrimitive("playerIndex");
		playerIndex = myPlayerIndex.getAsInt();

		JsonPrimitive myNumRolled = root.getAsJsonPrimitive("number");
		numRolled = myNumRolled.getAsInt();


	}

	public void sevenRolled() {
		ArrayList<Player> players = model.getPlayers();

		for(Player p : players) {
			if(p.getPlayerHand().getNumResources() > 7) {
				model.getTracker().setGameStatus(GameState.discarding);
				break;
			}
		}
	}


	public void givePlayersResources(int roll) {
		if(roll == 7) {
			// Do robber stuff here
			sevenRolled();
			return;
		}

		for(java.util.Map.Entry<HexLocation, TerrainHex> entry : map.getHexes().entrySet()) {
			if(entry.getValue().getNumber() == roll && !map.getRobberLocation().equals(entry.getKey())) {
				// Check every edge
				for(java.util.Map.Entry<VertexLocation, Vertex> entryTwo : entry.getValue().getVerticies().entrySet()) {
					if(entryTwo.getValue().getPiece() != null) {
						addResource(entryTwo.getValue().getPiece().getPieceType(), entry.getValue().getType(), entryTwo.getValue().getPiece().getPlayerID());
					}
				}

			}
		}
	}

	public void addResource(PieceType pt, HexType ht, int playerIndex) {
		Player p = model.getPlayers().get(playerIndex);
		int amount = 0;

		if(pt == PieceType.SETTLEMENT) {
			amount = 1;
		} else if(pt == PieceType.CITY) {
			amount = 2;
		} else {
			System.out.println("Error! PieceType doesn't exist!");
		}


		switch(ht) {
			case WOOD: p.getPlayerHand().addResources(amount, ResourceType.WOOD); break;
			case BRICK: p.getPlayerHand().addResources(amount, ResourceType.BRICK); break;
			case SHEEP: p.getPlayerHand().addResources(amount, ResourceType.SHEEP); break;
			case WHEAT: p.getPlayerHand().addResources(amount, ResourceType.WHEAT); break;
			case ORE: p.getPlayerHand().addResources(amount, ResourceType.ORE); break;
			default: System.out.println("Error! HexType doesn't exist!");
		}
	}












	/**
	 * Preconditions: It's the players turn
	 * 					The status of the player's model is 'rolling'
	 * Postconditions: Changes the status of the player to 'Discarding, Robbing, or Playing'
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		Player p = model.getPlayers().get(playerIndex);


		givePlayersResources(numRolled);
		Line line = new Line(p.getName(), p.getName() + " rolled a " + numRolled);
		model.getLog().addLine(line);



		//model.getTracker().setGameStatus(blah);

		ServerTranslator temp = new ServerTranslator(model);
		return temp.translate();
	}

}
