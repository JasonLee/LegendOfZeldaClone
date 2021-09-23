package dev.entities.statics;

import java.awt.Graphics;

import dev.Handler;
import dev.gfx.Assets;
import dev.tiles.Tile;

public class UpStair extends StaticEntity{
	
	public static boolean teleportUp = false;
	

	public UpStair(Handler handler, float x, float y){
		super(handler, x, y, Tile.TILEWIDTH / 2, Tile.TILEHEIGHT / 2);
	
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 0;
		bounds.height = 0;
		
		ibounds.x = 5;
		ibounds.y = 0;
		ibounds.width = 25;
		ibounds.height = 20;
		
		cbounds.x = 0;
		cbounds.y = 0;
		cbounds.width = 0;
		cbounds.height = 0;
		
	}

	
	
	@Override
	public void tick() {
		
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.upStair, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}



	@Override
	public void interact() {
		teleportUp = true;
		//gsm.setCurrentStat(GameStateManager.WORLD2STATE);
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