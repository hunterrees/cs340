package server.commands.moves;

import server.commands.Command;
import shared.model.GameModel;

public class Soldier extends Command {

	public Soldier(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: The robber is is being moved from its current location
	 * 					If a player is being robbed, that player has resource cards
	 * Postconditions: The robber is moved to the specified location
	 * 					A resource card is moved from the player being robbed's hand to the robbing player's hand
	 * 					If applicable, largest army is awarded to the robbing player
	 * 					No other Dev cards can be played this turn
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
