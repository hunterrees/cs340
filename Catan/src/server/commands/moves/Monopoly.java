package server.commands.moves;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import client.server.ServerException;
import server.commands.Command;
import shared.ResourceCard;
import shared.definitions.ResourceType;
import shared.model.GameModel;

public class Monopoly extends Command {
	
	private int playerIndex;
	ResourceType resourceToSteal;
	
	public Monopoly(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}

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
		
		JsonPrimitive primIndex = root.getAsJsonPrimitive("playerIndex");
		playerIndex = primIndex.getAsInt();
		
		JsonPrimitive primResource = root.getAsJsonPrimitive("resource");
		String resourceString = primResource.getAsString();
		switch(resourceString)
		{
			case "wood" : resourceToSteal = ResourceType.WOOD; break;
			case "ore" : resourceToSteal = ResourceType.ORE; break;
			case "wheat" : resourceToSteal = ResourceType.WHEAT; break;
			case "sheep" : resourceToSteal = ResourceType.SHEEP; break;
			case "brick" : resourceToSteal = ResourceType.BRICK; break;
			default : resourceToSteal = null; break;
		}
	}
	/**
	 * Preconditions: It's the player's turn. No other dev cards have been played.
	 * Postconditions: All resources of specified type are moved from their respective player hand's to this
	 * 					player's hand.
	 */
	
	private int removeResourcesOfType(int playerIndex, ResourceType type)
	{
		int numRemoved = 0;
		ArrayList<ResourceCard> resources = model.getPlayers().get(playerIndex).getPlayerHand().getResourceCards();
		ArrayList<ResourceType> newList = new ArrayList<ResourceType>();
		int wood = 0;
		int sheep = 0;
		int wheat = 0;
		int brick = 0;
		int ore = 0;
		for (int i = 0; i < resources.size(); i++)
		{
			if (resources.get(i).getType() == ResourceType.WOOD) wood++;
			if (resources.get(i).getType() == ResourceType.BRICK) brick++;
			if (resources.get(i).getType() == ResourceType.SHEEP) sheep++;
			if (resources.get(i).getType() == ResourceType.WHEAT) wheat++;
			if (resources.get(i).getType() == ResourceType.ORE) ore++;
			
		}
		if (type == ResourceType.WOOD) {numRemoved = wood; wood = 0;}
		else if (type == ResourceType.BRICK) {numRemoved = brick; brick = 0;}
		else if (type == ResourceType.SHEEP) {numRemoved = sheep; sheep = 0;}
		else if (type == ResourceType.WHEAT) {numRemoved = wheat; wheat = 0;}
		else if (type == ResourceType.ORE) {numRemoved = ore; ore = 0;}
		
		for (int i = 0; i < wood; i++) {newList.add(ResourceType.WOOD);}
		for (int i = 0; i < wheat; i++) {newList.add(ResourceType.WHEAT);}
		for (int i = 0; i < sheep; i++) {newList.add(ResourceType.SHEEP);}
		for (int i = 0; i < ore; i++) {newList.add(ResourceType.ORE);}
		for (int i = 0; i < brick; i++) {newList.add(ResourceType.BRICK);}
		
		resources = new ArrayList<ResourceCard>();
		for (int i = 0; i < newList.size(); i++)
		{
			resources.add(new ResourceCard(newList.get(i)));
		}
		
		return numRemoved;
	}
	
	
	@Override
	public Object execute() throws ServerException {
		translate();
		playingState();
		myTurn(playerIndex);
		int stealAmount = 0;
		for (int i = 0; i < 4; i++)
		{
			//if not the player who's stealing
			if (i != playerIndex)
			{
				stealAmount += removeResourcesOfType(i, resourceToSteal);
			}
		}
		model.getPlayers().get(playerIndex).getPlayerHand().addResources(stealAmount, resourceToSteal);
		// TODO Auto-generated method stub
		return model;
	}

}
