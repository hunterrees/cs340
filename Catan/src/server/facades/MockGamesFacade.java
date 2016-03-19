package server.facades;

import client.server.ServerException;
import server.User;

public class MockGamesFacade implements GamesFacadeInterface {

	@Override
	public String createGame(String json) throws ServerException {
		return "New Game created";
	}

	@Override
	public String listGames() throws ServerException {
		return "Game 1, Game 2, Game 3";
	}

	@Override
	public int joinGame(String json, User user) throws ServerException {
		return 0;
	}

}
