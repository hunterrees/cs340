package server.commands.moves;

import server.commands.Command;
import shared.model.GameModel;

public class BuildCity extends Command {

	public BuildCity(GameModel model, String json) {
		super(model, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: The city location is where you already have a settlement.
	 * You have the required resources (2 wheat, 3 ore, 1 city).
	 * Postconditions: You lost all of the required resources.
	 * The city is on the map at the specified location.
	 * You got one settlement back.
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
