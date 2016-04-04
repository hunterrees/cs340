package persistance.serverJar;

import java.sql.Connection;

import persistance.interFaceJar.AbstractFactory;
import persistance.interFaceJar.GameDAO;
import persistance.interFaceJar.UserDAO;

public class PersistanceManager {
	
	private AbstractFactory myAbstractFactory;
	private UserDAO myUserDAO;
	private GameDAO myGameDAO;
	private Connection myConnection;
	
	PersistanceManager myPersistanceManager;
	
	private PersistanceManager()
	{
		//set myAbstractFactory here
	}
	
	/**
	 * get Singleton instance of PersistanceManager
	 * @return
	 */
	public PersistanceManager getInstance()
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
	public void setPersistanceType(String persistanceType)
	{
		//set persistanceType here
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
