package server.commands.moves;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import client.server.ServerException;
import server.ServerTranslator;
import server.commands.Command;
import shared.definitions.ResourceType;
import shared.model.GameModel;
import shared.model.Line;

public class MaritimeTrade extends Command {

	private int playerIndex;
	private ResourceType inputResource;
	private ResourceType outputResource;
	private int ratio;
	
	public MaritimeTrade(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Preconditions: The player has the resources being offered according to owned ports, and the bank has 
	 * the desired resource available
	 * Postconditions: The resources are exchanged between the bank and player
	 */
	
	private void translate()
	{
		Gson gson = new Gson();
		JsonObject root;
		try{
			root = gson.fromJson(json, JsonObject.class);
		}catch(Exception e){
			return;
		}
		playerIndex = root.getAsJsonPrimitive("playerIndex").getAsInt();
		ratio = root.getAsJsonPrimitive("ratio").getAsInt();
		
		JsonPrimitive primResource = root.getAsJsonPrimitive("inputResource");
		String inputResourceString = primResource.getAsString();
		switch(inputResourceString)
		{
			case "wood" : inputResource = ResourceType.WOOD; break;
			case "ore" : inputResource = ResourceType.ORE; break;
			case "wheat" : inputResource = ResourceType.WHEAT; break;
			case "sheep" : inputResource = ResourceType.SHEEP; break;
			case "brick" : inputResource = ResourceType.BRICK; break;
			default : inputResource = null; break;
		}
		JsonPrimitive primResource2 = root.getAsJsonPrimitive("outputResource");
		String outputResourceString = primResource.getAsString();
		switch(outputResourceString)
		{
			case "wood" : outputResource = ResourceType.WOOD; break;
			case "ore" : outputResource = ResourceType.ORE; break;
			case "wheat" : outputResource = ResourceType.WHEAT; break;
			case "sheep" : outputResource = ResourceType.SHEEP; break;
			case "brick" : outputResource = ResourceType.BRICK; break;
			default : outputResource = null; break;
		}
	}
	
	private void makeTrade()
	{
		model.getPlayers().get(playerIndex).getPlayerHand().addResources(1, outputResource);
		model.getBank().removeResources(1, outputResource);
		
		model.getPlayers().get(playerIndex).getPlayerHand().removeResources(ratio, inputResource);
		model.getBank().addResources(ratio, inputResource);
	}

	@Override
	public Object execute() throws ServerException {
		// TODO Auto-generated method stub
		myTurn(playerIndex);
		playingState();
		translate();
		
		if (model.maritimeTrade(playerIndex, inputResource) == -1)
		{
			throw new ServerException("Not enough resources to make the trade");
		}
		if (model.getBank().numResourceRemaining(outputResource) == 0)
		{
			throw new ServerException("The bank has 0 of this resource");
		}
		makeTrade();
		
		
		ServerTranslator temp = new ServerTranslator(model);
		return temp.translate();
	}

}
