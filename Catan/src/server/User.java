package server;

public class User {
	
	private String name;
	private String password;
	private int ID;
	
	public User(String name, String password, int ID) {
		this.name = name;
		this.password = password;
		this.ID = ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * Generate the user cookie for the client and returns it
	 * @return
	 */
	public String generateCookie(){
		return null;
	}
}
