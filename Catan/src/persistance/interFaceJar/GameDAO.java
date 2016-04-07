package persistance.interFaceJar;

import java.util.ArrayList;

import server.CommandList;
import shared.model.GameModel;

public interface GameDAO {

	/**
	 * Adds a game to storage
	 */
	public void addGame(GameModel modelToAdd) throws DAOException;
	/**
	 * Retrieves all games from storage
	 * @return
	 */
	public ArrayList<GameModel> getAllGames() throws DAOException;
	/**
	 * updates a specified game in storage
	 */
	public void updateGame(int gameID, GameModel modelToAdd) throws DAOException;
	/**
	 * adds a specified command to storage
	 */
	public void addCommands(CommandList command) throws DAOException;
	/**
	 * retrives and returns all commands
	 * @return
	 */
	public CommandList getCommands(int gameID) throws DAOException;
}
