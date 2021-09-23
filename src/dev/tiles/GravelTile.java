package dev.tiles;

import dev.gfx.Assets;

public class GravelTile extends Tile{

	public GravelTile(int id) {
		super(Assets.gravel, id);
	}

	@Override
	public boolean isSolid(){
		return true;
}
}
