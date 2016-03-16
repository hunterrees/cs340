package server.commands.moves;

import client.gameManager.GameManager;
import server.commands.Command;
import shared.definitions.ResourceType;
import shared.model.Bank;
import shared.model.GameModel;
import shared.model.player.Player;

public class BuyDevCard extends Command{

	private int playerIndex;



	public BuyDevCard(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: The player has the required resources (1 ore, 1 wheat, 1 sheep)
	 * 					There are Dev cards remaining in the bank.
	 * PostConditions: A random dev card is added to the players hand. If it's a monument it's added to old
	 * 					dev cards. Otherwise it is added to new dev cards (unplayable this turn).
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub

		// Check if the player has enough resources to buy a development card
		boolean enough = true;

		if(enough) {
			Player p = GameManager.getInstance().getModel().getPlayers().get(playerIndex);

			// Remove resources
/*			p.getPlayerHand().removeResources(1, ResourceType.SHEEP);
			p.getPlayerHand().removeResources(1, ResourceType.WHEAT);
			p.getPlayerHand().removeResources(1, ResourceType.ORE);*/

			p.canBuyDevCard();



		}


		return null;
	}

}
