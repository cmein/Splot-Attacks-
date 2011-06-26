package com.cmein.tilemap.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.cmein.tilemap.Art;
import com.cmein.tilemap.Input;
import com.cmein.tilemap.bullet.Bullet;
import com.cmein.tilemap.enemy.Enemy;
import com.cmein.tilemap.tower.ThoriumTower;

public class TileScreen extends Screen{
	public static int[][] propertyMap = Art.propertyMap1;
	public static int selectedTileX;
	public static int selectedTileY;
	public ThoriumTower tower1;
	private boolean touched = false;
	private boolean selected = false;
	private boolean operateCursor = false;
	private boolean operateBuildMenu = false;
	private int selectedX;
	private int selectedY;
	private int pixelX;
	private int pixelY;
	private BitmapFont font = new BitmapFont();
	public float aspectRatioX;
	public float aspectRatioY;
	public int tileMapX;
	public int tileMapY;
	private float buffer = 0;
	private TextureRegion aBullet;
	private TextureRegion enemy;
	private float enemyCount;
	private float flyCount;
	public static int playerHealth=1000;
	private String healthStr;
	private String moneyStr;
	public static int playerMoney=100;
	
	public int translateTileToPixel (int i) {
		return (int) (i * 32);
	}
	
	private void selectTile () {
		if (propertyMap[tileMapY][tileMapX] >= 0) {
			selectedX = tileMapX;
			selectedY = tileMapY;
			selected = true;
			System.out.println("SELECTED");
			draw(Art.selectHighlight, translateTileToPixel(tileMapX), translateTileToPixel(tileMapY));
		}
		else {
			System.out.println("Cannot place on roads");
			selected = false;
		}
		touched = false;
	}
	
	private void cursorProcessor () {
		spriteBatch.begin();
		if (selected && selectedX == tileMapX && selectedY == tileMapY) {			
			selected = false;
			touched = false;
			System.out.println("UNSELECTED");		
		}
		else {
			selectTile();
		}
		spriteBatch.end();
	}
	
	private void ifSelectedDrawMenu () {
		spriteBatch.begin();
		if (selected) {
			draw(Art.selectHighlight, translateTileToPixel(selectedX), translateTileToPixel(selectedY));
		}
		spriteBatch.end();
	}
	/*
	private void drawTowers () {
		spriteBatch.begin();
		for (int i=0; i<TowerScreen.propertyMap.length; i++) {
			for (int j=0; j<TowerScreen.propertyMap[0].length; j++) {
				switch (TowerScreen.propertyMap[i][j]) {
				case 1:
					draw(Art.tower1, translateTileToPixel(j), translateTileToPixel(i));
					break;
				}
			}
		}
		spriteBatch.end();
	}
	*/
	private void drawTowers(){
		spriteBatch.begin();
		for (int i=0; i<Art.currentTowers.size(); i++){
			if (Art.currentTowers.get(i) != null) {
				draw(Art.tower1, translateTileToPixel(Art.currentTowers.get(i).getX()), translateTileToPixel(Art.currentTowers.get(i).getY()));
			}
		}
		spriteBatch.end();
	}
	
	private void drawHUD() {
		spriteBatch.begin();
		healthStr = "HP ";
		moneyStr = "* ";
		if(playerMoney<10){
			moneyStr += "   ";
		}
		else if(playerMoney>=10 && playerMoney<100){
			moneyStr += "  ";
		}
		else if(playerMoney>=100 && playerMoney<10000){
			moneyStr += " ";
		}
		moneyStr += playerMoney;
		for(int i=0; i<Math.floor(playerHealth/100); i++){
			healthStr += "&";
		}
		if(playerHealth%100 != 0){
			healthStr += "^";
		}
		drawString(healthStr, 0, 0);
		drawString(moneyStr, 384, 0);
		spriteBatch.end();
		if(playerHealth==0){
			resetGameState();
		}
	}
	
	private void buildMenu () {
		if (pixelX <= 76) {
			if (placable(selectedX, selectedY)) {
				placeTower(1, selectedX, selectedY);
				System.out.println(selectedX + ", " + selectedY);
				playerMoney -= ThoriumTower.cost;
			}
			else {
				System.out.println("Cannot place tower here.");
			}
			operateBuildMenu = false;
			selected = false;
			touched = false;
		}
		/*
		else if (pixelX > 110 && pixelX <= 174) {
			
		}
		else if (pixelX > 208 && pixelX <= 272) {
			
		}
		else if (pixelX > 306 && pixelX <= 370) {
			
		}
		else if (pixelX > 404 && pixelX <= 468) {
			
		}
		*/
	}
	
