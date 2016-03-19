package server.commands.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import client.server.ServerException;
import server.ServerManager;
import server.User;
import server.commands.Command;

public class Login extends Command{
	
	private String username;
	private String password;

	public Login(int gameID, String json) {
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
	 * @throws ServerException 
	 */
	@Override
	public Object execute() throws ServerException {
		if(username.equals("") || password.equals("")){
			throw new ServerException("Blank username or password");
		}
		User user = ServerManager.getInstance().getUser(username);
		if(user == null){
			throw new ServerException("User does not exist");
		}
		if(user.getName().equals(username) && user.getPassword().equals(password)){
			return user.generateCookie();
		}else{
			throw new ServerException("Invalid password for user");
		}
	}

}
