package com.cmein.tilemap;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class SplotAttacksDesktop {
	public static void main (String[] argv) {
		new JoglApplication(new SplotAttacks(), "TEST", 480, 320, false);
	}
}
