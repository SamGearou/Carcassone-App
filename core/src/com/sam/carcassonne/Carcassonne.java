package com.sam.carcassonne;

import com.badlogic.gdx.Game;
//The main game class
public class Carcassonne extends Game {
	
	//sets the screen to the main menu screen
	@Override
	public void create() {		
		setScreen(new MainMenuScreen(this));
	}

	//disposes of the screen
	@Override
	public void dispose() {
		super.dispose();	
		getScreen().dispose();
	}

	//calls the render method of the Game superclass
	@Override
	public void render() {		
		super.render();

	}

	//resizes the screen
	@Override
	public void resize(int width, int height) {
		super.resize(width,height);
	}

	//pauses
	@Override
	public void pause() {
		super.pause();
	}

	//resumes
	@Override
	public void resume() {
		super.resume();
	}
}
