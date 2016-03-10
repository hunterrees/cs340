package server.facades;

import client.server.ServerException;

public interface GameFacadeInterface {

	/**
	 * returns the gameModel of the requested game
	 * @param json
	 * @param gameID
	 * @return
	 * @throws ServerException
	 */
	public String getModel(String json, int gameID) throws ServerException;
	
	/**
	 * adds and AI to the request game
	 * @param json
	 * @param gameID
	 * @throws ServerException
	 */
	public void addAI(String json, int gameID) throws ServerException;
	
	/**
	 * lists the possible AIs to add to a game
	 * @return
	 * @throws ServerException
	 */
	public String[] listAIs() throws ServerException;

}
