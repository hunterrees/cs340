package server;

import java.net.URLEncoder;

import com.google.gson.Gson;

public class User {
	
	private String name;
	private String password;
	private int playerID;
	
	public User(String name, String password, int playerID) {
		this.name = name;
		this.password = password;
		this.playerID = playerID;
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
	public int getplayerID() {
		return playerID;
	}

	public void setplayerID(int playerID) {
		this.playerID = playerID;
	}

	/**
	 * Generate the user cookie for the client and returns it
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String generateCookie(){
		Gson gson = new Gson();
		return URLEncoder.encode(gson.toJson(this));
	}
}
