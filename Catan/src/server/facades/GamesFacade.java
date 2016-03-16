package server.facades;

import client.server.ServerException;
import server.commands.games.ListGames;

public class GamesFacade implements GamesFacadeInterface{

	@Override
	public String createGame(String json) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String listGames() throws ServerException {
		ListGames listGamesCommand = new ListGames(-1, "");
		return (String) listGamesCommand.execute();
	}

	@Override
	public int joinGame(String json) throws ServerException {
		// TODO Auto-generated method stub
		return -1;
	}

}
