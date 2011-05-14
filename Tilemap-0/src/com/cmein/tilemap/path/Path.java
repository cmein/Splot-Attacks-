package com.cmein.tilemap.path;

import com.cmein.tilemap.enemy.DIR;

public class Path {
	private int[][] waypoints;
	
	public Path(int[][] waypoints){
		this.waypoints = waypoints;
	}
	
	public int[] getNext(int i){
		return waypoints[i];
	}
	
	public int getSize(){
		return waypoints.length;
	}
}
