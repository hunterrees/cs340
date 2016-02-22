package server;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import model.GameModel;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import translators.ModelTranslator;

/**
 * 
 * See ServerInterface Javadoc for details of methods
 *
 */
public class MockServerProxy implements ServerInterface {


	@Override
	public GameModel getModel(int versionID){
		GameModel model = null;
		if(versionID != 1){
			try{
				File file = new File("translatorTests/pollerTest2.txt");
				String jsonText = FileUtils.readFileToString(file);
				ModelTranslator translator = new ModelTranslator();
				model = translator.getModelfromJSON(jsonText);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return model;
	}

	@Override
	public void userLogin(String username, String password) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userRegister(String username, String password) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String listGames() throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts)
			throws ServerException {
		// TODO Auto-generated method stub
	}

	@Override
	public void joinGame(int gameID, String color) throws ServerException {
		// TODO Auto-generated method stub
	}

	@Override
	public void addAI(String AIname) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String listAIs() throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel sendChat(int playerID, String content) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel rollNumber(int playerID, int numberRolled) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel robPlayer(int playerID, int victimID, HexLocation newRobberLocation) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel finishTurn(int playerID) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel buyDevCard(int playerID) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel yearOfPlenty(int playerID, ResourceType resource1, ResourceType resource2) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel roadBuilding(int playerID, EdgeLocation roadLocation1, EdgeLocation roadLocation2)
			throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel knight(int playerID, int victimID, HexLocation newRobberLocation) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel monopoly(int playerID, ResourceType resource) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel monument(int playerID) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel buildRoad(int playerID, EdgeLocation roadLocation, boolean setUp) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel buildSettlment(int playerID, VertexLocation vertexLocation, boolean setUp) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel buildCity(int playerID, VertexLocation vertexLocation) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel offerTrade(int playerID, ArrayList<ResourceType> resourceGive,
			ArrayList<ResourceType> resourceReceive, int receiverID) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel acceptTrade(int playerID, boolean accept) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel maritimeTrade(int playerID, int ratio, ResourceType inputResource, ResourceType outputResource)
			throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel discardCards(int playerID, ArrayList<ResourceType> resources) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

}
