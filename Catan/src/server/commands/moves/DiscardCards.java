package server.commands.moves;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import client.server.ServerException;
import client.translators.moves.ResourceList;
import server.ServerTranslator;
import server.commands.Command;
import shared.definitions.GameState;
import shared.definitions.ResourceType;
import shared.model.GameModel;
import shared.model.Line;

public class DiscardCards extends Command {

	private int playerIndex;
	
	int wood = 0;
	int brick = 0;
	int sheep = 0;
	int wheat = 0;
	int ore = 0;
	
	public DiscardCards(int gameID, String json) {
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
		
		playerIndex = root.getAsJsonPrimitive("playerIndex").getAsInt();
		
		JsonObject resourcesList = root.getAsJsonObject("discardedCards");
		wood = resourcesList.getAsJsonPrimitive("wood").getAsInt();
		ore = resourcesList.getAsJsonPrimitive("ore").getAsInt();
		brick = resourcesList.getAsJsonPrimitive("brick").getAsInt();
		sheep = resourcesList.getAsJsonPrimitive("sheep").getAsInt();
		wheat = resourcesList.getAsJsonPrimitive("wheat").getAsInt();
	}
	
	private void discard() throws ServerException 
	{
		model.getPlayers().get(playerIndex).getPlayerHand().removeResources(wood, ResourceType.WOOD);
		model.getPlayers().get(playerIndex).getPlayerHand().removeResources(brick, ResourceType.BRICK);
		model.getPlayers().get(playerIndex).getPlayerHand().removeResources(wheat, ResourceType.WHEAT);
		model.getPlayers().get(playerIndex).getPlayerHand().removeResources(ore, ResourceType.ORE);
		model.getPlayers().get(playerIndex).getPlayerHand().removeResources(sheep, ResourceType.SHEEP);
	}
	
	private void checkDiscarders()
	{
		boolean endDiscardingState = true;
		model.getPlayers().get(playerIndex).setHasDiscarded(true);
		for (int i = 0; i < 4; i++)
		{
			if (model.getPlayers().get(i).getPlayerHand().getNumResources() <= 7)
			{
				model.getPlayers().get(i).setHasDiscarded(true);
				if (model.getPlayers().get(i).isHasDiscarded() == false)
				{
					endDiscardingState = false;
				}
			}
		}
		if (endDiscardingState)
		{
			for (int i = 0; i < 4; i++)
			{
				model.getPlayers().get(i).setHasDiscarded(false);
			}
			model.getTracker().setGameStatus(GameState.playing);
		}
	}
	/**
	 * Preconditions: The status of the client model is 'Discarding'.
	 * You have over 7 cards.
	 * You have the cards you're choosing to discard.
	 * Postconditions: You gave up the specified resources.
	 * If you are the last one to discard, the client model status changes to 'Robbing'.
	 * @throws ServerException 
	 */
	@Override
	public Object execute() throws ServerException {
		// TODO Auto-generated method stub
		translate();
		if (model.getTracker().getGameStatus() != GameState.discarding)
		{
			throw new ServerException("Gamestate is not Discarding");
		}
		if (!model.getPlayers().get(playerIndex).canDiscard())
		{
			throw new ServerException("Player doesn't have over 7 cards or already discarded");
		}
		discard();
		checkDiscarders();
		Line tempLine = new Line(model.getPlayers().get(playerIndex).getName(), model.getPlayers().get(playerIndex).getName() + " discarded");
		model.getLog().addLine(tempLine);
		ServerTranslator temp = new ServerTranslator(model);
		return temp.translate();
	}

}
