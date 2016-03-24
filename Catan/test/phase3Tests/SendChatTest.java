package phase3Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import client.server.ServerException;
import client.translators.moves.MovesSendChatTranslator;
import server.ServerManager;
import server.commands.moves.SendChat;

public class SendChatTest {

	@Test
	public void test() {
		System.out.println("Testing SendChat command");
		MovesSendChatTranslator send = new MovesSendChatTranslator(0, "hello");
		SendChat chat = new SendChat(0, send.translate());
		try {
			chat.execute();
		} catch (ServerException e) {
			fail();
		}
		assertTrue(ServerManager.getInstance().getGame(0).getChat().getLines().size() == 1);
		assertTrue(ServerManager.getInstance().getGame(0).getChat().getLines().get(0).getMessage().equals("hello"));
	}

}
