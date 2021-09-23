package dev.entities.creature;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import dev.Handler;
import dev.audio.AudioPlayer;
import dev.entities.Entity;
import dev.gfx.Animation;
import dev.gfx.Assets;
import dev.inventory.Inventory;
import dev.inventory.Item;


public class Player extends Creature{
	
	//Inventory
	public static Inventory inv = new Inventory(0, 100, 6);
	
	private HashMap<String, AudioPlayer> sfx;
	
	//Movement
	private boolean xBasic = false;
	public static int direction = 4;
	private boolean pressed = false; 
	private int count = 0;
	
	//Stats
	public static double playerBaseHp = 100;
	public static double playerMaxHp = 100;
	public static double playerHp = 100;
	public static double playerMaxMana = 300;
	public static double playerMana = 300; 
	public static int playerAtk = 0;
	public static int playerDef = 0;
	public static int playerGold = 1000;
	public static float speed = 3f;
	public static boolean weaponEquiped = false;
	public static boolean dead = false;

	public static boolean damage = false;
	private boolean flinching = false;

	private int flinchCount = 1;
	

	//Animations
	private Animation animDown, animRight, animLeft, animUp, animFlinching;
	
	
	//Timer stuffs
	long lastShoot = System.currentTimeMillis();
	final long threshold = 500; // 500msec = half second
	final long threshold2 = 250;
	
