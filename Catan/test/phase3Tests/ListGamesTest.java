package phase3Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import server.ServerManager;
import server.commands.games.ListGames;

public class ListGamesTest {

	@Test
	public void test() {
		ServerManager.getInstance().testing();
		System.out.println("Testing list games command");
		ListGames list = new ListGames(-1, "");
		try{
			list.execute();
		}catch(Exception e){
			fail();
		}
	}

}
