package server.commands.games;

import com.google.gson.Gson;

import server.GameInfo;
import server.PlayerInfo;
import server.ServerManager;
import server.commands.Command;
import shared.model.GameModel;

public class ListGames extends Command{

	public ListGames(int gameID, String json) {
		super(gameID, json);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Preconditions: None
	 * Postconditions: If the operation succeeds:
	 *  1. The server returns an HTTP 200 success response.
	 *  2. The body contains a JSON array containing a list of objects that contain information about the server's games.
	 * If the operation fails:
	 *  1. The server returns an HTTP 400 error response, and the body contains an error message.
	 */
	@Override
	public Object execute() {
		Gson gson = new Gson();
		StringBuilder result = new StringBuilder();
		result.append("[");
		GameInfo[] gameInfos = new GameInfo[ServerManager.getInstance().getGames().size()];
		for(int i = 0; i < ServerManager.getInstance().getGames().size(); i++){
			GameModel game = ServerManager.getInstance().getGame(i);
			GameInfo gameInfo = new GameInfo();
			for(int j = 0; j < game.getPlayers().size(); j++){
				PlayerInfo player = new PlayerInfo();
				player.setColor(game.getPlayers().get(j).getPlayerColor());
				player.setId(game.getPlayers().get(j).getPlayerID());
				player.setName(game.getPlayers().get(j).getName());
				gameInfo.addPlayer(player);
			}
			gameInfo.setId(i);
			gameInfo.setTitle(game.getTitle());
			gameInfos[i] = gameInfo;
			if(game.getPlayers().size() < 4){
				String input = "";
				if(i != 0){
					result.append(",");
				}
				result.append("{");
				input = "\"title\": \"" + gameInfos[i].getTitle() + "\",";
				result.append(input);
				input = "\"id\": " + i + ",";
				result.append(input);
				input = "\"players\":";
				result.append(input);
				result.append("[");
				for(int j = 0; j < game.getPlayers().size(); j++){
					result.append(gson.toJson(gameInfo.getPlayers()[j]));
					result.append(",");
				}
				for(int j = game.getPlayers().size(); j < 3; j++){
					result.append("{},");
				}
				result.append("{} ] }");
			}else{
				result.append(gson.toJson(gameInfo));
			}
		}
		result.append("]");
		return result.toString();
	}

}