	//Attack timer
	private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown;
	
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT);
		this.x = x;
		this.y = y;
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 32;
		bounds.height = 32;
		
		ibounds.x = 0;
		ibounds.y = 0;
		ibounds.width = 32;
		ibounds.height = 32;
		
		cbounds.x = 0;
		cbounds.y = 0;
		cbounds.width = 32;
		cbounds.height = 32;
		
		playerOnlyBounds.x = 0;
		playerOnlyBounds.y = 0;
		playerOnlyBounds.width = 32;
		playerOnlyBounds.height = 32;
		
		attack = 0;
	
		//Animations
		animDown = new Animation(140, Assets.link_down);
		animRight = new Animation(140, Assets.link_right);
		animLeft = new Animation(140, Assets.link_left);
		animUp = new Animation(140, Assets.link_up);
		animFlinching = new Animation(100, Assets.link_Flinching);
		sfx = new HashMap<String, AudioPlayer>();
		sfx.put("sword", new AudioPlayer("/music/player/MC_Link_Sword.mp3"));
	}

	@Override
	public void tick() {
		//Animations
		animDown.tick();
		animRight.tick();
		animLeft.tick();
		animUp.tick();
		animFlinching.tick();
		//Speed
		inv.tick();
		
		if(damage){
			damageRecoil();
		}else{
			getInput();
		}
		checkAttacked();
		move();
		handler.getGameCamera().centreOnEntity(this);	
		
		
	}
	
	private void getInput(){
		xMove = 0;
		yMove = 0;

		if(handler.getKeyManager().interact ){
			long now = System.currentTimeMillis();
		       if (now - lastShoot > threshold2){
		    	   interact();
		    	   lastShoot = now;
		       }
		}
		if(handler.getKeyManager().up){
			yMove = -speed;
			xBasic = false;
		}
		else if(handler.getKeyManager().down){
			yMove = speed;
			xBasic = false;
		}
		else if(handler.getKeyManager().left){
			xMove = -speed;
			xBasic = false;
		}
		else if(handler.getKeyManager().right){
			xMove = speed;
			xBasic = false;
		}
		if(handler.getKeyManager().basic && !handler.getKeyManager().left && !handler.getKeyManager().right && 
		!handler.getKeyManager().up && !handler.getKeyManager().down && pressed == false ){ 
			if(weaponEquiped){
				sfx.get("sword").play();
				count++;
				xBasic = true;
				if (count >= 14) {
					pressed = true;
					count = 0;
					xBasic = false;
				}
			}
		}
		if(!handler.getKeyManager().basic){
			pressed = false;
			xBasic = false;
		}
		if(handler.getKeyManager().inventory ){
			long now = System.currentTimeMillis();
		       if (now - lastShoot > threshold2){
		    	   inv.toggle();
		    	   lastShoot = now;
		       }
		}	
		if(handler.getKeyManager().test){
		//	inv.addItem(Item.SHARP_SWORD);
		//	inv.addItem(Item.BRONZE_SHIELD);
			inv.addItem(Item.HEALTH_POT);
			inv.addItem(Item.MANA_POT);
		//	inv.addItem(Item.BOW_AND_ARROW);
		//	inv.addItem(Item.RING);
		//	inv.addItem(Item.GLOVE);
		//	inv.addItem(Item.PLATE_ARMOR);
			
		}
		if(handler.getKeyManager().test2){
			inv.addItem(Item.SHARP_SWORD , 36);
			
		}
}
	

	 private void checkAttacked() {
		flinchPlayerBounds();
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if(attackTimer < attackCooldown)
			return;
		 
		Rectangle cb = getCollisionBounds(0,0);
		Rectangle ar = new Rectangle();
		int arSize = 32;
		ar.height = arSize;
		ar.width = arSize;
		
		if(xBasic){
			if(direction == 3){ // up
				ar.x = cb.x + cb.width / 2 - arSize / 2;
				ar.y = cb.y - arSize;
			}else if(direction == 4){ // down
				ar.x = cb.x + cb.width / 2 - arSize / 2;
				ar.y = cb.y + cb.height;
			}else if(direction == 1){ // left
				ar.x = cb.x - arSize;
				ar.y = cb.y + cb.height / 2 - arSize /2;
			}else if(direction == 2){ //right
				ar.x = cb.x + cb.width;
				ar.y = cb.y + cb.height / 2 - arSize /2;
			}
		}else{
			return;
		}
		
		attackTimer = 0;
		
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this))
				continue;
			if(e.getCombatBounds(0, 0).intersects(ar)){
				e.hurt(playerAtk);
				return;
			}
		}
		
	}

		@Override
		public void render(Graphics g) {
			inv.render(g);
			if (xBasic == true && direction == 1 ){
				g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()- width), (int) (y - handler.getGameCamera().getyOffset()), width * 2, height, null);
			}else if (xBasic == true && direction == 2 ){
				g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width * 2, height, null);
			}else if (xBasic == true && direction == 3 ){
				g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset() - height), width, height * 2, null);
			}else if (xBasic == true && direction == 4 ){
				g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height * 2, null);
			}else{
				g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
			}


		}
	 
	public BufferedImage getCurrentAnimationFrame(){
		if(flinching){
			flinchCount++;
			if(flinchCount % 4 == 0){
				return Assets.blank;
			}else if(flinchCount > 100){
					flinching = false;
					flinchCount = 0;
			}
		
		}
		
		if(xMove < 0){
			direction = 1;
			return animLeft.getCurrentFrame();
		}else if(xMove > 0){
			direction = 2;
			return animRight.getCurrentFrame();
		}else if(yMove < 0){
			direction = 3;
			return animUp.getCurrentFrame();
		}else if(yMove > 0){
			direction = 4;
			return animDown.getCurrentFrame();
		}
		
		else if(xBasic == true && direction == 1){
			return Assets.linkA_left;
		}else if(xBasic == true && direction == 2){
			return Assets.linkA_right;
		}else if(xBasic == true && direction == 3){
			return Assets.linkA_up;
		}else if(xBasic == true && direction == 4){
			return Assets.linkA_down;
		}else{
			if(direction == 1){
				return Assets.link_left[0];
			}else if(direction == 2){
				return Assets.link_right[0];
			}else if(direction == 3){
				return Assets.link_up[0];
			}else if(direction == 4){
				return Assets.link_down[0];
			}
			return Assets.link;
		}
	}

	private void flinchPlayerBounds() {
		if(flinching){
			playerOnlyBounds.x = 0;
			playerOnlyBounds.y = 0;
			playerOnlyBounds.width = 0;
			playerOnlyBounds.height = 0;
		}else{
			playerOnlyBounds.x = 0;
			playerOnlyBounds.y = 0;
			playerOnlyBounds.width = 32;
			playerOnlyBounds.height = 32;
		}
		
	}

	@Override
	public void interact() {
	if(checkEntityInteraction(this.x, this.y)){
		Entity e = getInteractionEntity(this.x, this.y);
		e.interact();
		System.err.println("Interacts");
		System.err.println(e);
		}
	}

	@Override
	public void death() {
		System.out.println("You died");
		dead = true;
	}
	
	public float getAngle() {
	    float angle = (float) Math.toDegrees(Math.atan2(handler.getWorld().getEntityManager().getPlayer().getY() - y, handler.getWorld().getEntityManager().getPlayer().getX() - x));

	    if(angle < 0){
	        angle += 360;
	    }
	    return angle;
	}

	public void damageRecoil() {
		
		flinching = true;
		if(direction == 1){
			xMove -= 50;
			direction = 1;
		}else if(direction == 2){
			xMove += 50;
			direction = 2;
		}else if(direction == 3){
			yMove -= 50;
			direction = 3;
		}else if(direction == 4){
			yMove += 50;
			direction = 4;
		}

		damage = false;

	}

	@Override
	public void damaged() {
		// TODO Auto-generated method stub
		
	}

}
