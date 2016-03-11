package server.commands.moves;

import server.commands.Command;
import shared.model.GameModel;

public class DiscardCards extends Command {

	public DiscardCards(GameModel model, String json) {
		super(model, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: The status of the client model is 'Discarding'.
	 * You have over 7 cards.
	 * You have the cards you're choosing to discard.
	 * Postconditions: You gave up the specified resources.
	 * If you are the last one to discard, the client model status changes to 'Robbing'.
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
