package com.sam.carcassonne;

import java.util.ArrayList;
import java.util.Collections;

public class GamePlayers {
	public static String winner = "";
	static ArrayList<Player> gameplayers = new ArrayList<Player>();
	static ArrayList<Integer> finalScores = new ArrayList<Integer>(); 
	
	public static ArrayList<Player> getGamePlayers(){
		return gameplayers;
	}
	
	public static void addPlayers(Player player){
		gameplayers.add(player);
	}
	
	public static Player getPlayer(String name){
		for(int i=0; i<gameplayers.size(); i++){
			if(gameplayers.get(i).getName().equals(name)){
				return gameplayers.get(i);
			}
		}
		return null;
	}
	
	public static void calculateWinner(){
		for(int i =0; i<gameplayers.size(); i++){
			finalScores.add(gameplayers.get(i).getScore());
		}
		Collections.sort(finalScores);
		for(int i= 0; i<gameplayers.size(); i++){
			if(gameplayers.get(i).getScore() == finalScores.get(finalScores.size()-1)){
				if(i == 0){
				winner+= gameplayers.get(i).getName();
			}
				else if(i != 0 && winner.equals("")){
					winner+= gameplayers.get(i).getName();
				}
				else if(i != 0 && !winner.equals("")){
					winner+= " and " + gameplayers.get(i).getName();
				}
			}
		}
		}
	}

