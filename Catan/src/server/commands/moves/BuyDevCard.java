package server.commands.moves;

import server.commands.Command;
import shared.model.GameModel;

public class BuyDevCard extends Command{

	public BuyDevCard(GameModel model, String json) {
		super(model, json);
		// TODO Auto-generated constructor stub
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
		return null;
	}

}
