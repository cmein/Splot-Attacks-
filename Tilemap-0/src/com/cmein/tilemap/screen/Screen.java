package com.cmein.tilemap.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.cmein.tilemap.Art;
import com.cmein.tilemap.TileMap;
import com.cmein.tilemap.Input;
import com.cmein.tilemap.bullet.*;
import com.cmein.tilemap.enemy.Enemy;
import com.cmein.tilemap.screen.Screen;

public abstract class Screen {
	private TextureRegion aBullet;
	private TextureRegion enemy;
	private TileMap menuSystem;
	public SpriteBatch spriteBatch;
	public SpriteBatch extra;
	public int tileMap;
	public Texture texture;
	public OrthographicCamera cam;	
	public final int SCREEN_WIDTH = Gdx.graphics.getWidth();
	public final int SCREEN_HEIGHT = Gdx.graphics.getHeight();	

	public final void init (TileMap menuSystem) {
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
	
	public void drawTiled (TiledMapRenderer renderer, int x, int y) {
		Matrix4 projection = new Matrix4();
		projection.setToOrtho(0, 480, 0, 320, -1, 1);
		renderer.getProjectionMatrix().set(projection);
		
        renderer.render(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	public void drawEnemies (float count){
		if(!Art.walkingQueue.isEmpty()){
			for (int i=0; i<Art.walkingQueue.size(); i++){
				Enemy newenemy = Art.walkingQueue.get(i);
				count += .15;
				if((int) (count*2) % 3 == 0){
					enemy = Art.firstEnemyAnimationTop.getKeyFrame(newenemy.getStateTime(), true);
				}else{
					enemy = Art.firstEnemyAnimationBot.getKeyFrame(newenemy.getStateTime(), true);
				}
				newenemy.update(Gdx.graphics.getDeltaTime());

				if(newenemy.isDead()){
					Art.walkingQueue.remove(i);
					TileScreen.playerMoney += newenemy.getPay();
				}else{
					if(newenemy.getX()<480){
						draw(enemy, (int) newenemy.getX(), (int) newenemy.getY());
					}else{
						Art.walkingQueue.remove(i);
						TileScreen.playerHealth -= newenemy.getDamage();
					}
				}
				//Art.walkingQueue.get(i).update(Gdx.graphics.getDeltaTime());
			}
		}else{
			System.out.println("HERE");
			resetGameState();
		}
	}
	
	public void resetGameState() {
		setScreen(new LevelSelectScreen());
		TileScreen.playerHealth = 1000;
		TileScreen.playerMoney = 100;
	}
	
	public void drawBullets () {
		if(!Art.currentBullets.isEmpty()){
			for(int i=0; i<Art.currentBullets.size(); i++){
				Bullet bullet = Art.currentBullets.get(i);
				double angleFromTower = Math.toDegrees(getAngle(bullet.startX(), bullet.startY(), bullet.startTargetX(), bullet.startTargetY()));
				if(angleFromTower >= 22.5 && angleFromTower <= 67.5){
					if(isOnLeft(bullet.startX(), bullet.startTargetX())){
						aBullet = Art.diagBullet2;
					}else{
						aBullet = Art.diagBullet1;
					}
				}else if (angleFromTower > 67.5) {
					aBullet = Art.verticalBullet;
				}else if (Math.abs(angleFromTower) < 22.5){
					if(isOnLeft(bullet.startX(), bullet.startTargetX())){
						aBullet = Art.horizontalBullet;
					}else{
						aBullet = Art.horizontalBullet;
					}
				}else if (angleFromTower <= -22.5 && angleFromTower >= -67.5){
					if(isOnLeft (bullet.startX(), bullet.startTargetX())){
						aBullet = Art.diagBullet1;
					}else{
						aBullet = Art.diagBullet2;
					}
				}else{
					aBullet = Art.verticalBullet;
				}
				
				/*if((bullet.getTargetX() - bullet.getX())*(bullet.getTargetX() - bullet.getX()) > (bullet.getTargetY() - bullet.getY())*(bullet.getTargetY() - bullet.getY())){
					aBullet = Art.horizontalBullet;
				}else{
					aBullet = Art.bulletV;
				}*/
				Art.currentBullets.get(i).update(Gdx.graphics.getDeltaTime());
				if(Art.currentBullets.get(i).hitTarget()){
					if(Art.currentBullets.get(i).getTower().hasTarget()){
						Art.currentBullets.get(i).getTower().currentTarget().doDamage(5);
					}	
					Art.currentBullets.remove(i);
				}else{
					draw(aBullet, (int) bullet.getX(), (int) bullet.getY());
				}
			}
		}
	}
	
	private double getAngle(float x, float y, float xa, float ya){
		double dy = y-ya;
		double dx = xa-x;
		double ab = dx*dx + dy*dy;
		double hyp = Math.sqrt(ab);
		return Math.asin(dy/hyp);
	}
	
	private boolean isOnLeft(float x, float xa){
		return xa - x < 0;
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
	
	public void tick (Input input) {
		
	}
}
