package server.commands.moves;

import server.commands.Command;
import shared.model.GameModel;

public class BuildSettlement extends Command {

	public BuildSettlement(GameModel model, String json) {
		super(model, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: The settlement location is valid: 
	 * (open, not on water, follows the two-away rule, connected to friendly road unless setup), 
	 * you have the required resources (1 wood, 1 brick, 1 sheep, 1 wheat, 1 settlement).
	 * Postconditions: The player loses the resources for building a settlement (wood, brick, sheep, wheat)
	 * The settlement is placed on the map in the specified location.
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
