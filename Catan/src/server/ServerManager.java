package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import client.translators.ModelTranslator;
import shared.model.GameModel;
import shared.model.Line;
import shared.model.Log;

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
	
	private ModelTranslator translator;
	/**
	 * generates the default users and games on the server
	 * @throws FileNotFoundException 
	 */
	protected ServerManager() throws FileNotFoundException{
		try{
		translator = new ModelTranslator();
		users = new ArrayList<User>();
		games = new ArrayList<GameModel>();
		users.add(new User("Sam", "sam", 0));
		users.add(new User("Brooke", "brooke", 1));
		users.add(new User("Pete", "pete", 2));
		users.add(new User("Mark", "mark", 3));
		Scanner scanner1 = new Scanner(new BufferedReader(new FileReader("Games/defaultGame.json")));
		StringBuilder defaultJson = new StringBuilder();
		while(scanner1.hasNext()){
			String inputLine = scanner1.next();
			defaultJson.append(inputLine);
		}
		GameModel defaultGame = translator.getModelfromJSON(defaultJson.toString());
		defaultGame.setTitle("Default Game");
		defaultGame.setLog(generateLog());
		games.add(defaultGame);
		scanner1.close();
		
		Scanner scanner2 = new Scanner(new BufferedReader(new FileReader("Games/setUpGame.json")));
		StringBuilder setUpJson = new StringBuilder();
		while(scanner2.hasNext()){
			String inputLine = scanner2.next();
			setUpJson.append(inputLine);
		}
		GameModel setUpGame = translator.getModelfromJSON(setUpJson.toString());
		setUpGame.setTitle("Empty Game");
		games.add(setUpGame);
		scanner2.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * returns the game corresponding with the given game ID
	 * @param index game ID
	 * @return
	 */
	public GameModel getGame(int index){
		if(index >= games.size()){
			return null;
		}
		return games.get(index);
	}
	
	private Log generateLog(){
		Log log = new Log(new ArrayList<Line>());
		Line line = new Line("Sam", "Sam built a road");
		log.addLine(line);
		line = new Line("Sam", "Sam built a settlement");
		log.addLine(line);
		line = new Line("Sam", "Sam's turn just ended");
		log.addLine(line);
		line = new Line("Brooke", "Brooke built a road");
		log.addLine(line);
		line = new Line("Brooke", "Brooke built a settlement");
		log.addLine(line);
		line = new Line("Brooke", "Brooke's turn just ended");
		log.addLine(line);
		line = new Line("Pete", "Pete built a road");
		log.addLine(line);
		line = new Line("Pete", "Pete built a settlement");
		log.addLine(line);
		line = new Line("Pete", "Pete's turn just ended");
		log.addLine(line);
		line = new Line("Mark", "Mark built a road");
		log.addLine(line);
		line = new Line("Mark", "Mark built a settlement");
		log.addLine(line);
		line = new Line("Mark", "Mark's turn just ended");
		log.addLine(line);
		line = new Line("Mark", "Mark built a road");
		log.addLine(line);
		line = new Line("Mark", "Mark built a settlement");
		log.addLine(line);
		line = new Line("Mark", "Mark's turn just ended");
		log.addLine(line);
		line = new Line("Pete", "Pete built a road");
		log.addLine(line);
		line = new Line("Pete", "Pete built a settlement");
		log.addLine(line);
		line = new Line("Pete", "Pete's turn just ended");
		log.addLine(line);
		line = new Line("Brooke", "Brooke built a road");
		log.addLine(line);
		line = new Line("Brooke", "Brooke built a settlement");
		log.addLine(line);
		line = new Line("Brooke", "Brooke's turn just ended");
		log.addLine(line);
		line = new Line("Sam", "Sam built a road");
		log.addLine(line);
		line = new Line("Sam", "Sam built a settlement");
		log.addLine(line);
		line = new Line("Sam", "Sam's turn just ended");
		log.addLine(line);
		return log;
	}
	
	/**
	 * creates a new game on the server
	 * @param model
	 */
	public int createGame(GameModel model){
		games.add(model);
		return games.size() - 1;
	}
	
	/**
	 * finds and returns a user based on name
	 * @param name username of a user
	 * @return
	 */
	public User getUser(String name){
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).getName().equals(name)){
				return users.get(i);
			}
		}
		return null;
	}
	
	public User validateUser(String cookie){
		cookie = cookie.replace("catan.user=", "");
		cookie = cookie.replace(";", "");
		cookie = cookie.trim();
		for(int i = 0; i < users.size(); i++){
			if(users.get(i).generateCookie().equals(cookie)){
				return users.get(i);
			}
		}
		return null;
	}
	
	/**
	 * adds a user to the server
	 * @param user
	 */
	public void addUser(User user){
		users.add(user);
	}
	
	public int numUsers(){
		return users.size();
	}
	
	public static ServerManager getInstance(){
		if(manager == null){
			try {
				manager = new ServerManager();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return manager;
	}

	public void resetManager(){
		manager = null;
	}
	
	public ArrayList<GameModel> getGames() {
		return games;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setGames(ArrayList<GameModel> games) {
		this.games = games;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
}
