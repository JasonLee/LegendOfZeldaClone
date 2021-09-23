package dev.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {

	//STATIC
	
	public static Tile[] tiles = new Tile [256];
	public static Tile grassTile = new GrassTile(0);
	public static Tile gravelTile = new GravelTile(1);
	public static Tile sandTile = new SandTile(2);
	public static Tile waterTile = new WaterTile(3);
	public static Tile treeTile = new TreeTile(4);
	public static Tile dirtTile = new DirtTile(5);
	public static Tile downStairTile = new DownStairTile(6);
	public static Tile caveGroundTile = new	CaveGroundTile(7);
	public static Tile blackFillTile = new BlackFillTile(8);
	//CLASS
	
	public static final int TILEWIDTH = 64, TILEHEIGHT = 64;
	
	protected BufferedImage texture;
	protected final int id;
	
	public Tile(BufferedImage texture, int id){
		this.texture = texture;
		this.id = id;
		
		tiles[id] = this;
		
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics g, int x, int y){
		g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
	}
	
	public boolean isSolid(){
		return false;
	}
	
	public int getId(){
		return id;
	}
	
}
