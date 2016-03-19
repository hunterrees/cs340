package server.facades;

import client.server.ServerException;
import server.commands.game.*;

public class GameFacade implements GameFacadeInterface{

	@Override
	public String getModel(int gameID) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAI(String json) throws ServerException {
		return;
	}

	@Override
	public String listAIs() throws ServerException {
		ListAI listAICommand = new ListAI(-1, "");
		return (String) listAICommand.execute();
	}

}
