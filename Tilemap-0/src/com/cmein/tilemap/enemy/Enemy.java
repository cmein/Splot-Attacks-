package com.cmein.tilemap.enemy;

import com.badlogic.gdx.math.Vector2;
import com.cmein.tilemap.path.Path;

public abstract class Enemy {
	public int health;
	public Vector2 pos;
	public boolean isDead;
	public boolean isTarget;
	public float stateTime;
	private DIR currentDir;
	private Path path;
	private float velocity;
	private int currentPointIndex;
	public static final int MAP_HEIGHT = 320;
	public static final int MAP_WIDTH = 480;
	private int damage;
	private int pay;
	
	
	public Enemy(float x, float y, boolean isDead, boolean isTarget, int health, float stateTime, DIR dir, Path path, int ind, int damage, int pay){
		pos = new Vector2(x,y);
		this.isDead = isDead;
		this.isTarget = isTarget;
		this.health = health;
		this.stateTime = stateTime;
		this.currentDir = dir;
		this.path = path;
		//this.velocity = velocity;
		this.currentPointIndex = ind;
		this.damage = damage;
		this.pay = pay;
	}

	public void decideDirection(Path path, int ind, float deltaTime, DIR currentDir) {
		switch (currentDir) {
		case RIGHT:
			if (pos.x < path.getNext(ind)[0]) {
				return;
			} else {
				if (ind + 1 < path.getSize() - 1) {
					if (path.getNext(ind + 1)[1] > pos.y) {
						this.currentDir = DIR.DOWN;
					} else {
						this.currentDir = DIR.UP;
					}
					this.currentPointIndex++;
				}
			}
		case LEFT:
			if (pos.x > path.getNext(ind)[0]) {
				return;
			} else {
				if (ind + 1 < path.getSize() - 1) {
					if (path.getNext(ind + 1)[1] > pos.y) {
						this.currentDir = DIR.DOWN;
					} else {
						this.currentDir = DIR.UP;
					}
					this.currentPointIndex++;
				}
			}

		case UP:
			if (pos.y > path.getNext(ind)[1]) {
				return;
			} else {
				if (path.getNext(ind + 1)[0] > pos.x) {
					this.currentDir = DIR.RIGHT;
				} else {
					this.currentDir = DIR.LEFT;
				}
				this.currentPointIndex++;
				// }
			}
		case DOWN:
			if (pos.y < path.getNext(ind)[1]) {
				return;
			} else {
				if (ind + 1 < path.getSize() - 1) {
					if (path.getNext(ind + 1)[0] > pos.x) {
						this.currentDir = DIR.RIGHT;
					} else {
						this.currentDir = DIR.LEFT;
					}
					this.currentPointIndex++;
				}
			}
		}
	}
	public float getX() {
		return pos.x;
	}

	public float getY() {
		return pos.y;
	}
	
	public boolean isTarget() {
		return isTarget;
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	public void removeTarget() {
		isTarget = false;
	}

	public void setTarget() {
		isTarget = true;
	}
	
	public void doDamage(int dmg) {
		this.health -= dmg;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public int getPay() {
		return this.pay;
	}
	
	public float getStateTime() {
		return this.stateTime;
	}
	
	public DIR getDir(){
		return currentDir;
	}
	public Path getPath(){
		return path;
	}
	
	public int getCurrentPointIndex(){
		return currentPointIndex;
	}
	
	public void setDir(DIR dir1){
		this.currentDir = dir1; 
	}
	
	public abstract void update(float deltaTime);
	
}
