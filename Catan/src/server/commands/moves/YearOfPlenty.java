package server.commands.moves;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import client.server.ServerException;
import server.commands.Command;
import shared.definitions.ResourceType;
import shared.model.GameModel;

public class YearOfPlenty extends Command {

	private ResourceType resource1;
	private ResourceType resource2;
	private int playerIndex;
	public YearOfPlenty(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: The two specified resources are in the bank
	 * Postconditions: The two specified resources are removed from the bank and added to the hand of the 
	 * 					player.
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
		
		JsonPrimitive typePrim = root.getAsJsonPrimitive("type");
		
		playerIndex = root.getAsJsonPrimitive("playerIndex").getAsInt();
		
		JsonPrimitive primResource = root.getAsJsonPrimitive("resource1");
		String resource1String = primResource.getAsString();
		switch(resource1String)
		{
			case "wood" : resource1 = ResourceType.WOOD; break;
			case "ore" : resource1 = ResourceType.ORE; break;
			case "wheat" : resource1 = ResourceType.WHEAT; break;
			case "sheep" : resource1 = ResourceType.SHEEP; break;
			case "brick" : resource1 = ResourceType.BRICK; break;
			default : resource1 = null; break;
		}
		JsonPrimitive primResource2 = root.getAsJsonPrimitive("resource2");
		String resource2String = primResource.getAsString();
		switch(resource2String)
		{
			case "wood" : resource2 = ResourceType.WOOD; break;
			case "ore" : resource2 = ResourceType.ORE; break;
			case "wheat" : resource2 = ResourceType.WHEAT; break;
			case "sheep" : resource2 = ResourceType.SHEEP; break;
			case "brick" : resource2 = ResourceType.BRICK; break;
			default : resource2 = null; break;
		}
	}
	private boolean bankHasResources()
	{
		if (model.yearOfPlenty(playerIndex, resource1, resource2))
		{
			return true;
		}
		return false;
	}
	private void moveResources()
	{
		model.getBank().removeResources(1, resource1);
		model.getBank().removeResources(1, resource2);
		model.getPlayers().get(playerIndex).getPlayerHand().addResources(1, resource1);
		model.getPlayers().get(playerIndex).getPlayerHand().addResources(1, resource2);
	}
	//testing
	@Override
	public Object execute() throws ServerException {
		// TODO Auto-generated method stub
		playingState();
		myTurn(playerIndex);
		if (!bankHasResources())
		{
			throw new ServerException("The bank doesn't have the necessary resources");
		}
		return null;
	}
}
