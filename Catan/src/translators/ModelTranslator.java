package translators;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import map.Map;
import model.Bank;
import model.Chat;
import model.GameModel;
import model.Log;
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
		Chat chat = buildChat(chatJson);
		
		JsonObject logJson = root.getAsJsonObject("log");
		Log log = buildLog(logJson);
		
		JsonObject mapJson = root.getAsJsonObject("map");
		Map map = buildMap(mapJson);
		
		JsonObject bankJson = root.getAsJsonObject("bank");
		Bank bank = buildBank(deckJson, bankJson);
		
		JsonObject playersJson = root.getAsJsonObject("players");
		ArrayList<Player> players = buildPlayers(playersJson);
		
		JsonObject turnTrackerJson = root.getAsJsonObject("turnTracker");
		TurnTracker turnTracker = buildTurnTracker(turnTrackerJson);
		
		JsonObject tradeOfferJson = root.getAsJsonObject("tradeOffer");
		TradeOffer tradeOffer = null;
		if(tradeOfferJson != null){
			tradeOffer = buildTradeOffer(tradeOfferJson);
		}
		
		JsonPrimitive winnerJson = root.getAsJsonPrimitive("winner");
		int winner = winnerJson.getAsInt();
		JsonPrimitive versionJson = root.getAsJsonPrimitive("version");
		int version = versionJson.getAsInt();
		
		HexLocation robber = null; //how to get the robber location?
		
		GameModel model = new GameModel(map, bank, players, robber, tradeOffer, turnTracker, log, chat, winner);
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
	
	public Log buildLog(JsonObject logJson){
		return null;
	}
	
	public Chat buildChat(JsonObject chatJson){
		return null;
	}
}
