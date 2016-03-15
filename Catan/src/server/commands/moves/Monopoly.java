package server.commands.moves;

import server.commands.Command;
import shared.model.GameModel;

public class Monopoly extends Command {

	public Monopoly(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: It's the player's turn. No other dev cards have been played.
	 * Postconditions: All resources of specified type are moved from their respective player hand's to this
	 * 					player's hand.
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
