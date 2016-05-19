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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class WinScreen implements Screen {

	Stage stage;
	Skin skin;
	Carcassonne game;
	TextureAtlas atlas;
	BitmapFont font;
	Pixmap pixmap;
	TextButton button;
	public static int[] wins= {0,0,0};
	public WinScreen(Carcassonne game){
		this.game = game;
	}
		public void render(float delta) {
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			stage.act(Gdx.graphics.getDeltaTime());
	        stage.draw();

			//stage.setDebugAll(true); // This is optional, but enables debug lines for tables.
			
		}

		@Override
		public void resize(int width, int height) {
			stage.getViewport().update(width, height, true);
		}

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
	        
	        Image background = new Image(new Texture(Gdx.files.internal("epic2.png")));
	        stage.addActor(background);
	        background.scaleBy(.4f);

	        // Store the default libgdx font under the name "default".
	        skin.add("default", new BitmapFont());
	        TextFieldStyle style = new TextFieldStyle();
	        style.font = font;
	        style.fontColor= Color.GREEN;
	        
	        skin.add("default", style);
	        
	        TextField winner = new TextField("The Winner is: ", skin);
	        stage.addActor(winner);
	        winner.setBounds(50, 700, 1300, 200);
	        GamePlayers.calculateWinner();
	        winner.setText(winner.getText() + GamePlayers.winner);
	        
	        TextButtonStyle textButtonStyle = new TextButtonStyle();
	        textButtonStyle.up = new TextureRegionDrawable(upr);
	        textButtonStyle.font = font;
	        textButtonStyle.font.setScale(4f);
	        
	        skin.add("default", textButtonStyle);
	        
	        TextButton mainMenu = new TextButton("Main Menu", skin);
	        stage.addActor(mainMenu);
	        mainMenu.setBounds(700, 300, 800, 300);
	        
	        mainMenu.addListener(new ChangeListener(){

				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if(!SaveGameState.getPreferences().contains("Mark")){
						SaveGameState.addPreferences("Mark", "" + wins[0]);
					}
					if(!SaveGameState.getPreferences().contains("Sam")){
						SaveGameState.addPreferences("Sam", "" + wins[1]);
					}
					if(!SaveGameState.getPreferences().contains("Josh")){
						SaveGameState.addPreferences("Josh", "" + wins[2]);
					}
					SaveGameState.getPreferences().flush();
					//reset all of the game entities
					SelectionItems.getSelectedNames().clear();
					for(int i=0; i<GamePlayers.getGamePlayers().size(); i++){
						if(GamePlayers.getGamePlayers().get(i).getScore() == GamePlayers.finalScores.get(GamePlayers.finalScores.size()-1)){
							switch(GamePlayers.getGamePlayers().get(i).getName()){
							case "Mark": wins[0]+= 1;
									break;
							case "Sam": wins[1] +=1;
							        break;
							case "Josh": wins[2] +=1;
						            break;
						    default: break;
							}
						}
					}
					GamePlayers.getGamePlayers().clear();
					SelectionItems.getItemsArray().clear();
					PlayScreen.playerCheckBoxes.clear();
					PlayScreen.indexOrder.clear();
					GamePlayers.finalScores.clear();
					GamePlayers.winner = "";
					PlayScreen.elapsedTime = 0;
					OptionScreen.timerSetting = false;
					PlayScreen.start = true;
					OptionScreen.durationName = "";
					PlayScreen.playScreenActive = false;
					OptionScreen.durationClicked = false;
					NameScreen.newName = false;
					ExistingNameScreen.chooseTableOrder = false;
					
					game.setScreen(new MainMenuScreen(game));
				}
	        });

		}
		@Override
		public void hide() {
			 Gdx.input.setInputProcessor(null);
			
		}

		@Override
		public void pause() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void resume() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dispose() {
			stage.dispose();
			skin.dispose();
			font.dispose();
			atlas.dispose();
			pixmap.dispose();
			 Gdx.input.setInputProcessor(null);
		}

}
