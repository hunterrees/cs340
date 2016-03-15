package server.commands.moves;

import server.commands.Command;
import shared.model.GameModel;

public class BuildRoad extends Command {

	public BuildRoad(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}


	
	/**
	 * Preconditions: The road location is open
	 * 					The road is connected to another road owned by the player
	 * 					The build location is not on water
	 * 					The player has the required resources (1 wood, 1 brick, 1 road)
	 * 					If it's settup round the location must be adjacent to a settlement owned by the player
	 * 					with no adjacent road
	 * Postconditions: The resources required to build a road are lost
	 * 					The road is placed in the specified locatoin
	 * 					If applicable, longest road is given to the player.
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
