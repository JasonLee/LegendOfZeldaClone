package dev.entities.creature;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.Handler;
import dev.entities.Entity;
import dev.gfx.Assets;

public class Gannon extends Creature{
	
	private boolean slam = false, inRange = false, invincible = false, hit = false;
	private String mode = "normal", prevMode = "normal";
	double HealthDec = 0, HealthBar = 100 ;
	private int hurtCount = 0, attackCount = 0, attackCount2 = 0, attackCount3 = 0, setMode = 0, hitCount = 0;;
	
	
	//Timer stuffs
	long lastShoot = System.currentTimeMillis();
	final long threshold = 500; // 500msec = half second
	
	int a,b,c,d;
	
	//Hitbox
	Rectangle cb = getCollisionBounds(0,0);
	Rectangle ar = new Rectangle();
	
	private boolean flinching = false;
	private int flinchCount = 1;
	
	
	public Gannon(Handler handler, float x, float y) {
		super(handler, x, y, DEFAULT_CREATURE_WIDTH * 3, DEFAULT_CREATURE_HEIGHT * 3);
		this.x = x;
		this.y = y;
		bounds.x = 16;
		bounds.y = 16;
		bounds.width = 48;
		bounds.height = 48;
		
		cbounds.x = 0;
		cbounds.y = 0;
		cbounds.width = 96;
		cbounds.height = 96;
		
		health = 2000;
		maxHealth = 2000;
		attack = 20;
		
		speed = 0.2f;
		
		ibounds.x = 0;
		ibounds.y = 0;
		ibounds.width = 0;
		ibounds.height = 0;
	
	}

	@Override
	public void tick() {
		calculateHealthBar();
		checkState();
		checkAttacked();
		checkAttacked2();
		gannonMove();
		getAngle();
		if(hit){
			hitCount++;
			if(hitCount > 100)
				hit = false;
		}
		move();
	}
	
	public void gannonMove(){
		double distanceDiffX = handler.getWorld().getEntityManager().getPlayer().getX() - this.x;
		double distanceDiffY = handler.getWorld().getEntityManager().getPlayer().getY() - this.y;
		double range = Math.sqrt((distanceDiffX * distanceDiffX)+(distanceDiffY * distanceDiffY));
		if(range < 200){
			inRange = true;
		
		xMove = 0;
		yMove = 0;
	
			if (mode.equals("normal")) {
				if(x < handler.getWorld().getEntityManager().getPlayer().getX()) 
					xMove += speed;
				if(x > handler.getWorld().getEntityManager().getPlayer().getX()) 
					xMove -= speed;
				if(y < handler.getWorld().getEntityManager().getPlayer().getY()) 
					yMove += speed;
				if(y > handler.getWorld().getEntityManager().getPlayer().getY()) 
					yMove -= speed;
			}
		}else{
			inRange = false;
		}
	
	}
	
	
	public float getAngle() {
	    float angle = (float) Math.toDegrees(Math.atan2(handler.getWorld().getEntityManager().getPlayer().getY() - (y + 48), handler.getWorld().getEntityManager().getPlayer().getX() - (x + 48)));

	    if(angle < 0){
	        angle += 360;
	    }
	    return angle;
	}

	

	private void checkState() {
		  if(mode.equals("left")){
			invincible = true;
			attackCount++;
			if(attackCount > 70){
				attackCount = 0;
				prevMode = "left";
				mode = "slam";
				
			}	
		}else if(mode.equals("right")){
			invincible = true;
			attackCount2++;
			if(attackCount2 > 70){
				attackCount2 = 0;
				prevMode = "right";
				mode = "slam";
			}	
		}else if(mode.equals("centre")){
			invincible = true;
			attackCount3++;
			if(attackCount3 > 70){
				attackCount3 = 0;
				prevMode = "centre";
				mode = "slam";
			}	
		}else if(mode.equals("slam")){
			invincible = false;
			slam = true;
			attackCount++;			
			
			if(hit){
				mode = "hurt";
				flinching = true;
			}else if(attackCount > 120){
				invincible = true;
				attackCount = 0;
				prevMode = "normal";
				mode = "normal";
				slam = false;
			}
		}else if(mode.equals("hurt")){
			hit = false;
				hurtCount++;
				invincible = true;
				if(hurtCount > 100){
						hurtCount = 0;
						prevMode = "normal";
						mode = "normal";
						flinching = false;
				}	
		}else if(mode.equals("normal")){
			invincible = true;
			setMode++;
			if(setMode > 200){
				setMode = 0;
				if(inRange)
					modeSet();
			}
		}
	}

