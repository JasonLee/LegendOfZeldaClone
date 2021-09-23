package dev.entities.creature;

import java.awt.Graphics;

import dev.Handler;
import dev.entities.statics.StaticEntity;
import dev.gfx.Animation;
import dev.gfx.Assets;
import dev.inventory.Item;

public class OldMan extends StaticEntity{
	
	private Animation animOldMan;
	private boolean state = false;
	
	public OldMan(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		this.x = x;
		this.y = y;
		bounds.x = 2;
		bounds.y = 0;
		bounds.width = 30;
		bounds.height = 32;
		
		ibounds.x = 0;
		ibounds.y = 32;
		ibounds.width = 64;
		ibounds.height = 64;
		
		animOldMan = new Animation(300, Assets.old_Man);
	
	}

	@Override
	public void tick() {
		animOldMan.tick();
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(animOldMan.getCurrentFrame(),(int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), null);
		}
	

	@Override
	public void interact() {
		if (!state){
			Player.playerGold += 50;
			state = true;
			Player.inv.addItem(Item.SHARP_SWORD);
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
