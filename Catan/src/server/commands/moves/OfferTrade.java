package server.commands.moves;

import server.commands.Command;
import shared.model.GameModel;

public class OfferTrade extends Command{

	public OfferTrade(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Preconditions: The trader has the resources being offered
	 * PostConditions: The tradeOffer class instance in the model is populated
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
