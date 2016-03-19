package server.commands.game;

import server.commands.Command;

public class GetModel extends Command{

	public GetModel(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: 1. The caller has previously logged in to the server and joined a game (i.e., they have
	 *	valid catan.user and catan.game HTTP cookies).
	 *	2. If specified, the version number is included as the “version” query parameter in the
	 *	request URL, and its value is a valid integer.
	 * Postconditions: If the operation succeeds,
	 *	1. The server returns an HTTP 200 success response.
	 *	2. The response body contains JSON data
	 *	a. The full client model JSON is returned if the caller does not provide a version
	 *	number, or the provide version number does not match the version on the server
	 *	b. “true” (true in double quotes) is returned if the caller provided a version number,
	 *	and the version number matched the version number on the server
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
