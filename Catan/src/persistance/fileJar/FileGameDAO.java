package persistance.fileJar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.nio.file.*;


import persistance.interFaceJar.GameDAO;
import server.CommandList;
import shared.model.GameModel;

public class FileGameDAO implements GameDAO {

	@Override
	public void addGame(GameModel modelToAdd, int gameID) {
		// TODO Auto-generated method stub
		File gamesFile = new File("Games");
		if (!gamesFile.exists())
		{
			gamesFile.mkdir();
		}
		
		FileOutputStream myFileOutput = null;
		try {
			myFileOutput = new FileOutputStream(new File("/Games/game" + Integer.toString(gameID)));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ObjectOutputStream myObjectOutput = null;
		try {
			myObjectOutput  = new ObjectOutputStream(myFileOutput);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			myObjectOutput.writeObject((Object)modelToAdd);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			myObjectOutput.close();
			myFileOutput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<GameModel> getAllGames() {
		// TODO Auto-generated method stub
		ArrayList<GameModel> games = new ArrayList<GameModel>();
		try
		 {
			File myDirectory = new File("Games");
			for (File file: myDirectory.listFiles())
				{
				FileInputStream fileIn = new FileInputStream("Games/" + file.getName());
		        ObjectInputStream in = new ObjectInputStream(fileIn);
		        GameModel tempModel = null;
		        try {
					tempModel = (GameModel) in.readObject();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        if (tempModel == null)
		        {
		        	in.close();
			        fileIn.close();
		        	//throw exception!
		        }
		        else
		        {
		        	games.add(tempModel);
		        }
		        in.close();
		        fileIn.close();
			}
	     }catch(IOException i)
	     {
	        i.printStackTrace();
	        return null;
	     }
		return games;
	}

	@Override
	public void updateGame(int gameID, GameModel modelToAdd) {
		// TODO Auto-generated method stub
		addGame(modelToAdd, gameID);
	}

	@Override
	public void addCommands(int gameID, CommandList command) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CommandList getCommands(int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
