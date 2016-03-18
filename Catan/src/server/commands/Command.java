package server.commands;

import client.server.ServerException;
import server.ServerManager;
import shared.definitions.GameState;
import shared.model.GameModel;

public abstract class Command {
	
	/**
	 * Contains the current model so that execute can make changes
	 */
	protected GameModel model;
	/**
	 * Contains the JSON string with the required parameters for execute
	 */
	protected String json;
	
	protected int gameID;
	
	protected boolean myTurn(int playerID)
	{
		if (model.getTracker().getCurrentTurnPlayerID() == playerID)
		{
			return true;
		}
		return false;
	}
	
	protected boolean playingState()
	{
		if (model.getTracker().getGameStatus() == GameState.playing)
		{
			return true;
		}
		return false;
	}
	
	public Command(int gameID, String json){
		this.gameID = gameID;
		if(gameID != -1){
			this.model = ServerManager.getInstance().getGame(gameID);
		}
		this.json = json;
	}

	/**
	 * Updates the model according to the command and parameters given
	 * @return
	 * @throws ServerException 
	 */
	public abstract Object execute() throws ServerException;

}
