package com.cmein.tilemap.tower;

import com.cmein.tilemap.enemy.Enemy;

public abstract class Tower {
	private int x;
	private int y;
	private int dmg;
	private double attackRate;
	private float shotInterval;
	private boolean hasTarget;
	private Enemy currentTarget;
	
	public Tower(int x,int y,int dmg,double attackRate,float shotInterval,boolean hasTarget,Enemy currentTarget){
		this.x = x;
		this.y = y;
		this.dmg = dmg;
		this.attackRate = attackRate;
		this.shotInterval = shotInterval;
		this.hasTarget = hasTarget;
		this.currentTarget = currentTarget;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getDamage(){
		return dmg;
	}
	
	public double getAttackRate(){
		return attackRate;
	}
	
	public float getShotInterval(){
		return this.shotInterval;
	}
	
	public void setShotInterval(float deltaTime){
		this.shotInterval += deltaTime;
	}
	
	public void resetShotInterval(){
		this.shotInterval = 0;
	}
	
	public boolean hasTarget(){
		return hasTarget;
	}
	public void setTarget(Enemy enemy){
		enemy.setTarget();
		this.currentTarget = enemy;
		this.hasTarget = true;
	}
	public void removeTarget(Enemy enemy){
		enemy.removeTarget();
		this.hasTarget = false;
		this.currentTarget = null;
	}
	
	public Enemy currentTarget(){
		return this.currentTarget;
	}
	public abstract boolean inRange(int splotX, int splotY, int towerX, int towerY);
}
