package dev.input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {

	public static boolean leftPressed;
	public static boolean rightPressed;
	private static int mouseMovedX, mouseMovedY;
	private static int mouseX;
	private static int mouseY;
	public static Point mouse;
	
	public MouseManager(){
		
	}
	public static void tick(){
		mouse = new Point(mouseMovedX, mouseMovedY);
	}
	
	// Getters
	
	public boolean isLeftPressed(){
		return leftPressed;
	}
	
	public boolean isRightPressed(){
		return rightPressed;
	}
	
	public static int getMouseX(){
		return mouseX;
	}
	
	public static int getMouseY(){
		return mouseY;
	}
	
	//Implemented methods
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1){
			leftPressed = true;
			rightPressed = false;
		}else if(e.getButton() == MouseEvent.BUTTON3){
			rightPressed = true;
			leftPressed = false;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			leftPressed = false;
		else if(e.getButton() == MouseEvent.BUTTON3)
			rightPressed = false;
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
		mouseMovedX = e.getX();
		mouseMovedY = e.getY();
	}

}
