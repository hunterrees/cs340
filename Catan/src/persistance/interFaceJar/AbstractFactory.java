package persistance.interFaceJar;

public interface AbstractFactory {

	/**
	 * Sets up User Data Access Object according to factory type
	 */
	public UserDAO createUserDAO();
	
	/**
	 * sets up Game Data Access Object according to factory type
	 */
	public GameDAO createGameDAO();
	
	public String getType();
	
}
