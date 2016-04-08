package server.commands.moves;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import client.server.ServerException;
import server.ServerManager;
import server.ServerTranslator;
import server.commands.Command;
import shared.definitions.GameState;
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
		if(model.getGameState() == GameState.playing){
			playerIndex = ++playerIndex%4;
			model.getTracker().setCurrentTurnPlayerID(playerIndex);
			model.getTracker().setGameStatus(GameState.rolling);
		}else if((model.getGameState() == GameState.firstRound) && (model.getCurrentPlayerIndex() == 3)){
			model.getTracker().setGameStatus(GameState.secondRound);
		}else if((model.getGameState() == GameState.secondRound) && model.getCurrentPlayerIndex() == 0){
			model.getTracker().setGameStatus(GameState.rolling);
		}else if(model.getGameState() == GameState.firstRound){
			playerIndex = ++playerIndex%4;
			model.getTracker().setCurrentTurnPlayerID(playerIndex);
		}else if (model.getGameState() == GameState.secondRound){
			playerIndex = --playerIndex%4;
			model.getTracker().setCurrentTurnPlayerID(playerIndex);
		}
	}
	
	private void newDevCardsToOld(int playerIndex)
	{
		model.getPlayers().get(playerIndex).getPlayerHand().newDevCardsToOld();
		for (int i = 0; i < 4; i++)
		{
			model.getPlayers().get(i).setHasPlayedDevCard(false);
		}
	}
	
	@Override
	public Object execute() throws ServerException {
		if(gameID != -1){
			this.model = ServerManager.getInstance().getGame(gameID);
		}
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
		myTurn(playerIndex);
		
		incrementTurn(playerIndex);
		newDevCardsToOld(playerIndex);
		model.updateVersionNumber();
		Line tempLine = new Line(model.getPlayers().get(playerIndex).getName(), model.getPlayers().get(playerIndex).getName() + " turn just ended");
		model.getLog().addLine(tempLine);
		ServerTranslator temp = new ServerTranslator(model);
		return temp.translate();
		
	}

}
