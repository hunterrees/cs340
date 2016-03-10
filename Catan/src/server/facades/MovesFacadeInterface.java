package server.facades;

import client.server.ServerException;

public interface MovesFacadeInterface {
	
	/**
	 * determines which operation to call from the /moves commands
	 * 
	 * all 16 operations return the update GameModel (throw a ServerException if there is an error in the process)
	 * @param command url for command
	 * @param json parameters for the command
	 * @param gameID id of game to act on
	 * @return
	 */
	public String determineOperation(String command, String json, int gameID) throws ServerException;
	
	public String sendChat(String json, int gameID) throws ServerException;
	
	public String rollNumber(String json, int gameID) throws ServerException;
	
	public String robPlayer(String json , int gameID) throws ServerException;
	
	public String finishTurn(String json, int gameID) throws ServerException;
	
	public String buyDevCard(String json, int gameID) throws ServerException;
	
	public String yearOfPlenty(String json, int gameID) throws ServerException;
	
	public String roadBuilding(String json, int gameID) throws ServerException;
	
	public String soldier(String json, int gameID) throws ServerException;
	
	public String monopoly(String json, int gameID) throws ServerException;
	
	public String monument(String json, int gameID) throws ServerException;
	
	public String buildRoad(String json, int gameID) throws ServerException;
	
	public String buildSettlement(String json, int gameID) throws ServerException;
	
	public String buildCity(String json, int gameID) throws ServerException;
	
	public String offerTrade(String json, int gameID) throws ServerException;
	
	public String acceptTrade(String json, int gameID) throws ServerException;
	
	public String discardCards(String json, int gameID) throws ServerException;

}
