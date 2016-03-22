package server.commands.games;

import server.ServerTranslator;
import server.commands.Command;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.locations.*;
import shared.model.*;
import shared.model.map.*;

import java.util.*;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import client.server.ServerException;
import shared.model.player.Player;
import shared.model.trade.TradeOffer;

public class CreateGame extends Command{

	shared.model.map.Map map = new shared.model.map.Map();
	private boolean randomTiles;
	private boolean randomNumbers;
	private boolean randomPorts;
	private String name;
	private ArrayList<HexType> hexList;
	private ArrayList<Integer> numberList;
	private ArrayList<PortType> portList;
	Bank bank = new Bank();
	ArrayList<Player> players;
	HexLocation robberLoc;
	

	HashMap<VertexLocation, Port> ports = new HashMap<>();
	HashMap<EdgeLocation, PortType> edgePorts = new HashMap<>();
	public CreateGame(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
		translate(json);
		randomize();
	}

	private void createModel() {

	/*	model = new GameModel(map, bank, players, robberLoc, null,
				new TurnTracker(), new Log(), new Chat(), -1);*/
	}

	public void randomize(){
		hexList = getHexTypeList();
		numberList = generateNumberPool();
		portList = generatePortList();
	}
	
	public void translate(String json){
		Gson gson = new Gson();
		JsonObject root;
		try{
			root = gson.fromJson(json, JsonObject.class);
		}catch(Exception e){
			return;
		}

		JsonPrimitive tilesPrim = root.getAsJsonPrimitive("randomTiles");
		randomTiles = tilesPrim.getAsBoolean();
		
		JsonPrimitive numPrim = root.getAsJsonPrimitive("randomNumbers");
		randomNumbers = numPrim.getAsBoolean();
		
		JsonPrimitive portsPrim = root.getAsJsonPrimitive("randomPorts");
		randomPorts = portsPrim.getAsBoolean();
		
		JsonPrimitive namePrim = root.getAsJsonPrimitive("name");
		name = namePrim.getAsString();
		

		
	}

	public ArrayList<HexType> getHexTypeList() {
		ArrayList<HexType> list = new ArrayList<>();

		list.add(HexType.WOOD);
		list.add(HexType.WOOD);
		list.add(HexType.WOOD);

		list.add(HexType.BRICK);
		list.add(HexType.BRICK);

		list.add(HexType.SHEEP);
		list.add(HexType.SHEEP);
		list.add(HexType.SHEEP);


		list.add(HexType.WHEAT);
		list.add(HexType.WHEAT);
		list.add(HexType.WHEAT);


		list.add(HexType.ORE);
		list.add(HexType.ORE);

		list.add(HexType.DESERT);


		Collections.shuffle(list);
		return list;
	}

	public void randomizeHexes() {
		

		int counter = 0;
		for (java.util.Map.Entry<HexLocation, TerrainHex> entry : map.getHexes().entrySet()) {
			if (entry.getValue().getType() != HexType.WATER) {
				entry.getValue().setType(hexList.get(counter));
				counter++;
			}
		}

	}

	public ArrayList<Integer> generateNumberPool() {
		ArrayList<Integer> list = new ArrayList<>();

		// Add the numbers in
		list.add(2);

		list.add(3);
		list.add(3);

		list.add(4);
		list.add(4);

		list.add(5);
		list.add(5);

		list.add(6);
		list.add(6);

		list.add(8);
		list.add(8);

		list.add(9);
		list.add(9);

		list.add(10);
		list.add(10);

		list.add(11);
		list.add(11);

		list.add(12);

		// Shuffle the numbers
		Collections.shuffle(list);

		return list;
	}


	public void randomizeNumbers() {

		
		int counter = 0;


		for(java.util.Map.Entry<HexLocation, TerrainHex> entry: map.getHexes().entrySet()){

			if(entry.getValue().getType() != HexType.WATER &&
					entry.getValue().getType() != HexType.DESERT) {

				entry.getValue().setNumber(numberList.get(counter));
				counter++;
			}
			if(entry.getValue().getType() == HexType.DESERT) {
				robberLoc = entry.getKey();
			}
		}

		if(counter != numberList.size()) {
			System.out.println("Error! Didn't use correct number of numbers! Location: GetModel.generateRandomNumbers()");
		}
	}

	public ArrayList<PortType> generatePortList() {
		ArrayList<PortType> list = new ArrayList<>();

		list.add(PortType.THREE);
		list.add(PortType.THREE);
		list.add(PortType.THREE);

		list.add(PortType.THREE);

		list.add(PortType.WOOD);
		list.add(PortType.BRICK);
		list.add(PortType.SHEEP);
		list.add(PortType.WHEAT);
		list.add(PortType.ORE);

		Collections.shuffle(list);

		return list;

	}


