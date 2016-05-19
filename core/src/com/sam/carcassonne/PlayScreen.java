package com.sam.carcassonne;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.sam.carcassonne.Player.Order;

public class PlayScreen implements Screen {
	
	private TextureRegion upr;
	private CheckBoxStyle CheckBoxStyle;
	private CheckBoxStyle CheckBoxStyle2;

	Stage stage;
	Skin skin;
	Carcassonne game;
	TextureAtlas atlas;
	BitmapFont font;
	Pixmap pixmap;
	TextButton button;
	TextButton bidding;
	private float playerXCoord = 300;
	private float playerYCoord = 700;
	static ArrayList<CheckBox> playerCheckBoxes = new ArrayList<CheckBox>();
	static ArrayList<Integer> indexOrder = new ArrayList<Integer>();
	private static ArrayList<String> copy;
	private SpriteBatch batch = new SpriteBatch();
	private int indexHelp = 0;
	public static boolean playScreenActive = false;
	
	private static int numLeft = 0;
	
	public static float elapsedTime = 0;
	private static BitmapFont scores = new BitmapFont(Gdx.files.internal("gamefonts.fnt"), false);
	
	private Music music;
	
	public static boolean start = true;
	
	public PlayScreen(Carcassonne game){
		music = Gdx.audio.newMusic(Gdx.files.internal("GameOfThronesTheme.mp3"));
		music.setVolume(0.5f);   //sets the volume to half the maximum volume
		music.play();
		this.game = game;
	}
		public void render(float delta) {
			playScreenActive = true;
			int offset=15;
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			stage.act(Gdx.graphics.getDeltaTime());
	        stage.draw();
	        batch.begin();
	        for(int i=0; i<GamePlayers.getGamePlayers().size(); i++){
	        scores.setColor(Color.GREEN);
	        scores.setScale(3f);
	        scores.draw(batch, String.valueOf(GamePlayers.getGamePlayers().get(indexOrder.get(i)).getScore()), 320 + offset, 1000);
	        offset+=620;
	        }
	        batch.end();
	        if(elapsedTime > MyTextInputListener.timerDuration/2 && start && OptionScreen.durationClicked){
	        	batch.begin();
	        	scores.setScale(3f);
	        	scores.draw(batch, String.valueOf(elapsedTime), 320, 1260);
	        	batch.end();
	        }
	        if(OptionScreen.timerSetting && start){
	        	elapsedTime+= (delta * 1);
	        }
	        if(OptionScreen.timerSetting && elapsedTime > MyTextInputListener.timerDuration && OptionScreen.durationClicked){
	        	if(start){
	        	outer: for(int i=0; i<GamePlayers.getGamePlayers().size(); i++){
	        		if(playerCheckBoxes.get(i).getStyle().fontColor.toString().equals("ff0000ff")){
	        			for(int j = 0; j<GamePlayers.getGamePlayers().size(); j++){
	        				if(GamePlayers.getGamePlayers().get(j).getName().equals(playerCheckBoxes.get(i).getName())){
	        					GamePlayers.getGamePlayers().get(j).setPoints(-1);
	        					elapsedTime = MyTextInputListener.timerDuration/2;
	        					break outer;
	        				}
	        			}
	        		}
	        	}
	        	}
	        }
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
	       upr = atlas.findRegion("defaultbutton");
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
	        
	        Image background = new Image(new Texture(Gdx.files.internal("epic.png")));
	        stage.addActor(background);
	        background.scaleBy(1f);
	        
	        //Code for the Game Logic
	        gameLogic();
		}
		public void gameLogic(){
			//Sets the order of the players
			copy = new ArrayList<String>();
			for(int i=0; i<SelectionItems.getSelectedNames().size(); i++){
	        	Player player = new Player();
	        	player.setName(SelectionItems.getSelectedNames().get(i));
	        	GamePlayers.addPlayers(player);
	        	copy.add(SelectionItems.getSelectedNames().get(i));
	        }
			int index=GamePlayers.getGamePlayers().size();
			Order allOrders[] = Order.values(); 
			Random random = new Random();
			outer: for(int i=0; i<GamePlayers.getGamePlayers().size(); i++){
			int rand = random.nextInt(index); 
			for(int j=0; j <GamePlayers.getGamePlayers().size(); j++){
				if((GamePlayers.getGamePlayers().get(j).getName().equals(copy.get(rand))
						&& !ExistingNameScreen.chooseTableOrder) || (i == 0 
						&& GamePlayers.getGamePlayers().get(j).getName().equals(copy.get(rand)))){
			GamePlayers.getGamePlayers().get(j).setOrder(allOrders[i]);
			copy.remove(rand);
			numLeft = copy.size();
			index--;
			break;
				}
			if(i != 0 && ExistingNameScreen.chooseTableOrder){
				for(int k = 0; k<SelectionItems.getSelectedNames().size(); k++){
					for(int l=0; l<SelectionItems.getSelectedNames().size(); l++){
						if (!copy.contains(SelectionItems.getSelectedNames().get(l))){
							while(numLeft > 0){
								if(l+1 != SelectionItems.getSelectedNames().size()){
									GamePlayers.getPlayer(SelectionItems.getSelectedNames().get(l+1)).setOrder(allOrders[i++]);
									l++;
									numLeft--;
								}
								else{
									l=0;
									GamePlayers.getPlayer(SelectionItems.getSelectedNames().get(l)).setOrder(allOrders[i++]);
									numLeft--;
								}
							}
							break outer;
						}
					}
			}
		}
			}
			}
			CheckBoxStyle = new CheckBoxStyle();
	        CheckBoxStyle.font = font;
	        CheckBoxStyle.font.setScale(5f);
	        CheckBoxStyle.checkedFontColor = Color.BLUE;
	        CheckBoxStyle.fontColor = Color.BLUE;
	        
	        CheckBoxStyle2 = new CheckBoxStyle();
	        CheckBoxStyle2.font = font;
	        CheckBoxStyle2.font.setScale(5f);
	        CheckBoxStyle2.checkedFontColor = Color.RED;
	        CheckBoxStyle2.fontColor = Color.RED;
	        
	        TextButtonStyle textButtonStyle = new TextButtonStyle();
	        textButtonStyle.up = new TextureRegionDrawable(upr);
	        textButtonStyle.font = font;
	        textButtonStyle.font.setScale(4f);
	        
	        skin.add("default", textButtonStyle);
	        skin.add("default", CheckBoxStyle);
	        
	        TextButtonStyle textButtonStyle2 = new TextButtonStyle();
	        textButtonStyle2.up = new TextureRegionDrawable(upr);
	        textButtonStyle2.down = skin.newDrawable("white", Color.DARK_GRAY);
	        textButtonStyle2.checked = skin.newDrawable("white", Color.BLUE);
	        textButtonStyle2.over = skin.newDrawable("white", Color.LIGHT_GRAY);
	        textButtonStyle2.font = font;
	        
	        TextButton next = new TextButton("Next Turn", skin);
	        TextButton finish = new TextButton("Finish", skin);
	        bidding = new TextButton("Stop Timer", skin);
	        bidding.setStyle(textButtonStyle2);
	        if(OptionScreen.timerSetting){
	        stage.addActor(bidding);
	        }
	        bidding.setBounds(600, 100, 640, 200);
	        stage.addActor(finish);
	        finish.setBounds(1800, 30, 470, 200);
	        stage.addActor(next);
	        next.setBounds(800, 400, 710, 200);
	        
			for(int i=0; i<GamePlayers.getGamePlayers().size(); i++){
				for(int j=0; j<GamePlayers.getGamePlayers().size(); j++){
				if(GamePlayers.getGamePlayers().get(j).getOrder() == allOrders[i]){
					indexOrder.add(j);
		        CheckBox player = new CheckBox(GamePlayers.getGamePlayers().get(j).getName(), skin);
		        playerCheckBoxes.add(player);
		        stage.addActor(playerCheckBoxes.get(playerCheckBoxes.indexOf(player)));
		        playerCheckBoxes.get(playerCheckBoxes.indexOf(player)).setPosition(playerXCoord, playerYCoord);
		        playerCheckBoxes.get(playerCheckBoxes.indexOf(player)).setName(GamePlayers.getGamePlayers().get(j).getName());
		        if(i==0){
		        	playerCheckBoxes.get(0).setStyle(CheckBoxStyle2);
		        }
		        break;
			}
				}
				playerXCoord+=600;
			}
			for(int i=0;i<GamePlayers.getGamePlayers().size(); i++){
				playerCheckBoxes.get(i).addListener(new ChangeListener(){
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						MyTextInputListener.checkBoxName = actor.getName();
					    MyTextInputListener listener = new MyTextInputListener();
						Gdx.input.getTextInput(listener, "Update Points", "");
					}
					
				});
			}
			next.addListener(new ChangeListener(){

				@Override
				public void changed(ChangeEvent event, Actor actor) {
					bidding.setChecked(false);
					bidding.setText("Stop Timer");
					bidding.setBounds(600, 100, 640, 200);
					start =true;
					elapsedTime = 0;
					playerCheckBoxes.get(indexHelp % GamePlayers.getGamePlayers().size()).setStyle(CheckBoxStyle);
					playerCheckBoxes.get(++indexHelp % GamePlayers.getGamePlayers().size()).setStyle(CheckBoxStyle2);
				}
			});
			finish.addListener(new ChangeListener(){

				@Override
				public void changed(ChangeEvent event, Actor actor) {
					game.setScreen(new WinScreen(game));
				}
			});
			bidding.addListener(new ChangeListener(){

				@Override
				public void changed(ChangeEvent event, Actor actor) {
					bidding.setText("Timer Stopped");
					bidding.setBounds(600, 100, 850, 200);
					elapsedTime = 0;
					start = false;
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
			music.dispose();
			batch.dispose();
			scores.dispose();
			 Gdx.input.setInputProcessor(null);
		}

	}
