package client.roll;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import client.base.*;
import gameManager.GameManager;
import model.Dice;
import shared.definitions.GameState;


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
		getResultView().showModal();
		try{
			GameManager.getInstance().rollNumber(GameManager.getInstance().getPlayerInfo().getId(), number);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(getRollView().isModalShowing() || getResultView().isModalShowing()){
			System.out.println("rolling");
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

