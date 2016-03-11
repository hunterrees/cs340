package server.commands;

import shared.model.GameModel;

public class FinishTurn extends Command {

	public FinishTurn(GameModel model, String json) {
		super(model, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: The game is in the correct state
	 * PostConditions: Cards in the newDevCards hand are transferred to the oldDevCards hand.
	 * 					The turn counter is incremented.
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
