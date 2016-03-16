package server.commands.moves;

import client.gameManager.GameManager;
import server.commands.Command;
import shared.definitions.HexType;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.GameModel;
import shared.model.map.Map;
import shared.model.map.TerrainHex;
import shared.model.map.Vertex;
import shared.model.player.Player;

public class RollNumber extends Command{

	Map map;

	public RollNumber(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}



	public void givePlayerResources(int roll) {
		if(roll == 7) {
			// Do robber stuff here
			return;
		}

		for(java.util.Map.Entry<HexLocation, TerrainHex> entry : map.getHexes().entrySet()) {
			if(entry.getValue().getNumber() == roll && !map.getRobberLocation().equals(entry.getKey())) {
				// Check every edge
				for(java.util.Map.Entry<VertexLocation, Vertex> entryTwo : entry.getValue().getVerticies().entrySet()) {
					if(entryTwo.getValue().getPiece() != null) {
						// I don't know if the id in piece is the playerIndex or not
						addResource(entryTwo.getValue().getPiece().getPieceType(), entry.getValue().getType(), entryTwo.getValue().getPiece().getPlayerID());
					}
				}

			}
		}
	}

	public void addResource(PieceType pt, HexType ht, int playerIndex) {
		Player p = model.getPlayers().get(playerIndex);
		int amount = 0;

		if(pt == PieceType.SETTLEMENT) {
			amount = 1;
		} else if(pt == PieceType.CITY) {
			amount = 2;
		} else {
			System.out.println("Error! PieceType doesn't exist!");
		}


		switch(ht) {
			case WOOD: p.getPlayerHand().addResources(amount, ResourceType.WOOD); break;
			case BRICK: p.getPlayerHand().addResources(amount, ResourceType.BRICK); break;
			case SHEEP: p.getPlayerHand().addResources(amount, ResourceType.SHEEP); break;
			case WHEAT: p.getPlayerHand().addResources(amount, ResourceType.WHEAT); break;
			case ORE: p.getPlayerHand().addResources(amount, ResourceType.ORE); break;
			default: System.out.println("Error! HexType doesn't exist!");
		}
	}












	/**
	 * Preconditions: It's the players turn
	 * 					The status of the player's model is 'rolling'
	 * Postconditions: Changes the status of the player to 'Discarding, Robbing, or Playing'
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
