package server.commands.moves;

import server.commands.Command;
import shared.model.GameModel;

public class AcceptTrade extends Command{

	public AcceptTrade(GameModel model, String json) {
		super(model, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: You have been offered a domestic trade.
	 * To accept the trade offer, you have the required resources
	 * Postconditions: If you accepted, you and the player who offered swap the specified resources.
	 * If you declined, no resources are exchanged.
	 * The trade offer is removed after choice is made.
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
