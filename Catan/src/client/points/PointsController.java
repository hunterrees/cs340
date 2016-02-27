package client.points;

import java.util.ArrayList;
import java.util.Observable;

import client.base.*;
import gameManager.GameManager;
import player.Player;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController {

	private IGameFinishedView finishedView;
	
	/**
	 * PointsController constructor
	 * 
	 * @param view Points view
	 * @param finishedView Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView) {
		
		super(view);
		
		setFinishedView(finishedView);
		
		initFromModel();
	}
	
	public IPointsView getPointsView() {
		
		return (IPointsView)super.getView();
	}
	
	public IGameFinishedView getFinishedView() {
		return finishedView;
	}
	public void setFinishedView(IGameFinishedView finishedView) {
		this.finishedView = finishedView;
	}

	private void initFromModel() {
		if(GameManager.getInstance().isStartingUp()){
			return;
		}
		int index = GameManager.getInstance().getPlayerInfo().getPlayerIndex();
		ArrayList<Player> players= GameManager.getInstance().getModel().getPlayers();
		for(int i = 0; i < players.size(); i++){
			int points = players.get(i).getVictoryPoints();
			if(points >= 10){
				boolean local = false;
				if(index == i){
					local = true;
				}
				getFinishedView().setWinner(players.get(i).getName(), local);
				getFinishedView().showModal();
			}else if(index == i){
				getPointsView().setPoints(points);
			}
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(!GameManager.getInstance().isGameEnd()){
			initFromModel();
		}
	}
	
}

