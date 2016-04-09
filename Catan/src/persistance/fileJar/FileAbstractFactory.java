package persistance.fileJar;

import persistance.interFaceJar.AbstractFactory;
import persistance.interFaceJar.GameDAO;
import persistance.interFaceJar.UserDAO;

public class FileAbstractFactory implements AbstractFactory {

	@Override
	public UserDAO createUserDAO() {
		return new FileUserDAO();
	}

	@Override
	public GameDAO createGameDAO() {
		return new FileGameDAO();
	}
	
	public String getType(){
		return "file";
	}

}
