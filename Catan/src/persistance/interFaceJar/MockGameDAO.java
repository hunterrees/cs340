package persistance.interFaceJar;

import java.util.ArrayList;

import server.CommandList;
import shared.model.GameModel;

public class MockGameDAO implements GameDAO{

	@Override
	public void addGame(GameModel modelToAdd, int GameID) throws DAOException {
	}

	@Override
	public ArrayList<GameModel> getAllGames() throws DAOException {
		// TODO Auto-generated method stub
		return new ArrayList<GameModel>();
	}

	@Override
	public void updateGame(int gameID, GameModel modelToAdd) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCommands(int gameID, CommandList command) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CommandList getCommands(int gameID) throws DAOException {
		// TODO Auto-generated method stub
		return new CommandList();
	}

}
