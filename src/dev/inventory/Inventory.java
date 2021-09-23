package dev.inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.entities.creature.Player;
import dev.gfx.Assets;
import dev.input.MouseManager;
import dev.utils.Vector2F;

public class Inventory {

	private Vector2F pos = new Vector2F();
	private CopyOnWriteArrayList<Slot> slots;
	private int dimension;

	private boolean toggle, ObjectSelected;
	private Item selectedItem, tempItem;
	private int tempItemStack = 0 ,selectedItemStack = 0, row = 0, col = 0, posXThree = 420, posYThree = 292;
	
	long lastShoot = System.currentTimeMillis();
	final long threshold = 85; // 500msec = half second
	int count = 0;
	
	private int a1,a2,a3,a4;
	private int d1,d2,d3,d4,d5;
	private int h1,h2,h3,h4,h5;
	
	public static boolean invFull = false;

	
	public Inventory(float xpos, float ypos, int dimension){
		
		this.pos.xpos = xpos;
		this.pos.ypos = ypos;
		this.dimension = dimension;
	}
	
	public void init(){
		slots = new CopyOnWriteArrayList<Slot>();
		for(int x = 0; x < dimension; x++){
			for(int y = 0; y < dimension; y++){
				slots.add(new Slot(pos.xpos + y * 48, pos.ypos + x * 48));
			}
		}
		for (int z = 0; z < 3; z++) {
			slots.add(new Slot(posXThree - 48, posYThree - 144 + z * 48));
		}
		for (int z = 0; z < 3; z++) {
			slots.add(new Slot(posXThree + z * 48, posYThree ));
		}
		
	}
	


	public void toggle(){
		toggle = !toggle;
	}
	public void tick(){
		if(toggle){
			for(Slot slot : slots){
				slot.tick();
			}
			long now = System.currentTimeMillis();
		       if (now - lastShoot > threshold){
		    	   inventoryStuff();
		    	   lastShoot = now;
		       }
		}
		checkIfFull();
	}
	
	private void checkIfFull() {
		
	}

	private void inventoryStuff() {
		
		calculateStats();
		UpdateStatsEmpty();
		if(MouseManager.leftPressed && (selectedItem == null))
			grabItem(MouseManager.mouse);
		else if(MouseManager.leftPressed && (selectedItem != null))
			setDown(MouseManager.mouse);
		
	}

	private void calculateStats() {
		Player.playerAtk = a1 + a2 + a3 + a4;
		Player.playerMaxHp = Player.playerBaseHp + h1 + h2 + h3 + h4 + h5;
		Player.playerDef = d1 + d2 + d3 + d4 + d5;
	}

	public void render(Graphics g){
		if(toggle){
			g.setColor(Color.GRAY);
			g.fillRect((int)pos.xpos,(int)pos.ypos, 48 * 6, 48 * 6);
			
			for(Slot slot : slots){
				slot.render(g);
			}
			
			g.setColor(Color.BLACK);
			g.fillRect(posXThree, posYThree - 144, 144, 144);
			g.drawImage(Assets.link, posXThree + 4, posYThree - 144, 144, 144, null);
			
			if(ObjectSelected){
				g.setColor(Color.RED);
				g.drawImage(selectedItem.getItemImage(), MouseManager.getMouseX() - 16, MouseManager.getMouseY() - 16, null);
				g.setColor(Color.WHITE);
				if(selectedItem.getStackable())
					g.drawString(selectedItemStack + "", MouseManager.getMouseX() + 10, MouseManager.getMouseY() + 18);
			}
		}
	}
	
	public void addItem(Item item){
		
		for(Slot slot : slots){
			count++;
			if(slot.isAir()){
				slot.setItem(item);
				break;
			}else if(slot.hasSameID(item) && item.getStackable()){
					if(!slot.isFull()){
						slot.addItemToStack(item);
						break;
					}
			}else if(count == 36)
				break;
		}
		count = 0;
	}
	
