package com.sam.carcassonne;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;

//Class that handles players selection of names, and keeps track of each player's score
public class MyTextInputListener implements TextInputListener {
	public static int updatePointTotal = 0;
	public static String checkBoxName = "";
	public static int timerDuration = 0;
	   //Opens a dialog box and allows a player to type in their name
	   @Override
	   public void input (String text) {
		   if(!isANumber(text) && !PlayScreen.playScreenActive && OptionScreen.durationName.equals("")){
			   String pat = "^[a-zA-Z]+$";
		        Pattern r = Pattern.compile(pat);
		    Matcher m = r.matcher(text);
		    if(m.find()){
		    	if(!SelectionItems.getItemsArray().contains(text, false)){
			   SelectionItems.getItemsArray().add(text);
		    	}
			   if(!SelectionItems.getSelectedNames().contains(text)){
			   SelectionItems.getSelectedNames().add(text);
			   }
			   SaveGameState.addPreferences(SaveGameState.getPreferences().get().size(), text);
			   SaveGameState.getPreferences().flush();
			   NameScreen.newName = false;
		    }
		    else{
		    	Gdx.input.getTextInput(this, "Name must only consist of letters", "");
		    }
		   }
		   if(!OptionScreen.durationName.equals("")){
			   String pat = "^[0-9]*$";
		        Pattern r = Pattern.compile(pat);
		    Matcher m = r.matcher(text);
		    if(m.find()){
			   timerDuration = Integer.parseInt(text);
			   OptionScreen.durationName = "";
		   }
		    else{
		    	Gdx.input.getTextInput(this, "Time duration must only consist of valid digits (0-9)", "");
		    	OptionScreen.durationName = " ";
		    }
		   }
		   if(isANumber(text)){
			  updatePointTotal = Integer.parseInt(text);
			  for(int i=0;i<GamePlayers.getGamePlayers().size(); i++){
				  if(GamePlayers.getGamePlayers().get(i).getName().equals(checkBoxName)){
			  updatePoints(GamePlayers.getGamePlayers().get(i));
			  for(int i1=0; i1<GamePlayers.getGamePlayers().size(); i1++){
	        		if(PlayScreen.playerCheckBoxes.get(i1).getStyle().fontColor.toString().equals("ff0000ff")){
	        			for(int j = 0; j<GamePlayers.getGamePlayers().size(); j++){
	        				if(GamePlayers.getGamePlayers().get(j).getName().equals(PlayScreen.playerCheckBoxes.get(i1).getName())){
	        					if(GamePlayers.getGamePlayers().get(j).getName().equals(GamePlayers.getGamePlayers().get(i).getName())){
	        						PlayScreen.elapsedTime = 0;
	        						PlayScreen.start = false;
	        					}
	        				}
	        			}
	        		}
			  }
			  break;
		   }
			  }
		   }
		   if(isANumber(text) && !PlayScreen.playScreenActive && OptionScreen.durationName.equals("") && NameScreen.newName){
			   Gdx.input.getTextInput(this, "Name must only consist of letters", "");
		   }
	   }

	   //called when the user deletes the dialog box
	   @Override
	   public void canceled () {
		   
	   }
	   
	   //determines of the input that the user gives is a number or not,
	   //returning true if it is, and false otherwise
	   public boolean isANumber(String input){
		try{
			Integer.parseInt(input);
		}
		catch(NumberFormatException e){
			return false;
		}
		return true;
		}
	   
	   //updates the players' score in the game
	  public static void updatePoints(Player player){
		  player.setPoints(updatePointTotal);
	  }
	}
