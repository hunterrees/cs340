package server.commands;

import shared.model.GameModel;

public abstract class Command {
	
	private GameModel model;
	private String json;
	
	public Command(GameModel model, String json){
		this.model = model;
		this.json = json;
	}

	public abstract void execute();

}
