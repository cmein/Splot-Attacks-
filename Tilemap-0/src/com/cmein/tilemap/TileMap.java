package com.cmein.tilemap;

//import java.io.IOException;
//import java.util.Arrays;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.InputProcessor;
//import com.badlogic.gdx.input.RemoteInput;
//import com.badlogic.gdx.files.FileHandle;
//import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
//import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
//import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
//import com.badlogic.gdx.graphics.g2d.tiled.TiledMapRenderer;
//import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
//import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
//import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.math.Vector3;
import com.cmein.tilemap.screen.MenuScreen;
import com.cmein.tilemap.screen.Screen;

public class TileMap implements ApplicationListener {
	private Screen screen;
	private Input input = new Input();
	private float accum = 0;
	
	/*
	private static final boolean automove = false;
	
	private static final int SCREEN_WIDTH = 480;
	private static final int SCREEN_HEIGHT = 320;
	
	private int[] layersList = {0};
	private Screen screen;
	
	SpriteBatch spriteBatch;
	BitmapFont font;
	
	OrthographicCamera camera;
	OrthoCamController camController;
	Vector3 camDirection = new Vector3(1, 1, 0);
	Vector2 maxCamPosition = new Vector2(0, 0);
	
	TiledMapRenderer tiledMapRenderer;
	TiledMap map;
	TileAtlas atlas;
	
	Texture texture;
	
	Vector3 tmp = new Vector3();
	
	SpriteBatch batch;
	ImmediateModeRenderer renderer;
	String ips;
	SpriteBatch extra;
	*/
	
	/*
	private void updateCameraPosition() {
		camera.position.add(camDirection.tmp().mul(Gdx.graphics.getDeltaTime()).mul(60));
		
		if (camera.position.x < 0) {
			camera.position.x = 0;
			camDirection.x = 1;
		}
		if (camera.position.x > maxCamPosition.x) {
			camera.position.x = maxCamPosition.x;
			camDirection.x = -1;
		}
		if (camera.position.y < 0) {
			camera.position.y = 0;
			camDirection.y = 1;
		}
		if (camera.position.y > maxCamPosition.y) {
			camera.position.y = maxCamPosition.y;
			camDirection.y = -1;
		}
	}
	*/

