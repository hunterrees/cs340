package server.facades;

import client.server.ServerException;
import server.User;
import server.commands.games.CreateGame;
import server.commands.games.JoinGame;
import server.commands.games.ListGames;

public class GamesFacade implements GamesFacadeInterface{

	@Override
	public String createGame(String json) throws ServerException {
		CreateGame createGameCommand = new CreateGame(-1, json);
		return (String) createGameCommand.execute();
	}

	@Override
	public String listGames() throws ServerException {
		ListGames listGamesCommand = new ListGames(-1, "");
		return (String) listGamesCommand.execute();
	}

	@Override
	public int joinGame(String json, User user) throws ServerException {
		JoinGame joinGameCommand = new JoinGame(-1, json);
		joinGameCommand.setUser(user);
		return (int) joinGameCommand.execute();
	}

}
