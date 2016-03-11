package server.commands.moves;

import server.commands.Command;
import shared.model.GameModel;

public class YearOfPlenty extends Command {

	public YearOfPlenty(GameModel model, String json) {
		super(model, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: The two specified resources are in the bank
	 * Postconditions: The two specified resources are removed from the bank and added to the hand of the 
	 * 					player.
	 */
	//testing
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}
}
