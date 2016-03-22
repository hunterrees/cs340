package server.commands.game;

import client.server.ServerException;
import server.ServerManager;
import server.ServerTranslator;
import server.commands.Command;

public class GetModel extends Command{

	private ServerTranslator translator;
	
	public GetModel(int gameID, String json) {
		super(gameID, json);
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
	 * @throws ServerException 
	 */
	@Override
	public Object execute() throws ServerException {
		model = ServerManager.getInstance().getGame(gameID);
		if(model == null){
			throw new ServerException("Game doesn't exist");
		}
		translator = new ServerTranslator(model);
		if(json.contains("?")){
			json = json.replace("/model?version=", "");
			int version = Integer.parseInt(json);
			if(version < model.getVersion()){
				return translator.translate();
			}else{
				return "true";
			}
		}else{
			return translator.translate();
		}
	}

}
