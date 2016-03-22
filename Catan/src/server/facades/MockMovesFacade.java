package server.facades;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import client.server.ServerException;

public class MockMovesFacade implements MovesFacadeInterface{
	
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
		File file = new File("Games/defaultGame.json");
		String jsonText = "";
		try {
			jsonText = FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonText;
	}

	@Override
	public String rollNumber(String json, int gameID) throws ServerException {
		File file = new File("Games/defaultGame.json");
		String jsonText = "";
		try {
			jsonText = FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonText;
	}

	@Override
	public String robPlayer(String json, int gameID) throws ServerException {
		File file = new File("Games/defaultGame.json");
		String jsonText = "";
		try {
			jsonText = FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonText;
	}

	@Override
	public String finishTurn(String json, int gameID) throws ServerException {
		File file = new File("Games/defaultGame.json");
		String jsonText = "";
		try {
			jsonText = FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonText;
	}

	@Override
	public String buyDevCard(String json, int gameID) throws ServerException {
		File file = new File("Games/defaultGame.json");
		String jsonText = "";
		try {
			jsonText = FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonText;
	}

	@Override
	public String yearOfPlenty(String json, int gameID) throws ServerException {
		File file = new File("Games/defaultGame.json");
		String jsonText = "";
		try {
			jsonText = FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonText;
	}

	@Override
	public String roadBuilding(String json, int gameID) throws ServerException {
		File file = new File("Games/defaultGame.json");
		String jsonText = "";
		try {
			jsonText = FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonText;
	}

	@Override
	public String soldier(String json, int gameID) throws ServerException {
		File file = new File("Games/defaultGame.json");
		String jsonText = "";
		try {
			jsonText = FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonText;
	}

	@Override
	public String monopoly(String json, int gameID) throws ServerException {
		File file = new File("Games/defaultGame.json");
		String jsonText = "";
		try {
			jsonText = FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonText;
	}

	@Override
	public String monument(String json, int gameID) throws ServerException {
		File file = new File("Games/defaultGame.json");
		String jsonText = "";
		try {
			jsonText = FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonText;
	}

	@Override
	public String buildRoad(String json, int gameID) throws ServerException {
		File file = new File("Games/defaultGame.json");
		String jsonText = "";
		try {
			jsonText = FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonText;
	}

	@Override
	public String buildSettlement(String json, int gameID) throws ServerException {
		File file = new File("Games/defaultGame.json");
		String jsonText = "";
		try {
			jsonText = FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonText;
	}

	@Override
	public String buildCity(String json, int gameID) throws ServerException {
		File file = new File("Games/defaultGame.json");
		String jsonText = "";
		try {
			jsonText = FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonText;
	}

	@Override
	public String offerTrade(String json, int gameID) throws ServerException {
		File file = new File("Games/defaultGame.json");
		String jsonText = "";
		try {
			jsonText = FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonText;
	}

	@Override
	public String acceptTrade(String json, int gameID) throws ServerException {
		File file = new File("Games/defaultGame.json");
		String jsonText = "";
		try {
			jsonText = FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonText;
	}

	@Override
	public String discardCards(String json, int gameID) throws ServerException {
		File file = new File("Games/defaultGame.json");
		String jsonText = "";
		try {
			jsonText = FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonText;
	}

	@Override
	public String maritimeTrade(String json, int gameID) throws ServerException {
		File file = new File("Games/defaultGame.json");
		String jsonText = "";
		try {
			jsonText = FileUtils.readFileToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonText;
	}

}
