package model;

import java.util.Random;

/**
 * 
 * Static class used by model to roll dice on each turn
 *
 */
public class Dice {
	
	public static Dice DICE = new Dice();

	private Dice(){
	};
	/**
	 * Generates two random numbers 1-6 and adds them together
	 * @return number 2-12 
	 */
	public static int rollDice(){
		int number = 7;
		while(number == 7){
			Random rand = new Random();
			int first = rand.nextInt(6) + 1;
			int second = rand.nextInt(6) + 1;
			number = first + second;
		}
		return number;
	};
}
