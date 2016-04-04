package persistance.interFaceJar;

public interface AbstractFactory {

	/**
	 * Sets up User Data Access Object according to factory type
	 */
	public void createUserDAO();
	
	/**
	 * sets up Game Data Access Object according to factory type
	 */
	public void createGameDAO();
	
}
