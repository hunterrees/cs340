package server.commands.games;

import server.commands.Command;
import shared.model.GameModel;

public class JoinGame extends Command{

	public JoinGame(GameModel model, String json) {
		super(model, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: The user has previously logged in to the server.
	 *  The player may join the game because they are already in the game, or there is space in the game to add a new player.
	 *  The specified game ID is valid.
	 *  The specified color is valid(red, green, blue, yellow, puce, brown, white, purple).
	 * Postconditions: If the operation succeeds,
	 *	1. The server returns an HTTP 200 success response with “Success” in the body.
	 *	2. The player is in the game with the specified color (i.e. calls to /games/list method will
	 *	show the player in the game with the chosen color).
	 *	3. The server response includes the “Set­cookie” response header setting the catan.game
	 *	HTTP cookie
	 *	If the operation fails,
	 *	1. The server returns an HTTP 400 error response, and the body contains an error
	 *	message.
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
