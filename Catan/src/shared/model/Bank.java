package shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import shared.*;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
// test
public class Bank implements Serializable
{
	/**
	 * Deck of development cards
	 */
	private ArrayList<DevelopmentCard> bankDevelopmentCards;
	/**
	 * List of all available resource cards
	 */
	private ArrayList<ResourceCard> bankResourceCards;
	
	public Bank() {
		bankResourceCards = new ArrayList<ResourceCard>();
		bankDevelopmentCards = new ArrayList<DevelopmentCard>();
		generateStartingResources();
		generateStartingDevelopmentCards();
	}
	
	public Bank(ArrayList<ResourceCard> resources, ArrayList<DevelopmentCard> devCards){
		bankResourceCards = resources;
		Collections.shuffle(devCards);
		bankDevelopmentCards = devCards;
	}
	/**
	 * Populate deck of development cards and maximum number of resource cards
	 */
	public void generateStartingResources() {
		// Error checking
		int size = bankResourceCards.size();
		if(size != 0) {
			System.out.println("Error! There are already resources in the bank!");
		}

		// Generate the starting resources
		for(int i = 0; i < 19; i++) {
			bankResourceCards.add(new ResourceCard(ResourceType.WOOD));
			bankResourceCards.add(new ResourceCard(ResourceType.BRICK));
			bankResourceCards.add(new ResourceCard(ResourceType.SHEEP));
			bankResourceCards.add(new ResourceCard(ResourceType.WHEAT));
			bankResourceCards.add(new ResourceCard(ResourceType.ORE));

		}
	}
	
	//Needs to generate all the development cards
	public void generateStartingDevelopmentCards()
	{
		for(int i = 0; i < 19; i++){
			bankDevelopmentCards.add(new DevelopmentCard(DevCardType.SOLDIER));
		}
		for(int i = 0; i < 5; i++){
			bankDevelopmentCards.add(new DevelopmentCard(DevCardType.MONUMENT));
		}for(int i = 0; i < 2; i++){
			bankDevelopmentCards.add(new DevelopmentCard(DevCardType.MONOPOLY));
			bankDevelopmentCards.add(new DevelopmentCard(DevCardType.YEAR_OF_PLENTY));
			bankDevelopmentCards.add(new DevelopmentCard(DevCardType.ROAD_BUILD));
		}
		Collections.shuffle(bankDevelopmentCards);
	}
	/**
	 * add amount of type resources to the bank
	 * @param amount
	 * @param type
	 */
	public void addResources(int amount, ResourceType type) {
		// Error checking
		if(amount < 0) {
			System.out.println("Error! Invalid amount!");
		}

		// Add more resources to the bank
		for(int i = 0; i < amount; i++) {
			bankResourceCards.add(new ResourceCard(type));
		}
	}
	/**
	 * remove amount resources of type from the bank
	 * @param amount
	 * @param type
	 */
	public void removeResources(int amount, ResourceType type) {
		// Error checking
		int numLeft = numResourceRemaining(type);
		if(numLeft < amount) {
			System.out.println("Error! There are not enough resources to remove!");
		}

		// Remove the resources
		ArrayList<ResourceCard> theNewOne = new ArrayList<ResourceCard>();
		int count = 0;
		for(ResourceCard rc : bankResourceCards) {
			if (rc.getType() != type || count >= amount) {
				theNewOne.add(new ResourceCard(rc.getType()));
			} else {
				count++;
			}
		}
		bankResourceCards = theNewOne;
	}
	/**
	 * Remove returns number of resources of type remaining in the bank
	 * @param type
	 * @return
	 */
	
	public int numResourceRemaining(ResourceType type) {
		int num = 0;
		for(ResourceCard rc : bankResourceCards) {
			if(rc.getType() == type) {
				num++;
			}
		}
		return num;
	}
	
	public int numDevCardsRemaining(DevCardType type){
		int num = 0;
		for(DevelopmentCard dc : bankDevelopmentCards){
			if(dc.getType() == type){
				num++;
			}
		}
		return num;
	}
	/**
	 * returns development cards
	 * @return
	 */
	public ArrayList<DevelopmentCard> getDevelopmentCards() {
		return bankDevelopmentCards;
	}
	/**
	 * returns resource cards
	 * @return
	 */
	public ArrayList<ResourceCard> getResourceCards() {
		return bankResourceCards;
	}
	public void setResources(ArrayList<ResourceCard> resourceDeck)
	{
		bankResourceCards = resourceDeck;
	}

	public void setBankDevelopmentCards(ArrayList<DevelopmentCard> bankDevelopmentCards) {
		this.bankDevelopmentCards = bankDevelopmentCards;
	}
}