	private void cancelBuildMenu () {
		operateBuildMenu = false;
		touched = true;
		operateCursor = true;
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
	
	public static boolean placable (int x, int y) {
		if (propertyMap[y][x] == 0) {
			System.out.println(propertyMap[y][x]);
			selectedTileX = x;
			selectedTileY = y;
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean placeTower (int tower, int x, int y) {
		if (selectedTileX == x && selectedTileY == y) {
			propertyMap[y][x] = tower;
			ThoriumTower tower1 = new ThoriumTower(x,y);
			Art.currentTowers.add(tower1);
			System.out.println("Tower placed");
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public void render() {
		font.setColor(Color.BLUE);
		aspectRatioX = ((float) SCREEN_WIDTH / (float) Art.MAP_WIDTH);
		aspectRatioY = ((float) SCREEN_HEIGHT / (float) Art.MAP_HEIGHT);
		
		drawTiled(Art.level1, 0, 0);
		
		// Take user input coordinates and FPS
		extra.begin();
		tileMapX = Art.level1.getCol((int) (pixelX / aspectRatioX));
		tileMapY = Art.level1.getRow((int) (pixelY / aspectRatioY));
		font.drawMultiLine(extra, "touch: " + pixelX + ", " + pixelY + "\n" +
								  "tile : " + tileMapX + ", " + tileMapY, 10, 100);
		font.draw(extra, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, 20);
		extra.end();
		extra.dispose();
		
		// Operate tower building menu
		if (operateBuildMenu && ((selectedY < 5 && pixelY >= 240) || (selectedY >= 5 && pixelY <= 80))){
			buildMenu();		
		}
		if(operateBuildMenu && ((selectedY < 5 && pixelY < 240) || (selectedY >= 5 && pixelY > 80))){
			cancelBuildMenu();
		}

		
		// Draw towers
		drawTowers();
		
		// Operate select cursor
		if (operateCursor && touched) {
			cursorProcessor();		
		}
		else {
			ifSelectedDrawMenu();
		}

		// Enemy animation
		spriteBatch.begin();
		if(!Art.firstEnemy.isEmpty() || !Art.walkingQueue.isEmpty()){
			if((Art.walkingQueue.isEmpty() && !Art.firstEnemy.isEmpty())
					|| (Art.walkingQueue.peek().getStateTime() >= enemyCount + 1.2 && !Art.firstEnemy.isEmpty())){
				Art.walkingQueue.push(Art.firstEnemy.pop());
				enemyCount = Art.walkingQueue.peek().getStateTime();
			}
		}
		if(!Art.walkingQueue.isEmpty()){
			flyCount = Art.walkingQueue.firstElement().getStateTime();
			drawEnemies(flyCount);
			drawBullets();
		}
		spriteBatch.end();
		
		// Draw tower menu
		if (selected) {
			if (selectedY < 5) {
				spriteBatch.begin();
				draw(Art.towerMenuBottom, 0, 240);
				draw(Art.iconToriumI, 12, 270);
				spriteBatch.end();
			}
			else {
				spriteBatch.begin();
				draw(Art.towerMenuTop, 0, 0);
				draw(Art.iconToriumI, 12, 30);
				spriteBatch.end();
			}
		}
		
		// Draw HUD
		drawHUD();
	}	
	
	public void tick (Input input) {
		if (Gdx.input.isTouched()) {
			pixelX = Gdx.input.getX();
			pixelY = Gdx.input.getY();
			if (input.buttons[0] == true) {
				buffer += 1.0f / 60.0f;
			}
			operateCursor = false;
		}
		/*
		else if (Gdx.input.justTouched()) {
			pixelX = Gdx.input.getX();
			pixelY = Gdx.input.getY();
			operateCursor = false;
		}
		*/
		else {
			if (input.buttons[0] == false && input.oldButtons[0] == true) {
				if (buffer < 0.24f) {
					if (selected) {
						operateCursor = false;
						operateBuildMenu = true;
						touched = true;
					}
					else {
						operateCursor = true;
						touched = true;
					}
				}
				else {
					touched = false;
				}
				buffer = 0;
			}
		}
		// Comment out the following line to untame the FPS
		//tameFPS(16);
	}
	
	private void tameFPS (int n) {
		try {
		      Thread.sleep(n); // 16 milliseconds ~100FPS for desktop app
		} catch (InterruptedException e) {
			
		}
	}
}
