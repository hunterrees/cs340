package server.facades;

import client.server.ServerException;

public class UserFacade implements UserFacadeInterface{

	@Override
	public String login(String json) throws ServerException {
		System.out.println("Login called");
		System.out.println(json);
		return null;
	}

	@Override
	public String register(String json) throws ServerException {
		System.out.println("Register called");
		System.out.println(json);
		return null;
	}

}
