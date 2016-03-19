package server.commands.game;

import server.commands.Command;

public class AddAI extends Command {

	public AddAI(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Preconditions: The players has logged in
	 * 					The player has joined a game
	 * 					The joined game has open slots
	 * Postconditions: The server returns 200 OK with success in the body
	 * 					The server selects a name and color, and adds an AI of the specified type to the game
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
