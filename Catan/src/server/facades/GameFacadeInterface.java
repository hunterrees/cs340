package server.facades;

import client.server.ServerException;

public interface GameFacadeInterface {

	/**
	 * returns the gameModel of the requested game
	 * @param gameID
	 * @return
	 * @throws ServerException
	 */
	public String getModel(int gameID) throws ServerException;
	
	/**
	 * adds and AI to the request game
	 * @param json
	 * @throws ServerException
	 */
	public void addAI(String json) throws ServerException;
	
	/**
	 * lists the possible AIs to add to a game
	 * @return
	 * @throws ServerException
	 */
	public String listAIs() throws ServerException;

}
