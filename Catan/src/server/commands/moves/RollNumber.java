package server.commands.moves;

import server.commands.Command;
import shared.model.GameModel;

public class RollNumber extends Command{

	public RollNumber(GameModel model, String json) {
		super(model, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: It's the players turn
	 * 					The status of the player's model is 'rolling'
	 * Postconditions: Changes the status of the player to 'Discarding, Robbing, or Playing'
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
