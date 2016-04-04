package persistance.interFaceJar;

import java.util.ArrayList;

import server.User;

public interface UserDAO {

	/**
	 * Return list of all users from persistance
	 * @return
	 */
	public ArrayList<User> getUsers();
	
	/**
	 * Adds a single user to Persistance
	 */
	public void addUser(User userToAdd);
}
