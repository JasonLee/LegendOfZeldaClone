package dev.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{

	private boolean[] keys;
	public boolean up, down, left, right, basic, interact, enter, inventory, test, test2, esc;

	
	
	public KeyManager() {
		keys = new boolean[256];
	}
	
	public void tick(){
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left =  keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		basic = keys[KeyEvent.VK_J];
		interact = keys[KeyEvent.VK_K];
		inventory = keys[KeyEvent.VK_P];
		test = keys[KeyEvent.VK_O];
		test2 = keys[KeyEvent.VK_I];
		enter = keys[KeyEvent.VK_ENTER];
		esc = keys[KeyEvent.VK_ESCAPE];
		
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		keys[e.getKeyCode()] = true;
		
		System.out.println("Pressed");
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
