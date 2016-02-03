package serverTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import server.ServerProxy;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class ServerProxyTests {
	
	ServerProxy server = new ServerProxy("localhost", 8081);

	@Test
	public void test() {
		server.userLogin("Sam", "sam");
		server.joinGame(0, "orange");
		server.rollNumber(0, 11);
		server.sendChat(0, "test");
		assertTrue(true);
	}

}
