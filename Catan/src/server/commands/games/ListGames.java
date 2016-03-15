package server.commands.games;

import server.commands.Command;
import shared.model.GameModel;

public class ListGames extends Command{

	public ListGames(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: None
	 * Postconditions: If the operation succeds:
	 *  1. The server returns an HTTP 200 success response.
	 *  2. The body contains a JSON array containing a list of objects that contain information about the server's games.
	 * If the operation fails:
	 *  1. The server returns an HTTP 400 error response, and the body contains an error message.
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
