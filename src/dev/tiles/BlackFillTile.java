package dev.tiles;

import dev.gfx.Assets;

public class BlackFillTile extends Tile{

	public BlackFillTile(int id) {
		super(Assets.blackFill, id);
		
	}
	@Override
	public boolean isSolid(){
		return true;
	}

}

