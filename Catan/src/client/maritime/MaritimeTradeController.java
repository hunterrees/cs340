package client.maritime;

import shared.definitions.*;

import java.util.ArrayList;
import java.util.Observable;

import client.base.*;
import client.data.PlayerInfo;
import gameManager.GameManager;
import model.GameException;
import player.Player;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController {

	private IMaritimeTradeOverlay tradeOverlay;
	private GameManager myManager;
	private PlayerInfo myPlayerInfo;
	private Player myPlayer;
	private ResourceType giveResource;
	private ResourceType getResource;
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {
		
		super(tradeView);

		setTradeOverlay(tradeOverlay);
	}
	
	public IMaritimeTradeView getTradeView() {
		
		return (IMaritimeTradeView)super.getView();
	}
	
	public IMaritimeTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	private void showGiveResources()
	{
		ArrayList<ResourceType> canTrade = new ArrayList<ResourceType>();
		if (myPlayer.maritimeTradeRatio(ResourceType.BRICK) != -1)
		{
			canTrade.add(ResourceType.BRICK);
		}
		if (myPlayer.maritimeTradeRatio(ResourceType.ORE) != -1)
		{
			canTrade.add(ResourceType.ORE);
		}
		if (myPlayer.maritimeTradeRatio(ResourceType.SHEEP) != -1)
		{
			canTrade.add(ResourceType.SHEEP);
		}
		if (myPlayer.maritimeTradeRatio(ResourceType.WHEAT) != -1)
		{
			canTrade.add(ResourceType.WHEAT);
		}
		if (myPlayer.maritimeTradeRatio(ResourceType.WOOD) != -1)
		{
			canTrade.add(ResourceType.WOOD);
		}
		
		ResourceType[] tradeOptions = canTrade.toArray(new ResourceType[canTrade.size()]);
		
		getTradeOverlay().showGiveOptions(tradeOptions);
	}
	@Override
	public void startTrade() {
		//here we're going to find out whether they can trade
		
		
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().setStateMessage("Select a Resource to give");
		showGiveResources();
		getTradeOverlay().showModal();
	}

	@Override
	public void makeTrade() {

		getTradeOverlay().closeModal();
		getTradeOverlay().reset();
		try {
			myManager.maritimeTrade(myPlayerInfo.getPlayerIndex(), giveResource, getResource);
		} catch (GameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void cancelTrade() {
		getTradeOverlay().reset();
		getTradeOverlay().closeModal();
		
	}

	@Override
	public void setGetResource(ResourceType resource) {
		getResource = resource;
		getTradeOverlay().hideGetOptions();
		getTradeOverlay().selectGetOption(resource, 1);
		getTradeOverlay().setStateMessage("Trade!");
		getTradeOverlay().setTradeEnabled(true);
	}

	private void showGetResources()
	{
		
		ArrayList<ResourceType> giveResources = new ArrayList<ResourceType>();
		if(myManager.getModel().getBank().numResourceRemaining(ResourceType.BRICK) != 0)
		{
			giveResources.add(ResourceType.BRICK);
		}
		if(myManager.getModel().getBank().numResourceRemaining(ResourceType.ORE) != 0)
		{
			giveResources.add(ResourceType.ORE);
		}
		if(myManager.getModel().getBank().numResourceRemaining(ResourceType.SHEEP) != 0)
		{
			giveResources.add(ResourceType.SHEEP);
		}
		if(myManager.getModel().getBank().numResourceRemaining(ResourceType.WHEAT) != 0)
		{
			giveResources.add(ResourceType.WHEAT);
		}
		if(myManager.getModel().getBank().numResourceRemaining(ResourceType.WOOD) != 0)
		{
			giveResources.add(ResourceType.WOOD);
		}
		ResourceType[] tradeOptions = giveResources.toArray(new ResourceType[giveResources.size()]);
		getTradeOverlay().showGetOptions(tradeOptions);
	}
	@Override
	public void setGiveResource(ResourceType resource) {
		giveResource = resource;
		getTradeOverlay().hideGiveOptions();
		getTradeOverlay().selectGiveOption(resource, myPlayer.maritimeTradeRatio(resource));
		showGetResources();
		getTradeOverlay().setStateMessage("Select a Resource to get");
		
	}

	@Override
	public void unsetGetValue() {
		//ok, so we need to uset this and revert to choosing get value
		showGetResources();
		getTradeOverlay().setStateMessage("Select a Resource to get");
	}

	@Override
	public void unsetGiveValue() {
		getTradeOverlay().hideGetOptions();
		showGiveResources();
		getTradeOverlay().setStateMessage("Select a Resources to give");
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(GameManager.getInstance().isGameEnd()){
			return;
		}
		// TODO Auto-generated method stub
		myManager = GameManager.getInstance();
		myPlayerInfo = myManager.getPlayerInfo();
		myPlayer = myManager.getModel().getPlayers().get(myPlayerInfo.getPlayerIndex());
		if (myManager.isMyTurn())
		{
			getTradeView().enableMaritimeTrade(true);
		}
		else 
		{
			getTradeView().enableMaritimeTrade(false);
		}
		//getTradeOverlay().selectGiveOption(selectedResource, amount);
	}

}

