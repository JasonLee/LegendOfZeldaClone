package dev.entities.statics;

import java.awt.Graphics;

import dev.Handler;
import dev.gfx.Assets;
import dev.tiles.Tile;

public class Tree extends StaticEntity {
 int x = 0;
	public Tree(Handler handler, float x, float y){
		super(handler, x, y, Tile.TILEWIDTH * 2, Tile.TILEHEIGHT * 2);
	
		health = 100;
		
		bounds.x = 45;
		bounds.y = 100;
		bounds.width = 26;
		bounds.height = 5;
		
		ibounds.x = 0;
		ibounds.y = 0;
		ibounds.width = 0;
		ibounds.height = 0;
		
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
		g.drawImage(Assets.tree, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
	}



	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
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
