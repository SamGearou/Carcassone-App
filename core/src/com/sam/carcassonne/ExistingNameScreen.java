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
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class ExistingNameScreen implements Screen {

	Stage stage;
	Skin skin;
	Carcassonne game;
	TextureAtlas atlas;
	BitmapFont font;
	Pixmap pixmap;
	SelectBox<String> selectBox;
	private CheckBoxStyle CheckBoxStyle;
	private CheckBoxStyle CheckBoxStyle2;
	
	public static boolean chooseTableOrder = false;
	
	public boolean timerSetting = false;
	public ExistingNameScreen(Carcassonne game){
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
			// Add widgets to the table here.
	        // Generate a 1x1 white texture and store it in the skin named "white".
	        pixmap = new Pixmap(1, 1, Format.RGBA8888);
	        pixmap.setColor(Color.WHITE);
	        pixmap.fill();
	        skin.add("white", new Texture(pixmap));

	        // Store the default libgdx font under the name "default".
	        skin.add("default", new BitmapFont());
	        
	        Image background = new Image(new Texture(Gdx.files.internal("castle.png")));
	        stage.addActor(background);
	        background.scaleBy(.3f);
	        
	        SelectBoxStyle style = new SelectBoxStyle();
	        style.font = font;
	        style.background = skin.newDrawable("white", Color.GRAY);
	        style.fontColor = Color.BLUE;
	        style.font.setScale(4.5f);
	        style.listStyle = new List.ListStyle();
	        style.listStyle.font = font;
	        style.listStyle.fontColorSelected = Color.GREEN;
	        style.listStyle.fontColorUnselected = Color.GREEN;
	        style.listStyle.selection = skin.newDrawable("white", Color.GRAY);
	        style.scrollStyle = new ScrollPane.ScrollPaneStyle();
	        style.scrollStyle.background = skin.newDrawable("white", Color.BLACK);
	        style.scrollStyle.vScrollKnob = skin.newDrawable("white", Color.GREEN);
	        skin.add("default", style);
	        SelectionItems.checkUniqueness("Sam");
	        SelectionItems.checkUniqueness("Josh");
	        SelectionItems.checkUniqueness("Mark");
	        SelectionItems.checkUniqueness("Liz");
	        for(int i =0; i<=10; i++){
	        	if(SaveGameState.getPreferences().contains(String.valueOf(i))
	        			&& !SelectionItems.getItemsArray().contains(String.valueOf(i), false)){
	        		SelectionItems.checkUniqueness(SaveGameState.getPreferences().getString(String.valueOf(i)));
	        	}
	        }
	        selectBox = SelectionItems.getSelectBox(style);
	        selectBox.setItems(SelectionItems.getItemsArray());
	        stage.addActor(selectBox);
	        
	        CheckBoxStyle = new CheckBoxStyle();
	        CheckBoxStyle.font = font;
	        CheckBoxStyle.checkedFontColor = Color.GREEN;
	        CheckBoxStyle.fontColor = Color.BLUE;
	        CheckBoxStyle.font.setScale(4.5f);
	        skin.add("default", CheckBoxStyle);
	        
	        CheckBoxStyle2 = new CheckBoxStyle();
	        CheckBoxStyle2.font = font;
	        CheckBoxStyle2.fontColor = Color.MAGENTA;
	        
	        CheckBox back = new CheckBox("Back", skin);
	        CheckBox start = new CheckBox("Start Game", skin);
	        CheckBox tableOrder = new CheckBox("Select a Table Order", skin);
	        stage.addActor(tableOrder);
	        tableOrder.setBounds(1000, 600, 190, 60);
	        stage.addActor(back);
	        back.setPosition(20, 1200);
	        
	        stage.addActor(start);
	        start.setPosition(1600, 1200);
	        
	        selectBox.setBounds(1000, 800, 400, 200);
	        selectBox.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					//System.out.println(selectBox.getSelected());
					if(!SelectionItems.getSelectedNames().contains(selectBox.getSelected())){
					SelectionItems.getSelectedNames().add(selectBox.getSelected());
				}
				}
	        });
	        back.addListener(new ChangeListener(){

				@Override
				public void changed(ChangeEvent event, Actor actor) {
				      game.setScreen(new NameScreen(game));
					SelectionItems.getItemsArray().clear();
				}   
	           });
	        start.addListener(new ChangeListener(){

				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if(SelectionItems.getSelectedNames().size() > 1){
					game.setScreen(new PlayScreen(game));
					}
					else{
					CheckBox check = new CheckBox("Please select another player", skin);
					if(!check.isVisible()) {
					check.setStyle(CheckBoxStyle2);
					stage.addActor(check);
					check.setPosition(100, 250);
					}
					}
				}
	        });
	        tableOrder.addListener(new ChangeListener(){

				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if(SelectionItems.getSelectedNames().size() > 1){
						chooseTableOrder = true;
						game.setScreen(new PlayScreen(game));
						}
						else{
						CheckBox check = new CheckBox("Please select another player", skin);
						if(!check.isVisible()) {
						check.setStyle(CheckBoxStyle2);
						stage.addActor(check);
						check.setPosition(100, 250);
						}
						}
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
