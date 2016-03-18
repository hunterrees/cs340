package server.commands.moves;

import client.gameManager.GameManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import server.ServerTranslator;
import server.commands.Command;
import shared.DevelopmentCard;
import shared.definitions.ResourceType;
import shared.model.Bank;
import shared.model.GameModel;
import shared.model.Line;
import shared.model.player.Player;

public class BuyDevCard extends Command{

	private String type;
	private int playerIndex;



	public BuyDevCard(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
		translate(json);


	}



	private void translate(String json) {
		Gson gson = new Gson();
		JsonObject root;
		try{
			root = gson.fromJson(json, JsonObject.class);
		} catch(Exception e) {
			return;
		}

		JsonPrimitive typePrim = root.getAsJsonPrimitive("type");
		type = typePrim.getAsString();

		JsonPrimitive primIndex = root.getAsJsonPrimitive("playerIndex");
		playerIndex = primIndex.getAsInt();



	}

		/**
         * Preconditions: The player has the required resources (1 ore, 1 wheat, 1 sheep)
         * 					There are Dev cards remaining in the bank.
         * PostConditions: A random dev card is added to the players hand. If it's a monument it's added to old
         * 					dev cards. Otherwise it is added to new dev cards (unplayable this turn).
         */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub

		// Check if the player has enough resources to buy a development card
		Player p = model.getPlayers().get(playerIndex);

		if(p.canBuyDevCard()) {
			// Take a development card out of the bank and give it to the player
			DevelopmentCard card = model.getBank().getDevelopmentCards().get(0);
			p.getPlayerHand().addNewDevelopmentCard(card.getType());
		}


		Line line = new Line(p.getName(), p.getName() + " bought a development card");
		model.getLog().addLine(line);

		//model.getTracker().setGameStatus(blah);

		ServerTranslator temp = new ServerTranslator(model);
		return temp.translate();
	}

}
