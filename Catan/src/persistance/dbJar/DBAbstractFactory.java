package persistance.dbJar;

import persistance.interFaceJar.AbstractFactory;
import persistance.interFaceJar.GameDAO;
import persistance.interFaceJar.UserDAO;

public class DBAbstractFactory implements AbstractFactory {

	@Override
	public UserDAO createUserDAO() {
		return new DBUserDAO();
	}

	@Override
	public GameDAO createGameDAO() {
		return new DBGameDAO();
	}

	public String getType(){
		return "sqlite";
	}
}
