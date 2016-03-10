package server.facades;

import client.server.ServerException;

public interface UserFacadeInterface {
	
	/**
	 * logs an existing user into the server, returns a user cookie
	 * @param json
	 * @return
	 * @throws ServerException
	 */
	public String login(String json) throws ServerException;
	
	/**
	 * creates a new user on the server and logs them in, returns a user cookie
	 * @param json
	 * @return
	 * @throws ServerException
	 */
	public String register(String json) throws ServerException;

}