	private void checkAttacked() {
			int arSize = 32;	
			if(!slam){
		//		ar.x = 0;
		//		ar.y = 0;
				ar.height = 0;
				ar.width = 0;
				
		//		a = (int) x;
		//		b = (int) y;
				c = ar.width;
				d = ar.height;
				return;
			}
				if(prevMode.equals("centre")){ // down
					ar.x = (int) x;
					ar.y = (int) (y + arSize * 3);
					ar.height = arSize * 2;
					ar.width = arSize * 3;
				}else if(prevMode.equals("left")){ // left
					ar.x = (int) x - arSize ;
					ar.y = (int) y;
					ar.height = arSize * 3;
					ar.width = arSize;
				}else if(prevMode.equals("right")){ //right
					ar.x = (int) x + cb.width;
					ar.y =(int) y;
					ar.height = arSize * 3;
					ar.width = arSize;
				}else{
					return;
				}
			
			a = (int) x;
			b = (int) y;
			c = ar.width;
			d = ar.height;
			
			for(Entity e : handler.getWorld().getEntityManager().getEntities()){
				if(e.equals(this))
					continue;
				if(e.getPlayerOnlyBounds(0, 0).intersects(ar)){
					e.hurtPlayer(attack);
					return;
				}
			}
			
		}
	
	 private void checkAttacked2() {
		 if(invincible){
				cbounds.x = 0;
				cbounds.y = 0;
				cbounds.width = 0;
				cbounds.height = 0;
		 }else{
				cbounds.x = 0;
				cbounds.y = 0;
				cbounds.width = 96;
				cbounds.height = 96;
		 }
			for(Entity e : handler.getWorld().getEntityManager().getEntities()){
				if(e.equals(this))
					continue;
				if(e.getPlayerOnlyBounds(0, 0).intersects(this.getCombatBounds(0, 0))){
					e.hurtPlayer(attack);
					return;
				}
			}
			
		
	}
	
	private void calculateHealthBar() {
		HealthDec = HealthBar * (1 - ((double) (maxHealth - health) / (double) maxHealth));
	}

	@Override
	public void render(Graphics g) {
		g.drawImage( getCurrentAnimationFrame(),(int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height,null);
		g.setColor(Color.red);
		
		if(prevMode.equals("left"))
			g.drawImage(Assets.boom1, (int) (x - handler.getGameCamera().getxOffset() - 64),(int) (y - handler.getGameCamera().getyOffset()), null);
		else if(prevMode.equals("right"))
			g.drawImage(Assets.boom1, (int) (x - handler.getGameCamera().getxOffset() + 96),(int) (y - handler.getGameCamera().getyOffset()), null);
		else if(prevMode.equals("centre"))
			g.drawImage(Assets.boom2, (int) (x - handler.getGameCamera().getxOffset()),(int) (y - handler.getGameCamera().getyOffset() + 96), null);
				
		g.setColor(Color.BLACK);
		g.drawRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset())- 16, (int) (HealthBar + 1), 20);
		g.setColor(Color.RED);
		g.fillRect((int) (x - handler.getGameCamera().getxOffset())+ 1, (int) (y - handler.getGameCamera().getyOffset())- 15, (int) HealthBar, 19);
		g.setColor(Color.GREEN);
		g.fillRect((int) (x - handler.getGameCamera().getxOffset()) + 1, (int) (y - handler.getGameCamera().getyOffset())- 15,(int) HealthDec, 19);
		
	}
	private BufferedImage getCurrentAnimationFrame(){
		if(flinching){
			flinchCount++;
			if(flinchCount % 4 == 0){
				return Assets.blank;
			}else if(flinchCount > 100){
					flinching = false;
					flinchCount = 0;
			}
		}
		if(mode == "normal")
			return Assets.gannon;
		else if(mode == "left")
			return Assets.gannon_arm_left;
		else if(mode == "right")
			return Assets.gannon_arm_right;
		else if(mode == "centre")
			return Assets.gannon_both_arms;
		else if(mode == "hurt")
			return Assets.gannon_hurt;
		else if(mode == "slam")
			return Assets.gannon_vunerable;
		return Assets.gannon;
		
	}
	
	private void modeSet(){
		if(45 <= getAngle() && getAngle() < 135)
			mode = "centre";
		else if(135 <= getAngle() && getAngle() < 225)
			mode = "left";
		else if(225 <= getAngle() && getAngle() < 315)
			System.err.println("above");
		else
			mode = "right";
				
	}


	@Override
	public void interact() {
	}

	@Override
	public void death() {
		// TODO Auto-generated method stub
	}

	@Override
	public void damaged() {
		hit = true;
		if(!mode.equals("left") && !mode.equals("right") && !mode.equals("centre") && !mode.equals("slam")){
			mode = "hurt";
			flinching = true;
		}
		
	}
}
