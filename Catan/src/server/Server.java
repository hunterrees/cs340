package server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpServer;

import persistance.interFaceJar.AbstractFactory;
import persistance.interFaceJar.MockFactory;

import java.net.*;
import java.util.Scanner;

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
			ServerManager.getInstance().resetManager();
			ServerManager.getInstance().testing();
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
	 * @throws ClassNotFoundException 
	 */
	public static void main(String args[]) throws IOException, ClassNotFoundException{
		if(args.length > 0){
			boolean jarFound = false;
			int numCommands = Integer.parseInt(args[1]);
			PersistanceManager.getInstance().setCommandNumber(numCommands);
			String persistance = args[0];
			
			File file = new File("jars.config");
			Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)));
			StringBuilder json = new StringBuilder();
			String jsonText;
			while(scanner.hasNext()){
				json.append(scanner.next());
			}
			scanner.close();
			jsonText = json.toString();
			Gson gson = new Gson();
			JsonObject root = null;
			try{
				root = gson.fromJson(jsonText, JsonObject.class);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			JsonArray jars = root.get("jars").getAsJsonArray();
			for(int i = 0; i < jars.size(); i++){
				JsonObject jar = (JsonObject) jars.get(i);
				String name = jar.get("key").getAsJsonPrimitive().getAsString();
				if(name.equals(persistance)){
					jarFound = true;
					File jarFile = new File("jars/" + jar.get("jar").getAsJsonPrimitive().getAsString());
					if(!jarFile.exists()){
						try {
							throw new Exception("Jar File doesn't exist");
						} catch (Exception e) {
							System.out.println(e.getMessage());
							System.exit(0);
						}
					}
					URL url = jarFile.toURI().toURL();
					URL[] urls = new URL[]{url};
					
					ClassLoader loader = new URLClassLoader(urls);
					Class c = loader.loadClass(jar.get("factory").getAsJsonPrimitive().getAsString());
					try {
						AbstractFactory factory = (AbstractFactory) c.newInstance();
						PersistanceManager.getInstance().setPersistanceType(factory);
						if(args.length == 3 && args[2].equals("wipe")){
							PersistanceManager.getInstance().cleanUp();
						}
						PersistanceManager.getInstance().startUp();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if(!jarFound){
				PersistanceManager.getInstance().setPersistanceType(new MockFactory());
				PersistanceManager.getInstance().startUp();
				System.out.println("Jar File doesn't exist");
			}
		}
		
		new Server(false).run();
	}
}
