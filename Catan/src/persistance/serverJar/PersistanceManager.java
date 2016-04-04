package persistance.serverJar;

import persistance.interFaceJar.AbstractFactory;
import persistance.interFaceJar.GameDAO;
import persistance.interFaceJar.UserDAO;

public class PersistanceManager {
	
	private AbstractFactory myAbstractFactory;
	private UserDAO myUserDAO;
	private GameDAO myGameDAO;
	
	public PersistanceManager(String persistanceType)
	{
		//set myAbstractFactory here
	}
	
	/**
	 * Requests DAO to persist addCommand
	 */
	public void addCommand()
	{
		
	}
	
	/**
	 * Calls DAO to persist User
	 */
	public void addUser()
	{
		
	}
	
	/**
	 * Calls DAO to persist Game
	 */
	public void addGame()
	{
		
	}
	
	/**
	 * Pulls all Game and User information from Persistance. If no persistance exists, files setup
	 */
	public void startUp()
	{
		
	}
	
	/**
	 * Resets all persistance data
	 */
	public void cleanUp()
	{
		
	}
	
	public void registerPlugin(String thingToRegister)
	{
		
	}
}
