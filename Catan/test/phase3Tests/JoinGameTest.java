package phase3Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import client.translators.games.GamesJoinTranslator;
import server.User;
import server.commands.games.JoinGame;

public class JoinGameTest {

	@Test
	public void test() {
		System.out.println("Testing join game command");
		GamesJoinTranslator joining = new GamesJoinTranslator(3, "orange");
		JoinGame join = new JoinGame(-1, joining.translate());
		User user = new User("Sam", "sam", 0);
		join.setUser(user);
		try{
			join.execute();
			fail();
		}catch(Exception e){
		}
		joining.setId(0);
		joining.setColor("notCorrect");
		join = new JoinGame(-1, joining.translate());
		join.setUser(user);
		try{
			join.execute();
			fail();
		}catch(Exception e){
		}
		joining.setColor("orange");
		join = new JoinGame(-1, joining.translate());
		join.setUser(user);
		try{
			join.execute();
		}catch(Exception e){
			fail();
		}
		join.setUser(new User("Hunter", "hunter", 4));
		try{
			join.execute();
			fail();
		}catch(Exception e){
		}
	}

}
