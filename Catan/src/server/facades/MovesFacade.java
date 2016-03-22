package server.facades;

import client.server.ServerException;
import server.commands.moves.*;

public class MovesFacade implements MovesFacadeInterface{

	@Override
	public String determineOperation(String command, String json, int gameID) throws ServerException {
		switch(command){
			case "/sendChat": return sendChat(json, gameID);
			case "/rollNumber": return rollNumber(json, gameID);
			case "/robPlayer": return robPlayer(json, gameID);
			case "/finishTurn": return finishTurn(json, gameID);
			case "/buyDevCard": return buyDevCard(json, gameID);
			case "/Year_of_Plenty": return yearOfPlenty(json, gameID);
			case "/Road_Building": return roadBuilding(json, gameID);
			case "/Soldier": return soldier(json, gameID);
			case "/Monopoly": return monopoly(json, gameID);
			case "/Monument": return monument(json, gameID);
			case "/buildRoad": return buildRoad(json, gameID);
			case "/buildSettlement": return buildSettlement(json, gameID);
			case "/buildCity": return buildCity(json, gameID);
			case "/offerTrade": return offerTrade(json, gameID);
			case "/acceptTrade": return acceptTrade(json, gameID);
			case "/maritimeTrade": return maritimeTrade(json, gameID);
			case "/discardCards": return discardCards(json, gameID);
		}
		return null;
	}

	@Override
	public String sendChat(String json, int gameID) throws ServerException {
		SendChat sendChatCommand = new SendChat(gameID, json);
		return (String) sendChatCommand.execute();
	}

	@Override
	public String rollNumber(String json, int gameID) throws ServerException {
		RollNumber rollNumberCommand = new RollNumber(gameID, json);
		return (String) rollNumberCommand.execute();
	}

	@Override
	public String robPlayer(String json, int gameID) throws ServerException {
		RobPlayer robPlayerCommand = new RobPlayer(gameID, json);
		return (String) robPlayerCommand.execute();
	}

	@Override
	public String finishTurn(String json, int gameID) throws ServerException {
		FinishTurn finishTurnCommand = new FinishTurn(gameID, json);
		return (String) finishTurnCommand.execute();
	}

	@Override
	public String buyDevCard(String json, int gameID) throws ServerException {
		BuyDevCard buyDevCardCommand = new BuyDevCard(gameID, json);
		return (String) buyDevCardCommand.execute();
	}

	@Override
	public String yearOfPlenty(String json, int gameID) throws ServerException {
		YearOfPlenty yearOfPlentyCommand = new YearOfPlenty(gameID, json);
		return (String) yearOfPlentyCommand.execute();
	}

	@Override
	public String roadBuilding(String json, int gameID) throws ServerException {
		RoadBuilding roadBuildingCommand = new RoadBuilding(gameID, json);
		return (String) roadBuildingCommand.execute();
	}

	@Override
	public String soldier(String json, int gameID) throws ServerException {
		Soldier soldierCommand = new Soldier(gameID, json);
		return (String) soldierCommand.execute();
	}

	@Override
	public String monopoly(String json, int gameID) throws ServerException {
		Monopoly monopolyCommand = new Monopoly(gameID, json);
		return (String) monopolyCommand.execute();
	}

	@Override
	public String monument(String json, int gameID) throws ServerException {
		Monument monumentCommand = new Monument(gameID, json);
		return (String) monumentCommand.execute();
	}

	@Override
	public String buildRoad(String json, int gameID) throws ServerException {
		BuildRoad buildRoadCommand = new BuildRoad(gameID, json);
		return (String) buildRoadCommand.execute();
	}

	@Override
	public String buildSettlement(String json, int gameID) throws ServerException {
		BuildSettlement buildSettlementCommand = new BuildSettlement(gameID, json);
		return (String) buildSettlementCommand.execute();
	}

	@Override
	public String buildCity(String json, int gameID) throws ServerException {
		BuildCity buildCityCommand = new BuildCity(gameID, json);
		return (String) buildCityCommand.execute();
	}

	@Override
	public String offerTrade(String json, int gameID) throws ServerException {
		OfferTrade offerTradeCommand = new OfferTrade(gameID, json);
		return (String) offerTradeCommand.execute();
	}

	@Override
	public String acceptTrade(String json, int gameID) throws ServerException {
		AcceptTrade acceptTradeCommand = new AcceptTrade(gameID, json);
		return (String) acceptTradeCommand.execute();
	}

	@Override
	public String maritimeTrade(String json, int gameID) throws ServerException {
		MaritimeTrade maritimeTradeCommand = new MaritimeTrade(gameID, json);
		return (String) maritimeTradeCommand.execute();
	}
	
	@Override
	public String discardCards(String json, int gameID) throws ServerException {
		DiscardCards discardCardsCommand = new DiscardCards(gameID, json);
		return (String) discardCardsCommand.execute();
	}


}
