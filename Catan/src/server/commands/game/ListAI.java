package server.commands.game;

import com.google.gson.Gson;

import server.commands.Command;
import shared.model.GameModel;

public class ListAI extends Command {

	public ListAI(int gameID, String json) {
		super(gameID, json);
	}
	/**
	 * Preconditions: none
	 * PostConditions: Returns HTTP 200 response with success in body
	 * 					Returns JSON array of the types of AI players that can be added to a game
	 */
	@Override
	public Object execute() {
		return "[]";
	}

}
