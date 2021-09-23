package dev.gfx;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import dev.entities.creature.Player;

public class UIManager {
	
	
	public static final String LETTERS = 	"ABCDEFGHIJKLMNOPQRSTUVWXYZ    ";
	public static final String NUMBERS = 	"0123456789";
	private double HealthDec = 0; 
	private final int HealthBar = 300;
	private double ManaDec = 0; 
	private final int ManaBar = 200;
	
	public UIManager() {
	
	}
	
	public void tick(){
		calculateHealthMana();
		
	}
	
	private void calculateHealthMana() {
		HealthDec = HealthBar * (1 - ((Player.playerMaxHp - Player.playerHp) / Player.playerMaxHp));
		ManaDec = ManaBar * (1 - ((Player.playerMaxMana - Player.playerMana) / Player.playerMaxMana));
	}

	public void render(Graphics g){
		//HealthBar
        ((Graphics2D) g).setStroke(new BasicStroke(3));
		g.setColor(Color.BLACK);
		g.drawRect(32, 32, HealthBar + 1, 20);
		g.setColor(Color.RED);
		g.fillRect(33, 33, HealthBar, 19);
		g.setColor(Color.GREEN);
		g.fillRect(33, 33,(int) HealthDec, 19);
	
		//ManaBar
		g.setColor(Color.BLACK);
		g.drawRect(32, 57, ManaBar + 1, 20);
		g.setColor(Color.RED);
		g.fillRect(572, 0, 128, 128);
		g.fillRect(32, 58, ManaBar, 19);
		g.setColor(Color.BLUE);
		g.fillRect(32, 58,(int) ManaDec, 19);
		
		
		draw(g, "MAXHP", 80 , 428);
		drawnum(g, Integer.toString((int) Player.playerMaxHp), 140 , 428);
		draw(g, "HP", 80 , 450);
		drawnum(g, Integer.toString((int) Player.playerHp), 120 , 450);
		
		draw(g, "ATK", 230 , 450);
		drawnum(g, Integer.toString(Player.playerAtk), 270, 450);
		
		draw(g, "DEF", 380, 450);
		drawnum(g, Integer.toString(Player.playerDef), 420, 450);
		
		draw(g, "GOLD", 530, 450);
		drawnum(g, Integer.toString(Player.playerGold), 590 , 450);
		
		//g.drawImage(Assets.deadScreen, 0, 0, null);		
		//drawnum(g, Integer.toString(heal), 100, 100);
		
		
	}
	void draw(Graphics g, String str, int x, int y) {
		str = str.toUpperCase();
		int length = str.length();	
		//Drawing
				for(int i = 0; i < length; i++) {
					int c = LETTERS.indexOf(str.charAt(i)) + 1;
					//System.out.println(c);
					g.drawImage(Assets.fonts[c], x + i*11 , y, null);
		}
		}
	void drawnum(Graphics g, String str, int x, int y) {
		str = str.toUpperCase();
		int length = str.length();	
		//System.out.println(length);
		//Drawing
				for(int i = 0; i < length; i++) {
					int d = NUMBERS.indexOf(str.charAt(i));
					//System.out.println(c);
					g.drawImage(Assets.numfonts[d], x + i*11 , y, null);
		}
		}
}
