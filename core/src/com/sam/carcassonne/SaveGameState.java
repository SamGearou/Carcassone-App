package com.sam.carcassonne;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
//Class that saves crucial game state 
public class SaveGameState {
static Preferences prefs = Gdx.app.getPreferences("SavedGame");


    //returns the game state preferences
	public static Preferences getPreferences(){
		return prefs;
	}
	
	//adds a preference based on an integer key and string value
	public static void addPreferences(int key, String value){
		prefs.putString(String.valueOf(key), value);
	}
	
	//adds a preference based on a String key and a String value
	public static void addPreferences(String key, String value){
		prefs.putString(key, value);
	}
	}

