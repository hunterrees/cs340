package client.turntracker;

import shared.definitions.CatanColor;
import shared.definitions.GameState;

import java.util.Observable;

import client.base.*;
import gameManager.GameManager;
import model.GameException;
import player.Player;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController {

	private GameState myGameState;
	private int currentPlayerIndex;
	GameManager myManager;
	boolean initiated = false;
	boolean endTurnEnabled = false;
	
	public TurnTrackerController(ITurnTrackerView view) {
		
		super(view);
		
		initFromModel();
	}
	
	@Override
	public ITurnTrackerView getView() {
		
		return (ITurnTrackerView)super.getView();
	}

	
	@Override
	public void endTurn() {
		if (endTurnEnabled)
		{
			try {
				myManager.finishTurn(myManager.getPlayerInfo().getId());
			} catch (GameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void initFromModel() {
		//<temp>
		getView().setLocalPlayerColor(CatanColor.RED);
		/*
		//</temp>
		 * 
		 */
	}
	
	private void initiate()
	{
		getView().setLocalPlayerColor(myManager.getPlayerInfo().getColor());
		for (int i = 0; i < 4; i++)
		{
			String playerName = myManager.getModel().getName(i);
			CatanColor playerColor = myManager.getColor(i);
			getView().initializePlayer(i, playerName, playerColor);
		}
	}
	
	private void updatePlayer()
	{
		for (int i = 0; i < 4; i++)
		{
			Player tempPlayer = myManager.getModel().getPlayers().get(i);
			int playerIndex = i;
			int points = tempPlayer.getVictoryPoints();
			boolean highlight;
			if (i == myManager.getCurrentPlayerIndex())
			{
				highlight = true;
			}
			else 
			{
				highlight = false;
			}
			boolean largestArmy = false;
			boolean longestRoad = false;
			getView().updatePlayer(playerIndex, points, highlight, largestArmy, longestRoad);
		}
	}
	
	private void updateGameState()
	{
		endTurnEnabled = false;
		String buttonMessage = "";
		
		if (myGameState == GameState.rolling || myGameState == GameState.robbing || myGameState == GameState.discarding
				|| currentPlayerIndex != myManager.getPlayerInfo().getPlayerIndex())
		{
			buttonMessage = "Waiting for Other Players";
			endTurnEnabled = false;
		}
		else
		{
			buttonMessage = "Finish Turn";
			endTurnEnabled = true;
		}
		getView().updateGameState(buttonMessage, endTurnEnabled);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(GameManager.getInstance().isStartingUp()){
			return;
		}
		// TODO Auto-generated method stub
		if (!initiated)
		{
			initiate();
			initiated = true;
		}
		myManager = (GameManager)o;
		currentPlayerIndex = myManager.getCurrentPlayerIndex();
		myGameState = myManager.getGameState();
		
		updateGameState();
		updatePlayer();
	}
}



