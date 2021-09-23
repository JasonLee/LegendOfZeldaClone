package dev.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dev.entities.creature.Player;
import dev.entities.statics.DownStair;
import dev.entities.statics.UpStair;
import dev.Game;
import dev.Handler;
import dev.audio.AudioPlayer;
import dev.gfx.UIManager;
import dev.worlds.World;

public class World2State extends GameState {

	private World world;
	private UIManager uiManager;
	private Font font = new Font("Arial", Font.PLAIN, 14);
	private AudioPlayer bgMusic;


	
	public World2State(GameStateManager gsm,Handler handler){
		super(gsm, handler);
		world = new World(handler, "res/worlds/cave001.txt" );
		uiManager = new UIManager();
		bgMusic = new AudioPlayer("/music/world2/Dungeon music.mp3");
		bgMusic.play();
		handler.setWorld(world);

		
	}
	@Override
	public void tick() {
		world.tick();
		uiManager.tick();
		if(UpStair.teleportUp){
			bgMusic.stop();
			gsm.setState(GameStateManager.WORLD1STATE);
			UpStair.teleportUp = false;
		}else if(DownStair.teleportDown){
			bgMusic.stop();
			gsm.setState(GameStateManager.WORLD3STATE);
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
