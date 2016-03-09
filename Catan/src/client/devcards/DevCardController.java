package client.devcards;

import shared.DevelopmentCard;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.model.player.Player;

import java.util.ArrayList;
import java.util.Observable;

import client.base.*;
import client.gameManager.GameManager;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController {

	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;
	private Player player;
	
	/**
	 * DevCardController constructor
	 * 
	 * @param view "Play dev card" view
	 * @param buyCardView "Buy dev card" view
	 * @param soldierAction Action to be executed when the user plays a soldier card.  It calls "mapController.playSoldierCard()".
	 * @param roadAction Action to be executed when the user plays a road building card.  It calls "mapController.playRoadBuildingCard()".
	 */
	public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView, 
								IAction soldierAction, IAction roadAction) {

		super(view);
		
		this.buyCardView = buyCardView;
		this.soldierAction = soldierAction;
		this.roadAction = roadAction;
	}

	public IPlayDevCardView getPlayCardView() {
		return (IPlayDevCardView)super.getView();
	}

	public IBuyDevCardView getBuyCardView() {
		return buyCardView;
	}

	@Override
	public void startBuyCard() {
		getBuyCardView().showModal();
	}

	@Override
	public void cancelBuyCard() {
		getBuyCardView().closeModal();
	}

	@Override
	public void buyCard() {
		try{
			GameManager.getInstance().buyDevCard(GameManager.getInstance().getPlayerInfo().getPlayerIndex());
			getBuyCardView().closeModal();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void startPlayCard() {
		int index = GameManager.getInstance().getPlayerInfo().getPlayerIndex();
		player = GameManager.getInstance().getModel().getPlayers().get(index);
		ArrayList<DevelopmentCard> oldCards = player.getPlayerHand().getOldDevelopmentCards();
		ArrayList<DevelopmentCard> newCards = player.getPlayerHand().getNewDevelopmentCards();
		int monopoly = 0;
		int soldier = 0;
		int monument = 0;
		int yearOfPlenty = 0;
		int roadBuild = 0;
		for(int i = 0; i < oldCards.size(); i++){
			switch(oldCards.get(i).getType()){
				case MONOPOLY: monopoly++; break;
				case SOLDIER: soldier++; break;
				case MONUMENT: monument++; break;
				case YEAR_OF_PLENTY: yearOfPlenty++; break;
				case ROAD_BUILD: roadBuild++; break;
			}
		}
		for(int i = 0; i < newCards.size(); i++){
			switch(newCards.get(i).getType()){
				case MONOPOLY: monopoly++; break;
				case SOLDIER: soldier++; break;
				case MONUMENT: monument++; break;
				case YEAR_OF_PLENTY: yearOfPlenty++; break;
				case ROAD_BUILD: roadBuild++; break;
			}
		}
		getPlayCardView().setCardAmount(DevCardType.MONOPOLY, monopoly);
		getPlayCardView().setCardAmount(DevCardType.SOLDIER, soldier);
		getPlayCardView().setCardAmount(DevCardType.MONUMENT, monument);
		getPlayCardView().setCardAmount(DevCardType.YEAR_OF_PLENTY, yearOfPlenty);
		getPlayCardView().setCardAmount(DevCardType.ROAD_BUILD, roadBuild);
		getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, false);
		getPlayCardView().setCardEnabled(DevCardType.SOLDIER, false);
		getPlayCardView().setCardEnabled(DevCardType.MONUMENT, false);
		getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, false);
		getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, false);
		if(!player.isHasPlayedDevCard()){
			for(int i = 0; i < oldCards.size(); i++){
				getPlayCardView().setCardEnabled(oldCards.get(i).getType(), true);
			}
			for(int i = 0; i < newCards.size(); i++){
				if(newCards.get(i).getType() == DevCardType.MONUMENT){
					getPlayCardView().setCardEnabled(DevCardType.MONUMENT, true);
				}
			}
		}
		getPlayCardView().showModal();
	}

	@Override
	public void cancelPlayCard() {
		getPlayCardView().closeModal();
	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
		try{
			GameManager.getInstance().monopoly(GameManager.getInstance().getPlayerInfo().getPlayerIndex(), resource);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void playMonumentCard() {
		try{
			GameManager.getInstance().monument(GameManager.getInstance().getPlayerInfo().getPlayerIndex());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void playRoadBuildCard() {
		roadAction.execute();
	}

	@Override
	public void playSoldierCard() {
		soldierAction.execute();
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		try{
			GameManager.getInstance().yearOfPlenty(GameManager.getInstance().getPlayerInfo().getPlayerIndex(), resource1, resource2);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}

