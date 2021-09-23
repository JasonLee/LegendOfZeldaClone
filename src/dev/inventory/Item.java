package dev.inventory;

import java.awt.image.BufferedImage;

import dev.gfx.Assets;

public enum Item {

	SHARP_SWORD("SHARP_SWORD", 1, Assets.sword, "WEAPON", false, 0, 100, 0, 0),
	BRONZE_SHIELD("BRONZE_SHIELD", 2, Assets.shield, "SHIELD", false, 100, 0, 7, 0),
	HEALTH_POT("HEALTH_POT", 3, Assets.hpPot, "CONSUMIBLE" , true, 0, 0, 0, 0),
	MANA_POT("MANA_POT", 4, Assets.manaPot, "COMSUMUBLE" , true, 0, 0, 0, 0),
	BOW_AND_ARROW("BOT_AND_ARROW", 5, Assets.bow, "WEAPON", false, 0, 7, 0, 0),
	RING("RING", 5, Assets.ring, "ACCESSORY", false, 10, 0, 5, 0),
	GLOVE("GLOVE", 5, Assets.glove, "ACCESSORY", false, 0, 5, 0, 0),
	PLATE_ARMOR("PLATE_ARMOR", 5, Assets.plateArmor, "ARMOR", false, 50, 0, 15, 0);
	
	
	private String itemName, itemType;
	private int itemID, health, attack, defense, speed;
	private BufferedImage itemImage;
	private Boolean stackable;
	
	
	private Item(String itemName, int itemID, BufferedImage itemImage, String itemType, Boolean stackable,
			int health, int attack, int defense, int speed){
		
		this.itemName = itemName;
		this.itemID = itemID;
		this.itemImage = itemImage;
		this.itemType = itemType;
		this.stackable = stackable;
		this.health = health;
		this.attack = attack;
		this.defense = defense;
		this.speed = speed;
		
	}
	
	
	public String getItemName() {
		return itemName;
	}
	public int getItemID() {
		return itemID;
	}
	public BufferedImage getItemImage() {
		return itemImage;
	}
	public String getItemType() {
		return itemType;
	}
	public Boolean getStackable() {
		return stackable;
	}
	public int getHealth() {
		return health;
	}
	public int getAttack() {
		return attack;
	}
	public int getDefence() {
		return defense;
	}
	public int getSpeed() {
		return speed;
	}
}
