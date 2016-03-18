package server.commands.moves;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import client.server.ServerException;
import client.translators.ModelTranslator;
import client.translators.moves.ResourceList;
import server.commands.Command;
import shared.definitions.ResourceType;
import shared.model.GameModel;
import shared.model.trade.TradeOffer;

public class OfferTrade extends Command{

	private int playerIndex;
	private int reciever;
	private ResourceList offer;
	
	public OfferTrade(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Preconditions: The trader has the resources being offered
	 * PostConditions: The tradeOffer class instance in the model is populated
	 * @throws ServerException 
	 */
	

	@Override
	public Object execute() throws ServerException {
		// TODO Auto-generated method stub
		ModelTranslator microTranslator = new ModelTranslator();
		Gson gson = new Gson();
		JsonObject root;
		try{
			root = gson.fromJson(json, JsonObject.class);
		}catch(Exception e){
			return model;
		}
		int playerIndex = root.getAsJsonPrimitive("playerIndex").getAsInt();
		playingState();
		myTurn(playerIndex);
		TradeOffer myTradeOffer = microTranslator.buildTradeOffer(root);
		model.setTradeOffer(myTradeOffer);
		return model;
	}

}
