package server.commands.moves;

import server.commands.Command;
import shared.model.GameModel;

public class RoadBuilding extends Command {

	public RoadBuilding(GameModel model, String json) {
		super(model, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: The first road location (spot1) is connected to one of your roads.
		*				The second road location (spot2) is connected to one of your roads or to the first
		*				road location (spot1)
		*				Neither road location is on water
		*				You have at least two unused roads
	 * Postconditions: Usable roads in hand of player is reduced by 2.
	 * 					The roads are built in the specified locations 
	 * 					If applicable longest road is given to the player.

	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
