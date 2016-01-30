package model;

import shared.definitions.GameState;

public class TurnTracker 
{
	int longestRoadplayerID;
	int largestRoadPlayerID;
	//Create enum for game Statuses
	//
	GameState gameStatus;
	int currentTurnPlayerID;
	
	public int getLongestRoadplayerID() {
		return longestRoadplayerID;
	}
	public void setLongestRoadplayerID(int longestRoadplayerID) {
		this.longestRoadplayerID = longestRoadplayerID;
	}
	public int getLargestRoadPlayerID() {
		return largestRoadPlayerID;
	}
	public void setLargestRoadPlayerID(int largestRoadPlayerID) {
		this.largestRoadPlayerID = largestRoadPlayerID;
	}
	public GameState getGameStatus() {
		return gameStatus;
	}
	public void setGameStatus(GameState gameStatus) {
		this.gameStatus = gameStatus;
	}
	public int getCurrentTurnPlayerID() {
		return currentTurnPlayerID;
	}
	public void setCurrentTurnPlayerID(int currentTurnPlayerID) {
		this.currentTurnPlayerID = currentTurnPlayerID;
	}
	
}
