package com.cmein.tilemap.bullet;

import com.cmein.tilemap.enemy.DIR;
import com.cmein.tilemap.enemy.Enemy;
import com.cmein.tilemap.tower.Tower;

public class ThoriumBullet extends Bullet{
	public float stateTime;
	private int x;
	private int y;
	private DIR offset;
	private final int BULLET_VELOCITY = 10;
	
	public ThoriumBullet(int x, int y, Enemy target, Tower tower){
		super(x,y,target.getX(),target.getY(),target,(float)Math.random(), false, tower);
        this.x=x;
        this.y=y;
	}
	
	public float bulletOffset(DIR dir){
		if(dir == DIR.UP){
			return -1;
		}else if(dir == DIR.DOWN){
			return 1;
		}else if(dir == DIR.LEFT){
			return -1;
		}else{
			return 1;
		}	
	}
	
	

	public void update(float deltaTime) {
		offset = target.getDir();
		
		//quad4
		if(x < target.getX() && y < target.getY()){
			if(pos.x < target.getX() && pos.y < target.getY()){
				pos.x -= BULLET_VELOCITY*deltaTime*(x-target.getX());
				pos.y -= BULLET_VELOCITY*deltaTime*(y-target.getY());
			}else{
				hitTarget = true;
			}
		//quad1
		}else if(x < target.getX() && y > target.getY()){
			if(pos.x < target.getX() && pos.y > target.getY()){
				pos.x -= BULLET_VELOCITY*deltaTime*(x-target.getX());
				pos.y -= BULLET_VELOCITY*deltaTime*(y-target.getY());
			}else{
				hitTarget = true;
			}
		//quad3	
		}else if(x > target.getX() && y < target.getY()){
			if(pos.x > target.getX() && pos.y < target.getY()){
				pos.x -= BULLET_VELOCITY*deltaTime*(x-target.getX());
				pos.y -= BULLET_VELOCITY*deltaTime*(y-target.getY());
			}else{
				hitTarget = true;
			}
		//quad2	
		}else if(x > target.getX() && y > target.getY()){
			if(pos.x >= target.getX() && pos.y >= target.getY()){
				pos.x -= BULLET_VELOCITY*deltaTime*(x-target.getX());
				pos.y -= BULLET_VELOCITY*deltaTime*(y-target.getY());
			}else{
				hitTarget = true;
			}
		}
	}

	




	



}
