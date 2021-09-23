package dev.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dev.Game;
import dev.Handler;
import dev.gfx.Assets;
import dev.input.MouseManager;

public class MenuState extends GameState{
	
	private Font titleFont, font;
	private int currentChoice = 2;
	private String[] options = {
			"Start",
			"Options",
			"Quit"
		};
	
	//Timer stuffs
	long lastShoot = System.currentTimeMillis();
	final long threshold = 100; // 500msec = half second
	
	public MenuState(GameStateManager gsm, Handler handler){
		super(gsm, handler);
		titleFont = new Font("Arial", Font.PLAIN, 28);
		font = new Font("Arial", Font.PLAIN, 20);
		
	}
	


	private void handleInput() {
		long now = System.currentTimeMillis();
	       if (now - lastShoot > threshold){
	    	   if (handler.getKeyManager().down){
	    	   		if(currentChoice > 0){
	    	   			currentChoice--;
	    	   		}
	    	   	}if(handler.getKeyManager().up){
	    	   		if(currentChoice < options.length - 1){
	    	   			currentChoice++;
	    	   		}	
	    	   	}if(handler.getKeyManager().enter){
	    	   		selected();
	    	   	}
	    	   lastShoot = now;
	       }

		
	}
	private void selected() {
		if(currentChoice == 2){
			gsm.setState(GameStateManager.WORLD1STATE);
		}else if(currentChoice == 1){
			System.out.println("test");
		}else if(currentChoice == 0){
			System.exit(1);
		}
		
	}
	
	public void tick() {
		//System.out.println(handler.getMouseManager().getMouseX() + " " + handler.getMouseManager().getMouseY());
		handleInput();
	}
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.titleScreen, 0, 0 , null);
		g.setColor(Color.red);
		g.drawString("FPS: " + Game.frames, 20, 20);
		g.setColor(Color.white);
		g.setFont(font);
		g.drawString("Start", 290, 300);
		g.drawString("Options", 290, 330);
		g.drawString("Quit", 290, 360);
		g.fillRect(MouseManager.getMouseX(), MouseManager.getMouseY(), 8, 8);
		
		g.setColor(Color.blue);
		if(currentChoice == 0){
			g.fillRect(270, 350, 8, 8);
		}else if(currentChoice == 1){
			g.fillRect(270, 320, 8, 8);
		}else if(currentChoice == 2){
			g.fillRect(270, 290, 8, 8);
		}

		
		
	}
		
		
}

