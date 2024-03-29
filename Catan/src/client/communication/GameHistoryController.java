package client.communication;

import java.util.*;

import client.base.*;
import client.gameManager.GameManager;
import shared.definitions.*;
import shared.model.Line;
import shared.model.Log;
import shared.model.player.Player;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController {

	public GameHistoryController(IGameHistoryView view) {
		
		super(view);
		
		initFromModel();
	}
	
	@Override
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	
	private void initFromModel() {
		if(GameManager.getInstance().isStartingUp()){
			return;
		}
		Log log = GameManager.getInstance().getModel().getLog();
		ArrayList<Line> lines = log.getLines();
		ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
		for(int i = 0; i < lines.size(); i++){
			CatanColor color = null;
			String source = lines.get(i).getSource();
			ArrayList<Player> players = GameManager.getInstance().getModel().getPlayers();
			for(int j = 0; j < players.size(); j++){
				if(players.get(j).getName().equals(source)){
					color = players.get(j).getPlayerColor();
				}
			}
			LogEntry entry = new LogEntry(color, lines.get(i).getMessage());
			entries.add(entry);
		}
		getView().setEntries(entries);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(GameManager.getInstance().isGameEnd()){
			return;
		}
		if(GameManager.getInstance().isStartingUp()){
			return;
		}
		initFromModel();
	}
	
}

