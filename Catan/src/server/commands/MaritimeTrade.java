package server.commands;

import shared.model.GameModel;

public class MaritimeTrade extends Command {

	public MaritimeTrade(GameModel model, String json) {
		super(model, json);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Preconditions: The player has the resources being offered according to owned ports, and the bank has 
	 * the desired resource available
	 * Postconditions: The resources are exchanged between the bank and player
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
