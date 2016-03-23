package phase3Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import client.translators.user.UserTranslator;
import server.commands.user.Login;

public class LoginTest {

	@Test
	public void test() {
		System.out.println("Testing login command");
		UserTranslator user = new UserTranslator("Sam", "samuel");
		Login login = new Login(-1, user.translate());
		try{
			login.execute();
			fail();
		}catch(Exception e){
		}
		user.setUsername("");
		login = new Login(-1, user.translate());
		try{
			login.execute();
			fail();
		}catch(Exception e){
		}
		user.setUsername("Sam");
		user.setPassword("sam");
		login = new Login(-1, user.translate());
		try{
			login.execute();
		}catch(Exception e){
			fail();
		}
	}

}
