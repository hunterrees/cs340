package client.translators.user;

import client.translators.GenericTranslator;

public class UserTranslator 
	extends GenericTranslator {

	private String username;
	private String password;
	
	public UserTranslator(){};
	
	public UserTranslator(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
