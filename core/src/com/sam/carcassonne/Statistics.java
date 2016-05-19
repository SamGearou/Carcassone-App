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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Statistics implements Screen{

	Stage stage;
	Skin skin;
	Carcassonne game;
	TextureAtlas atlas;
	BitmapFont font;
	Pixmap pixmap;
	
	public static boolean chooseTableOrder = false;
	
	public boolean timerSetting = false;
	public Statistics(Carcassonne game){
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
	        skin = new Skin();
	        atlas = new TextureAtlas(Gdx.files.internal("SpriteSheet.txt"));
	        TextureRegion upr = atlas.findRegion("defaultbutton");
			// Add widgets to the table here.
	        // Generate a 1x1 white texture and store it in the skin named "white".
	        pixmap = new Pixmap(1, 1, Format.RGBA8888);
	        pixmap.setColor(Color.WHITE);
	        pixmap.fill();
	        skin.add("white", new Texture(pixmap));

	        // Store the default libgdx font under the name "default".
	        skin.add("default", new BitmapFont());
	        
	        TextFieldStyle style = new TextFieldStyle();
	        style.font= font;
	        style.fontColor = Color.GREEN;
	        style.font.setScale(3.5f);
	        skin.add("default", style);
	        
	        TextField field = new TextField("Wins", skin);
	        stage.addActor(field);
	        field.setPosition(20, 950);
	        field.setSize(300, 80);
	        
	        TextField names = new TextField("", skin);
	        stage.addActor(names);
	        names.setText("Mark     Sam     Josh");
	        names.setPosition(380,  1100);
	        names.setSize(1100, 80);
	        
	        TextField markWins = new TextField("", skin);
	        stage.addActor(markWins);
	        markWins.setText("" + WinScreen.wins[0]);
	        markWins.setPosition(470, 940);
	        markWins.setSize(400, 80);
	        
	        TextField samWins = new TextField("", skin);
	        stage.addActor(samWins);
	        samWins.setText("" + WinScreen.wins[1]);
	        samWins.setPosition(850, 940);
	        samWins.setSize(400, 80);
	        
	        TextField joshWins = new TextField("", skin);
	        stage.addActor(joshWins);
	        joshWins.setText("" + WinScreen.wins[2]);
	        joshWins.setPosition(1250, 940);
	        joshWins.setSize(400, 80);
	        
	        TextButtonStyle textButtonStyle = new TextButtonStyle();
	        textButtonStyle.up = new TextureRegionDrawable(upr);
	        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
	        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
	        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
	        textButtonStyle.font = font;
	        textButtonStyle.font.setScale(4f);
	        skin.add("default", textButtonStyle);
	        
	        TextButton back = new TextButton("Back", skin);
	        stage.addActor(back);
	        back.setPosition(60,20);
	        back.setSize(400,140);
	        back.addListener(new ChangeListener(){

				@Override
				public void changed(ChangeEvent event, Actor actor) {
					game.setScreen(new MainMenuScreen(game));
				}
	        });
		}
		public Carcassonne getGame(){
			return game;
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
			pixmap.dispose();
			 Gdx.input.setInputProcessor(null);
		}
}
