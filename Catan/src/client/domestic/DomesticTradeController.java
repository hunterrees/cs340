package client.domestic;

import shared.ResourceCard;
import shared.definitions.*;
import shared.model.GameException;
import shared.model.player.Player;
import shared.model.trade.TradeOffer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;

import client.base.*;
import client.data.PlayerInfo;
import client.gameManager.GameManager;
import client.misc.*;
import client.translators.moves.ResourceList;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController {

	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;

	GameManager myGameManager;
	PlayerInfo myPlayerInfo;
	Player myPlayer;
	
	boolean playersSet = false;
	
	TradeOffer myTradeOffer = null;
	//keep track of which resources are being sent and which are being recieved
	HashSet<ResourceType> resourceTypesBeingSent = new HashSet<ResourceType>();
	HashSet<ResourceType> resourceTypesBeingRecieved = new HashSet<ResourceType>();
	
	ArrayList<ResourceType> resourcesBeingSent = new ArrayList<ResourceType>();
	ArrayList<ResourceType> resourcesBeingRecieved = new ArrayList<ResourceType>();
	
	int playerToTradeWith = -1;
	/**
	 * DomesticTradeController constructor
	 * 
	 * @param tradeView Domestic trade view (i.e., view that contains the "Domestic Trade" button)
	 * @param tradeOverlay Domestic trade overlay (i.e., view that lets the user propose a domestic trade)
	 * @param waitOverlay Wait overlay used to notify the user they are waiting for another player to accept a trade
	 * @param acceptOverlay Accept trade overlay which lets the user accept or reject a proposed trade
	 */
	public DomesticTradeController(IDomesticTradeView tradeView, IDomesticTradeOverlay tradeOverlay,
									IWaitView waitOverlay, IAcceptTradeOverlay acceptOverlay) {

		super(tradeView);
		
		setTradeOverlay(tradeOverlay);
		setWaitOverlay(waitOverlay);
		setAcceptOverlay(acceptOverlay);
	}
	
	public IDomesticTradeView getTradeView() {
		
		return (IDomesticTradeView)super.getView();
	}

	public IDomesticTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	public IWaitView getWaitOverlay() {
		return waitOverlay;
	}

	public void setWaitOverlay(IWaitView waitView) {
		this.waitOverlay = waitView;
	}

	public IAcceptTradeOverlay getAcceptOverlay() {
		return acceptOverlay;
	}

	public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay) {
		this.acceptOverlay = acceptOverlay;
	}

	private PlayerInfo[] getPlayerInfos()
	{
		PlayerInfo[] playerInfos = new PlayerInfo[3];
		int playerIndex = myPlayerInfo.getPlayerIndex();
		for (int i = 0; i < 4; i++)
		{
			if (i == playerIndex)
			{
				
			}
			else
			{
				PlayerInfo tempInfo = new PlayerInfo();
				tempInfo.setPlayerIndex(i);
				tempInfo.setName(myGameManager.getModel().getPlayers().get(i).getName());
				tempInfo.setColor(myGameManager.getModel().getPlayers().get(i).getPlayerColor());
				for (int j = 0; j < 4; j++)
				{
					if (playerInfos[j] == null)
					{
						playerInfos[j] = tempInfo;
						break;
					}
				}
			}
		}
		return playerInfos;
	}
	@Override
	public void startTrade() {

		getTradeOverlay().setTradeEnabled(false);
		if (playersSet == false)
		{
			getTradeOverlay().setPlayers(getPlayerInfos());
			playersSet = true;
		}
		getTradeOverlay().showModal();
		playerToTradeWith = -1;
	}

	private int getAmount(ArrayList<ResourceType> list, ResourceType resource)
	{
		int amount = 0;
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i) == resource)
			{
				amount++;
			}
		}
		System.out.println("amount is" + amount);
		return amount;
	}
	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		boolean sending = false;
		if (resourceTypesBeingSent.contains(resource))
		{
			sending = true;
		}
		if (sending)
		{
			//not positive this only removes 1!!!!!!!!
			resourcesBeingSent.remove(resource);
			getTradeOverlay().setResourceAmount(resource, Integer.toString(getAmount(resourcesBeingSent, resource)));
			sendIncreaseDecreaseEnable(resource);
		}
		else
		{
			//we must be requesting to recieve this resource
			resourcesBeingRecieved.remove(resource);
			recieveIncreaseDecreaseEnable(resource);
			getTradeOverlay().setResourceAmount(resource, Integer.toString(getAmount(resourcesBeingRecieved, resource)));
		}
		if (resourcesBeingSent.size() > 0 && resourcesBeingRecieved.size() > 0 && playerToTradeWith > 0)
		{
			getTradeOverlay().setTradeEnabled(true);
			getTradeOverlay().setStateMessage("make trade!");
		}
		else
		{
			getTradeOverlay().setTradeEnabled(false);
			getTradeOverlay().setStateMessage("set the trade you want to make");
		}
	}
	
	@Override
	public void increaseResourceAmount(ResourceType resource) {
		boolean sending = false;
		if (resourceTypesBeingSent.contains(resource))
		{
			sending = true;
		}
		if (sending)
		{
			resourcesBeingSent.add(resource);
			sendIncreaseDecreaseEnable(resource);
			getTradeOverlay().setResourceAmount(resource, Integer.toString(getAmount(resourcesBeingSent, resource)));
		}
		else
		{
			//we must be requesting to recieve this resource
			resourcesBeingRecieved.add(resource);
			recieveIncreaseDecreaseEnable(resource);
			getTradeOverlay().setResourceAmount(resource, Integer.toString(getAmount(resourcesBeingRecieved, resource)));
		}
		if (resourcesBeingSent.size() > 0 && resourcesBeingRecieved.size() > 0 && playerToTradeWith != -1)
		{
			getTradeOverlay().setTradeEnabled(true);
			getTradeOverlay().setStateMessage("make trade!");
		}
		else
		{
			getTradeOverlay().setTradeEnabled(false);
			getTradeOverlay().setStateMessage("set the trade you want to make");
		}
	}

	private void recieveIncreaseDecreaseEnable(ResourceType resource)
	{
		int numberInHand = myPlayer.getPlayerHand().numResourceOfType(resource);
		int numberRecieving = 0;
		for (int i = 0; i < resourcesBeingRecieved.size(); i++)
		{
			if (resourcesBeingRecieved.get(i) == resource)
			{
				numberRecieving++;
			}
		}
		if (numberRecieving > 0)
		{
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
		}
		else
		{
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
		}
	}
	private void sendIncreaseDecreaseEnable(ResourceType resource)
	{
		int numberInHand = myPlayer.getPlayerHand().numResourceOfType(resource);
		int numberTrading = 0;
		for (int i = 0; i < resourcesBeingSent.size(); i++)
		{
			if (resourcesBeingSent.get(i) == resource)
			{
				numberTrading++;
			}
		}
		boolean canReduce = false;
		boolean canIncrease = false;
		if (numberTrading > 0) canReduce = true;
		if (numberInHand > numberTrading) canIncrease = true;
		getTradeOverlay().setResourceAmountChangeEnabled(resource, canIncrease, canReduce);
	}
	@Override
	public void sendTradeOffer() {
		getTradeOverlay().closeModal();
		getWaitOverlay().showModal();
		try {
			myGameManager.offerTrade(myPlayerInfo.getPlayerIndex(), resourcesBeingRecieved, resourcesBeingSent, playerToTradeWith);
			System.out.println("Player " + myPlayerInfo.getPlayerIndex() + " is sending to " + playerToTradeWith);
		} catch (GameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resourcesBeingSent.clear();
		resourcesBeingRecieved.clear();
		resourceTypesBeingSent.clear();
		resourceTypesBeingRecieved.clear();
		getTradeOverlay().reset();
		playerToTradeWith = -1;
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		playerToTradeWith = playerIndex;
		//System.out.println("player to trade with set to: " + playerIndex);
		if (resourcesBeingSent.size() > 0 && resourcesBeingRecieved.size() > 0 && playerToTradeWith != -1)
		{
			getTradeOverlay().setTradeEnabled(true);
			getTradeOverlay().setStateMessage("make trade!");
		}
		else
		{
			getTradeOverlay().setTradeEnabled(false);
			getTradeOverlay().setStateMessage("set the trade you want to make");
		}
	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
		if (resourceTypesBeingSent.contains(resource))
		{
			resourceTypesBeingSent.remove(resource);
			resourcesBeingSent = removeResourcesOfType(resourcesBeingSent, resource);
		}
		resourceTypesBeingRecieved.add(resource);
		getTradeOverlay().setResourceAmount(resource, Integer.toString(getAmount(resourcesBeingRecieved, resource)));
		getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
	}

	private ArrayList<ResourceType> removeResourcesOfType(ArrayList<ResourceType> resources, ResourceType type)
	{
		ArrayList<ResourceType> newList = new ArrayList<ResourceType>();
		int wood = 0;
		int sheep = 0;
		int wheat = 0;
		int brick = 0;
		int ore = 0;
		for (int i = 0; i < resources.size(); i++)
		{
			if (resources.get(i) == ResourceType.WOOD) wood++;
			if (resources.get(i) == ResourceType.BRICK) brick++;
			if (resources.get(i) == ResourceType.SHEEP) sheep++;
			if (resources.get(i) == ResourceType.WHEAT) wheat++;
			if (resources.get(i) == ResourceType.ORE) ore++;
			
		}
		if (type == ResourceType.WOOD) wood = 0;
		else if (type == ResourceType.BRICK) brick = 0;
		else if (type == ResourceType.SHEEP) sheep = 0;
		else if (type == ResourceType.WHEAT) wheat = 0;
		else if (type == ResourceType.ORE) ore = 0;
		for (int i = 0; i < wood; i++) {newList.add(ResourceType.WOOD);}
		for (int i = 0; i < wheat; i++) {newList.add(ResourceType.WHEAT);}
		for (int i = 0; i < sheep; i++) {newList.add(ResourceType.SHEEP);}
		for (int i = 0; i < ore; i++) {newList.add(ResourceType.ORE);}
		for (int i = 0; i < brick; i++) {newList.add(ResourceType.BRICK);}
		return newList;
		
	}
	
	@Override
	public void setResourceToSend(ResourceType resource) {
		resourceTypesBeingSent.add(resource);
		if (resourceTypesBeingRecieved.contains(resource))
		{
			resourceTypesBeingRecieved.remove(resource);
			resourcesBeingRecieved = removeResourcesOfType(resourcesBeingRecieved, resource);
			getTradeOverlay().setResourceAmount(resource, Integer.toString(getAmount(resourcesBeingSent, resource)));
		}
		if (myPlayer.getPlayerHand().numResourceOfType(resource) == 0)
		{
			getTradeOverlay().setResourceAmountChangeEnabled(resource, false, false);
		}
		else
		{
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
		}
	}

	@Override
	public void unsetResource(ResourceType resource) {
		resourceTypesBeingSent.remove(resource);
		resourceTypesBeingRecieved.remove(resource);
		for (int i = 0; i < resourcesBeingSent.size(); i++)
		{
			if (resourcesBeingSent.get(i) == resource)
			{
				resourcesBeingSent.remove(i);
			}
		}
		for (int i = 0; i < resourcesBeingRecieved.size(); i++)
		{
			if (resourcesBeingRecieved.get(i) == resource)
			{
				resourcesBeingRecieved.remove(i);
			}
		}
	}

	@Override
	public void cancelTrade() {
		resourcesBeingSent.clear();
		resourcesBeingRecieved.clear();
		resourceTypesBeingSent.clear();
		resourceTypesBeingRecieved.clear();
		playerToTradeWith = -1;
		getTradeOverlay().reset();
		getTradeOverlay().closeModal();
	}

	@Override
	public void acceptTrade(boolean willAccept) {
		
		getAcceptOverlay().closeModal();
		try {
			myGameManager.acceptTrade(myPlayerInfo.getPlayerIndex(), willAccept);
		} catch (GameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getAcceptOverlay().reset();
	}
	
	private void populateOffered(ArrayList<ResourceType> resourceOffered)
	{
		int brick = getAmount(resourceOffered, ResourceType.BRICK); 
		if (brick > 0) getAcceptOverlay().addGiveResource(ResourceType.BRICK, brick);
		int ore = getAmount(resourceOffered, ResourceType.ORE); 
		if (ore > 0) getAcceptOverlay().addGiveResource(ResourceType.ORE, ore);
		int sheep = getAmount(resourceOffered, ResourceType.SHEEP); 
		if (sheep > 0) getAcceptOverlay().addGiveResource(ResourceType.SHEEP, sheep);
		int wheat = getAmount(resourceOffered, ResourceType.WHEAT); 
		if (wheat > 0) getAcceptOverlay().addGiveResource(ResourceType.WHEAT, wheat);
		int wood = getAmount(resourceOffered, ResourceType.WOOD); 
		if (wood > 0) getAcceptOverlay().addGiveResource(ResourceType.WOOD, wood);
	}
	private void populateDesired(ArrayList<ResourceType> resourceDesired)
	{
		int brick = getAmount(resourceDesired, ResourceType.BRICK); 
		if (brick > 0) getAcceptOverlay().addGetResource(ResourceType.BRICK, brick);
		int ore = getAmount(resourceDesired, ResourceType.ORE); 
		if (ore > 0) getAcceptOverlay().addGetResource(ResourceType.ORE, ore);
		int sheep = getAmount(resourceDesired, ResourceType.SHEEP); 
		if (sheep > 0) getAcceptOverlay().addGetResource(ResourceType.SHEEP, sheep);
		int wheat = getAmount(resourceDesired, ResourceType.WHEAT); 
		if (wheat > 0) getAcceptOverlay().addGetResource(ResourceType.WHEAT, wheat);
		int wood = getAmount(resourceDesired, ResourceType.WOOD); 
		if (wood > 0) getAcceptOverlay().addGetResource(ResourceType.WOOD, wood);
	}
	void setUpAcceptTrade()
	{
		//need to bring up the modal with the appropriate information
		
		populateOffered(myTradeOffer.getResourceOffered());
		populateDesired(myTradeOffer.getResourceDesired());
		boolean canAcceptTrade = true;
		if (getAmount(myTradeOffer.getResourceOffered(), ResourceType.BRICK) > myPlayer.getPlayerHand().numResourceOfType(ResourceType.BRICK))
		{
			canAcceptTrade = false;
		}
		if (getAmount(myTradeOffer.getResourceOffered(), ResourceType.ORE) > myPlayer.getPlayerHand().numResourceOfType(ResourceType.ORE))
		{
			canAcceptTrade = false;
		}
		if (getAmount(myTradeOffer.getResourceOffered(), ResourceType.WHEAT) > myPlayer.getPlayerHand().numResourceOfType(ResourceType.WHEAT))
		{
			canAcceptTrade = false;
		}
		if (getAmount(myTradeOffer.getResourceOffered(), ResourceType.SHEEP) > myPlayer.getPlayerHand().numResourceOfType(ResourceType.SHEEP))
		{
			canAcceptTrade = false;
		}
		if (getAmount(myTradeOffer.getResourceOffered(), ResourceType.WOOD) > myPlayer.getPlayerHand().numResourceOfType(ResourceType.WOOD))
		{
			canAcceptTrade = false;
		}
		
		if (!canAcceptTrade)
		{
			getAcceptOverlay().setAcceptEnabled(false);
		}
		else
		{
			getAcceptOverlay().setAcceptEnabled(true);
		}
		getAcceptOverlay().showModal();
	}
	@Override
	public void update(Observable o, Object arg) {
		if (getTradeOverlay().isModalShowing() || GameManager.getInstance().isGameEnd() || GameManager.getInstance().isStartingUp())
		{
			return;
		}
		// TODO Auto-generated method stub
		myGameManager = GameManager.getInstance();
		myPlayerInfo = myGameManager.getPlayerInfo();
		myPlayer = myGameManager.getModel().getPlayers().get(myPlayerInfo.getPlayerIndex());
		if (myGameManager.isMyTurn() && GameManager.getInstance().getGameState() == GameState.playing)
		{
			getTradeView().enableDomesticTrade(true);
			if (myGameManager.getModel().getTradeOffer() == null && getWaitOverlay().isModalShowing())
			{
				getWaitOverlay().closeModal();
			}
		}
		
		else 
		{
			getTradeView().enableDomesticTrade(false);
		}
		if (myGameManager.getModel().getTradeOffer() != null)
		{
			//check if somebody has offered me a trade offer
			myTradeOffer = myGameManager.getModel().getTradeOffer();
			if (myTradeOffer.getAcceptingPlayerID() == myPlayerInfo.getPlayerIndex())
			{
				setUpAcceptTrade();
			}
		}
	}

}
