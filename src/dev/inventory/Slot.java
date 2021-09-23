package dev.inventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import dev.input.MouseManager;
import dev.utils.Vector2F;

public class Slot extends Rectangle{

	private Item item;
	private int slotID;
	
	private Vector2F pos = new Vector2F();
	private int size = 48;
	
	private boolean isHeldOver;
	
	private int maxStack = 64;
	private int currentStack = 0;
	
	private Font font = new Font("Calbri",20,20);
	
	public Slot(float xpos, float ypos) {
		this.pos.xpos = xpos;
		this.pos.ypos = ypos;
		setBounds((int)pos.xpos, (int)pos.ypos, size, size);
	}
	
	public void tick(){
		setBounds((int)pos.xpos, (int)pos.ypos, size, size);
	
		if(currentStack == 0 ){
			if(item != null){
				clear();
			}
		}
		if (contains(MouseManager.mouse)){
			isHeldOver = true;
		}else{
			isHeldOver = false;
		}
	}
	
	public void render(Graphics g){
		g.setColor(Color.BLACK);
		if(item != null){
			g.drawImage(item.getItemImage(),(int)pos.xpos, (int)pos.ypos, size, size, null);
			g.setFont(font);
			g.setColor(Color.WHITE);
			if(item.getStackable())
				g.drawString(currentStack + "",(int)pos.xpos + size /2, (int)pos.ypos + size - 4 );
			g.setColor(Color.BLACK);
			//g.setColor(Color.RED);
		}
		
		if(isHeldOver){
			g.setColor(Color.BLUE);
			g.drawRect((int)pos.xpos, (int)pos.ypos, size - 1, size - 1);
			
		}
		g.drawRect((int)pos.xpos, (int)pos.ypos, size - 1, size - 1);
		
		//g.setColor(Color.RED);
	}
	
	public void setItem(Item item){
		this.item = item;
		slotID = item.getItemID();
		currentStack += 1;
	}

	
	public void clear() {
		slotID = 0;
		item = null;
		currentStack = 0;
	}
	
	public Item getItem() {
		return item;
	}
	
	public int getSlotID() {
		return slotID;
	}
	
	public int getCurrentStack() {
		return currentStack;
	}
	
	public void setCurrentStack(int currentStack) {
		this.currentStack = currentStack;
	}
	
	public boolean isAir(){
		return (item == null && slotID == 0);	
	}
	
	public boolean hasSameID(Item item){
		if(item.getItemID() == slotID){
			return true;
		}else{
			return false;
		}
	}
	
	public void addItemToStack(Item item){
		if(item.getStackable())
			currentStack += 1;
		
	}
	
	public boolean isFull(){
		if(currentStack < maxStack){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean isLeftClicked(){
		
		if(isHeldOver){
			if(MouseManager.leftPressed){
				MouseManager.leftPressed = false;
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
		
	}
	
	public boolean isRightClicked(){
		
		if(isHeldOver){
			if(MouseManager.rightPressed){
				MouseManager.rightPressed = false;
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
		
	}
	
	public boolean hasItem(){
		if(item != null){
			if(slotID != 0){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	
}
