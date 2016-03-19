package phase3Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import server.commands.games.ListGames;

public class ListGamesTest {

	@Test
	public void test() {
		ListGames list = new ListGames(-1, "");
		System.out.println(list.execute());
	}

}
