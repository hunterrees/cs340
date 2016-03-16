package server.commands.games;

import server.ServerManager;
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
		for(int i = 0; i < ServerManager.getInstance().getGames().size(); i++){
			GameModel game = ServerManager.getInstance().getGame(i);
			for(int j = 0; j < game.getPlayers().size(); j++){
				//extract PlayerInfo
			}
			//extract GameInfo and add PlayerInfo
			//add game info to the array
		}
		//make the GameInfo into a Json string and return it
		return null;
	}

}
