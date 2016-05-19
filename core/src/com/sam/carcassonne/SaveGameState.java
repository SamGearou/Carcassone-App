package com.sam.carcassonne;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SaveGameState {
static Preferences prefs = Gdx.app.getPreferences("SavedGame");


	public static Preferences getPreferences(){
		return prefs;
	}
	
	public static void addPreferences(int key, String value){
		prefs.putString(String.valueOf(key), value);
	}
	public static void addPreferences(String key, String value){
		prefs.putString(key, value);
	}
	}

