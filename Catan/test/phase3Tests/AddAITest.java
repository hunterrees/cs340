package phase3Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import server.commands.game.ListAI;

public class AddAITest {

	@Test
	public void test() {
		System.out.println("Testing add ai command");
		ListAI addAI = new ListAI(-1, "");
		try{
			addAI.execute();
		}catch(Exception e){
			fail();
		}
	}

}
