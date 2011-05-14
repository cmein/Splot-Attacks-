package com.cmein.tilemap.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.cmein.tilemap.Art;
import com.cmein.tilemap.Input;

public class MenuScreen extends Screen {
	private boolean touched = false;
	BitmapFont font;
	
	@Override
	public void render() {
		font = new BitmapFont();
		font.setColor(Color.BLUE);
		
		// Menu button touch-down animation
		spriteBatch.begin();
		draw(Art.menuBackground, 0, 0);
		if (!touched) {
			draw(Art.startGameButtonUnpressed, 80, 100);
		} else {
			draw(Art.startGameButtonPressed, 80, 100);
		}
		spriteBatch.end();
		//spriteBatch.dispose();
		
		// Take user input coordinates and FPS
		extra.begin();
		font.drawMultiLine(extra, "touch: " + Gdx.input.getX() + ", " + Gdx.input.getY(), 10, 100);
		font.draw(extra, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, 20);
		extra.end();
		extra.dispose();
	}
	
	public void tick (Input input) {
		if (Gdx.input.isTouched()) {
			if (Gdx.input.getX() >= SCREEN_WIDTH/6 && Gdx.input.getX() <= SCREEN_WIDTH/6 + 2*SCREEN_WIDTH/3 &&
				Gdx.input.getY() >= 5*SCREEN_HEIGHT/16 && Gdx.input.getY() <= 5*SCREEN_HEIGHT/16 + SCREEN_HEIGHT/8) {
				touched = true;
				input.releaseAllKeys();
			}
			else {
				touched = false;
			}
		}
		else if (touched == true && !Gdx.input.isTouched()) {
			touched = false;
			setScreen(new LevelSelectScreen());
		}
		else{
			touched = false;
		}
	}
}
