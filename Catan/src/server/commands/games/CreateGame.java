package server.commands.games;

import server.commands.Command;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.GameModel;
import shared.model.map.Port;
import shared.model.map.TerrainHex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class CreateGame extends Command{

	shared.model.map.Map map;
	public CreateGame(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
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
		ArrayList<HexType> list = getHexTypeList();

		int counter = 0;
		for (java.util.Map.Entry<HexLocation, TerrainHex> entry : map.getHexes().entrySet()) {
			if (entry.getValue().getType() != HexType.WATER) {
				entry.getValue().setType(list.get(counter));
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

		ArrayList<Integer> numbers = generateNumberPool();
		int counter = 0;


		for(java.util.Map.Entry<HexLocation, TerrainHex> entry: map.getHexes().entrySet()){

			if(entry.getValue().getType() != HexType.WATER &&
					entry.getValue().getType() != HexType.DESERT) {

				entry.getValue().setNumber(numbers.get(counter));
				counter++;
			}
		}

		if(counter != numbers.size()) {
			System.out.println("Error! Didn't use correct number of numbers! Location: GetModel.generateRandomNumbers()");
		}
	}

	public ArrayList<PortType> generatePortList() {
		ArrayList<PortType> list = new ArrayList<>();

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
		ArrayList<PortType> list = generatePortList();

		for(Map.Entry<VertexLocation, Port> entry : map.getPorts().entrySet()) {

		}
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
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
