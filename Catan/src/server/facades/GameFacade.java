package server.facades;

import client.server.ServerException;
import server.commands.game.*;

public class GameFacade implements GameFacadeInterface{

	@Override
	public String getModel(int gameID, String command) throws ServerException {
		GetModel getModelCommand = new GetModel(gameID, command);
		return (String) getModelCommand.execute();
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
