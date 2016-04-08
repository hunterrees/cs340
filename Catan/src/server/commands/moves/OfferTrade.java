package server.commands.moves;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import client.server.ServerException;
import client.translators.ModelTranslator;
import client.translators.moves.ResourceList;
import server.ServerManager;
import server.ServerTranslator;
import server.commands.Command;
import shared.ResourceCard;
import shared.definitions.ResourceType;
import shared.model.GameModel;
import shared.model.trade.TradeOffer;

public class OfferTrade extends Command{

	private int playerIndex;
	private int reciever;
	private ResourceList offer;
	
	private TradeOffer completedOffer;
	
	public OfferTrade(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Preconditions: The trader has the resources being offered
	 * PostConditions: The tradeOffer class instance in the model is populated
	 * @throws ServerException 
	 */
	
	private void addTradeResources(ArrayList<ResourceType> offer, ArrayList<ResourceType> receive, ResourceType type, int amount){
		if(amount > 0){
			
			for(int i = 0; i < amount; i++){
				receive.add(type);
			}
		}else if(amount < 0){
			amount = -1 * amount;
			for(int i = 0; i < amount; i++){
				offer.add(type);
			}
		}
	}
	
	private void translate()
	{
		Gson gson = new Gson();
		JsonObject root;
		try{
			root = gson.fromJson(json, JsonObject.class);
		}catch(Exception e){
			return;
		}
	
		playerIndex = root.getAsJsonPrimitive("playerIndex").getAsInt();
		reciever = root.getAsJsonPrimitive("receiver").getAsInt();
		
		JsonObject offerJson = root.getAsJsonObject("offer");
		JsonPrimitive brickJson = offerJson.getAsJsonPrimitive("brick");
		int brick = brickJson.getAsInt();
		
		JsonPrimitive woodJson = offerJson.getAsJsonPrimitive("wood");
		int wood = woodJson.getAsInt();
		
		JsonPrimitive sheepJson = offerJson.getAsJsonPrimitive("sheep");
		int sheep = sheepJson.getAsInt();
		
		JsonPrimitive wheatJson = offerJson.getAsJsonPrimitive("wheat");
		int wheat = wheatJson.getAsInt();
		
		JsonPrimitive oreJson = offerJson.getAsJsonPrimitive("ore");
		int ore = oreJson.getAsInt();
		
		ArrayList<ResourceType> resourcesOffered = new ArrayList<ResourceType>();
		ArrayList<ResourceType> resourcesDesired = new ArrayList<ResourceType>();
		addTradeResources(resourcesOffered, resourcesDesired, ResourceType.BRICK, brick);
		addTradeResources(resourcesOffered, resourcesDesired, ResourceType.WOOD, wood);
		addTradeResources(resourcesOffered, resourcesDesired, ResourceType.SHEEP, sheep);
		addTradeResources(resourcesOffered, resourcesDesired, ResourceType.WHEAT, wheat);
		addTradeResources(resourcesOffered, resourcesDesired, ResourceType.ORE, ore);
		
		completedOffer = new TradeOffer(playerIndex, reciever, resourcesOffered, resourcesDesired);
	}
	
	@Override
	public Object execute() throws ServerException {
		if(gameID != -1){
			this.model = ServerManager.getInstance().getGame(gameID);
		}
		// TODO Auto-generated method stub
		translate();
		playingState();
		myTurn(playerIndex);
		
		model.setTradeOffer(completedOffer);
		
		model.updateVersionNumber();
		ServerTranslator temp = new ServerTranslator(model);
		return temp.translate();
	}

}