	public void addItem(Item item, int slotNum){
		if(slotNum < slots.size()){
			Slot slot = slots.get(slotNum);
			if(slot.isAir()){
				slot.setItem(item);
				updateStat(item, slotNum);
			}else if(slot.hasSameID(item)){
					if(!slot.isFull()){
						slot.addItemToStack(item);
					}
			}else if(slot.hasItem())
					System.out.println("slot has item");		
		}else
			System.err.println("OUT OF BOUNDS: INVENTORY");
		
	}
	
	  void grabItem(Point mouse){
		for(Slot slot : slots){
			if(!slot.isAir()){
				if(slot.contains(mouse)){
					selectedItem = slot.getItem();
					selectedItemStack = slot.getCurrentStack();
				//	System.out.println("selectedItem: " + selectedItem);
				//	System.out.println("selectedItemStack: " + selectedItemStack);
					ObjectSelected = true;
					slot.clear();
				}
			}
		}
	}
	
	void setDown(Point mouse){
		for(Slot slot : slots){
			if(slot.contains(mouse)){
				int slotLoc = calSlot(mouse);
				if(slotLoc == 36 && selectedItem.getItemType() != "WEAPON"){
				}else if(slotLoc == 37 && selectedItem.getItemType() != "SHIELD"){
				}else if(slotLoc == 38 && selectedItem.getItemType() != "ARMOR"){
				}else if(slotLoc == 39 && selectedItem.getItemType() != "ACCESSORY"){
				}else if(slotLoc == 40 && selectedItem.getItemType() != "ACCESSORY"){
				}else if(slotLoc == 41 && selectedItem.getItemType() != "ACCESSORY"){
			
				}else if((!slot.hasSameID(selectedItem) || !selectedItem.getStackable()) && slot.hasItem() || (slot.isFull() && slot.hasSameID(selectedItem))){
				//	System.out.println("Slot has item");
					tempItem = slot.getItem();
					tempItemStack = slot.getCurrentStack();
					slot.clear();
			//		System.out.println("tempItem: " + tempItem);
					placeItemButNoPlace(slotLoc);
					selectedItem = tempItem;
					selectedItemStack = tempItemStack;
				}else{
					placeItem(slot , slotLoc);

				}
			}
		}
		
	}

	
	

	private void placeItem(Slot slot, int loc) {
		int count = 0;
	//	System.out.println("placeItem");
		if(!slot.isFull()){
			for (int i = 0; i < selectedItemStack; i++) {
				addItem(selectedItem, loc);
				count++;
				if(slot.isFull()){
					break;
				}
		}
		if(count == selectedItemStack){
			selectedItem = null;
			selectedItemStack = 0;
			ObjectSelected = false;
		}else{
			selectedItemStack -= count;
		}
		}
	//	selectedItem = null;
		
	//	ObjectSelected = false;
		
	}
	private void placeItemButNoPlace(int loc) {
		for (int i = 0; i <selectedItemStack; i++) {
			addItem(selectedItem, loc);
		}
	}

