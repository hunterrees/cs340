package server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpServer;
import java.net.*;

import org.apache.commons.io.FileUtils;

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
	 * @throws IOException 
	 */
	public static void main(String args[]) throws IOException{
		if(args.length > 0){
			int numCommands = Integer.parseInt(args[1]);
			String persistance = args[0];
			PersistanceManager.getInstance().setCommandNumber(numCommands);
			if(args.length == 3 && args[2].equals("wipe")){
				PersistanceManager.getInstance().cleanUp();
			}
		}
		new Server(false).run();
	}
}
