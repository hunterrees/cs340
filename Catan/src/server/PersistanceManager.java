package server;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import persistance.interFaceJar.AbstractFactory;
import persistance.interFaceJar.GameDAO;
import persistance.interFaceJar.UserDAO;
import server.commands.Command;

public class PersistanceManager {
	
	private AbstractFactory abstractFactory;
	private UserDAO userDAO;
	private GameDAO gameDAO;
	private Connection connection;
	private ArrayList<CommandList> commands;
	public static String DATABASE_URL = "jdbc:sqlite:CatanDatabase.sql";
	private int numCommands;
	
	private static PersistanceManager myPersistanceManager;
	
	private PersistanceManager()
	{
		commands = new ArrayList<CommandList>();
		try {
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * get Singleton instance of PersistanceManager
	 * @return
	 * @throws ClassNotFoundException 
	 */
	public static PersistanceManager getInstance()
	{
		if (myPersistanceManager == null)
		{
			myPersistanceManager = new PersistanceManager();
		}
		return myPersistanceManager;
	}
	
	/**
	 * Set persistance Type for Game and User Backup
	 * @param persistanceType
	 */
	public void setPersistanceType(AbstractFactory abstractFactory)
	{
		this.abstractFactory = abstractFactory;
	}
	
	public void setCommandNumber(int numCommands){
		this.numCommands = numCommands;
	}
	
	/**
	 * Requests DAO to persist addCommand
	 */
	public void addCommand(Command command)
	{
		startTransaction();
		try{
			if(commands.get(command.getGameID()).size() < numCommands){
				commands.get(command.getGameID()).addCommand(command);
				gameDAO.addCommands(command.getGameID(), commands.get(command.getGameID()));
			}else{
				updateGame(command.getGameID());
				commands.get(command.getGameID()).clear();
			}
			endTransaction(true);
		}catch(Exception e){
			endTransaction(false);
		}
	}
	
	/**
	 * Calls DAO to persist User
	 */
	public void addUser(User user, int userID)
	{
		startTransaction();
		try{
			userDAO.addUser(user, userID);
			endTransaction(true);
		}catch(Exception e){
			endTransaction(false);
		}
	}
	
	/**
	 * Calls DAO to persist Game
	 */
	public void addGame(int id)
	{
		startTransaction();
		try{
			commands.add(new CommandList());
			gameDAO.addGame(ServerManager.getInstance().getGame(id), id);
			endTransaction(true);
		}catch(Exception e){
			endTransaction(false);
		}
	}
	
	public void updateGame(int id){
		startTransaction();
		try{
			gameDAO.updateGame(id, ServerManager.getInstance().getGame(id));
			endTransaction(true);
		}catch(Exception e){
			endTransaction(false);
		}
	}
	
	/**
	 * Pulls all Game and User information from Persistance. If no persistance exists, files setup
	 */
	public void startUp()
	{
		gameDAO = abstractFactory.createGameDAO();
		userDAO = abstractFactory.createUserDAO();
		startTransaction();
		try{
			ServerManager.getInstance().setUsers(userDAO.getUsers());
			ServerManager.getInstance().setGames(gameDAO.getAllGames());
			for(int i = 0; i < ServerManager.getInstance().getGames().size(); i++){
				CommandList commandList = gameDAO.getCommands(i);
				commandList.execute();
			}
			endTransaction(true);
		}catch(Exception e){
			e.printStackTrace();
			endTransaction(false);
		}
	}
	
	public void cleanUp(){
		if(abstractFactory.getType().equals("file")){
			FilecleanUp();
		}else{
			DBcleanUp();
		}
	}
	
	public void FilecleanUp(){
		File file = new File("fileDB");
		deleteDirectory(file);
		new File("fileDB").mkdir();
		new File("fileDB/users").mkdir();
		new File("fileDB/games").mkdir();
		new File("fileDB/commands").mkdir();
	}
	
	private boolean deleteDirectory(File path){
		if(path.exists()){
			File[] files = path.listFiles();
		    for(int i=0; i<files.length; i++){
		    	if(files[i].isDirectory()){
		    		deleteDirectory(files[i]);
		        }
		        else{
		        	files[i].delete();
		        }
		    }
		}
		return(path.delete());
	}
	
	/**
	 * Resets all persistance data
	 * @throws SQLException 
	 */
	public void DBcleanUp(){
		startTransaction();
		try{
			PreparedStatement stmt = null;
		
			String query = "drop table if exists Users";
			stmt = connection.prepareStatement(query);
			stmt.executeUpdate();
			query = "create table Users "
				+ "(PlayerID INTEGER PRIMARY KEY NOT NULL , Name TEXT NOT NULL, Password TEXT NOT NULL)";
			stmt = connection.prepareStatement(query);
			stmt.executeUpdate();
		
			query = "drop table if exists Games";
			stmt = connection.prepareStatement(query);
			stmt.executeUpdate();
			query = "create table Games "
				+ "(ID INTEGER PRIMARY KEY NOT NULL , Name TEXT NOT NULL, Model BLOB NOT NULL)";
			stmt = connection.prepareStatement(query);
			stmt.executeUpdate();
		
			query = "drop table if exists Commands";
			stmt = connection.prepareStatement(query);
			stmt.executeUpdate();
			query = "create table Commands "
				+ "(GameID INTEGER PRIMARY KEY NOT NULL , CommandList BLOB NOT NULL)";
			stmt = connection.prepareStatement(query);
			stmt.executeUpdate();
			endTransaction(true);
		}catch(Exception e){
			e.printStackTrace();
			endTransaction(false);
		}
	}
	
	public Connection getConnection(){
		return connection;
	}
	
	public void startTransaction(){
		try {
			connection = DriverManager.getConnection(DATABASE_URL);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void endTransaction(boolean commit){
		if(connection != null){
			try {
				if (commit) {
					connection.commit();
				}
				else {
					connection.rollback();
				}
			}
			catch (SQLException e) {
				System.out.println("Could not end transaction");
				e.printStackTrace();
			}
			finally {
				safeClose(connection);
				connection = null;
			}
		}
	}
	
	public static void safeClose(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	};
}
