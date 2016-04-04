package persistance.interFaceJar;

import java.util.ArrayList;

import shared.model.GameModel;

public interface GameDAO {

	/**
	 * Adds a game to storage
	 */
	public void addGame(GameModel modelToAdd);
	/**
	 * Retrieves all games from storage
	 * @return
	 */
	public ArrayList<GameModel> getAllGames();
	/**
	 * updates a specified game in storage
	 */
	public void updateGame(int gameID, GameModel modelToAdd);
	/**
	 * adds a specified command to storage
	 */
	public void addCommands(int gameID, String command);
	/**
	 * retrives and returns all commands
	 * @return
	 */
	public ArrayList<String> getCommands(int gameID);
}