	public void randomizePorts() {
		


		// Port 1
		VertexLocation loc1 = new VertexLocation(new HexLocation(0,-2), VertexDirection.NorthWest).getNormalizedLocation();
		ports.put(loc1, new Port(portList.get(0)));

		VertexLocation loc2 = new VertexLocation(new HexLocation(0,-2), VertexDirection.NorthEast).getNormalizedLocation();
		ports.put(loc2, new Port(portList.get(0)));

		edgePorts.put(new EdgeLocation(new HexLocation(0,-2), EdgeDirection.North).getNormalizedLocation(),portList.get(0));

		// Port 2
		VertexLocation loc3 = new VertexLocation(new HexLocation(2,-1), VertexDirection.NorthEast).getNormalizedLocation();
		ports.put(loc3, new Port(portList.get(1)));

		VertexLocation loc4 = new VertexLocation(new HexLocation(2,-1), VertexDirection.East).getNormalizedLocation();
		ports.put(loc4, new Port(portList.get(1)));

		edgePorts.put(new EdgeLocation(new HexLocation(2,-1),EdgeDirection.NorthEast).getNormalizedLocation(),portList.get(1));


		// Port 3
		VertexLocation loc5 = new VertexLocation(new HexLocation(2,0), VertexDirection.East).getNormalizedLocation();
		ports.put(loc5, new Port(portList.get(2)));

		VertexLocation loc6 = new VertexLocation(new HexLocation(2,0), VertexDirection.SouthEast).getNormalizedLocation();
		ports.put(loc6, new Port(portList.get(2)));

		edgePorts.put(new EdgeLocation(new HexLocation(2,0),EdgeDirection.SouthEast),portList.get(2));


		// Port 4
		VertexLocation loc7 = new VertexLocation(new HexLocation(-2,2), VertexDirection.West).getNormalizedLocation();
		ports.put(loc7, new Port(portList.get(3)));

		VertexLocation loc8 = new VertexLocation(new HexLocation(-2,2), VertexDirection.SouthWest).getNormalizedLocation();
		ports.put(loc8, new Port(portList.get(3)));

		edgePorts.put(new EdgeLocation(new HexLocation(-2,2),EdgeDirection.SouthWest),portList.get(3));


		// Port 5
		VertexLocation loc9 = new VertexLocation(new HexLocation(-1,2), VertexDirection.SouthWest).getNormalizedLocation();
		ports.put(loc9, new Port(portList.get(4)));

		VertexLocation loc10 = new VertexLocation(new HexLocation(-1,2), VertexDirection.SouthEast).getNormalizedLocation();
		ports.put(loc10, new Port(portList.get(4)));

		edgePorts.put(new EdgeLocation(new HexLocation(-1,2),EdgeDirection.South).getNormalizedLocation(),portList.get(4));


		// Port 6
		VertexLocation loc11 = new VertexLocation(new HexLocation(1,1), VertexDirection.SouthWest).getNormalizedLocation();
		ports.put(loc11, new Port(portList.get(5)));

		VertexLocation loc12 = new VertexLocation(new HexLocation(1,1), VertexDirection.SouthEast).getNormalizedLocation();
		ports.put(loc12, new Port(portList.get(5)));

		edgePorts.put(new EdgeLocation(new HexLocation(1,1),EdgeDirection.South),portList.get(5));


		// Port 7
		VertexLocation loc13 = new VertexLocation(new HexLocation(1,-2), VertexDirection.NorthEast).getNormalizedLocation();
		ports.put(loc13, new Port(portList.get(6)));

		VertexLocation loc14 = new VertexLocation(new HexLocation(1,-2), VertexDirection.NorthWest).getNormalizedLocation();
		ports.put(loc14, new Port(portList.get(6)));

		edgePorts.put(new EdgeLocation(new HexLocation(1,-2),EdgeDirection.NorthEast),portList.get(6));


		// Port 8
		VertexLocation loc15 = new VertexLocation(new HexLocation(-2,1), VertexDirection.NorthWest).getNormalizedLocation();
		ports.put(loc15, new Port(portList.get(7)));

		VertexLocation loc16 = new VertexLocation(new HexLocation(-2,1), VertexDirection.West).getNormalizedLocation();
		ports.put(loc16, new Port(portList.get(7)));

		edgePorts.put(new EdgeLocation(new HexLocation(-2,1),EdgeDirection.NorthWest),portList.get(7));


		// Port 9
		VertexLocation loc17 = new VertexLocation(new HexLocation(-1,-1), VertexDirection.NorthWest).getNormalizedLocation();
		ports.put(loc17, new Port(portList.get(8)));

		VertexLocation loc18 = new VertexLocation(new HexLocation(-1,-1), VertexDirection.West).getNormalizedLocation();
		ports.put(loc18, new Port(portList.get(8)));

		edgePorts.put(new EdgeLocation(new HexLocation(-1,-1),EdgeDirection.NorthWest),portList.get(8));




	}






	/**
	 * Preconditions: name is not null and randomTiles, randomNumbers, and randomPorts contain valid boolean values.
	 * PostConditions: If the operation succeeds,
	 *	1. A new game with the specified properties has been created
	 *	2. The server returns an HTTP 200 success response.
	 *	3. The body contains a JSON object describing the newly created game
	 * If the operation fails,
	 *	1. The server returns an HTTP 400 error response, and the body contains an error
	 *	   message.
	 * @throws ServerException 
	 */
	@Override
	public Object execute() throws ServerException {
		// TODO Auto-generated method stub
		if(randomNumbers){
			randomizeNumbers();
		}
		if(randomTiles){
			randomizeHexes();
		}
		if(randomPorts){
			randomizePorts();
		}

		createModel();

		ServerTranslator temp = new ServerTranslator(model);
		return temp.translate();
	}

}
