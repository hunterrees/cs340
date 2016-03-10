package server.commands;

import shared.model.GameModel;

public class SendChat extends Command{

	public SendChat(GameModel model, String json) {
		super(model, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: None. Message may be sent at any time by any player.
	 * PostConditions: The message is sent to the chat log.
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
