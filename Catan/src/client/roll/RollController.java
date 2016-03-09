package client.roll;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import client.base.*;
import client.gameManager.GameManager;
import shared.definitions.GameState;
import shared.model.Dice;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController {

	private IRollResultView resultView;
	private Timer timer;

	/**
	 * RollController constructor
	 * 
	 * @param view Roll view
	 * @param resultView Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView) {

		super(view);
		
		setResultView(resultView);
	}
	
	public IRollResultView getResultView() {
		return resultView;
	}
	public void setResultView(IRollResultView resultView) {
		this.resultView = resultView;
	}

	public IRollView getRollView() {
		return (IRollView)getView();
	}
	
	@Override
	public void rollDice() {
		timer.cancel();
		if(getRollView().isModalShowing()){
			getRollView().closeModal();
		}
		int number = Dice.rollDice();
		getResultView().setRollValue(number);
		try{
			GameManager.getInstance().rollNumber(GameManager.getInstance().getPlayerInfo().getPlayerIndex(), number);
			getResultView().showModal();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(GameManager.getInstance().isGameEnd() || GameManager.getInstance().isStartingUp()){
			return;
		}
		if(getRollView().isModalShowing() || getResultView().isModalShowing()){
			return;
		}
		if(GameManager.getInstance().getGameState() == GameState.rolling && GameManager.getInstance().isMyTurn()){
			
			timer = new Timer();
			timer.schedule(new TimerTask(){

				@Override
				public void run() {
					getRollView().setMessage("Rolling automatically in...4 seconds");
					getRollView().showModal();
				}
				
			}, 1000);
			
			timer.schedule(new TimerTask(){

				@Override
				public void run() {
					getRollView().setMessage("Rolling automatically in...3 seconds");
				}
				
			}, 2000);
			
			timer.schedule(new TimerTask(){

				@Override
				public void run() {
					getRollView().setMessage("Rolling automatically in...2 seconds");
				}
				
			}, 3000);
			
			timer.schedule(new TimerTask(){

				@Override
				public void run() {
					getRollView().setMessage("Rolling automatically in...1 seconds");
				}
				
			}, 4000);
			
			timer.schedule(new TimerTask(){

				@Override
				public void run() {
					getRollView().setMessage("Rolling automatically...");
					rollDice();
				}
				
			}, 5000);
		}
	}

}

