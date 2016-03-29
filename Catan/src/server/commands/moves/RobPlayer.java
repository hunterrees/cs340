package server.commands.moves;

import client.server.ServerException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import server.ServerTranslator;
import server.commands.Command;
import shared.ResourceCard;
import shared.definitions.GameState;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.GameModel;
import shared.model.Line;
import shared.model.player.Player;

import java.util.ArrayList;

public class RobPlayer extends Command {

	String type;
	int playerIndex;
	int victimIndex;
	HexLocation hexLoc;


	public RobPlayer(int gameID, String json) {
		super(gameID, json);

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

	public void robPlayer() {
		Player stealer = model.getPlayers().get(playerIndex);
		Player loser = model.getPlayers().get(victimIndex);

		ArrayList<ResourceCard> stealerCards = stealer.getPlayerHand().getResourceCards();
		ArrayList<ResourceCard> loserCards = loser.getPlayerHand().getResourceCards();

		if(loserCards.size() == 0) {
			return;
		}

		ResourceCard stolenCard = loserCards.get(0);
		loserCards.remove(0);
		stealerCards.add(stolenCard);

		stealer.getPlayerHand().setResourceCards(stealerCards);
		loser.getPlayerHand().setResourceCards(loserCards);

	}


	/**
	 * Preconditions: The robber is being moved to a different location
	 * 					The location the robber being moved to is legal
	 * 					If a player is being robbed, the player being robbed has resource cards
	 * Postconditions: The robber is moved to the new location
	 * 					A resource card is moved from the hand of the player being robbed to the hand of the robber.
	 */
	@Override
	public Object execute() throws ServerException {
		if(!model.robPlayer(hexLoc, victimIndex)){
			throw new ServerException("Can't rob player (wrong location or wrong player)");
		}

		// Change robber location
		model.getMap().setRobberLocation(hexLoc);
		model.setRobberLoc(hexLoc);

		// Exchange resources
		if(victimIndex != -1){
			robPlayer();
		} else {
			Player p = model.getPlayers().get(playerIndex);

			Line line = new Line(p.getName(), p.getName() + " didn't rob from anybody");
			model.getLog().addLine(line);

			model.updateVersionNumber();

			model.getTracker().setGameStatus(GameState.playing);

			ServerTranslator temp = new ServerTranslator(model);
			return temp.translate();
		}



		Player p = model.getPlayers().get(playerIndex);
		Player v = model.getPlayers().get(victimIndex);

		Line line = new Line(p.getName(), p.getName() + " robbed from " + v.getName());
		model.getLog().addLine(line);

		model.updateVersionNumber();

		model.getTracker().setGameStatus(GameState.playing);

		ServerTranslator temp = new ServerTranslator(model);
		return temp.translate();
	}

}
