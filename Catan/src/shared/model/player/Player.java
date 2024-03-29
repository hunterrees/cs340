package shared.model.player;
import java.io.Serializable;
import java.util.ArrayList;

import shared.Piece;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.PieceType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.model.map.Port;

//testing stuff

/*
 * Hey zack i think that the parameters for canBuild____ functions need to change
 * i'm going to pass in the location and if it's a setup or not for canBuildSettlement
 * there also needs to be a setvictorypoint getter
 * brian
 */

public class Player implements Serializable
{
	/**
	 * roads, settlements and cities owned by player
	 */
	private ArrayList<Piece> playerPieces;
	/**
	 * ports owned by player
	 */
	private ArrayList<Port> playerPorts;
	/**
	 * hand containing cards owned by player
	 */
	private Hand playerHand;
	/**
	 * number of victory points player has
	 */
	private int victoryPoints;
	/**
	 * ID of player
	 */
	private int playerID;
	/**
	 * color of player pieces
	 */
	private String name;
	/**
	 * name of player
	 */
	private CatanColor playerColor;
	/**
	 * initialize pieces arraylist
	 */
	private boolean hasPlayedDevCard;
	
	private boolean hasDiscarded;
	
	private int soldiers;
	
	private int monuments;
	
	public Player(int playerID, CatanColor playerColor, String name)
	{
		playerPieces = new ArrayList<Piece>();
		generateStartingPieces();
		playerPorts = new ArrayList<Port>();
		this.playerID = playerID;
		this.playerColor = playerColor;
		this.name = name;
		playerHand = new Hand();
		hasPlayedDevCard = false;
		hasDiscarded = false;
		soldiers = 0;
		monuments = 0;
		victoryPoints = 0;
	}
	
	public void addPiece(PieceType type){
		playerPieces.add(new Piece(type, null, null, playerID));
	}
	
	public int getNumCities(){
		int count = 0;
		for( int i = 0; i < playerPieces.size(); i++){
			if(playerPieces.get(i).getPieceType() == PieceType.CITY){
				count++;
			}
		}
		return count;
	}
	
	public int getNumSettlements(){
		int count = 0;
		for( int i = 0; i < playerPieces.size(); i++){
			if(playerPieces.get(i).getPieceType() == PieceType.SETTLEMENT){
				count++;
			}
		}
		return count;
	}
	
	public int getNumRoads(){
		int count = 0;
		for( int i = 0; i < playerPieces.size(); i++){
			if(playerPieces.get(i).getPieceType() == PieceType.ROAD){
				count++;
			}
		}
		return count;
	}
	
	public void removePiece(PieceType type){
		for(int i = 0; i < playerPieces.size(); i++){
			if(playerPieces.get(i).getPieceType() == type){
				playerPieces.remove(i);
				return;
			}
		}
	}
	
	
	public void generateStartingPieces(){
		for(int i = 0; i < 5; i++){
			playerPieces.add(new Piece(PieceType.SETTLEMENT, null, null, playerID));
		}
		for(int i = 0; i < 4; i++){
			playerPieces.add(new Piece(PieceType.CITY, null, null, playerID));
		}
		for(int i = 0; i < 15; i++){
			playerPieces.add(new Piece(PieceType.ROAD, null, null, playerID));
		}
	}

