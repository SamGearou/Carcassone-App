package com.sam.carcassonne;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.utils.Array;
//Class that determines which names were selected when the players select
//their name from the drop down list
public class SelectionItems {
	static Array<String> items = new Array<String>();
	static ArrayList<String> selectedNames = new ArrayList<String>();
	static SelectBox<String> selectbox;

	//returns the select boxe's style
	public static SelectBox<String> getSelectBox(SelectBoxStyle style){
		selectbox = new SelectBox<String>(style);
		
		return selectbox;
	}
	
	//returns the item arraylist
	public static Array<String> getItemsArray(){
		return items;
	}
	
	//returns the arraylist of selected players from the drop down list
	public static ArrayList<String> getSelectedNames(){
		return selectedNames;
	}
	
	//adds String elements to the item arraylist
	public static void addItems(String item){
		items.add(item);
	}
	
	//adds an item to the arraylist only if it is not already in the arraylist
	public static void checkUniqueness(String text){
		if (!items.contains(text, false)){
			items.add(text);
		}
	}
}
