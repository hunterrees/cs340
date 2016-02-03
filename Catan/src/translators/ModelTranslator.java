package translators;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import map.Map;
import model.Bank;
import model.GameModel;
import model.TurnTracker;
import player.Player;
import shared.locations.HexLocation;
import trade.TradeOffer;

/**
 * This class should parse through the model that is returned
 */
public class ModelTranslator {
	
	public ModelTranslator(){
	
	};
	
	//takes in an object, casts it a JsonObject then parses through it
	//What do I do with the log and chat and winner?
	public GameModel getModelfromJSON(String json){
		Gson gson = new Gson();
		JsonObject root = gson.fromJson(json, JsonObject.class);
		JsonObject deckJson = root.getAsJsonObject("deck");
		JsonObject chatJson = root.getAsJsonObject("chat");
		JsonObject logJson = root.getAsJsonObject("log");
		JsonObject mapJson = root.getAsJsonObject("map");
		JsonObject bankJson = root.getAsJsonObject("bank");
		JsonObject playersJson = root.getAsJsonObject("players");
		JsonObject turnTrackerJson = root.getAsJsonObject("turnTracker");
		JsonObject tradeOfferJson = root.getAsJsonObject("tradeOffer");
		JsonPrimitive winnerJson = root.getAsJsonPrimitive("winner");
		JsonPrimitive versionJson = root.getAsJsonPrimitive("version");
		int version = versionJson.getAsInt();
		Bank bank = buildBank(deckJson, bankJson);
		Map map = buildMap(mapJson);
		ArrayList<Player> players = buildPlayers(playersJson);
		TurnTracker turnTracker = buildTurnTracker(turnTrackerJson);
		TradeOffer tradeOffer = null;
		if(tradeOfferJson != null){
			tradeOffer = buildTradeOffer(tradeOfferJson);
		}
		HexLocation robber = null; //how to get the robber location?
		GameModel model = new GameModel(map, bank, players, robber, tradeOffer, turnTracker);
		model.setVersion(version);
		return model;
	};
	
	public Bank buildBank(JsonObject deckJson, JsonObject bankJson){
		return null;
	}
	
	public Map buildMap(JsonObject mapJson){
		return null;
	}
	
	public ArrayList<Player> buildPlayers(JsonObject playersJson){
		return null;
	}
	
	public TurnTracker buildTurnTracker(JsonObject turnTrackerJson){
		return null;
	}
	
	public TradeOffer buildTradeOffer(JsonObject tradeOfferJson){
		return null;
	}
}
