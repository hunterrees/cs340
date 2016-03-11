package server.commands.user;

import server.commands.Command;
import shared.model.GameModel;

public class Login extends Command{

	public Login(GameModel model, String json) {
		super(model, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: username and password are not null.
	 * Postconditions: If the username and password are valid: 
	 * 1. The server returns an HTTP 200 success response with "Success" in the body.
	 * 2. The HTTP response headers set the catan.user cookie to contain the identity of the
	 *	logged­in player. The cookie uses ”Path=/”, and its value contains a url­encoded JSON object of
	 *	the following form: { “name”: STRING, “password”: STRING, “playerID”: INTEGER }. For
	 *	example, { “name”: “Rick”, “password”: “secret”, “playerID”: 14 }.
	 *
	 *If the username and password ane not valid, or the operation fails for any reason:
	 *1. The server returns an HTTP 400 error response, and the body contains an error message.
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
