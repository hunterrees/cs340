package persistance.dbJar;

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
		connection = PersistanceManager.getInstance().getConnection();
	}

	@Override
	public void addGame(GameModel modelToAdd) throws DAOException {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		Statement keyStmt = null;
		int id = -1;
		try {
			String query = "insert into Games (ID, Name, Model) values (?, ?, ?)";
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, modelToAdd.getVersion());
			stmt.setString(2, modelToAdd.getTitle());
			stmt.setBlob(3, (Blob) modelToAdd);


			if(stmt.executeUpdate() == 1) {
				keyStmt = connection.createStatement();
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
	public ArrayList<GameModel> getAllGames() {
		// TODO Auto-generated method stub
		ArrayList<GameModel> games = new ArrayList<GameModel>();
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "select * from Games";
			stmt = connection.prepareStatement(query);

			keyRS = stmt.executeQuery();
			//While there are rows
			while (keyRS.next())
			{
				int id = keyRS.getInt("PlayerID");
				String name = keyRS.getString("Name");
				String password = keyRS.getString("Password");

				GameModel game;// = new GameModel(map, bank,)
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
			stmt = connection.prepareStatement(query);
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
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		Statement keyStmt = null;
		int id = -1;
		try {
			String query = "insert into Commands (GameID, CommandList) values (?, ?)";
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, gameID);
			stmt.setBlob(2, (Blob) list);//


			if(stmt.executeUpdate() == 1) {
				keyStmt = connection.createStatement();
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
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		CommandList list = null;
		try {
			String query = "Select * from Field where fieldid = ?";
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, gameID);

			keyRS = stmt.executeQuery();
			boolean hasNext = keyRS.next();
			//If we found a Batch
			if (hasNext)
			{

				Blob blob = keyRS.getBlob("Commandlist");
				list = (CommandList) blob;

			}
		} catch (SQLException e)
		{
			System.out.println("Could not Get the Field. " + e);
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