	/**
	 * calculate victory points of player
	 * @return
	 */
	public int getVictoryPoints()
	{
		return victoryPoints;
	}
	/**
	 * calculate longest chain of unbroken roads
	 * @return
	 */
	public int getLongestRoadChain()
	{
		return 0;
	}
	public int numResourceRemaining(ResourceType type)
	{
		return playerHand.numResourceOfType(type);
	}
	public int numOldDevCardRemaining(DevCardType type)
	{
		return playerHand.numOldDevCardRemaining(type);
	}
	public int numNewDevCardRemaining(DevCardType type)
	{
		return playerHand.numNewDevCardRemaining(type);
	}
	/**
	 * check resources required for building road
	 * @return
	 */
	public boolean canBuildRoad()
	{
		if (playerHand.numResourceOfType(ResourceType.BRICK) > 0 && playerHand.numResourceOfType(ResourceType.WOOD) > 0)
		{
			for(int i = 0; i < playerPieces.size(); i++){
				if(playerPieces.get(i).getPieceType() == PieceType.ROAD){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * check resources required for building settlement
	 * @return
	 */
	public boolean canBuildSettlement()
	{
		if 		(playerHand.numResourceOfType(ResourceType.BRICK) > 0 
				&& playerHand.numResourceOfType(ResourceType.WOOD) > 0
				&& playerHand.numResourceOfType(ResourceType.WHEAT) > 0
				&& playerHand.numResourceOfType(ResourceType.SHEEP) > 0)
		{
			for(int i = 0; i < playerPieces.size(); i++){
				if(playerPieces.get(i).getPieceType() == PieceType.SETTLEMENT){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * check resources required for building city
	 * @return
	 */
	public boolean canBuildCity()
	{
		if (playerHand.numResourceOfType(ResourceType.WHEAT) > 1 && playerHand.numResourceOfType(ResourceType.ORE) > 2)
		{
			for(int i = 0; i < playerPieces.size(); i++){
				if(playerPieces.get(i).getPieceType() == PieceType.CITY){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * check resources required for buying development card
	 * @return
	 */
	public boolean canBuyDevCard()
	{
		if (playerHand.numResourceOfType(ResourceType.SHEEP) > 0 
				&& playerHand.numResourceOfType(ResourceType.WHEAT) > 0
				&& playerHand.numResourceOfType(ResourceType.ORE) > 0)
		{
			return true;
		}
		return false;
	}
	
	public int maritimeTradeRatio(ResourceType type)
	{
		//return -1 for unable
		if (hasPortType(type))
		{
			if (playerHand.numResourceOfType(type) >= 2)
			{
				return 2;
			}
		}
		else if (hasThreePort())
		{
			if (playerHand.numResourceOfType(type) >= 3)
			{
				return 3;
			}
		}
		else
		{
			if (playerHand.numResourceOfType(type) >= 4)
			{
				return 4;
			}
		}
		return -1;
	}
	public boolean hasThreePort()
	{
		for (int i = 0; i < playerPorts.size(); i++)
		{
			if (playerPorts.get(i).getType().equals(PortType.THREE))
			{
				return true;
			}
		}
		return false;
	}
	public boolean hasPortType(ResourceType type)
	{
		PortType assignPort = null;
		if (type == ResourceType.BRICK)
		{
			assignPort = PortType.BRICK;
		}
		else if (type == ResourceType.ORE)
		{
			assignPort = PortType.ORE;
		}
		else if (type == ResourceType.SHEEP)
		{
			assignPort = PortType.SHEEP;
		}
		else if (type == ResourceType.WHEAT)
		{
			assignPort = PortType.WHEAT;
		}
		else if (type == ResourceType.WOOD)
		{
			assignPort = PortType.WOOD;
		}
	
		
		for (int i = 0; i < playerPorts.size(); i++)
		{
			if (playerPorts.get(i).getType().equals(assignPort))
			{
				return true;
			}
		}
		return false;
	}
	public void addPort(PortType type)
	{
		playerPorts.add(new Port(type));
	}
	public void removePort(PortType type)
	{
		for (int i = 0; i < playerPorts.size(); i++)
		{
			if (playerPorts.get(i).getType() == type)
			{
				playerPorts.remove(i);
				break;
			}
		}
	}
	public void addVictoryPoint()
	{
		victoryPoints++;
	}
	
	public void removeVictoryPoint(){
		victoryPoints--;
	}
	/**
	 * return all pieces owned by player
	 * @return
	 */
	public ArrayList<Piece> getPlayerPieces()
	{
		return playerPieces;
	}
	
	public boolean canDiscard(){
		if(playerHand.getNumResources() > 7 && !hasDiscarded){
			return true;
		}else{
			return false;
		}
	}
	public int numPiecesOfType(PieceType myPieceType)
	{
		int amount = 0;
		for (int i = 0; i < playerPieces.size(); i++)
		{
			if (myPieceType == playerPieces.get(i).getPieceType())
			{
				amount++;
			}
		}
		return amount;
	}
	/**
	 * return player hand class
	 * @return
	 */
	public Hand getPlayerHand()
	{
		return playerHand;
	}
	/**
	 * returns name of the player
	 * @return
	 */
	public String getName()
	{
		return name;
	}
	public int getPlayerID()
	{
		return playerID;
	}
	public CatanColor getPlayerColor()
	{
		return playerColor;
	}

	public boolean isHasPlayedDevCard() {
		return hasPlayedDevCard;
	}

	public void setHasPlayedDevCard(boolean hasPlayedDevCard) {
		this.hasPlayedDevCard = hasPlayedDevCard;
	}

	public boolean isHasDiscarded() {
		return hasDiscarded;
	}

	public void setHasDiscarded(boolean hasDiscarded) {
		this.hasDiscarded = hasDiscarded;
	}

	public int getSoldiers() {
		return soldiers;
	}

	public void setSoldiers(int soldiers) {
		this.soldiers = soldiers;
	}

	public int getMonuments() {
		return monuments;
	}

	public void setMonuments(int monuments) {
		this.monuments = monuments;
	}

	public ArrayList<Port> getPlayerPorts() {
		return playerPorts;
	}

	public void setPlayerPorts(ArrayList<Port> playerPorts) {
		this.playerPorts = playerPorts;
	}

	public void setPlayerPieces(ArrayList<Piece> playerPieces) {
		this.playerPieces = playerPieces;
	}

	public void setPlayerHand(Hand playerHand) {
		this.playerHand = playerHand;
	}

	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPlayerColor(CatanColor playerColor) {
		this.playerColor = playerColor;
	}
}
