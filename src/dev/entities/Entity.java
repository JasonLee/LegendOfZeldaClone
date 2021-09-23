package dev.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.entities.creature.Player;
import dev.Handler;

public abstract class Entity {

	protected Handler handler;		// This is the SuperClass, all entities inherits the protected and public 
	protected float x, y;			// variables and methods. Best not to tinker too much here
	

	protected int width, height;	// This basically gives the entities its properties
	
	public static final int DEFAULT_HEALTH = 100;
	public static final int DEFAULT_ATTACK = 0;
	
	protected int health;
	protected int maxHealth;
	protected int attack;
	protected boolean active = true;
	protected Rectangle bounds; 
	protected Rectangle ibounds;
	protected Rectangle cbounds;
	protected Rectangle playerOnlyBounds;

	protected Graphics g;
	public int range;
	
	public Entity(Handler handler, float x, float y, int width, int height){
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		maxHealth = DEFAULT_HEALTH;
		health = DEFAULT_HEALTH;
		attack = DEFAULT_ATTACK;
		
		bounds = new Rectangle(0, 0, width , height);	//Creates Bounds for all entities
		ibounds = new Rectangle(0, 0, width , height);
		cbounds = new Rectangle(0, 0, width , height);
		playerOnlyBounds = new Rectangle(0, 0, 0, 0);
	}
	
	public abstract void tick();				//abstract methods not instantiated here, instantiated in subclass.
	public abstract void render(Graphics g);
	public abstract void interact();
	public abstract void damaged();
	public abstract void death();

	public void hurt(int amt){
		health -= amt;
		damaged();
		if(health <= 0){
			active = false;
			death();
		}
	}
	
	public void hurtPlayer(int amt){
		Player.damage = true;
		
		Player.playerHp -= amt;
		if(Player.playerHp <= 0){
			active = false;
			death();
		}
	}
	

	public boolean checkEntityCollisions(float xOffset, float yOffset){		//Checks if you bump into solid tiles/objects
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this))
				continue;
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
				return true;
		}
		return false;
	}
	
	public boolean checkCombatCollisions(float xOffset, float yOffset){		
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this))
				continue;
			if(e.getCombatBounds(0f, 0f).intersects(getCombatBounds(xOffset, yOffset)))
				return true;
		}
		return false;
	}
	
	public boolean checkEntityInteraction(float xOffset, float yOffset){	//Checks if you are in range to interact with another object
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this))
				continue;
			if(e.getInteractionBounds(0f, 0f).intersects(getInteractionBounds(xOffset, yOffset)))
				return true;
		}
		return false;
	}
	
	public Entity getInteractionEntity(float xOffset, float yOffset){	//Checks which entity you interact with
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this))
				continue;
			if(e.getInteractionBounds(0f, 0f).intersects(getInteractionBounds(xOffset, yOffset)))
				return e;
		}
		return null;
	}


	
	public Rectangle getCollisionBounds(float xOffset, float yOffset){
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}
	
	
	public Rectangle getInteractionBounds(float xOffset, float yOffset){
		return new Rectangle((int) (x), (int) (y), ibounds.width, ibounds.height);
	}
	
	public Rectangle getCombatBounds(float xOffset, float yOffset){
		return new Rectangle((int) (x + cbounds.x + xOffset), (int) (y + cbounds.y + yOffset), cbounds.width, cbounds.height);
	}
	
	public Rectangle getPlayerOnlyBounds(float xOffset, float yOffset){
		return new Rectangle((int) (x + playerOnlyBounds.x + xOffset), (int) (y + playerOnlyBounds.y + yOffset), playerOnlyBounds.width, playerOnlyBounds.height);
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
