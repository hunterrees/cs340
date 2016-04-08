package shared.model;

import java.io.Serializable;
import java.util.Random;

/**
 * 
 * Static class used by model to roll dice on each turn
 *
 */
public class Dice implements Serializable{
	
	public static Dice DICE = new Dice();

	private Dice(){
	};
	/**
	 * Generates two random numbers 1-6 and adds them together
	 * @return number 2-12 
	 */
	public static int rollDice(){
		Random rand = new Random();
		int first = rand.nextInt(6) + 1;
		int second = rand.nextInt(6) + 1;
		return first + second;
	};
}
