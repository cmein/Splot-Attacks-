package com.cmein.tilemap.bullet;

import com.badlogic.gdx.math.Vector2;
import com.cmein.tilemap.enemy.Enemy;
import com.cmein.tilemap.tower.Tower;

public abstract class Bullet {
	public Vector2 pos;
	public float targetX;
	public float targetY;
	public float startX;
	public float startY;
	public Enemy target;
	public float stateTime;
	public boolean hitTarget;
	public Tower tower;

	public static final int MAP_HEIGHT = 320;
	public static final int MAP_WIDTH = 480;
	
	public Bullet (float x, float y, float xa, float ya, Enemy target, float stateTime, boolean hitTarget, Tower tower){
		pos = new Vector2(x,y);
		this.startX = x;
		this.startY = y;
		this.targetX = xa;
		this.targetY = ya;
		this.target = target;
		this.stateTime = stateTime;
		this.hitTarget = hitTarget;
		this.tower = tower;
	}
	
	//Current X of bullet
	public float getX() {
		return pos.x;
	}
	
	//Current Y of bullet
	public float getY() {
		return pos.y;
	}
	
	//Starting X of bullet's target
	public float startTargetX() {
		return targetX;
	}
	
	//Starting Y of bullet's target
	public float startTargetY() {
		return targetY;
	}
	
	//Starting X of bullet
	public float startX(){
		return startX;
	}
	
	//Starting Y of bullet
	public float startY(){
		return startY;
	}
	
	//Current X of bullet's target
	public float getTargetX() {
		return target.getX();
	}

	//Current Y of bullet's target
	public float getTargetY() {
		return target.getY();
	}
	
	public float getStateTime() {
		return stateTime;
	}
	
	public boolean hitTarget(){
		return hitTarget;
	}
	
	public Tower getTower(){
		return tower;
	}
	
	public abstract void update(float deltaTime);
}
