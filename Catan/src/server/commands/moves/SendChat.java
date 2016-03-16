package server.commands.moves;

import server.commands.Command;
import shared.model.GameModel;

public class SendChat extends Command{

	public SendChat(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: None. Message may be sent at any time by any player.
	 * PostConditions: The message is sent to the chat log.
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
