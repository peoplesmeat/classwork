package xbill;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.util.ArrayList;

/**
 * 
 * @author bdavis
 * CharacterTwo is has two hit points and is
 * worth twice as much as character one. 
 */
public class CharacterTwo extends CharacterOne {
	static ArrayList<Image> images = new ArrayList<Image>();
	public CharacterTwo() 
	{
		super(); 
		this.workingImages = CharacterTwo.images; 
	}
	
	public CharacterTwo(int x1, int y1) 
	{
		super(x1, y1); 
		this.workingImages = CharacterTwo.images; 
	}
	
	int hitPoints = 2; 
	public void hit() 
	{
		hitPoints --; 
		if (hitPoints == 1)
			this.workingImages  = CharacterOne.images;
		if (hitPoints < 1)
		{
			super.hit();
		}
	}
	
	public int getMultiplier() 
	{
		return 2; 
	}
	
	public static void initialize(MediaTracker mediaTracker, 
            Applet applet)
	{
		images.add( applet.getImage(applet.getDocumentBase(), "images/2bdeath1.gif") );
		mediaTracker.addImage(images.get(0), 0); 
		
		images.add( applet.getImage(applet.getDocumentBase(), "images/2bdeath2.gif") );
		mediaTracker.addImage(images.get(1), 0); 
		
		images.add( applet.getImage(applet.getDocumentBase(), "images/2bdeath3.gif") );
		mediaTracker.addImage(images.get(2), 0); 
		
		images.add( applet.getImage(applet.getDocumentBase(), "images/2bdeath4.gif") );
		mediaTracker.addImage(images.get(3), 0);
		
		images.add( applet.getImage(applet.getDocumentBase(), "images/2bill1r.gif") );
		mediaTracker.addImage(images.get(4), 0); 
		
		images.add( applet.getImage(applet.getDocumentBase(), "images/2bill2r.gif") );
		mediaTracker.addImage(images.get(5), 0); 
		
		images.add( applet.getImage(applet.getDocumentBase(), "images/2bill3r.gif") );
		mediaTracker.addImage(images.get(6), 0); 
		
		images.add( applet.getImage(applet.getDocumentBase(), "images/2bill1l.gif") );
		mediaTracker.addImage(images.get(7), 0); 
		
		images.add( applet.getImage(applet.getDocumentBase(), "images/2bill2l.gif") );
		mediaTracker.addImage(images.get(8), 0); 
		
		images.add( applet.getImage(applet.getDocumentBase(), "images/2bill3l.gif") );
		mediaTracker.addImage(images.get(9), 0); 
	}
}
