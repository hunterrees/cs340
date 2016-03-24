package server.commands.moves;

import client.server.ServerException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import server.ServerTranslator;
import server.commands.Command;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.model.GameModel;
import shared.model.Line;

public class Monument extends Command {

	ResourceType resource;
	int playerIndex;
	public Monument(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: It is the player's turn
	 * Postconditions: A victory point is added ot the player
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
		JsonPrimitive primResource = root.getAsJsonPrimitive("type");
		String resource1String = primResource.getAsString();
		switch(resource1String)
		{
			case "wood" : resource = ResourceType.WOOD; break;
			case "ore" : resource = ResourceType.ORE; break;
			case "wheat" : resource = ResourceType.WHEAT; break;
			case "sheep" : resource = ResourceType.SHEEP; break;
			case "brick" : resource = ResourceType.BRICK; break;
			default : resource = null; break;
		}
	}
	
	private void addVictoryPoint()
	{
		model.getPlayers().get(playerIndex).addVictoryPoint();
		model.checkVictory();
	}
	@Override
	public Object execute() throws ServerException {
		// TODO Auto-generated method stub
		translate();
		addVictoryPoint();
		if (!model.getPlayers().get(playerIndex).getPlayerHand().removeNewDevCard(DevCardType.MONUMENT))
		{
			model.getPlayers().get(playerIndex).getPlayerHand().removeOldDevCard(DevCardType.MONUMENT);
		}
		model.getPlayers().get(playerIndex).setHasPlayedDevCard(true);
		model.updateVersionNumber();
		Line tempLine = new Line(model.getPlayers().get(playerIndex).getName(), model.getPlayers().get(playerIndex).getName() + " built a monument and gained a victory point");
		model.getLog().addLine(tempLine);
		ServerTranslator temp = new ServerTranslator(model);
		return temp.translate();
	}

}
