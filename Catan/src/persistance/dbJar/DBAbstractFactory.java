package persistance.dbJar;

import persistance.interFaceJar.AbstractFactory;
import persistance.interFaceJar.GameDAO;
import persistance.interFaceJar.UserDAO;

public class DBAbstractFactory implements AbstractFactory {

	@Override
	public UserDAO createUserDAO() {
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public GameDAO createGameDAO() {
		return null;
		// TODO Auto-generated method stub
		
	}

}
