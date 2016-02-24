package client.resources;

import java.util.*;

import client.base.*;
import client.data.PlayerInfo;
import gameManager.GameManager;
import model.GameException;
import player.Player;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController {

	private Map<ResourceBarElement, IAction> elementActions;
	
	public ResourceBarController(IResourceBarView view) {

		super(view);
		
		elementActions = new HashMap<ResourceBarElement, IAction>();
	}

	@Override
	public IResourceBarView getView() {
		return (IResourceBarView)super.getView();
	}

	/**
	 * Sets the action to be executed when the specified resource bar element is clicked by the user
	 * 
	 * @param element The resource bar element with which the action is associated
	 * @param action The action to be executed
	 */
	public void setElementAction(ResourceBarElement element, IAction action) {

		elementActions.put(element, action);
	}

	@Override
	public void buildRoad() {
		executeElementAction(ResourceBarElement.ROAD);
	}

	@Override
	public void buildSettlement() {
		executeElementAction(ResourceBarElement.SETTLEMENT);
	}

	@Override
	public void buildCity() {
		executeElementAction(ResourceBarElement.CITY);
	}

	@Override
	public void buyCard() {
		executeElementAction(ResourceBarElement.BUY_CARD);
	}

	@Override
	public void playCard() {
		executeElementAction(ResourceBarElement.PLAY_CARD);
	}
	
	private void executeElementAction(ResourceBarElement element) {
		
		if (elementActions.containsKey(element)) {
			
			IAction action = elementActions.get(element);
			action.execute();
		}
	}

	private void setResourceAmounts(Player myPlayer)
	{
		getView().setElementAmount(ResourceBarElement.BRICK, myPlayer.getPlayerHand().numResourceOfType(ResourceType.BRICK));
		getView().setElementAmount(ResourceBarElement.WOOD, myPlayer.getPlayerHand().numResourceOfType(ResourceType.WOOD));
		getView().setElementAmount(ResourceBarElement.ORE, myPlayer.getPlayerHand().numResourceOfType(ResourceType.ORE));
		getView().setElementAmount(ResourceBarElement.WHEAT, myPlayer.getPlayerHand().numResourceOfType(ResourceType.WHEAT));
		getView().setElementAmount(ResourceBarElement.SHEEP, myPlayer.getPlayerHand().numResourceOfType(ResourceType.SHEEP));
		getView().setElementAmount(ResourceBarElement.ROAD, myPlayer.numPiecesOfType(PieceType.ROAD));
		getView().setElementAmount(ResourceBarElement.SETTLEMENT, myPlayer.numPiecesOfType(PieceType.SETTLEMENT));
		getView().setElementAmount(ResourceBarElement.CITY, myPlayer.numPiecesOfType(PieceType.CITY));
		getView().setElementAmount(ResourceBarElement.SOLDIERS, myPlayer.getSoldiers());
	}
	private void enableDisableButtons(GameManager myManager, Player myPlayer, PlayerInfo myPlayerInfo)
	{
		boolean myTurn;
		if (myManager.getCurrentPlayerIndex() == myPlayerInfo.getId())
		{
			myTurn = true;
		}
		else
		{
			myTurn = false;
		}
		if (myTurn)
		{
			//if my turn I can bring up dev card thing
			getView().setElementEnabled(ResourceBarElement.PLAY_CARD, true);
			//check resources for buying dev card
			if (myManager.getModel().buyDevCard(myPlayerInfo.getId()))
			{
				getView().setElementEnabled(ResourceBarElement.BUY_CARD, true);
			}
			else
			{
				getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
			}
			//check resources for buying Road
			if (myPlayer.canBuildRoad())
			{
				getView().setElementEnabled(ResourceBarElement.ROAD, true);
			}
			else 
			{
				getView().setElementEnabled(ResourceBarElement.ROAD, false);
			}
			//check resources for buying settlement
			if (myPlayer.canBuildSettlement())
			{
				getView().setElementEnabled(ResourceBarElement.SETTLEMENT, true);
			}
			else 
			{
				getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
			}
			//check resources for buying city
			if (myPlayer.canBuildCity())
			{
				getView().setElementEnabled(ResourceBarElement.CITY, true);
			}
			else
			{
				getView().setElementEnabled(ResourceBarElement.CITY, false);
			}
		}
		else
		{
			getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
			getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
			getView().setElementEnabled(ResourceBarElement.CITY, false);
			getView().setElementEnabled(ResourceBarElement.ROAD, false);
			getView().setElementEnabled(ResourceBarElement.PLAY_CARD, false);
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if(GameManager.getInstance().isStartingUp()){
			return;
		}
		//Get manager
		GameManager myManager = GameManager.getInstance();
		//Get player
		PlayerInfo myPlayerInfo = myManager.getPlayerInfo();
		Player myPlayer = myManager.getModel().getPlayers().get(myPlayerInfo.getPlayerIndex());
		//Set element amount (resources)
		setResourceAmounts(myPlayer);
		//Set element enabled/disabled(action buttons)
		enableDisableButtons(myManager, myPlayer, myPlayerInfo);
	}
}