	@Override
	public void create() {
		Art.load();
		Art.loadButtons();
		Art.loadBullets();
		Gdx.input.setInputProcessor(input);
		setScreen(new MenuScreen());
		
		/*
		// Open port for remote input
		RemoteInput receiver = new RemoteInput(8190);
		Gdx.input = receiver;
		ips = Arrays.toString(receiver.getIPs());
		*/
		
		/*
		Gdx.input.setInputProcessor(this);
		*/
		
		/*
		batch = new SpriteBatch();
		*/
		
		/*
		renderer = new ImmediateModeRenderer();
		*/
		
		/*
		extra = new SpriteBatch();
		*/
		
		/*
		// Initialize variables
		int i;
		long startTime, endTime;
		font = new BitmapFont();
		font.setColor(Color.BLUE);
		
		spriteBatch = new SpriteBatch();
		
		//GET CREATED FILES
		FileHandle mapHandle = Gdx.files.internal("data/LEVEL2.tmx");
		FileHandle packfile = Gdx.files.internal("data/LEVEL2 packfile");
		FileHandle baseDir = Gdx.files.internal("data/");
		
		startTime = System.currentTimeMillis();
		map = TiledLoader.createMap(mapHandle); //MAKE MAP
		endTime = System.currentTimeMillis();
		System.out.println("Loaded map in " + (endTime - startTime) + "mS");
		
		atlas = new TileAtlas(map, packfile, baseDir);
		
		int blockWidth = SCREEN_WIDTH/3;
		int blockHeight = SCREEN_HEIGHT/3;
		
		startTime = System.currentTimeMillis();
		tiledMapRenderer = new TiledMapRenderer(map, atlas, blockWidth, blockHeight);
		endTime = System.currentTimeMillis();
		System.out.println("Created cache in " + (endTime - startTime) + "mS");
		*/
		
		/*
		//Add sprites where objects occur
		for (TiledObjectGroup group: map.objectGroups){
			for (TiledObject object: group.objects){
				//TODO: draw the objects
				System.out.println("Object " + object.name + " x,y = " + object.x + "," + object.y
						+ " width,height = " + object.width + "," + object.height);
			}
		}
		
		camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);                
		camera.position.set(tiledMapRenderer.getMapWidthPixels()/2, tiledMapRenderer.getMapHeightPixels()/2 ,0);
		camController = new OrthoCamController(camera);
		Gdx.input.setInputProcessor(camController);
		
		float maxX = tiledMapRenderer.getMapWidthPixels();
		float maxY = tiledMapRenderer.getMapHeightPixels();
		maxCamPosition.set(maxX,maxY);
		*/
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void pause() {
		System.out.println("PAUSED");
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		accum += Gdx.graphics.getDeltaTime();
		while (accum > 1.0f / 60.0f) {
			screen.tick(input);
			input.tick();
			accum -= 1.0f / 60.0f;
		}
		screen.render();
		screen.remove();
		
		/*
		camera.update();
		*/
		
		/*
		if (automove) {
			updateCameraPosition();
		}
		*/
		
		/*
		tiledMapRenderer.getProjectionMatrix().set(camera.combined);
		tmp.set(0,0,0);
		camera.unproject(tmp);
		tiledMapRenderer.render((int)tmp.x, tiledMapRenderer.getMapHeightPixels() - (int)tmp.y, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), layersList);
		*/
		
		/*
		// FPS
		spriteBatch.begin();
		font.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, 20);
		//font.draw(spriteBatch, "InitialCol, LastCol: " + tiledMapRenderer.getInitialCol() + "," + tiledMapRenderer.getLastCol(), 20, 40);
		//font.draw(spriteBatch, "InitialRow, LastRow: " + tiledMapRenderer.getInitialRow() + "," + tiledMapRenderer.getLastRow(), 20, 60);
		//font.draw(spriteBatch, "camera.getScreenToWorldY(0): " + camera.getScreenToWorldY(0), 20, 80);
		spriteBatch.end();
		*/
		
		/*
		// Take accelerometer and compass readings
		batch.begin();
		font.drawMultiLine(batch, "ip: " + ips + "\n" +  
				"accel:" + Gdx.input.getAccelerometerX() + "," + 
							Gdx.input.getAccelerometerY() + "," + 
							Gdx.input.getAccelerometerZ() + "\n" + 
				"compass: " + Gdx.input.getAzimuth() + "," + 
								Gdx.input.getPitch() + "," +
								Gdx.input.getRoll() + "\n" +
				"fps: " + Gdx.graphics.getFramesPerSecond(), 10, 130);
		batch.end();
		*/
		
		/*
		// Take user input coordinates
		extra.begin();
		font.drawMultiLine(extra, "touch: " + Gdx.input.getX() + ", " + Gdx.input.getY() + "\n" +
									"tile : " + tiledMapRenderer.getCol(Gdx.input.getX()) + ", " + tiledMapRenderer.getRow(Gdx.input.getY()), 10, 100);
		extra.end();
		*/
		
		/*
		// Draw a triangle at a point of touch
		renderer.begin(GL10.GL_TRIANGLES);
		for(int i = 0; i < 10; i++) {           
			if(Gdx.input.isTouched(i)) {
				renderer.color(0, 0, 1, 0);
				renderer.vertex(Gdx.input.getX(i) - 20, Gdx.graphics.getHeight() - Gdx.input.getY(i) - 20, 0);
				renderer.color(0, 1, 0, 0);
				renderer.vertex(Gdx.input.getX(i) + 20, Gdx.graphics.getHeight() - Gdx.input.getY(i) - 20, 0);
				renderer.color(1, 0, 0, 0);
				renderer.vertex(Gdx.input.getX(i), Gdx.graphics.getHeight() - Gdx.input.getY(i) + 20, 0);
			}
		}
		renderer.end();
		*/
	}

	@Override
	public void resize(int width, int height) {
		//float aspectRatio = (float) width / (float) height;
		//camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
		//camera.position.set(tiledMapRenderer.getMapWidthPixels()/2, tiledMapRenderer.getMapHeightPixels()/2 ,0);
	}

	@Override
	public void resume() {
		System.out.println("RESUMED");
	}
	
	public void setScreen(Screen newScreen) {
		if (screen != null)
			screen.remove();
		screen = newScreen;
		if (screen != null)
			screen.init(this);
	}
	
}
