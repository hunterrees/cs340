package server;

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
	public static String DATABASE_URL = "";
	private int numCommands;
	
	private static PersistanceManager myPersistanceManager;
	
	private PersistanceManager()
	{
		commands = new ArrayList<CommandList>();
	}
	
	/**
	 * get Singleton instance of PersistanceManager
	 * @return
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
				commands.get(command.getGameID()).clear();
				gameDAO.updateGame(command.getGameID(), ServerManager.getInstance().getGame(command.getGameID()));
			}
			endTransaction(true);
		}catch(Exception e){
			endTransaction(false);
		}
	}
	
	/**
	 * Calls DAO to persist User
	 */
	public void addUser(User user)
	{
		startTransaction();
		try{
			userDAO.addUser(user);
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
			gameDAO.addGame(ServerManager.getInstance().getGame(id));
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
			endTransaction(false);
		}
	}
	
	public void cleanUp(){
		//determine which clean up function to call
	}
	
	public void FilecleanUp(){
		//delete all files
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
	
	public static void safeClose(Statement stmt){
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	};
	
	public static void safeClose(PreparedStatement stmt){
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	};
	
	public static void safeClose(ResultSet rs){
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	};
}
