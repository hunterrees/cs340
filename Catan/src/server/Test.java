package server;

import java.util.ArrayList;

import client.translators.ModelTranslator;
import shared.Piece;
import shared.definitions.CatanColor;
import shared.definitions.GameState;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.Bank;
import shared.model.Chat;
import shared.model.GameModel;
import shared.model.Line;
import shared.model.Log;
import shared.model.TurnTracker;
import shared.model.map.Map;
import shared.model.player.Player;
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
		
		ArrayList<Player> players = new ArrayList<Player>();
		Player player1 = new Player(0, CatanColor.ORANGE, "Steve");
		player1.setHasDiscarded(false);
		player1.setHasPlayedDevCard(false);
		player1.addVictoryPoint();
		player1.setMonuments(2);
		player1.setSoldiers(2);
		players.add(player1);
		System.out.println(translator.buildPlayers(players));
		Player player2 = new Player(1, CatanColor.ORANGE, "Steven");
		player2.setHasDiscarded(false);
		player2.setHasPlayedDevCard(false);
		player2.addVictoryPoint();
		player2.setMonuments(2);
		player2.setSoldiers(2);
		player2.getPlayerPieces().add(new Piece(PieceType.ROAD, null, null, 1));
		players.add(player2);
		players.add(player1);
		players.add(player1);
		System.out.println(translator.buildPlayers(players));
		
		Map map = new Map();
		map.placeRoad(1, new EdgeLocation(new HexLocation(-1, 1), EdgeDirection.South));
		map.placeRoad(1, new EdgeLocation(new HexLocation(1, 1), EdgeDirection.NorthWest));
		map.placeSettlement(1, new VertexLocation(new HexLocation(1, 1), VertexDirection.East));
		map.placeCity(1, new VertexLocation(new HexLocation(-1, 1), VertexDirection.East));
		map.placeSettlement(1, new VertexLocation(new HexLocation(-1, 1), VertexDirection.West));
		System.out.println(translator.buildMap(map));
		
		GameModel model = new GameModel(map, bank, players, map.getRobberLocation(), null, tracker, log, chat, -1);
		translator = new ServerTranslator(model);
		System.out.println(translator.translate());
		ModelTranslator fromJson = new ModelTranslator();
		String input = translator.translate();
		GameModel version2 = fromJson.getModelfromJSON(input);
		System.out.println(version2.getWinner());
	}
}
