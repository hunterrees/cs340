package server;

import java.util.ArrayList;

import shared.model.GameModel;

public class ServerManager {
	
	/**
	 * list of all games on the server
	 */
	private ArrayList<GameModel> games;
	/**
	 * list of all users on the server
	 */
	private ArrayList<User> users;
	
	private static ServerManager manager = null;
	/**
	 * generates the default users and games on the server
	 */
	protected ServerManager(){
	}
	
	/**
	 * returns the game corresponding with the given game ID
	 * @param index game ID
	 * @return
	 */
	public GameModel getGame(int index){
		return games.get(index);
	}
	
	/**
	 * creates a new game on the server
	 * @param model
	 */
	public void createGame(GameModel model){
		
	}
	
	/**
	 * finds and returns a user based on name
	 * @param name username of a user
	 * @return
	 */
	public User getUser(String name){
		return null;
	}
	
	/**
	 * adds a user to the server
	 * @param user
	 */
	public void addUser(User user){
		
	}
	
	public static ServerManager getInstance(){
		if(manager == null){
			manager = new ServerManager();
		}
		return manager;
	}

}
