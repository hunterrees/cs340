package persistance.dbJar;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

import persistance.interFaceJar.DAOException;
import persistance.interFaceJar.GameDAO;
import server.CommandList;
import server.PersistanceManager;
import server.User;
import server.commands.Command;
import shared.model.GameModel;

public class DBGameDAO implements GameDAO {

	Connection connection;

	public DBGameDAO() {
		try{
			connection = PersistanceManager.getInstance().getConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void addGame(GameModel modelToAdd, int gameID) throws DAOException {
		// TODO Auto-generated method stub
		System.out.println("add game called");
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		Statement keyStmt = null;
		int id = -1;
		try {
			String query = "insert into Games (ID, Name, Model) values (?, ?, ?)";
			stmt = PersistanceManager.getInstance().getConnection().prepareStatement(query);
			stmt.setInt(1, modelToAdd.getVersion());
			stmt.setString(2, modelToAdd.getTitle());
			//stmt.setBlob(3, (Blob) modelToAdd);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			ObjectOutput out = null;
			Blob blob = null;
			ByteArrayInputStream bais = null;
			byte[] yourBytes = new byte[0];

			System.out.println("about to try");
			try {
				out = new ObjectOutputStream(bos);
				out.writeObject(modelToAdd);
				System.out.println("1");
				yourBytes = bos.toByteArray();
				bais = new ByteArrayInputStream(yourBytes);
				System.out.println("2");
				//blob = PersistanceManager.getInstance().getConnection().createBlob();
				//blob.setBytes(1, yourBytes);
				System.out.println("done trying");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("catch");
			}
			stmt.setBytes(3, yourBytes);
			//(3, bais, yourBytes.length);
			//stmt.setBlob(3, blob);
			System.out.println("everything set");

			if(stmt.executeUpdate() == 1) {
				keyStmt = PersistanceManager.getInstance().getConnection().createStatement();
				keyRS = (keyStmt).executeQuery("select last_insert_rowid()");
				keyRS.next();
			}
			else {
				throw new DAOException("Could not insert User.");
			}
		}
		catch (SQLException e) {
			throw new DAOException("Could not insert User: " + e);
		}
		finally {
			/*PersistanceManager.getInstance().safeClose(stmt);
			PersistanceManager.getInstance().safeClose(keyRS);*/
			try {
				stmt.close();
				keyRS.close();
				System.out.println("finally");

			} catch (SQLException e) {
				e.printStackTrace();
			}
			//db.safeClose(keyStmt);
		}
	}

	@Override
	public ArrayList<GameModel> getAllGames() {
		// TODO Auto-generated method stub
		System.out.println("getAllGames");
		ArrayList<GameModel> games = new ArrayList<GameModel>();
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "select * from Games";
			stmt = PersistanceManager.getInstance().getConnection().prepareStatement(query);

			keyRS = stmt.executeQuery();
			//While there are rows
			while (keyRS.next())
			{
				int id = keyRS.getInt("ID");
				int name = keyRS.getInt("Name");
				System.out.println("1");
				byte[] model = keyRS.getBytes("Model");
				
				ObjectInputStream ois;
				ByteArrayInputStream bais;
				GameModel game = null;
				try {
					bais = new ByteArrayInputStream(model);
					ois = new ObjectInputStream(bais);
					game = (GameModel) ois.readObject();

				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				games.add(game);
				
			}
		} catch (SQLException e) {
			System.out.println("Could not Get the Field. " + e);
		}
		finally {
			// manually closes the connection
			try {
				stmt.close();
				keyRS.close();
				System.out.println("2");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return games;
	}

	@Override
	public void updateGame(int gameID, GameModel modelToAdd) throws DAOException {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		try {
			String query = "update updateGame set ID = ?";
			stmt = PersistanceManager.getInstance().getConnection().prepareStatement(query);
			stmt.setBlob(1, (Blob) modelToAdd);
			if (stmt.executeUpdate() != 1) {
				throw new DAOException("Could not update Field.");
			}
		}
		catch (SQLException e) {
			throw new DAOException("Could not update Field: " + e);
		}
		finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void addCommands(int gameID, CommandList list) throws DAOException {// needs to pass in an arraylist of commands
		// TODO Auto-generated method stub
		System.out.println("adding command");
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		Statement keyStmt = null;
		int id = -1;
		try {
			String query = "insert into Commands (GameID, CommandList) values (?, ?)";
			stmt = PersistanceManager.getInstance().getConnection().prepareStatement(query);
			stmt.setInt(1, gameID);
			//stmt.setBlob(2, (Blob) list);//
			ObjectOutput out = null;
			Blob blob = null;
			ByteArrayInputStream bais = null;
			byte[] yourBytes = new byte[0];
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			try {
				out = new ObjectOutputStream(bos);
				out.writeObject(list);

				yourBytes = bos.toByteArray();
				//bais = new ByteArrayInputStream(yourBytes);
				//blob = connection.createBlob();
				//blob.setBytes(1, yourBytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
			stmt.setBytes(2, yourBytes);



			if(stmt.executeUpdate() == 1) {
				keyStmt = PersistanceManager.getInstance().getConnection().createStatement();
				keyRS = (keyStmt).executeQuery("select last_insert_rowid()");
				keyRS.next();
			}
			else {
				throw new DAOException("Could not insert User.");
			}
		}
		catch (SQLException e) {
			throw new DAOException("Could not insert User: " + e);
		}
		finally {
			/*PersistanceManager.getInstance().safeClose(stmt);
			PersistanceManager.getInstance().safeClose(keyRS);*/
			try {
				stmt.close();
				keyRS.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			//db.safeClose(keyStmt);
		}
	}

	@Override
	public CommandList getCommands(int gameID) {
		// TODO Auto-generated method stub
		System.out.println("getCommands");
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		CommandList list = new CommandList();
		try {
			String query = "Select * from Commands";
			System.out.println("5");
			stmt = PersistanceManager.getInstance().getConnection().prepareStatement(query);
			
			keyRS = stmt.executeQuery();
			System.out.println("8");
			boolean hasNext = keyRS.next();
			//If we found a list
			if (hasNext)
			{
				//stmt.setInt(1, gameID);
				System.out.println("7");
				System.out.println("didnt skip");
				byte[] model = keyRS.getBytes("CommandList");

				ObjectInputStream ois;
				ByteArrayInputStream bais;
				try {
					bais = new ByteArrayInputStream(model);
					ois = new ObjectInputStream(bais);
					list = (CommandList) ois.readObject();

				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

			}
		} catch (SQLException e)
		{
			System.out.println("Could not Get the Game. " + e);
		}
		finally {

			try {
				stmt.close();
				keyRS.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	

}
