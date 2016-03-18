package server;

import java.util.ArrayList;

import shared.definitions.GameState;
import shared.definitions.ResourceType;
import shared.model.Bank;
import shared.model.Chat;
import shared.model.Line;
import shared.model.Log;
import shared.model.TurnTracker;
import shared.model.trade.TradeOffer;

public class Test {

	public static void main(String[] args){
		TurnTracker tracker = new TurnTracker(0, 1, GameState.rolling, 2);
		ServerTranslator translator = new ServerTranslator(null);
		System.out.println(translator.buildTurnTracker(tracker));
		
		Bank bank = new Bank();
		System.out.println(translator.buildBank(bank));
		
		ArrayList<Line> lines = new ArrayList<Line>();
		lines.add(new Line("Hunter", "almost there"));
		lines.add(new Line("Test", "testing"));
		Chat chat = new Chat(lines);
		Log log = new Log(lines);
		System.out.println(translator.buildChat(chat));
		System.out.println(translator.buildLog(log));
		
		ArrayList<ResourceType> offering = new ArrayList<ResourceType>();
		ArrayList<ResourceType> receiving = new ArrayList<ResourceType>();
		offering.add(ResourceType.BRICK);
		offering.add(ResourceType.BRICK);
		receiving.add(ResourceType.WHEAT);
		
		System.out.println(translator.buildTradeOffer(null));
		TradeOffer offer = new TradeOffer(2, 1, offering, receiving);
		System.out.println(translator.buildTradeOffer(offer));
	}
}
