package dev.states;

import java.awt.Graphics;

import dev.Handler;

public class GameStateManager {

	private GameState[] gameStates;
	private int currentState;
	private Handler handler;
	
	public static final int NUMGAMESTATES = 16;
	public static final int MENUSTATE = 0;
	public static final int WORLD1STATE = 1;
	public static final int WORLD2STATE = 2;
	public static final int WORLD3STATE = 3;
	public static final int DEATHSTATE = 10;
	
	public GameStateManager(Handler handler){
		this.handler = handler;
		
		gameStates = new GameState[NUMGAMESTATES];
		currentState = MENUSTATE;
		loadState(currentState);
	}
	private void loadState(int state) {
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this, handler);
		else if(state == WORLD1STATE)
			gameStates[state] = new World1State(this, handler);
		else if(state == WORLD2STATE)
			gameStates[state] = new World2State(this, handler);
		else if(state == WORLD3STATE)
			gameStates[state] = new World3State(this, handler);
		else if(state == DEATHSTATE)
			gameStates[state] = new DeathState(this, handler);
	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}
	
	public void tick(){
		if(gameStates[currentState] != null) 
			gameStates[currentState].tick();
	}
	
	public void render(Graphics g){
		if(gameStates[currentState] != null) 
			gameStates[currentState].render(g);
		
	}
	
}
