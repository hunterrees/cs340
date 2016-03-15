package server.commands.user;

import server.commands.Command;
import shared.model.GameModel;

public class Register extends Command{

	public Register(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: The username and password are not null.
	 * the specified username is not already in use.
	 * PostConditions: If there is no existing user with the specified username,
	 *	1. A new user account has been created with the specified username and password.
	 *	2. The server returns an HTTP 200 success response with “Success” in the body.
	 *	3. The HTTP response headers set the catan.user cookie to contain the identity of the
	 *	logged­in player. The cookie uses ”Path=/”, and its value contains a url­encoded JSON object of
	 *	the following form: { “name”: STRING, “password”: STRING, “playerID”: INTEGER }. For
	 *	example, { “name”: “Rick”, “password”: “secret”, “playerID”: 14 }.
	 *	If there is already an existing user with the specified name, or the operation fails for any other
	 *	reason,
	 *	1. The server returns an HTTP 400 error response, and the body contains an error
	 *	message.
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
