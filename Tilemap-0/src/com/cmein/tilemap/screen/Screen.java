package com.cmein.tilemap.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.cmein.tilemap.Art;
import com.cmein.tilemap.SplotAttacks;
import com.cmein.tilemap.Input;
import com.cmein.tilemap.screen.Screen;

public abstract class Screen {
	private SplotAttacks menuSystem;
	public SpriteBatch spriteBatch;
	public SpriteBatch extra;
	public int tileMap;
	public Texture texture;
	public OrthographicCamera cam;	
	public final int SCREEN_WIDTH = Gdx.graphics.getWidth();
	public final int SCREEN_HEIGHT = Gdx.graphics.getHeight();	

	public final void init (SplotAttacks menuSystem) {
		this.menuSystem = menuSystem;
		Matrix4 projection = new Matrix4();
		projection.setToOrtho(0, 480, 320, 0, -1, 1);
		spriteBatch = new SpriteBatch(100);
		extra = new SpriteBatch();
		spriteBatch.setProjectionMatrix(projection);
		//cam = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
	}
		
	protected void setScreen (Screen screen) {
		menuSystem.setScreen(screen);
	}
	
	public void remove () {
		spriteBatch.dispose();
	}
	
	public void draw (TextureRegion region, int x, int y) {
		int width = region.getRegionWidth();
		int height = region.getRegionHeight();
		if(height<0) height = -height;
		if(width<0) width = -width;
		spriteBatch.draw(region, x, y, width, height);
		//spriteBatch.dispose();
	}
	
	String[] chars = {"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789?!*&^"};
	
	public void drawString(String string, int x, int y) {
		string = string.toUpperCase();
		for (int i=0; i<string.length(); i++) {
			char ch = string.charAt(i);
			for (int j = 0; j < chars.length; j++) {
				int c = chars[j].indexOf(ch);
				if (c >= 0) {
					draw(Art.alphaNumeric[c], x+i*16, y);
				}
			}
		}
	}
	
	public abstract void render ();
	
	public abstract void tick (Input input) ;
}
