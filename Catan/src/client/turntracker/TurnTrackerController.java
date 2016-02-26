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
				GameManager.getInstance().finishTurn(GameManager.getInstance().getPlayerInfo().getPlayerIndex());
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
		getView().setLocalPlayerColor(GameManager.getInstance().getPlayerInfo().getColor());
		for (int i = 0; i < 4; i++)
		{
			String playerName = GameManager.getInstance().getModel().getName(i);
			CatanColor playerColor = GameManager.getInstance().getColor(i);
			getView().initializePlayer(i, playerName, playerColor);
		}
	}
	
	private void updatePlayer()
	{
		for (int i = 0; i < 4; i++)
		{
			Player tempPlayer = GameManager.getInstance().getModel().getPlayers().get(i);
			int playerIndex = i;
			
			int points = tempPlayer.getVictoryPoints();
			boolean highlight;
			if (i == GameManager.getInstance().getCurrentPlayerIndex())
			{
				highlight = true;
			}
			else 
			{
				highlight = false;
			}

			boolean largestArmy = false;
			boolean longestRoad = false;
			if(GameManager.getInstance().getModel().getTracker().getLargestArmyPlayerID() == i){
				largestArmy = true;
			}
			if(GameManager.getInstance().getModel().getTracker().getLongestRoadplayerID() == i){
				longestRoad = true;
			}
			getView().updatePlayer(playerIndex, points, highlight, largestArmy, longestRoad);
		}
	}
	
	private void updateGameState()
	{
		endTurnEnabled = false;
		String buttonMessage = "";
		
		if (myGameState == GameState.rolling || myGameState == GameState.robbing || myGameState == GameState.discarding
				|| currentPlayerIndex != GameManager.getInstance().getPlayerInfo().getPlayerIndex())
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
		if(!initiated){
			initiated = true;
			initiate();
		}
		currentPlayerIndex = GameManager.getInstance().getCurrentPlayerIndex();
		myGameState = GameManager.getInstance().getGameState();
		
		updateGameState();
		updatePlayer();
	}
}



