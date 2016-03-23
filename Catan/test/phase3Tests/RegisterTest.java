package phase3Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import client.translators.user.UserTranslator;
import server.commands.user.Register;

public class RegisterTest {

	@Test
	public void test() {
		System.out.println("Testing register command");
		UserTranslator user = new UserTranslator("Sam", "samuel");
		Register register = new Register(-1, user.translate());
		try{
			register.execute();
			fail();
		}catch(Exception e){
		}
		user.setUsername("");
		register = new Register(-1, user.translate());
		try{
			register.execute();
			fail();
		}catch(Exception e){
		}
		user.setUsername("Sam");
		user.setPassword("sam");
		register = new Register(-1, user.translate());
		try{
			register.execute();
			fail();
		}catch(Exception e){
		}
		user.setUsername("Hunter");
		user.setPassword("hunter");
		register = new Register(-1, user.translate());
		try{
			register.execute();
		}catch(Exception e){
			fail();
		}
	}

}
