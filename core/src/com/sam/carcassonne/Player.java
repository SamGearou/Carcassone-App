package com.sam.carcassonne;

public class Player {
	
	enum Order{
		FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH
	}

	private int score;
	private int biddingFrequency;
	private String name;
	private Order order;
	
	public Player(){
		score = 0;
		biddingFrequency = 0;
		name = "";
		order = null;
	}
	
	public int getScore(){
		return score;
	}
	public int getBiddingFrequency(){
		return biddingFrequency;
	}
	public String getName(){
		return name;
	}
	public Order getOrder(){
		return order;
	}
	public void setPoints(int points){
		score+=points;
	}
	public void setBiddingFrequency(int points){
		biddingFrequency += points;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setOrder(Order order){
		this.order = order;
	}
}
