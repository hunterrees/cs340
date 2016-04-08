package persistance.fileJar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import persistance.interFaceJar.UserDAO;
import server.User;
import shared.model.GameModel;

public class FileUserDAO implements UserDAO {

	@Override
	public ArrayList<User> getUsers() {
		// TODO Auto-generated method stub
		ArrayList<User> users = new ArrayList<User>();
		try
		 {
			File myDirectory = new File("fileDB/users");
			for (File file: myDirectory.listFiles())
				{
				FileInputStream fileIn = new FileInputStream("fileDB/users/" + file.getName());
		        ObjectInputStream in = new ObjectInputStream(fileIn);
		        User tempUser = null;
		        try {
					tempUser = (User) in.readObject();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        if (tempUser == null)
		        {
		        	in.close();
			        fileIn.close();
		        	//throw exception!
		        }
		        else
		        {
		        	users.add(tempUser);
		        }
		        in.close();
		        fileIn.close();
			}
	     }catch(IOException i)
	     {
	        i.printStackTrace();
	        return null;
	     }
		return users;
	}

	@Override
	public void addUser(User userToAdd, int userID) {
		// TODO Auto-generated method stub
		File usersFile = new File("fileDB/users");
		if (!usersFile.exists())
		{
			usersFile.mkdir();
		}
		
		FileOutputStream myFileOutput = null;
		try {
			myFileOutput = new FileOutputStream(new File("fileDB/users/user" + Integer.toString(userID)));
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
			myObjectOutput.writeObject((Object)userToAdd);
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

}
