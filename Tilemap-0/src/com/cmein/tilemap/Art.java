package com.cmein.tilemap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMapRenderer;
import com.cmein.tilemap.bullet.Bullet;
import com.cmein.tilemap.enemy.DIR;
import com.cmein.tilemap.enemy.Enemy;
import com.cmein.tilemap.enemy.FlyingSplot;
import com.cmein.tilemap.path.Path;
import com.cmein.tilemap.tower.Tower;

public class Art {
	public static LinkedList<Enemy> currentTargets;
	public static LinkedList<Bullet> currentBullets;
	public static LinkedList<Tower> currentTowers;
	public static Stack<Enemy> firstEnemy;
	public static Stack<Enemy> walkingQueue;
	public static Animation firstEnemyAnimationTop;
	public static Animation firstEnemyAnimationBot;
	
	public static int[] levelOnePathTurns;
	public static int[][] levelOnePath =  { {80, 188}, {80, -4}, {208, -4}, {208, 284}, {336, 284}, {336, 92}, {400, 92} };
	//public static int[][] levelOnePath =  { {80, 188}, {80, 220}, {40, 220}, {40, 188}, {80, 188}, {80, -4}, {208, -4}, {208, 284}, {336, 284}, {336, 92}, {400, 92} };

	public static int[][] turn = { {80, 208, 336 }, { -4, 284, 92 } };
	public static DIR[] levelOneDirections;
	public static boolean[] levelOnePathStatus;
	public static Path levelOne;
	
	public static TextureRegion[] alphaNumeric;
	
	public static TextureRegion horizontalBullet;
	public static TextureRegion verticalBullet;
	public static TextureRegion diagBullet1;
	public static TextureRegion diagBullet2;

	public static TextureRegion bulletV;
	public static int[][] propertyMap1;
	public static TextureRegion titleScreen;
	public static TextureRegion menuBackground;
	public static TextureRegion levelSelectBackground;
	
	public static TextureRegion levelOneButtonUnpressed;
	public static TextureRegion levelOneButtonPressed;
	public static TextureRegion startGameButtonUnpressed;
	public static TextureRegion startGameButtonPressed;
	
	public static TextureRegion selectHighlight;
	public static TextureRegion tileScreen;
	public static TextureRegion towerMenuBottom;
	public static TextureRegion towerMenuTop;
	public static TextureRegion iconToriumI;
	public static TextureRegion tower1;
	public static TiledMap map;
	public static TileAtlas atlas;
	public static TiledMapRenderer renderer, level1;
	public static final int MAP_WIDTH = 480;
	public static final int MAP_HEIGHT = 320;
	static long startTime, endTime;
	
	public static void load () {
		currentTargets = new LinkedList<Enemy>();
		currentBullets = new LinkedList<Bullet>();
		currentTowers = new LinkedList<Tower>();
		walkingQueue = new Stack<Enemy>();
		firstEnemy = new Stack<Enemy>();
		
		levelOne = new Path(levelOnePath);
		
		for(int i=0; i<20; i++){
			firstEnemy.push(new FlyingSplot(-50, 188, levelOne, DIR.RIGHT));
		}
		bulletV = load("data/other/ThoriumBulletV.png", 0, 0, 9, 25);
		firstEnemyAnimationTop = loadAnimation("data/other/FlyingSplotTop.png", 64, 32);
		firstEnemyAnimationBot = loadAnimation("data/other/FlyingSplotBot.png", 64, 32);
		propertyMap1 = loadProperties("data/LEVEL3.txt");
		titleScreen = load("data/other/titlescreen.png", 0, 0, 480, 320);
		menuBackground = load("data/other/MenuBackground.png", 0, 0, 480, 320);
		levelSelectBackground = load("data/other/LevelSelectBackground.png", 0, 0, 480, 320);
		selectHighlight = load("data/other/target.png", 0, 0, 32, 32);
		towerMenuBottom = load("data/other/tower_menu_bottom.png", 0, 0, 480, 80);
		towerMenuTop = load("data/other/tower_menu_top.png", 0, 0, 480, 80);
		iconToriumI = load("data/other/icon_thorium_turret[42x42].png", 0, 0, 42, 42);
		tower1 = load("data/other/thorium_turret.png", 0, 0, 32, 32);
		level1 = loadLevel(Gdx.files.internal("data/tiledmaps/LEVEL3.tmx"), Gdx.files.internal("data/LEVEL3 packfile"), Gdx.files.internal("data/"));
		alphaNumeric = new TextureRegion[41];
		for(int j=0; j<5; j++){
			for(int k=0; k<8; k++){
				alphaNumeric[k+j*8] = load("data/other/alpha_num.png", k*16, j*16, 16, 16);
			}
		}
		alphaNumeric[alphaNumeric.length-1] = load("data/other/alpha_num.png", 7*16, 4*16, 8, 16);
	}
	
	public static void loadBullets(){
		horizontalBullet = load("data/other/Bullets.png", 0, 0, 20, 6);
		verticalBullet = load("data/other/Bullets.png", 25, 0, 6, 20);
		diagBullet1 = load("data/other/Bullets.png", 36, 0, 17, 17);
		diagBullet2 = load("data/other/Bullets.png", 61, 0, 16, 17);
	}
	
	public static void loadButtons (){
		startGameButtonUnpressed = load("data/other/Buttons.png", 0, 90, 320, 40);
		startGameButtonPressed = load("data/other/Buttons.png", 0, 40, 320, 40);
		levelOneButtonUnpressed = load("data/other/Buttons.png", 0, 0, 210, 30);
		levelOneButtonPressed = load("data/other/Buttons.png", 300, 0, 210, 30);
	}
	public static TextureRegion load (String name, int x, int y, int width, int height) {
		Texture texture = new Texture(Gdx.files.internal(name));
		TextureRegion region = new TextureRegion(texture, x, y, width, height);
		region.flip(false, true);
		return region;
	}
	
	public static TiledMapRenderer loadLevel (FileHandle mapHandle, FileHandle packFile, FileHandle baseDir) {
		int blockWidth = MAP_WIDTH/3;
		int blockHeight = MAP_HEIGHT/3;
		
		startTime = System.currentTimeMillis();
        map = TiledLoader.createMap(mapHandle); //MAKE MAP
        endTime = System.currentTimeMillis();
		System.out.println("Loaded map in " + (endTime - startTime) + "mS");
		
        atlas = new TileAtlas(map, packFile, baseDir);
        
        startTime = System.currentTimeMillis();
		renderer = new TiledMapRenderer(map, atlas, blockWidth, blockHeight);
		endTime = System.currentTimeMillis();
		System.out.println("Created cache in " + (endTime - startTime) + "mS");
		
		return renderer;
	}
	
	public static int[][] loadProperties(String name) {
		int[][] properties = new int[10][15];
		try {
			Scanner scanner =  new Scanner(new File(name));
			try {
				while (scanner.hasNextInt()) {
					for (int i=0; i<10; i++) {
						for (int j=0; j<15; j++) {
							properties[i][j] = scanner.nextInt();
						}
					}
				}
			} finally {
				scanner.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return properties;
	}
	
	public static Animation loadAnimation (String name, int width, int height) {
		Texture texture = new Texture(Gdx.files.internal(name));
		TextureRegion[] regions = TextureRegion.split(texture, width, height)[0];
		for(TextureRegion region : regions) {
			region.flip(false,true);
		}
		return new Animation(0.25f, regions);
	}
}
