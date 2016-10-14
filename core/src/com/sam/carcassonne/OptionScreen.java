package com.sam.carcassonne;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

//Allows the players to have an optional timer with a specific duration for each player's turn
public class OptionScreen implements Screen {

	Stage stage;
	Skin skin;
	Carcassonne game;
	TextureAtlas atlas;
	BitmapFont font;
	Pixmap pixmap;	
	CheckBoxStyle CheckBoxStyle2;
	CheckBoxStyle CheckBoxStyle3;
	CheckBox timer;
	public static boolean timerSetting = false;
	
	public static String durationName = "";
	
	public static boolean durationClicked = false;
	
	//passes in an instance of the game to allow the ability to switch screens
	public OptionScreen(Carcassonne game){
		this.game = game;
	}
	//draws the actors and updates the stage
		public void render(float delta) {
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			stage.act(Gdx.graphics.getDeltaTime());
	        stage.draw();

			//stage.setDebugAll(true); // This is optional, but enables debug lines for tables.
			
		}

		//resizes 
		@Override
		public void resize(int width, int height) {
			stage.getViewport().update(width, height, true);
		}

		//initializes all of the actors
		@Override
		public void show() {
			stage = new Stage();
			Gdx.input.setInputProcessor(stage);
		    font = new BitmapFont(Gdx.files.internal("gamefonts.fnt"), false);
			Table table = new Table();
			table.setFillParent(true);
			stage.addActor(table);
	        skin = new Skin();

	        // Generate a 1x1 white texture and store it in the skin named "white".
	        pixmap = new Pixmap(1, 1, Format.RGBA8888);
	        pixmap.setColor(Color.WHITE);
	        pixmap.fill();
	        skin.add("white", new Texture(pixmap));

	        // Store the default libgdx font under the name "default".
	        skin.add("default", new BitmapFont());
	        
	        Image background = new Image(new Texture(Gdx.files.internal("epic3.png")));
	        stage.addActor(background);
	        background.scaleBy(1f);
	        
	        CheckBoxStyle CheckBoxStyle = new CheckBoxStyle();
	        CheckBoxStyle.font = font;
	        CheckBoxStyle.checkedFontColor = Color.GREEN;
	        CheckBoxStyle.fontColor = Color.RED;
	        CheckBoxStyle.font.setScale(4f);
	        skin.add("default", CheckBoxStyle);
	        
	        CheckBoxStyle2 = new CheckBoxStyle();
	        CheckBoxStyle2.font = font;
	        CheckBoxStyle2.fontColor = Color.RED;
	        
	        CheckBoxStyle3 = new CheckBoxStyle();
	        CheckBoxStyle3.font = font;
	        CheckBoxStyle3.fontColor = Color.GREEN;
	        
	        timer = new CheckBox("Enable a timer for each round", skin);
	        CheckBox duration = new CheckBox("Set the timer duration for each round", skin);
	        CheckBox back = new CheckBox("Back", skin);
	        stage.addActor(timer);
	        stage.addActor(duration);
	        stage.addActor(back);
	        back.setPosition(8, 1280);
	        timer.setPosition(110, 800);
	        duration.setPosition(60, 400);
	        duration.setName("timerDuration");
	        timer.addListener(new ChangeListener(){
	        	public void changed (ChangeEvent event, Actor actor) {
	        		if(!timerSetting){
	        		timerSetting = true;
	        		timer.setStyle(CheckBoxStyle3);
	        		}
	        		else{
	        			timerSetting = false;
	        			timer.setStyle(CheckBoxStyle2);
	        		}
	        	}
	        });
           duration.addListener(new ChangeListener(){
        	//sets the specified duration of the timer for each turn a player takes   
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				durationClicked = true;
				durationName = actor.getName();
				MyTextInputListener listener = new MyTextInputListener();
				Gdx.input.getTextInput(listener, "Timer Duration (in seconds)", "");
			}
			});
           back.addListener(new ChangeListener(){

        	//sets the screen to the Main Menu Screen
			@Override
			public void changed(ChangeEvent event, Actor actor) {
			      game.setScreen(new MainMenuScreen(game));
				
			}   
           });
		}
		//returns true if the timer is on, and false otherwise
		public boolean timerOnOff(){
			return timerSetting;
		}
		//destroys the input processor when the user hides the app
		@Override
		public void hide() {
			 Gdx.input.setInputProcessor(null);
			
		}

		//pauses
		@Override
		public void pause() {
			// TODO Auto-generated method stub
			
		}

		//resumes
		@Override
		public void resume() {
			// TODO Auto-generated method stub
			
		}

		//disposes of all necessary resources
		@Override
		public void dispose() {
			stage.dispose();
			skin.dispose();
			font.dispose();
			pixmap.dispose();
			 Gdx.input.setInputProcessor(null);
		}

	}
