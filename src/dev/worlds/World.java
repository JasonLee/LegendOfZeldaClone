package dev.worlds;

import java.awt.Graphics;

import dev.entities.EntityManager;
import dev.entities.creature.Gannon;
import dev.entities.creature.OldMan;
import dev.entities.creature.Player;
import dev.entities.creature.Slime;
import dev.Handler;
import dev.entities.statics.Chest;
import dev.entities.statics.DownStair;
import dev.entities.statics.Rock;
import dev.entities.statics.Tree;
import dev.entities.statics.UpStair;
import dev.tiles.Tile;
import dev.utils.Utils;

public class World {

	private Handler handler ;
	private int width, height;
	private int spawnX, spawnY;
	private int[][] tiles;
	public static boolean stairSpawn = false;
	//Entities
	private EntityManager entityManager;
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public World(Handler handler, String path){
		this.handler = handler;
		loadWorld(path);
		
		if(path == "res/worlds/world1.txt"){
		entityManager = new EntityManager(handler, new Player(handler, spawnX, spawnY));
		entityManager.addEntity(new Tree(handler, 32 ,100));
		entityManager.addEntity(new Tree(handler, 32 ,250));
		entityManager.addEntity(new Tree(handler, 32 ,380));
		entityManager.addEntity(new Tree(handler, 175 ,100));
		entityManager.addEntity(new Rock(handler, 500 ,200));
		entityManager.addEntity(new DownStair(handler, 500, 100));
		entityManager.addEntity(new UpStair(handler, 532, 100));
		//entityManager.addEntity(new Slime(handler, 500, 600));
		//entityManager.addEntity(new Slime(handler, 400, 300));
		entityManager.addEntity(new OldMan(handler, 300, 300));
		entityManager.addEntity(new Gannon(handler, 400, 400));
		entityManager.addEntity(new Slime(handler, 800, 120));
		
		
		entityManager.addEntity(new Chest(handler, 600, 300));
		
	
		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);
		stairSpawn = true;
		
	
		}else if(path == "res/worlds/cave001.txt"){
			entityManager = new EntityManager(handler, new Player(handler, 50, 50));	
			entityManager.getPlayer().setX(spawnX);
			entityManager.getPlayer().setY(spawnY);
			entityManager.addEntity(new DownStair(handler, 150, 150));
			entityManager.addEntity(new Gannon(handler, 1056, 384));
			entityManager.addEntity(new UpStair(handler, 182, 150));
	
		}else if(path == "res/worlds/maze.txt"){
			entityManager = new EntityManager(handler, new Player(handler, 50, 50));	
			entityManager.getPlayer().setX(spawnX);
			entityManager.getPlayer().setY(spawnY);
			entityManager.addEntity(new Gannon(handler, 1376, 192));
			entityManager.addEntity(new DownStair(handler, 80 , 144));
			entityManager.addEntity(new UpStair(handler, 112 , 144));
			
		}
		
	}
	
	public void tick(){
		entityManager.tick();
	}
	
	public void render(Graphics g){
		int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH);
		int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 1);
		int yStart = (int) Math.max(0 , handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);
		int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT + 1);
		
		
		
		for(int y = yStart; y < yEnd; y++){
			for(int x = xStart; x < xEnd; x++){
				getTile(x,y).render(g,(int)(x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset()),
						(int)(y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
			}
		}
		//Entities
		//g.drawImage(Assets.downStair,(int) (300 - handler.getGameCamera().getxOffset()),(int) (100 - handler.getGameCamera().getyOffset()) ,null);
		entityManager.render(g);
		
		Player.inv.render(g);
	}
	
	public Tile getTile(int x, int y){
		if(x < 0 || y < 0 || x >= width || y >= height)
			return Tile.grassTile;
		
		Tile t = Tile.tiles[tiles[x][y]];
		if (t == null) 
			return Tile.grassTile;
		return t;
	}
	
	private void loadWorld(String path){
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		
		tiles = new int[width][height];
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
			}
		}
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	
}
