package persistance.fileJar;

import java.io.File;

import persistance.interFaceJar.AbstractFactory;
import persistance.interFaceJar.GameDAO;
import persistance.interFaceJar.UserDAO;

public class FileAbstractFactory implements AbstractFactory {

	@Override
	public UserDAO createUserDAO() {
		// TODO Auto-generated method stub
		File fileDB = new File("fileDB");
		if (!fileDB.exists())
		{
			fileDB.mkdir();
		}
		new File("fileDB/users").mkdir();
		return null;
	}

	@Override
	public GameDAO createGameDAO() {
		// TODO Auto-generated method stub
		File fileDB = new File("fileDB");
		if (!fileDB.exists())
		{
			fileDB.mkdir();
		}
		new File("fileDB/games").mkdir();
		new File("fileDB/commands").mkdir();
		return null;
	}
	
	public String getType(){
		return "file";
	}

}
