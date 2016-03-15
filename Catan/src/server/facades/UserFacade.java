package server.facades;

import client.server.ServerException;
import server.commands.user.Login;
import server.commands.user.Register;

public class UserFacade implements UserFacadeInterface{

	@Override
	public String login(String json) throws ServerException {
		Login loginCommand = new Login(-1, json);
		return (String) loginCommand.execute();
	}

	@Override
	public String register(String json) throws ServerException {
		Register registerCommand = new Register(-1, json);
		return (String) registerCommand.execute();
	}

}
