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
	public void userLogin(String username, String password) {
		// TODO Auto-generated method stub

	}

	@Override
	public void userRegister(String username, String password) {
		// TODO Auto-generated method stub

	}

	@Override
	public String listGames() {
		return null;

	}

	@Override
	public void createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts) {
		// TODO Auto-generated method stub

	}

	@Override
	public void joinGame(int playerID, String color) {
		// TODO Auto-generated method stub

	}

	@Override
	public GameModel getModel(int versionID){
		GameModel model = null;
		if(versionID != 1){
			try{
				File file = new File("translateTests/pollerTest2.txt");
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
	public void addAI(String AIname) {
		// TODO Auto-generated method stub

	}

	@Override
	public String listAIs() {
		return null;

	}

	@Override
	public void sendChat(int playerID, String content) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rollNumber(int playerID, int numberRolled) {
		// TODO Auto-generated method stub

	}

	@Override
	public void robPlayer(int playerID, int victimID, HexLocation newRobberLocation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void finishTurn(int playerID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buyDevCard(int playerID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void yearOfPlenty(int playerID, ResourceType resource1, ResourceType resource2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void roadBuilding(int playerID, EdgeLocation roadLocation1, EdgeLocation roadLocation2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void knight(int playerID, int victimID, HexLocation newRobberLocation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void monopoly(int playerID, ResourceType resource) {
		// TODO Auto-generated method stub

	}

	@Override
	public void monument(int playerID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildRoad(int playerID, EdgeLocation roadLocation, boolean setUp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildSettlment(int playerID, VertexLocation vertexLocation, boolean setUp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildCity(int playerID, VertexLocation vertexLocation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void acceptTrade(int playerID, boolean accept) {
		// TODO Auto-generated method stub

	}

	@Override
	public void maritimeTrade(int playerID, int ratio, ResourceType inputResource, ResourceType outputResource) {
		// TODO Auto-generated method stub

	}

	@Override
	public void discardCards(int playerID, ArrayList<ResourceType> resources) {
		// TODO Auto-generated method stub

	}

	@Override
	public void offerTrade(int playerID, ArrayList<ResourceType> resourceGive, ArrayList<ResourceType> resourceReceive,
			int receiverID) {
		// TODO Auto-generated method stub
		
	}

}
