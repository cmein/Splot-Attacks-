package com.cmein.tilemap;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class TileMapDesktop {
	public static void main (String[] argv) {
		new JoglApplication(new TileMap(), "TEST", 480, 320, false);
	}
}
