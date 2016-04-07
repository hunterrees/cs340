package server.facades;

import client.server.ServerException;
import server.PersistanceManager;
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
		String result = (String) sendChatCommand.execute();
		PersistanceManager.getInstance().addCommand(sendChatCommand);
		return result;
	}

	@Override
	public String rollNumber(String json, int gameID) throws ServerException {
		RollNumber rollNumberCommand = new RollNumber(gameID, json);
		String result = (String) rollNumberCommand.execute();
		PersistanceManager.getInstance().addCommand(rollNumberCommand);
		return result;
	}

	@Override
	public String robPlayer(String json, int gameID) throws ServerException {
		RobPlayer robPlayerCommand = new RobPlayer(gameID, json);
		String result = (String) robPlayerCommand.execute();
		PersistanceManager.getInstance().addCommand(robPlayerCommand);
		return result;
	}

	@Override
	public String finishTurn(String json, int gameID) throws ServerException {
		FinishTurn finishTurnCommand = new FinishTurn(gameID, json);
		String result = (String) finishTurnCommand.execute();
		PersistanceManager.getInstance().addCommand(finishTurnCommand);
		return result;
	}

	@Override
	public String buyDevCard(String json, int gameID) throws ServerException {
		BuyDevCard buyDevCardCommand = new BuyDevCard(gameID, json);
		String result = (String) buyDevCardCommand.execute();
		PersistanceManager.getInstance().addCommand(buyDevCardCommand);
		return result;
	}

	@Override
	public String yearOfPlenty(String json, int gameID) throws ServerException {
		YearOfPlenty yearOfPlentyCommand = new YearOfPlenty(gameID, json);
		String result = (String) yearOfPlentyCommand.execute();
		PersistanceManager.getInstance().addCommand(yearOfPlentyCommand);
		return result;
	}

	@Override
	public String roadBuilding(String json, int gameID) throws ServerException {
		RoadBuilding roadBuildingCommand = new RoadBuilding(gameID, json);
		String result = (String) roadBuildingCommand.execute();
		PersistanceManager.getInstance().addCommand(roadBuildingCommand);
		return result;
	}

	@Override
	public String soldier(String json, int gameID) throws ServerException {
		Soldier soldierCommand = new Soldier(gameID, json);
		String result = (String) soldierCommand.execute();
		PersistanceManager.getInstance().addCommand(soldierCommand);
		return result;
	}

	@Override
	public String monopoly(String json, int gameID) throws ServerException {
		Monopoly monopolyCommand = new Monopoly(gameID, json);
		String result = (String) monopolyCommand.execute();
		PersistanceManager.getInstance().addCommand(monopolyCommand);
		return result;
	}

	@Override
	public String monument(String json, int gameID) throws ServerException {
		Monument monumentCommand = new Monument(gameID, json);
		String result = (String) monumentCommand.execute();
		PersistanceManager.getInstance().addCommand(monumentCommand);
		return result;
	}

	@Override
	public String buildRoad(String json, int gameID) throws ServerException {
		BuildRoad buildRoadCommand = new BuildRoad(gameID, json);
		String result = (String) buildRoadCommand.execute();
		PersistanceManager.getInstance().addCommand(buildRoadCommand);
		return result;
	}

	@Override
	public String buildSettlement(String json, int gameID) throws ServerException {
		BuildSettlement buildSettlementCommand = new BuildSettlement(gameID, json);
		String result = (String) buildSettlementCommand.execute();
		PersistanceManager.getInstance().addCommand(buildSettlementCommand);
		return result;
	}

	@Override
	public String buildCity(String json, int gameID) throws ServerException {
		BuildCity buildCityCommand = new BuildCity(gameID, json);
		String result = (String) buildCityCommand.execute();
		PersistanceManager.getInstance().addCommand(buildCityCommand);
		return result;
	}

	@Override
	public String offerTrade(String json, int gameID) throws ServerException {
		OfferTrade offerTradeCommand = new OfferTrade(gameID, json);
		String result = (String) offerTradeCommand.execute();
		PersistanceManager.getInstance().addCommand(offerTradeCommand);
		return result;
	}

	@Override
	public String acceptTrade(String json, int gameID) throws ServerException {
		AcceptTrade acceptTradeCommand = new AcceptTrade(gameID, json);
		String result = (String) acceptTradeCommand.execute();
		PersistanceManager.getInstance().addCommand(acceptTradeCommand);
		return result;
	}

	@Override
	public String maritimeTrade(String json, int gameID) throws ServerException {
		MaritimeTrade maritimeTradeCommand = new MaritimeTrade(gameID, json);
		String result = (String) maritimeTradeCommand.execute();
		PersistanceManager.getInstance().addCommand(maritimeTradeCommand);
		return result;
	}
	
	@Override
	public String discardCards(String json, int gameID) throws ServerException {
		DiscardCards discardCardsCommand = new DiscardCards(gameID, json);
		String result = (String) discardCardsCommand.execute();
		PersistanceManager.getInstance().addCommand(discardCardsCommand);
		return result;
	}


}
