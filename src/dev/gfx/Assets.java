package dev.gfx;

import java.awt.image.BufferedImage;

public class Assets {

	private static final int width = 32, height = 32;
	private static final int fwidth = 16, fheight = 16;
	private static int a = 0;
	private static int b = 0;
	
	public static BufferedImage deadScreen, titleScreen;
	
	public static BufferedImage grass, gravel, sand, water, tree, dirt, chestOpen, chestClosed, rock, mossRock, downStair, upStair,
	 							link, linkA_right, linkA_left, linkA_down, linkA_up,
								bow, sword, hpPot, manaPot,shield ,coin , big_Link,
								ring, glove, plateArmor,
								caveGround,blackFill, blank,
								boom1,boom2,
								gannon, gannon_hurt, gannon_arm_left, gannon_arm_right, gannon_both_arms, gannon_vunerable;
	public static BufferedImage[] link_down, link_right, link_left, link_up ,link_Flinching, old_Man,
	                              slime_down, slime_right, slime_left, slime_up, fonts, numfonts;
	
	
	public static void init(){
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/32bitSpriteSheet.png"));
		SpriteSheet fontsheet = new SpriteSheet(ImageLoader.loadImage("/fonts/font.png"));
		SpriteSheet deathScreen = new SpriteSheet(ImageLoader.loadImage("/fonts/DeathScreen.png"));
		SpriteSheet titleScreenPNG = new SpriteSheet(ImageLoader.loadImage("/fonts/TitleScreen.png"));
		
		
		link_down = new BufferedImage[2];
		link_right = new BufferedImage[2];
		link_left = new BufferedImage[2];
		link_up = new BufferedImage[2];
		link_Flinching = new BufferedImage[2];
		old_Man = new BufferedImage[2];
		fonts = new BufferedImage[26];
		numfonts = new BufferedImage[10];
		
		link = sheet.crop(0, 0, width, height);
		
		
		
		for (int i = 1; i < 26; i++) {
			fonts[i] = fontsheet.crop(a , 0, fwidth, fheight);
			a = a + 16;
		}
		for (int j = 0; j < 10; j++) {
			numfonts[j] = fontsheet.crop(b , 16, fwidth, fheight);
			b = b + 16;
		}
		
		deadScreen = deathScreen.crop(0, 0, 700, 500);
		titleScreen = titleScreenPNG.crop(0, 0, 700, 500);
		
		linkA_left = sheet.crop(0, width * 4, width * 2, height);
		linkA_right = sheet.crop(width * 2, width * 4, width * 2, height);
		linkA_down = sheet.crop(width * 4, width * 4, width, height * 2);
		linkA_up = sheet.crop(width * 5, width * 4, width, height * 2);
		
		
		link_down[0] = sheet.crop(0, 0, width, height);
		link_down[1] = sheet.crop(width, 0, width, height);
	
		link_right[0] = sheet.crop(width * 2, 0, width, height);
		link_right[1] = sheet.crop(width * 3, 0, width, height);
		
		link_left[0] = sheet.crop(width * 4, 0, width, height);
		link_left[1] = sheet.crop(width * 5, 0, width, height);
		
		link_up[0] = sheet.crop(width * 6, 0, width, height);
		link_up[1] = sheet.crop(width * 7, 0, width, height);
		
		blank = sheet.crop(width * 2, height * 5, width, height);
		
		link_Flinching[0] = link;
		link_Flinching[1] = blank;
		
		boom1 = sheet.crop(width * 9, height * 3, width * 2, height * 3);
		boom2 = sheet.crop(width * 6, height * 4, width * 3, height * 2);
	
		
		slime_down = new BufferedImage[2];
		slime_right = new BufferedImage[2];
		slime_left = new BufferedImage[2];
		slime_up = new BufferedImage[2];
		
		
		slime_down[0] = sheet.crop(0, height * 3, width, height);
		slime_down[1] = sheet.crop(width, height * 3, width, height);
	
		slime_right[0] = sheet.crop(width * 2, height * 3, width, height);
		slime_right[1] = sheet.crop(width * 3, height* 3, width, height);
		
		slime_left[0] = sheet.crop(width * 4, height * 3, width, height);
		slime_left[1] = sheet.crop(width * 5, height * 3, width, height);
		
		slime_up[0] = sheet.crop(width * 6, height * 3, width, height);
		slime_up[1] = sheet.crop(width * 7, height * 3, width, height);
		
		grass = sheet.crop(0, height, width, height);
		gravel = sheet.crop(width, height, width, height);
		sand = sheet.crop(width * 2, height, width, height);
		water = sheet.crop(width * 3, height, width, height);
		dirt = sheet.crop(width * 4, height, width, height);
		caveGround = sheet.crop(width * 8, height, width, height);
		blackFill = sheet.crop(width * 5, height, width, height);
		
		tree = sheet.crop(0, height * 2, width, height);
		chestClosed = sheet.crop(width, height * 2, width, height);
		chestOpen = sheet.crop(width * 2, height * 2, width, height);	
		rock = sheet.crop(width * 3, height * 2, width, height);
		mossRock = sheet.crop(width * 4, height * 2, width, height);
		downStair = sheet.crop(width * 6, height * 2, width, height);
		upStair = sheet.crop(width * 7, height * 2, width, height);
		
		bow = sheet.crop(width, height * 6, width, height);
		sword = sheet.crop(width * 2, height * 6, width, height);
		hpPot = sheet.crop(width * 3, height * 6, width, height);
		manaPot = sheet.crop(width * 4, height * 6, width, height);
		shield = sheet.crop(width * 5, height * 6, width, height);
		coin = sheet.crop(width * 6, height * 6, width, height);
		ring = sheet.crop(width * 7, height * 6, width, height);
		glove = sheet.crop(width * 8, height * 6, width, height);
		plateArmor = sheet.crop(width * 9, height * 6, width, height);
		
		old_Man[0] = sheet.crop(0, height * 5, width, height);
		old_Man[1] = sheet.crop(width, height * 5, width, height);
		
		gannon = sheet.crop(0, height * 7, width, height);
		gannon_hurt = sheet.crop(width, height * 7, width, height);
		gannon_arm_left = sheet.crop(width * 2, height * 7, width, height);
		gannon_arm_right = sheet.crop(width * 3, height * 7, width, height);
		gannon_both_arms = sheet.crop(width * 4, height * 7, width, height);
		gannon_vunerable = sheet.crop(width * 5, height * 7, width, height);
	
	}

	
}
