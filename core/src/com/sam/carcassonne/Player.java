package com.sam.carcassonne;
// The player class, with various attributes such as their score bidding frequency, and name
public class Player {
	
	enum Order{
		FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH
	}

	private int score;
	private int biddingFrequency;
	private String name;
	private Order order;
	
	//initializes the players score, bidding frequency, name, and order constant
	public Player(){
		score = 0;
		biddingFrequency = 0;
		name = "";
		order = null;
	}
	
	//returns the player's score
	public int getScore(){
		return score;
	}
	
	//returns the players bidding frequency
	public int getBiddingFrequency(){
		return biddingFrequency;
	}
	
	//returns the player's name
	public String getName(){
		return name;
	}
	
	//returns the player's order constant
	public Order getOrder(){
		return order;
	}
	
	//sets/updates the player's score
	public void setPoints(int points){
		score+=points;
	}
	
	//sets/updates the player's bidding frequency 
	public void setBiddingFrequency(int points){
		biddingFrequency += points;
	}
	
	//sets the players name
	public void setName(String name){
		this.name = name;
	}
	
	//sets the players order constant
	public void setOrder(Order order){
		this.order = order;
	}
}
