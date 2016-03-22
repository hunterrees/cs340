package server;

import com.sun.net.httpserver.HttpServer;
import java.net.*;
import java.io.*;

import server.facades.*;
import server.handlers.GameHandler;
import server.handlers.GamesHandler;
import server.handlers.MovesHandler;
import server.handlers.UserHandler;

public class Server {
	
	/**
	 * server itself
	 */
	private HttpServer server;
	/**
	 * which port the server is listening on
	 */
	private static int PORT = 8081;
	/**
	 * the max number of pending connections
	 */
	private static int MAX_WAITING_CONNECTIONS = 10;
	/**
	 * used for /user/* commands
	 */
	private UserHandler userHandler;
	/**
	 * used for /games/* commands
	 */
	private GamesHandler gamesHandler;
	/**
	 * used for /game/* commands
	 */
	private GameHandler gameHandler;
	/**
	 * used for /moves/* commands
	 */
	private MovesHandler movesHandler;

	public Server(boolean testing){
		if(testing){
			userHandler = new UserHandler(new MockUserFacade());
			gamesHandler = new GamesHandler(new MockGamesFacade());
			gameHandler = new GameHandler(new MockGameFacade());
			movesHandler = new MovesHandler(new MockMovesFacade());
		}else{
			userHandler = new UserHandler(new UserFacade());
			gamesHandler = new GamesHandler(new GamesFacade());
			gameHandler = new GameHandler(new GameFacade());
			movesHandler = new MovesHandler(new MovesFacade());
		}
	}

	/**
	 * creates the server and creates contexts with handlers
	 */
	public void run(){
		try {
			server = HttpServer.create(new InetSocketAddress(PORT), MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) {		
			return;
		}
		
		server.setExecutor(null);
		
		server.createContext("/user", userHandler);
		server.createContext("/games", gamesHandler);
		server.createContext("/game", gameHandler);
		server.createContext("/moves", movesHandler);
		server.createContext("/docs/api/data", new Handlers.JSONAppender("")); 
		server.createContext("/docs/api/view", new Handlers.BasicFile(""));
		
		server.start();
	}
	/**
	 * Starts the server running
	 * @param args command line argument for port to run on
	 */
	public static void main(String args[]){
		if(args.length > 0){
			PORT = Integer.parseInt(args[0]);
		}
		new Server(false).run();
	}
}
