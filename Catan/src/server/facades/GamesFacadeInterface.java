package server.facades;

import client.server.ServerException;

public interface GamesFacadeInterface {

	/**
	 * Creates a new game on the server
	 * @param json json parameters from client
	 * @return
	 * @throws ServerException
	 */
	public String createGame(String json) throws ServerException;
	
	/**
	 * returns a list of all the games on the server
	 * @return
	 * @throws ServerException
	 */
	public String listGames() throws ServerException;
	
	/**
	 * joins the game specified on the server
	 * @param json json parameters from client
	 * @return
	 * @throws ServerException
	 */
	public void joinGame(String json) throws ServerException;

}
