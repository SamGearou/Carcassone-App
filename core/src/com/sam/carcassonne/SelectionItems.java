package com.sam.carcassonne;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.utils.Array;

public class SelectionItems {
	static Array<String> items = new Array<String>();
	static ArrayList<String> selectedNames = new ArrayList<String>();
	static SelectBox<String> selectbox;

	public static SelectBox<String> getSelectBox(SelectBoxStyle style){
		selectbox = new SelectBox<String>(style);
		
		return selectbox;
	}
	
	public static Array<String> getItemsArray(){
		return items;
	}
	public static ArrayList<String> getSelectedNames(){
		return selectedNames;
	}
	public static void addItems(String item){
		items.add(item);
	}
	public static void checkUniqueness(String text){
		if (!items.contains(text, false)){
			items.add(text);
		}
	}
}
