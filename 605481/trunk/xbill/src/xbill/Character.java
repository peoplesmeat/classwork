package xbill;

import java.awt.Graphics; 
import java.awt.Rectangle;
import java.applet.Applet;
import java.util.ArrayList; 

/**
 * 
 * @author bdavis
 * The character class represents an on-screen 
 * character capable of being clicked. 
 */
public abstract class Character extends Thread {
	enum AliveState { Alive, Dying, Dead }
	
	AliveState state = AliveState.Alive; 
	
	int x; 
	int y; 
	
	int velX; 
	int velY; 
	
	int age = 0; 
	
	ArrayList<AliveEventListener> listeners = new ArrayList<AliveEventListener>();  
	
	public Character(int x, int y) 
	{
		this.x = x;
		this.y = y; 
		
	}
	
	abstract int getWidth(); 
	abstract int getHeight(); 
	abstract void draw(Graphics g, Applet a);
	
	public synchronized boolean hitTest(int hitx, int hity) 
	{
		Rectangle r = new Rectangle(x, y, getWidth(), getHeight()); 
		
		return r.contains(hitx,hity); 
	}
	
	protected void directionSwitched() { } 
	
	public synchronized void move() {
		x += velX; 
		y += velY; 
		if (x < 0 || x + getWidth() > 
		              Configuration.getConfiguration().width) 
		{
			velX = -velX;
			directionSwitched(); 
		}
		if (y<0 || y + getHeight() > 
		              Configuration.getConfiguration().height)
			velY = -velY;
		age++;
	}
	
	
	public void hit()
	{
		
	}
	
	public int getAge() 
	{
		return age; 
	}
		
	public int getMultiplier() 
	{
		return 1; 
	}

	public AliveState getAliveState() 
	{
		return state; 
	}

	public void addAliveEventListener(AliveEventListener l) 
	{
		listeners.add(l); 
	}
	public void removeAliveEventListener(AliveEventListener l) 
	{
		listeners.remove(l);
	}
	


}
