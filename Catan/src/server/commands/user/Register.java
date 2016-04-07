package server.commands.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import client.server.ServerException;
import server.PersistanceManager;
import server.ServerManager;
import server.User;
import server.commands.Command;

public class Register extends Command{
	
	private String username;
	private String password;

	public Register(int gameID, String json) {
		super(gameID, json);
		Gson gson = new Gson();
		JsonObject root = null;
		try{
			root = gson.fromJson(json, JsonObject.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		username = root.getAsJsonPrimitive("username").getAsString();
		password = root.getAsJsonPrimitive("password").getAsString();
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
	 * @throws ServerException 
	 */
	@Override
	public Object execute() throws ServerException {
		if(username.equals("") || password.equals("")){
			throw new ServerException("Blank username or password");
		}
		User user = ServerManager.getInstance().getUser(username);
		if(user != null){
			throw new ServerException("User already exists");
		}
		user = new User(username, password, ServerManager.getInstance().numUsers());
		ServerManager.getInstance().addUser(user);
		PersistanceManager.getInstance().addUser(user);
		return user.generateCookie();
	}

}
