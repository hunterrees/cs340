package persistance.dbJar;

import persistance.interFaceJar.AbstractFactory;
import persistance.interFaceJar.GameDAO;
import persistance.interFaceJar.UserDAO;

public class DBAbstractFactory implements AbstractFactory {

	@Override
	public UserDAO createUserDAO() {
		try{
			return new DBUserDAO();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public GameDAO createGameDAO() {
		try{
			return new DBGameDAO();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public String getType(){
		return "sqlite";
	}
}
