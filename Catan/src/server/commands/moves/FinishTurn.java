package server.commands.moves;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import client.server.ServerException;
import server.ServerTranslator;
import server.commands.Command;
import shared.model.GameModel;
import shared.model.Line;

public class FinishTurn extends Command {

	public FinishTurn(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: The game is in the correct state
	 * PostConditions: Cards in the newDevCards hand are transferred to the oldDevCards hand.
	 * 					The turn counter is incremented.
	 * @throws ServerException 
	 */
	
	private void incrementTurn(int playerIndex)
	{
		int nextTurn = 0;
		if (playerIndex == 3)
		{
			nextTurn = 0;
		}
		else 
		{
			nextTurn = playerIndex + 1;
		}
		model.getTracker().setCurrentTurnPlayerID(nextTurn);
	}
	
	private void newDevCardsToOld(int playerIndex)
	{
		model.getPlayers().get(playerIndex).getPlayerHand().newDevCardsToOld();
	}
	
	@Override
	public Object execute() throws ServerException {
		// TODO Auto-generated method stub
		int playerIndex;
		Gson gson = new Gson();
		JsonObject root;
		try{
			root = gson.fromJson(json, JsonObject.class);
		}catch(Exception e){
			return null;
		}
		playerIndex = root.getAsJsonPrimitive("playerIndex").getAsInt();
		playingState();
		myTurn(playerIndex);
		
		//increment player turn
		incrementTurn(playerIndex);
		newDevCardsToOld(playerIndex);
		
		Line tempLine = new Line(model.getPlayers().get(playerIndex).getName(), model.getPlayers().get(playerIndex).getName() + " turn just ended");
		model.getLog().addLine(tempLine);
		ServerTranslator temp = new ServerTranslator(model);
		return temp.translate();
		
	}

}
