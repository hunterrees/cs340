package phase3Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import server.ServerManager;
import server.commands.game.ListAI;

public class ListAITest {

	@Test
	public void test() {
		ServerManager.getInstance().testing();
		System.out.println("Testing list ai command");
		ListAI listAI = new ListAI(-1, "");
		try{
			listAI.execute();
		}catch(Exception e){
			fail();
		}
	}

}
