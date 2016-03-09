package client.discard;

import shared.ResourceCard;
import shared.definitions.*;
import shared.model.player.Player;

import java.util.ArrayList;
import java.util.Observable;

import client.base.*;
import client.gameManager.GameManager;
import client.misc.*;
import client.translators.moves.ResourceList;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController {

	private IWaitView waitView;
	private Player player;
	private int numCardsToDiscard;
	private ResourceList resourceList;
	private ResourceList resourcesToDiscard;
	
	/**
	 * DiscardController constructor
	 * 
	 * @param view View displayed to let the user select cards to discard
	 * @param waitView View displayed to notify the user that they are waiting for other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {
		
		super(view);
		
		this.waitView = waitView;
	}

	public IDiscardView getDiscardView() {
		return (IDiscardView)super.getView();
	}
	
	public IWaitView getWaitView() {
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource) {
		resourcesToDiscard.add(resource);
		if(resourcesToDiscard.get(resource) == resourceList.get(resource)){
			getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
		}else{
			getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
		}
		setButtonsNormally();
		if(resourcesToDiscard.size() == numCardsToDiscard){
			getDiscardView().setDiscardButtonEnabled(true);
			setButtonsFinally();
		}
		getDiscardView().setResourceDiscardAmount(resource, resourcesToDiscard.get(resource));
		getDiscardView().setStateMessage(getState());
	}

	@Override
	public void decreaseAmount(ResourceType resource) {
		resourcesToDiscard.remove(resource);
		if(resourcesToDiscard.get(resource) == 0){
			getDiscardView().setResourceAmountChangeEnabled(resource, true, false);
		}else{
			getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
		}
		setButtonsNormally();
		getDiscardView().setDiscardButtonEnabled(false);
		getDiscardView().setResourceDiscardAmount(resource, resourcesToDiscard.get(resource));
		getDiscardView().setStateMessage(getState());
	}

	@Override
	public void discard() {
		try{
			getDiscardView().closeModal();
			GameManager.getInstance().discardCards(GameManager.getInstance().getPlayerInfo().getPlayerIndex(), getDiscardedCards());
			resourceList.clear();
			resourcesToDiscard.clear();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(GameManager.getInstance().isGameEnd() || GameManager.getInstance().isStartingUp()){
			return;
		}
		if(getDiscardView().isModalShowing()){
			return;
		}
		int index = GameManager.getInstance().getPlayerInfo().getPlayerIndex();
		if(index == -1){
			return;
		}
		player = GameManager.getInstance().getModel().getPlayers().get(index);
		if(GameManager.getInstance().getGameState() == GameState.discarding && player.canDiscard()){
			numCardsToDiscard = player.getPlayerHand().getNumResources() / 2;
			if(resourcesToDiscard == null){
				resourcesToDiscard = new ResourceList(0,0,0,0,0);
			}
			getResources(player.getPlayerHand().getResourceCards());
			setMaxResources();
			setButtonsInitially();
			getDiscardView().setDiscardButtonEnabled(false);
			getDiscardView().setStateMessage(getState());
			getDiscardView().showModal();
		}
	}
	
	private ArrayList<ResourceType> getDiscardedCards(){
		ArrayList<ResourceType> discard = new ArrayList<ResourceType>();
		for(int i = 0; i < resourcesToDiscard.get(ResourceType.BRICK); i++){
			discard.add(ResourceType.BRICK);
		}
		for(int i = 0; i < resourcesToDiscard.get(ResourceType.WOOD); i++){
			discard.add(ResourceType.WOOD);
		}
		for(int i = 0; i < resourcesToDiscard.get(ResourceType.ORE); i++){
			discard.add(ResourceType.ORE);
		}
		for(int i = 0; i < resourcesToDiscard.get(ResourceType.SHEEP); i++){
			discard.add(ResourceType.SHEEP);
		}
		for(int i = 0; i < resourcesToDiscard.get(ResourceType.WHEAT); i++){
			discard.add(ResourceType.WHEAT);
		}
		return discard;
	}
	
	private String getState(){
		StringBuilder result = new StringBuilder();
		result.append(resourcesToDiscard.size());
		result.append("/");
		result.append(numCardsToDiscard);
		return result.toString();
	}
	
	private void setButtonsFinally(){
		if(resourcesToDiscard.getBrick() == 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, false, false);
		}else{
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, false, true);
		}
		if(resourcesToDiscard.getSheep() == 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, false, false);
		}else{
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, false, true);
		}
		if(resourcesToDiscard.getWood() == 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, false, false);
		}else{
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, false, true);
		}
		if(resourcesToDiscard.getOre() == 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, false, false);
		}else{
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, false, true);
		}
		if(resourcesToDiscard.getWheat() == 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, false, false);
		}else{
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, false, true);
		}
	}
	
	private void setButtonsInitially(){
		if(resourceList.getBrick() == 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, false, false);
		}else if(resourceList.getBrick() > 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, true, false);
		}
		if(resourceList.getWood() == 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, false, false);
		}else if(resourceList.getWood() > 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, true, false);
		}
		if(resourceList.getOre() == 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, false, false);
		}else if(resourceList.getOre() > 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, true, false);
		}
		if(resourceList.getWheat() == 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, false, false);
		}else if(resourceList.getWheat() > 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, true, false);
		}
		if(resourceList.getSheep() == 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, false, false);
		}else if(resourceList.getSheep() > 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, true, false);
		}
	}
	
	private void getResources(ArrayList<ResourceCard> resources){
		int brick = 0;
		int wood = 0;
		int ore = 0;
		int wheat = 0;
		int sheep = 0;
		for(int i = 0; i < resources.size(); i++){
			switch(resources.get(i).getType()){
				case BRICK:	brick++; break;
				case WOOD: wood++; break;
				case ORE: ore++; break;
				case WHEAT: wheat++;break;
				case SHEEP: sheep++;break;
			}
		}
		resourceList = new ResourceList(brick, ore, sheep, wheat, wood);
		
	}
	
	private void setButtonsNormally(){
		if(resourceList.get(ResourceType.BRICK) == 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, false, false);
		}else if(resourcesToDiscard.getBrick() == resourceList.get(ResourceType.BRICK)){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, false, true);
		}else if(resourceList.get(ResourceType.BRICK) > 0 && resourcesToDiscard.getBrick() > 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, true, true);
		}else{
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, true, false);
		}
		if(resourceList.get(ResourceType.SHEEP) == 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, false, false);
		}else if(resourcesToDiscard.getSheep() == resourceList.get(ResourceType.SHEEP)){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, false, true);
		}else if(resourceList.get(ResourceType.SHEEP) > 0 && resourcesToDiscard.getSheep() > 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, true, true);
		}else{
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, true, false);
		}
		if(resourceList.get(ResourceType.WOOD) == 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, false, false);
		}else if(resourcesToDiscard.getWood() == resourceList.get(ResourceType.WOOD)){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, false, true);
		}else if(resourceList.get(ResourceType.WOOD) > 0 && resourcesToDiscard.getWood() > 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, true, true);
		}else{
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, true, false);
		}
		if(resourceList.get(ResourceType.ORE) == 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, false, false);
		}else if(resourcesToDiscard.getOre() == resourceList.get(ResourceType.ORE)){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, false, true);
		}else if(resourceList.get(ResourceType.ORE) > 0 && resourcesToDiscard.getOre() > 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, true, true);
		}else{
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, true, false);
		}
		if(resourceList.get(ResourceType.WHEAT) == 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, false, false);
		}else if(resourcesToDiscard.getWheat() == resourceList.get(ResourceType.WHEAT)){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, false, true);
		}else if(resourceList.get(ResourceType.WHEAT) > 0 && resourcesToDiscard.getWheat() > 0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, true, true);
		}else{
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, true, false);
		}
	}
	
	private void setMaxResources(){
		getDiscardView().setResourceMaxAmount(ResourceType.BRICK, resourceList.getBrick());
		getDiscardView().setResourceMaxAmount(ResourceType.WOOD, resourceList.getWood());
		getDiscardView().setResourceMaxAmount(ResourceType.ORE, resourceList.getOre());
		getDiscardView().setResourceMaxAmount(ResourceType.WHEAT, resourceList.getWheat());
		getDiscardView().setResourceMaxAmount(ResourceType.SHEEP, resourceList.getSheep());
		getDiscardView().setResourceDiscardAmount(ResourceType.BRICK, 0);
		getDiscardView().setResourceDiscardAmount(ResourceType.WOOD, 0);
		getDiscardView().setResourceDiscardAmount(ResourceType.ORE, 0);
		getDiscardView().setResourceDiscardAmount(ResourceType.WHEAT, 0);
		getDiscardView().setResourceDiscardAmount(ResourceType.SHEEP, 0);
	}

}

