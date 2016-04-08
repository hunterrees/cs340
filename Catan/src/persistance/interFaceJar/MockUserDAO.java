package persistance.interFaceJar;

import java.sql.SQLException;
import java.util.ArrayList;

import server.User;

public class MockUserDAO implements UserDAO{

	@Override
	public ArrayList<User> getUsers() throws DAOException {
		// TODO Auto-generated method stub
		return new ArrayList<User>();
	}

	@Override
	public void addUser(User userToAdd, int UserID) throws DAOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
