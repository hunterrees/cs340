package server.facades;

import client.server.ServerException;
import server.ServerManager;

public class MockUserFacade implements UserFacadeInterface {

	@Override
	public String login(String json) throws ServerException {
		return ServerManager.getInstance().getUser("Sam").generateCookie();
	}

	@Override
	public String register(String json) throws ServerException {
		return ServerManager.getInstance().getUser("Sam").generateCookie();
	}

}
