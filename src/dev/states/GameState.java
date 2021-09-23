package dev.states;

import java.awt.Graphics;

import dev.Handler;

public abstract class GameState {

	protected Handler handler;
	protected GameStateManager gsm;
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public GameState(GameStateManager gsm, Handler handler){
		this.gsm = gsm;
		this.handler = handler;
		
	}
	
}
