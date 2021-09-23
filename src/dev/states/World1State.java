package dev.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dev.entities.creature.Player;
import dev.entities.statics.DownStair;
import dev.Game;
import dev.Handler;
import dev.audio.AudioPlayer;
import dev.gfx.UIManager;
import dev.worlds.World;

public class World1State extends GameState {

	private World world;
	private UIManager uiManager;
	private Font font = new Font("Arial", Font.PLAIN, 14);
	private AudioPlayer bgMusic;


	
	public World1State(GameStateManager gsm, Handler handler){
			super(gsm, handler);	
			init();

		
	}
	
	private void init(){
		world = new World(handler, "res/worlds/world1.txt");
		uiManager = new UIManager();
		bgMusic = new AudioPlayer("/music/world1/SRW Z2 - The Wings of a Boy that Killed Adolescence.mp3");
		bgMusic.play();
		handler.setWorld(world);
	}
	
	@Override
	public void tick() {
		world.tick();
		uiManager.tick();
		if(DownStair.teleportDown){
			bgMusic.stop();
			gsm.setState(GameStateManager.WORLD2STATE);
			DownStair.teleportDown = false;
		}else if(Player.dead){
			bgMusic.stop();
			gsm.setState(GameStateManager.DEATHSTATE);
		}

	}

	@Override
	public void render(Graphics g) {
		world.render(g);
		uiManager.render(g);
		g.setColor(Color.RED);
		g.setFont(font);
		g.drawString("FPS: " + Game.frames, 20, 20);

	}

}
