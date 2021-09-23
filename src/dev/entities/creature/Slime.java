package dev.entities.creature;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.Handler;
import dev.entities.Entity;
import dev.gfx.Animation;
import dev.gfx.Assets;

public class Slime extends Creature {
		
		//Animations
		private Animation animDown, animRight, animLeft, animUp;
		
		double distanceDiffX;
		double distanceDiffY;
		static double range;
		int steps = 0; 
		double HealthDec = 0, HealthBar = 100 ;
		
		boolean left = false;
		boolean right = false;
		boolean inRange = false;
		
		//Timer stuffs
		long lastShoot = System.currentTimeMillis();
		final long threshold = 500; // 500msec = half second
		final long threshold2 = 200;
		
		//Attack timer
		private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown;
		

		public Slime(Handler handler, float x, float y) {
			super(handler, x, y, DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT);
			this.x = x;
			this.y = y;
			
			bounds.x = 10;
			bounds.y = 10;
			bounds.width = 12;
			bounds.height = 12;
			
			cbounds.x = 0;
			cbounds.y = 0;
			cbounds.width = 32;
			cbounds.height = 32;
			
			maxHealth = 100;
			health = 100;
			attack = 5;
			
		
			//Animations
			animDown = new Animation(500, Assets.slime_down);
			animRight = new Animation(200, Assets.slime_right);
			animLeft = new Animation(200, Assets.slime_left);
			animUp = new Animation(500, Assets.slime_up);
		
		}

		@Override
		public void tick() {
			//Animations
			animDown.tick();
			animRight.tick();
			animLeft.tick();
			animUp.tick();
			//Speed
			move();
			CheckIfNearPlayer();	
			calculateHealthBar();
			getAngle();
		
		}

		private void calculateHealthBar() {
			HealthDec = HealthBar * (1 - ((double) (maxHealth - health) / (double) maxHealth));
		}

		private void CheckIfNearPlayer() {
			checkInRange();
			slimeMove();
			checkAttacked();
		}
		
		 private void checkAttacked() {
				attackTimer += System.currentTimeMillis() - lastAttackTimer;
				lastAttackTimer = System.currentTimeMillis();
				if(attackTimer < attackCooldown)
					return;
				
				attackTimer = 0;
				
				for(Entity e : handler.getWorld().getEntityManager().getEntities()){
					if(e.equals(this))
						continue;
					if(e.getPlayerOnlyBounds(0, 0).intersects(this.getCombatBounds(0, 0))){
						e.hurtPlayer(attack);
						return;
					}
				}
				
			}

		@Override
		public void render(Graphics g) {
			g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	
			g.setColor(Color.BLACK);
			g.drawRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset())- 16, (int) (HealthBar + 1), 20);
			g.setColor(Color.RED);
			g.fillRect((int) (x - handler.getGameCamera().getxOffset())+ 1, (int) (y - handler.getGameCamera().getyOffset())- 15, (int) HealthBar, 19);
			g.setColor(Color.GREEN);
			g.fillRect((int) (x - handler.getGameCamera().getxOffset()) + 1, (int) (y - handler.getGameCamera().getyOffset())- 15,(int) HealthDec, 19);
		}
		 
		private BufferedImage getCurrentAnimationFrame(){
			if(!inRange){
				if(xMove < 0){
					return animLeft.getCurrentFrame();
				}else if(xMove > 0){
					return animRight.getCurrentFrame();
				}else if(yMove < 0){
					return animUp.getCurrentFrame();
				}else if(yMove > 0){
					return animDown.getCurrentFrame();
				}else{
					return Assets.slime_down[0];
				}
			}else{
				if(45 <= getAngle() && getAngle() < 135)
					return animDown.getCurrentFrame();
				else if(135 <= getAngle() && getAngle() < 225)
					return animLeft.getCurrentFrame();
				else if(225 <= getAngle() && getAngle() < 315)
					return animUp.getCurrentFrame();
				else
					return animRight.getCurrentFrame();
					
				
			}
		}
		
		
		public double checkInRange() {
			distanceDiffX = handler.getWorld().getEntityManager().getPlayer().getX() - this.x;
			distanceDiffY = handler.getWorld().getEntityManager().getPlayer().getY() - this.y;
			range = Math.sqrt((distanceDiffX * distanceDiffX)+(distanceDiffY * distanceDiffY));
			if(range < 150)
				inRange = true;
			else
				inRange = false;
			
			return range;
		}
		
		public void slimeMove(){
			
		xMove = 0;
		yMove = 0;
	
		if (inRange) {
			if(x < handler.getWorld().getEntityManager().getPlayer().getX()) 
				xMove ++;
			if(x > handler.getWorld().getEntityManager().getPlayer().getX()) 
				xMove --;
			if(y < handler.getWorld().getEntityManager().getPlayer().getY()) 
				yMove ++;
			if(y > handler.getWorld().getEntityManager().getPlayer().getY()) 
				yMove --;
		}else{
			moveLeftRight();
		}

		}
		
		public float getAngle() {
		    float angle = (float) Math.toDegrees(Math.atan2(handler.getWorld().getEntityManager().getPlayer().getY() - y, handler.getWorld().getEntityManager().getPlayer().getX() - x));

		    if(angle < 0){
		        angle += 360;
		    }
		    return angle;
		}

		private void moveLeftRight() {
			xMove = 0;
			if(!left){
				xMove++;
				steps++;
				if(steps > 200){
					left = true;
					steps = 0;
					right = false;
				}
			}else if(!right){
				xMove--;
				steps++;
				if(steps > 200){
					left = false;
					steps = 0;
					right = true;
				}
			}
			
		}

		@Override
		public void interact() {

		}


		public void death() {
			System.out.println("Slime should die");
		}

		@Override
		public void damaged() {
			// TODO Auto-generated method stub
			
		}


}
