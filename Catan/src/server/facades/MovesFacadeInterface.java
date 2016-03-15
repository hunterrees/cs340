package server.facades;

import client.server.ServerException;

public interface MovesFacadeInterface {
	
	/**
	 * determines which operation to call from the /moves commands
	 * 
	 * all 16 operations return the update GameModel (throw a ServerException if there is an error in the process)
	 * @param command url for command
	 * @param json parameters for the command
	 * @return
	 */
	public String determineOperation(String command, String json) throws ServerException;
	
	public String sendChat(String json) throws ServerException;
	
	public String rollNumber(String json) throws ServerException;
	
	public String robPlayer(String json) throws ServerException;
	
	public String finishTurn(String json) throws ServerException;
	
	public String buyDevCard(String json) throws ServerException;
	
	public String yearOfPlenty(String json) throws ServerException;
	
	public String roadBuilding(String json) throws ServerException;
	
	public String soldier(String json) throws ServerException;
	
	public String monopoly(String json) throws ServerException;
	
	public String monument(String json) throws ServerException;
	
	public String buildRoad(String json) throws ServerException;
	
	public String buildSettlement(String json) throws ServerException;
	
	public String buildCity(String json) throws ServerException;
	
	public String offerTrade(String json) throws ServerException;
	
	public String acceptTrade(String json) throws ServerException;
	
	public String discardCards(String json) throws ServerException;

}
