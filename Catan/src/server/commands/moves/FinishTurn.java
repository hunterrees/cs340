package server.commands.moves;

import server.commands.Command;
import shared.model.GameModel;

public class FinishTurn extends Command {

	public FinishTurn(int gameID, String json) {
		super(gameID, json);
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
