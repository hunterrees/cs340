package server.facades;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import client.server.ServerException;
import server.commands.game.ListAI;

public class MockGameFacade implements GameFacadeInterface{

	@Override
	public String getModel(int gameID, String command) throws ServerException {
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
	public void addAI(String json) throws ServerException {
		return;
	}

	@Override
	public String listAIs() throws ServerException {
		ListAI listAICommand = new ListAI(-1, "");
		return (String) listAICommand.execute();
	}

}
