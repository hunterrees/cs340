package server.commands.moves;

import server.commands.Command;
import shared.model.GameModel;

public class RobPlayer extends Command {

	public RobPlayer(GameModel model, String json) {
		super(model, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: The robber is being moved to a different location
	 * 					The location the robber being moved to is legal
	 * 					If a player is being robbed, the player being robbed has resource cards
	 * Postconditions: The robber is moved to the new location
	 * 					A resource card is moved from the hand of the player being robbed to the hand of the robber.
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
