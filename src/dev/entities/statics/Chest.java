package dev.entities.statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.entities.creature.Player;
import dev.Handler;
import dev.audio.AudioPlayer;
import dev.gfx.Assets;

public class Chest extends StaticEntity {

	BufferedImage image = Assets.chestClosed;
	boolean state = false;
	int align = 32;
	private AudioPlayer bgMusic;
	
	public Chest(Handler handler, float x, float y){
		super(handler, x, y, 44, 43);
	
		bounds.x = align + 5;
		bounds.y = align;
		bounds.width = 30;
		bounds.height = 26;
		
		ibounds.x = 0;
		ibounds.y = 0;
		ibounds.width = 96;
		ibounds.height = 96;
		
		cbounds.x = 0;
		cbounds.y = 0;
		cbounds.width = 0;
		cbounds.height = 0;
		
		bgMusic = new AudioPlayer("/music/SFX/chest open.mp3");
	}

	
	
	@Override
	public void tick() {
		
		
	}

	@Override
	public void render(Graphics g) {
	//g.setColor(Color.red);
	//g.fillRect((int) (x + ibounds.x - handler.getGameCamera().getxOffset()),
	//(int) (y + ibounds.y - handler.getGameCamera().getyOffset()),
	//ibounds.width, ibounds.height);
		
		g.drawImage(image, (int) (x - handler.getGameCamera().getxOffset() + align), (int) (y - handler.getGameCamera().getyOffset() + align), width, height, null);
		
		
	}



	@Override
	public void interact() {
	System.out.println("Interacts");
	if (!state){
		bgMusic.play();
		image = Assets.chestOpen;
		Player.playerGold += 50;
		state = true;
	//}else{
		//image = Assets.chestClosed;
		//state = false;
	}
	}

	@Override
	public void death() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void damaged() {
		// TODO Auto-generated method stub
		
	}
	
	
}