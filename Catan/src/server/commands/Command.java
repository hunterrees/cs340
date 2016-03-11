package server.commands;

import shared.model.GameModel;

public abstract class Command {
	
	/**
	 * Contains the current model so that execute can make changes
	 */
	protected GameModel model;
	/**
	 * Contains the JSON string with the required parameters for execute
	 */
	protected String json;
	
	public Command(GameModel model, String json){
		this.model = model;
		this.json = json;
	}

	/**
	 * Updates the model according to the command and parameters given
	 * @return
	 */
	public abstract Object execute();

}
