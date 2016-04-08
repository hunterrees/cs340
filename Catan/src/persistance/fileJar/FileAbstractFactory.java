package persistance.fileJar;

import persistance.interFaceJar.AbstractFactory;
import persistance.interFaceJar.GameDAO;
import persistance.interFaceJar.UserDAO;

public class FileAbstractFactory implements AbstractFactory {

	@Override
	public UserDAO createUserDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameDAO createGameDAO() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getType(){
		return "file";
	}

}
