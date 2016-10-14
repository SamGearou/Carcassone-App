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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

//The main menu screen where players can select one of the following options: New Game,
//Options, or Statistics
public class MainMenuScreen implements Screen {

	Stage stage;
	Skin skin;
	Carcassonne game;
	TextureAtlas atlas;
	BitmapFont font;
	Pixmap pixmap;
	
	//takes an instance of the game to allow the ability to switch screens
	public MainMenuScreen(Carcassonne game){
		this.game = game;
	}
	    //draws the stage and all of the actors on the stage
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
	       atlas = new TextureAtlas(Gdx.files.internal("SpriteSheet.txt"));
			// Add widgets to the table here.
	       TextureRegion upr = atlas.findRegion("defaultbutton");
	        // A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
	        // recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
	        skin = new Skin();

	        // Generate a 1x1 white texture and store it in the skin named "white".
	        pixmap = new Pixmap(1, 1, Format.RGBA8888);
	        pixmap.setColor(Color.WHITE);
	        pixmap.fill();
	        skin.add("white", new Texture(pixmap));

	        // Store the default libgdx font under the name "default".
	        skin.add("default", new BitmapFont());
	        Image background = new Image(new Texture(Gdx.files.internal("spooky.png")));
	        table.addActor(background);
	        background.scaleBy(2f);

	        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
	        TextButtonStyle textButtonStyle = new TextButtonStyle();
	        textButtonStyle.up = new TextureRegionDrawable(upr);
	        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
	        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
	        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
	        textButtonStyle.font = font;
	        textButtonStyle.font.setScale(4f);
	        skin.add("default", textButtonStyle);
	        // Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
	        final TextButton button = new TextButton("New Game", skin);
	        final TextButton options = new TextButton("Options", skin);
	        final TextButton statistics = new TextButton("Statistics", skin);
	        stage.addActor(statistics);
	        table.add(button).width(870).height(380).spaceBottom(10);
	        table.row();
	        table.add(options).width(870).height(380).spaceBottom(10);
	        table.row();
	        button.addListener(new ChangeListener() {
	        	//changes the screen to the Name Screen
	        	public void changed (ChangeEvent event, Actor actor) {
	        	game.setScreen(new NameScreen(game));	
	        	}
	        });
	        options.addListener(new ChangeListener(){
	        	//changes the screen to the Options Screen
	        	public void changed(ChangeEvent event, Actor actor) {
	        	game.setScreen(new OptionScreen(game));
	        	}
	        });
	        statistics.addListener(new ChangeListener(){

	        	//changes the screen to the Statistics Screen
				@Override
				public void changed(ChangeEvent event, Actor actor) {
                 game.setScreen(new Statistics(game));					
				}
	        });
		}
		//destroys the input processor when the user hides the app
		@Override
		public void hide() {
			 Gdx.input.setInputProcessor(null);
			
		}

		//pause
		@Override
		public void pause() {
			// TODO Auto-generated method stub
			
		}

		//resume
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
			atlas.dispose();
			pixmap.dispose();
			//game.dispose(); creates the error message "unfortunately, Carcassone (or the app) has stopped working" 
			// when you hit the back button from the main menu screen
			 Gdx.input.setInputProcessor(null);
		}

	}
