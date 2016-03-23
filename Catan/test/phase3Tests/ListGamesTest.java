package phase3Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import server.commands.games.ListGames;

public class ListGamesTest {

	@Test
	public void test() {
		System.out.println("Testing list games command");
		ListGames list = new ListGames(-1, "");
		try{
			list.execute();
		}catch(Exception e){
			fail();
		}
	}

}
