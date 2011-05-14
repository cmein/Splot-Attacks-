package com.cmein.tilemap;

import com.badlogic.gdx.Input.Buttons;
//import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class Input implements InputProcessor {
	public static final int CHANGE = 0;
	
	public boolean[] buttons = new boolean[64];
	public boolean[] oldButtons = new boolean[64];
	
	public void set(int key, boolean down) {
		int button = -1;
		if (key == 0) button = CHANGE;
		
		if (button >=0) {
			buttons[button] = down;
			System.out.println(down);
		}
	}
	
	public void tick() {
		for (int i=0; i<buttons.length; i++) {
			oldButtons[i] = buttons[i];
		}
	}
	
	public void releaseAllKeys(){
		for (int i=0; i<buttons.length; i++) {
			buttons[i]= false;
		}
	}
	
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}
	
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		set(button, true);
		//System.out.println(getButtonString(button));
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		set(button, false);
		//System.out.println(getButtonString(button));
		return false;
	}
	
	@SuppressWarnings("unused")
	private String getButtonString(int button) {
        if(button == Buttons.LEFT)
                return "left";
        if(button == Buttons.RIGHT)
                return "right";
        if(button == Buttons.MIDDLE)
                return "middle";
        return "left";
}
}
