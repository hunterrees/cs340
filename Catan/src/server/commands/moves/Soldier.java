package server.commands.moves;

import client.server.ServerException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import server.ServerManager;
import server.ServerTranslator;
import server.commands.Command;
import shared.ResourceCard;
import shared.definitions.DevCardType;
import shared.locations.HexLocation;
import shared.model.GameModel;
import shared.model.Line;
import shared.model.player.Player;

import java.util.ArrayList;

public class Soldier extends Command {
	String type;
	int playerIndex;
	int victimIndex;
	HexLocation hexLoc;

	public Soldier(int gameID, String json) {
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
	 * Preconditions: The robber is is being moved from its current location
	 * 					If a player is being robbed, that player has resource cards
	 * Postconditions: The robber is moved to the specified location
	 * 					A resource card is moved from the player being robbed's hand to the robbing player's hand
	 * 					If applicable, largest army is awarded to the robbing player
	 * 					No other Dev cards can be played this turn
	 */
	@Override
	public Object execute() throws ServerException {
		if(gameID != -1){
			this.model = ServerManager.getInstance().getGame(gameID);
		}
		// TODO Auto-generated method stub

		Player p = model.getPlayers().get(playerIndex);



		// Change robber location
		model.getMap().setRobberLocation(hexLoc);
		model.setRobberLoc(hexLoc);

		// Update solider number
		p.setSoldiers(p.getSoldiers()+1);
		p.getPlayerHand().removeOldDevCard(DevCardType.SOLDIER);
		// Change has played dev card
		p.setHasPlayedDevCard(true);

		// Exchange resources
		if(victimIndex != -1) {
			robPlayer();
		}

		// Check for largest army
		model.updateLargestArmy();






		model.checkVictory();
		Line line;

		if(victimIndex != -1) {
			line = new Line(p.getName(), p.getName() + " played a soldier and robbed " + model.getPlayers().get(victimIndex).getName());
		} else {
			line = new Line(p.getName(), p.getName() + " played a soldier and didn't rob from anyone");

		}

		model.getLog().addLine(line);

		model.updateVersionNumber();


		ServerTranslator temp = new ServerTranslator(model);
		return temp.translate();
	}

}
