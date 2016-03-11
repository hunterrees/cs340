package server.commands.games;

import server.commands.Command;
import shared.model.GameModel;

public class CreateGame extends Command{

	public CreateGame(GameModel model, String json) {
		super(model, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: name is not null and randomTiles, randomNumbers, and randomPorts contain valid boolean values.
	 * PostConditions: If the operation succeeds,
	 *	1. A new game with the specified properties has been created
	 *	2. The server returns an HTTP 200 success response.
	 *	3. The body contains a JSON object describing the newly created game
	 * If the operation fails,
	 *	1. The server returns an HTTP 400 error response, and the body contains an error
	 *	   message.
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
