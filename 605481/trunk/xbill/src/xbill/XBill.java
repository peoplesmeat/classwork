package xbill;

import java.applet.*;
import java.awt.*; 
import java.awt.event.*;
import java.util.*; 

/**
 * 
 * @author bdavis
 * xbill applet game. 
 */
public class XBill extends Applet implements MouseListener {
		
	MediaTracker mediaTracker;
	Game game = new Game(); 
	Hud hud; 
	public void init() 
	{
		width = getSize().width; 
		height = getSize().height;
		
		game.initialize(); 
		hud = new Hud(game); 
		
		Configuration.getConfiguration().height = height; 
		Configuration.getConfiguration().width = width; 
		
		mediaTracker = new MediaTracker(this);
		CharacterOne.initialize(mediaTracker, this);
		CharacterTwo.initialize(mediaTracker, this); 
		
		try { 
			mediaTracker.waitForAll();		
		} catch (Exception e) 
		{
			System.out.println(e.toString()); 
		}
		

		offScreenImage = createImage(width, height);
		offScreenGraphics = offScreenImage.getGraphics(); 
		
		UpdateThread t = new UpdateThread(); 
		t.start();
		
		this.addMouseListener(this); 
	}
	
	public void update(Graphics g) 
	{
		paint(g); 
	}
	
	int width, height; 
	Image offScreenImage; 
	Graphics offScreenGraphics; 


	public void paint(Graphics g)
	{
		offScreenGraphics.clearRect(0, 0, width, height);
			
		game.clearDead();
		game.addMoreCharacters(); 
		
		Iterator<Character> i = game.characterIterator();
		
		while (i.hasNext())
		{
			i.next().draw(offScreenImage.getGraphics(), this); 
		}
		
		

		hud.draw(offScreenImage.getGraphics()); 
		g.drawImage(offScreenImage, 0, 0, this);
	}
	
	
	
	class UpdateThread extends Thread 
	{
		public void run() 
		{
			while (true) 
			{
				XBill.this.repaint(); 
				try { 
					Thread.sleep(50); 
				
				} catch (Exception e) 
				{ }
			}
		}		
	}
	
	
	  public void mouseClicked(MouseEvent e)
	  {

	  }
	  
	  public void mouseEntered(MouseEvent e)
	  {
	  }
	  
	  public void mouseExited(MouseEvent e) 
	  {
	  }
	   
	  public void mouseReleased(MouseEvent e) 
	  {
		  System.out.println("Clicked " + e.getX() + " " + e.getY()); 
		  game.hitTest(e.getX(), e.getY());
	  }
	  
	  public void mousePressed(MouseEvent e) 
	  {
		  
	  }
}
