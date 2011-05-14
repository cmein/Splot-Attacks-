package com.cmein.tilemap.screen;

import com.cmein.tilemap.Art;
import com.cmein.tilemap.tower.ThoriumTower;

public class TowerScreen {
	public static int[][] propertyMap = Art.propertyMap1;
	public static int selectedTileX;
	public static int selectedTileY;
	public ThoriumTower tower1;
	
	
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
}
