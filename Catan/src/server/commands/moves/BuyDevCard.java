package server.commands.moves;

import client.gameManager.GameManager;
import client.server.ServerException;

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

import java.util.ArrayList;

public class BuyDevCard extends Command{

	private String type;
	private int playerIndex;
	private boolean test = false;



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
		 * @throws ServerException 
         */
	@Override
	public Object execute() throws ServerException {
		// TODO Auto-generated method stub

		// Check if the player has enough resources to buy a development card
		Player p = model.getPlayers().get(playerIndex);

		if(p.canBuyDevCard() || test) {
			// Take a development card out of the bank and give it to the player
			DevelopmentCard card = model.getBank().getDevelopmentCards().get(0);
			ArrayList<DevelopmentCard> banksCards = model.getBank().getDevelopmentCards();
			banksCards.remove(0);
			model.getBank().setBankDevelopmentCards(banksCards);

			p.getPlayerHand().addNewDevelopmentCard(card.getType());

			p.getPlayerHand().removeResources(1, ResourceType.SHEEP);
			p.getPlayerHand().removeResources(1, ResourceType.WHEAT);
			p.getPlayerHand().removeResources(1, ResourceType.ORE);



		}


		Line line = new Line(p.getName(), p.getName() + " bought a development card");
		model.getLog().addLine(line);

		//model.getTracker().setGameStatus(blah);

		ServerTranslator temp = new ServerTranslator(model);
		return temp.translate();
	}

	public void setTest(boolean test) {
		this.test = test;
	}
}
