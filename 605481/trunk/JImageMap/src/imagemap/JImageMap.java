package imagemap;

import java.awt.*; 
import java.awt.event.*; 

import java.applet.Applet;
import java.net.URL;
import java.util.ArrayList;

import sun.java2d.loops.DrawPolygons;

/**
 * This class supports displaying imagemaps inside of an applet
 * It also happens to change the cursor when it moves over a region.
 * 
 * @author bdavis
 *
 */
public class JImageMap extends Applet {
	

	
	/**
	 * Used to load the background image
	 */
	MediaTracker tracker;
	
	/**
	 * The loaded background image
	 */
	Image image; 
	
	/**
	 * The target currently underneath the mouse, or
	 * null if there is none
	 */
	Target currTarget = null;
	
	/**
	 * The last target to be underneath the mouse
	 */
	Target oldTarget = null; 
	
	/**
	 * Loads the background image and initializes the handlers
	 */
	
	/**
	 * A list of targets and their locations
	 */
	java.util.ArrayList<Target> targets = new ArrayList<Target>();
	
	
	/**
	 * This class represents an image map location and target
	 * The on-screen block is represented as a polygon
	 * @author bdavis
	 *
	 */
	class Target 
	{ 
		Polygon p; 
		String targetUrl;
		public Target(String target, Rectangle r) 
		{
			p = new Polygon(
					new int [] { r.x, r.x+r.width, r.x + r.width, r.x} ,
					new int [] { r.y, r.y, r.y+r.height, r.y + r.height} , 
					4); 
			
			targetUrl = target; 
		}
		public Target(String target, Polygon polygon) 
		{
			p = polygon; 
			targetUrl = target; 
		}
		public boolean contains(int x, int y) 
		{ 
			return p.contains(x,y); 
		}
		public Polygon getPolygon() 
		{ 
			return p; 
		}
		public String getTarget() 
		{
			return targetUrl; 
		}
	}
	
	/**
	 * Handles mouse activities. Checks on mouse move to see if there is 
	 * a target underneath the current mouse location
	 * @author bdavis
	 *
	 */
	class MouseHandler extends MouseAdapter implements MouseMotionListener
	{
		public void mousePressed(MouseEvent event) 
		{
			System.out.println("["+event.getX() + ","+event.getY() + "]");
			if (currTarget != null) 
			{
				try 
				{ 
				System.out.println(currTarget.getTarget()); 
				getAppletContext().showDocument(new URL(currTarget.getTarget()), "frame2");
				}
				catch (Exception e) 
				{ } 
			}
		}				
		
		public void mouseMoved(MouseEvent event) 
		{
			Target target = null; 
			
			for (Target t : targets)
			{
				if (t.contains(event.getX(), event.getY()))
				{					
					target = t; 
					//showRectangle((Rectangle)s); 
					
				}
				
			}
			setTarget(target); 
		}
	}
	
	public void init()
	{
		addMouseListener(new MouseHandler()); 
		addMouseMotionListener(new MouseHandler()); 
		tracker = new MediaTracker(this); 
		image = getImage(getCodeBase(), "map.jpg");
		tracker.addImage(image, 0); 
		
		try { 
			tracker.waitForAll();		
		} catch (Exception e) 
		{
			System.out.println(e.toString()); 
		}
		String rd = "http://www.peoplesmeat.com/605481/imagemap/"; 
		targets.add(new Target(rd+"moon2.gif", 
				new Rectangle(50,50,200,200)) );
		targets.add(new Target(rd+"latest_eit_195.gif", 
				new Rectangle(325,50,175,200)) );
		targets.add(new Target(rd+"death-star-1.jpg",
				new Rectangle(650, 50, 125, 200)));
		targets.add(new Target(rd+"hst_galaxy.JPG",
				new Rectangle(450, 260, 300, 40)));
	}
	

	/**
	 * Called when mouse moves with the target 
	 * currently underneath the mouse
	 * @param t 
	 */
	public void setTarget(Target t) 
	{
		if (currTarget != t)
		{
			oldTarget = currTarget; 
			currTarget = t; 
			
			this.drawImageMap();			
		}
	}
	
	/**
	 * Displays the selected block, or clears the old block
	 */
	public void drawImageMap() 
	{
		Graphics g = this.getGraphics();
		g.setXORMode(Color.WHITE);
		if (currTarget != null)
		{
			g.fillPolygon(currTarget.getPolygon());
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		else 
		{
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
		if (oldTarget != null) 
		{
			g.fillPolygon(oldTarget.getPolygon()); 
		} 		
	}

	/**
	 * Draw the background image
	 */
	public void paint(Graphics g) 
	{
		g.drawImage(image, 0,0, this);		
	}
	
}