	private int calSlot(Point mouse) {
		if(pos.xpos <= mouse.getX() && mouse.getX() < pos.xpos + 48){
			col = 0;
		}else if(pos.xpos + 48 <= mouse.getX() && mouse.getX() < pos.xpos + (48 * 2)){
			col = 1;
		}else if(pos.xpos + (48 * 2) <= mouse.getX() && mouse.getX() < pos.xpos + (48 * 3)){
			col = 2;
		}else if(pos.xpos + (48 * 3) <= mouse.getX() && mouse.getX() < pos.xpos + (48 * 4)){
			col = 3;
		}else if(pos.xpos + (48 * 4) <= mouse.getX() && mouse.getX() < pos.xpos + (48 * 5)){
			col = 4;
		}else if(pos.xpos + (48 * 5) <= mouse.getX() && mouse.getX() < pos.xpos + (48 * 6)){
			col = 5;
		}
		
		if(pos.ypos <= mouse.getY() && mouse.getY() < pos.ypos + 48){
			row = 1;
		}else if(pos.ypos + 48 <= mouse.getY() && mouse.getY() < pos.ypos + (48 * 2)){
			row = 2;
		}else if(pos.ypos + (48 * 2) <= mouse.getY() && mouse.getY() < pos.ypos + (48 * 3)){
			row = 3;
		}else if(pos.ypos + (48 * 3) <= mouse.getY() && mouse.getY() < pos.ypos + (48 * 4)){
			row = 4;
		}else if(pos.ypos + (48 * 4) <= mouse.getY() && mouse.getY() < pos.ypos + (48 * 5)){
			row = 5;
		}else if(pos.ypos + (48 * 5) <= mouse.getY() && mouse.getY() < pos.ypos + (48 * 6)){
			row = 6;
		}
		
		if(posXThree - 48 <= mouse.getX() && mouse.getX() < posXThree){
				if(posYThree - 144 <= mouse.getY() && mouse.getY() < posYThree - 96){
					col = 0;
					row = 7;
				}else if(posYThree - 96 <= mouse.getY() && mouse.getY() < posYThree - 48){
					col = 1;
					row = 7;
				}else if(posYThree - 48 <= mouse.getY() && mouse.getY() < posYThree){
					col = 2;
					row = 7;
				}
		}else if(posYThree <= mouse.getY() && mouse.getY() < posYThree + 48) {
				if(posXThree <= mouse.getX() && mouse.getX() < posXThree + 48){
					col = 3;
					row = 7;
				}else if(posXThree + 48 <= mouse.getX() && mouse.getX() < posXThree + 96){
					col = 4;
					row = 7;
				}else if(posXThree + 96 <= mouse.getX() && mouse.getX() < posXThree + 144){
					col = 5;
					row = 7;
				}
		}
		System.out.println("Slot Location: " + (col + (row * 6 - 6)));
		return col + (row * 6 - 6);
	}
	
	private void updateStat(Item item, int slotLoc){
			if(slotLoc == 36){ // weapon
				a1 = item.getAttack();
				if(item.getItemType().equals("WEAPON"))
					Player.weaponEquiped = true;
			}else if (slotLoc == 37){ // defence
				d1 = item.getDefence();
				h1 = item.getHealth();
			}else if(slotLoc == 38){ // armour
				d2 = item.getDefence();
				h2 = item.getHealth();
			}else if(slotLoc == 39){ // accessory1
				a2 = item.getAttack();
				h2 = item.getHealth();
				d3 = item.getDefence();
			}else if(slotLoc == 40){ // accessory2
				a3 = item.getAttack();
				h3 = item.getHealth();
				d4 = item.getDefence();
			}else if(slotLoc == 41)	{ // accessory3
				a4 = item.getAttack();
				h4 = item.getHealth();
				d5 = item.getDefence();
			}
	}
	
	private void UpdateStatsEmpty() {
		for(Slot slot : slots){
			count++;
			if(count == 37 && slot.isAir()){ // weapon
				a1 = 0;
				Player.weaponEquiped = false;
			}else if (count == 38 && slot.isAir()){ // defence
				d1 = 0;
				h1 = 0;
			}else if(count == 39 && slot.isAir()){ // armour
				d2 = 0;
				h2 = 0;
			}else if(count == 40 && slot.isAir()){ // accessory1
				a2 = 0;
				d3 = 0;
				h3 = 0;
			}else if(count == 41 && slot.isAir()){ // accessory2
				a3 = 0;
				d4 = 0;
				h4 = 0;
			}else if(count == 42 && slot.isAir())	{ // accessory3
				a4 = 0;
				d5 = 0;
				h5 = 0;
			}
		}
		count = 0;
		}
		
		
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

