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
				
				startCommands(gameID);
				
				
				//end of tryiing...
				
				
				
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
	public void startCommands(int gameID) throws SQLException, DAOException{
		//tryiing to add empty commands here
		PreparedStatement stmt2 = null;
		ResultSet keyRS2 = null;
		Statement keyStmt2 = null;
		String query2 = "insert into Commands (GameID, CommandList) values (?, ?)";
		stmt2 = PersistanceManager.getInstance().getConnection().prepareStatement(query2);
		stmt2.setInt(1, gameID);
		System.out.println("10");
		CommandList list2 = new CommandList();
		//stmt.setBlob(2, (Blob) list);//
		ObjectOutput out2 = null;
		
		System.out.println("11");
		byte[] yourBytes2 = new byte[0];
		ByteArrayOutputStream bos2 = new ByteArrayOutputStream();

		try {
			out2 = new ObjectOutputStream(bos2);
			out2.writeObject(list2);

			yourBytes2 = bos2.toByteArray();
			System.out.println("12");
			//bais = new ByteArrayInputStream(yourBytes);
			//blob = connection.createBlob();
			//blob.setBytes(1, yourBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		stmt2.setBytes(2, yourBytes2);



		if(stmt2.executeUpdate() == 1) {
			keyStmt2 = PersistanceManager.getInstance().getConnection().createStatement();
			keyRS2 = (keyStmt2).executeQuery("select last_insert_rowid()");
			keyRS2.next();
			keyStmt2.close();
			keyRS2.close();
		}
		else {
			throw new DAOException("Could not insert User.");
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
			System.out.println("updating game");
			String query = "update Games set Model= ? where ID = ?";
			stmt = PersistanceManager.getInstance().getConnection().prepareStatement(query);
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
			stmt.setBytes(1, yourBytes);
			stmt.setInt(2, gameID);
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
		System.out.println("adding command size of list of commands is now: " + list.size());
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		Statement keyStmt = null;
		int id = -1;
		try {
			System.out.println("start try");
			String query = "update Commands set CommandList = ? where GameID = ?";
			stmt = PersistanceManager.getInstance().getConnection().prepareStatement(query);
			stmt.setInt(2, gameID);
			System.out.println("10");
			//stmt.setBlob(2, (Blob) list);//
			ObjectOutput out = null;
			Blob blob = null;
			ByteArrayInputStream bais = null;
			System.out.println("11");
			byte[] yourBytes = new byte[0];
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			try {
				out = new ObjectOutputStream(bos);
				out.writeObject(list);

				yourBytes = bos.toByteArray();
				System.out.println("12");
				//bais = new ByteArrayInputStream(yourBytes);
				//blob = connection.createBlob();
				//blob.setBytes(1, yourBytes);
			} catch (IOException e) {
				e.printStackTrace();
			}
			stmt.setBytes(1, yourBytes);



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
			String query = "Select * from Commands where GameID = ?";
			System.out.println("5");
			stmt = PersistanceManager.getInstance().getConnection().prepareStatement(query);
			stmt.setInt(1, gameID);
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
