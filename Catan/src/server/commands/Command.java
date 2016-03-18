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
	
	protected void myTurn(int playerID) throws ServerException
	{
		if (model.getTracker().getCurrentTurnPlayerID() != playerID)
		{
			throw new ServerException("Not your turn");
		}
	}
	
	protected void playingState() throws ServerException
	{
		if (model.getTracker().getGameStatus() != GameState.playing)
		{
			throw new ServerException("Gamestate is not Playing");
		}
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
