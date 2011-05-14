package com.cmein.tilemap.tower;

public class ThoriumTower extends Tower{
	private final double radius = 100;
	public static int cost=10;
	
	
	public ThoriumTower(int x, int y){
		super(x,y,5,0.5,0,false,null);
	}
	

	public boolean inRange(int x, int y, int xa, int ya){
		if ((x - xa)*(x - xa) + (y - ya)*(y - ya) < radius*radius){
			return true;
		}
		return false;
	}



	
}
