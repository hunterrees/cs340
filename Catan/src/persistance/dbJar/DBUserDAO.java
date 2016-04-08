package persistance.dbJar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import persistance.interFaceJar.DAOException;
import persistance.interFaceJar.UserDAO;
import server.PersistanceManager;
import server.User;

public class DBUserDAO implements UserDAO {

	Connection connection;
	public DBUserDAO(){
		try{
			connection = PersistanceManager.getInstance().getConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<User> getUsers() {
		// TODO Auto-generated method stub
		ArrayList<User> users = new ArrayList<User>();
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		try {
			String query = "select * from Users";
			stmt = PersistanceManager.getInstance().getConnection().prepareStatement(query);

			keyRS = stmt.executeQuery();
			//While there are rows
			while (keyRS.next())
			{
				int id = keyRS.getInt("PlayerID");
				String name = keyRS.getString("Name");
				String password = keyRS.getString("Password");

				User user = new User(name, password, id);
				users.add(user);
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

		return users;
	}

	@Override
	public void addUser(User userToAdd, int gameID) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		PreparedStatement stmt = null;
		ResultSet keyRS = null;
		Statement keyStmt = null;
		int id = -1;
		try {
			String query = "insert into Users (PlayerID, Name, Password) values (?, ?, ?)";
			stmt = PersistanceManager.getInstance().getConnection().prepareStatement(query);
			stmt.setInt(1, userToAdd.getplayerID());
			stmt.setString(2, userToAdd.getName());
			stmt.setString(3, userToAdd.getPassword());


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
			stmt.close();
			keyRS.close();
			//db.safeClose(keyStmt);
		}
	}

}
