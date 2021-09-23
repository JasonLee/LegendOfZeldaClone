package dev.states;

import java.awt.Color;
import java.awt.Graphics;

import dev.Handler;
import dev.audio.AudioPlayer;
import dev.gfx.Assets;

public class DeathState extends GameState{

	private AudioPlayer bgMusic;
	
	public DeathState(GameStateManager gsm, Handler handler) {
		super(gsm,handler);
		bgMusic = new AudioPlayer("/music/SFX/death jingle.mp3");
		bgMusic.play();
	}

	@Override
	public void tick() {
		if(handler.getKeyManager().enter){
			System.exit(1);
		}
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawString("Hello", 300, 300);
		g.drawImage(Assets.deadScreen, 0 , 0, null);
		
	}

}
