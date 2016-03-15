package server.commands.moves;

import server.commands.Command;
import shared.model.GameModel;

public class Monument extends Command {

	public Monument(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: It is the player's turn
	 * Postconditions: A victory point is added ot the player
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
