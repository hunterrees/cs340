package server.commands;

import shared.model.GameModel;

public abstract class Command {
	
	protected GameModel model;
	protected String json;
	
	public Command(GameModel model, String json){
		this.model = model;
		this.json = json;
	}

	public abstract Object execute();

}
