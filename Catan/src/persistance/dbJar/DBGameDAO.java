package persistance.dbJar;

import java.util.ArrayList;

import persistance.interFaceJar.GameDAO;
import shared.model.GameModel;

public class DBGameDAO implements GameDAO {

	@Override
	public void addGame(GameModel modelToAdd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<GameModel> getAllGames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateGame(int gameID, GameModel modelToAdd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCommands(int gameID, String command) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<String> getCommands(int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
