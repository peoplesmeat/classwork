package xbill;

import java.awt.Graphics;
import java.awt.MediaTracker;
import java.applet.Applet;
import java.awt.Image; 
import java.util.ArrayList;

import xbill.Character.AliveState;
public class CharacterOne extends Character  {
	
	static ArrayList<Image> images = new ArrayList<Image>();
	ArrayList<Image> workingImages = null; 
	
	AnimationState aniState; 
	public CharacterOne() { 
		super((int)(200 * Math.random()), 
			  (int)(200 * Math.random()));
		 				
		init(1,1); 
	}
	public CharacterOne(int xvel, int yvel)
	{
		super((int)(200 * Math.random()), 
			  (int)(200 * Math.random())); 
		
		init(xvel, yvel); 
	}
	
	protected void init(int xvel, int yvel) 
	{		 
		velX = xvel; 
		velY = yvel;
		
		if (velX < 0) 
			aniState = new AnimationState(7, 3, -1, (int)(Math.random()*4+1) ); 
		else if (velX > 0)
			aniState = new AnimationState(4, 3, -1, (int)(Math.random()*4+1) );
		
		imageIndex = aniState.getFrame();
		workingImages = images;		
	}
	
	boolean running = true; 
	public void run() 
	{
		while(running)
		{
			try { 
				switch (state) 
				{
				case Dying: break;  
					default: break; 
					
						
				}
				imageIndex = aniState.tick(); 
				if (imageIndex == workingImages.size())
					imageIndex = 0; 
				Thread.sleep(100);
			}
			catch(Exception e) {
				System.out.println(e); 
				return; 
			}	
		}
	}
	
	int getWidth() 
	{
		return 48; 
	}
	
	public void hit() 
	{
		 
		if (state == AliveState.Dying) 
			return; 
		
		for(AliveEventListener e : listeners) 
			e.characterAliveStateChanged(CharacterOne.this, 
					CharacterOne.this.state,AliveState.Dying);
		
		state = AliveState.Dying; 
		velX = 0; 
		velY = 0; 
		aniState = new AnimationState(0, 4, 1); 
		aniState.setAnimationType(AnimationState.AnimationType.Loop);
		aniState.addAnimationCompleteListener(new AnimationActionListener() { 
			public void animationEnded() {
				state = AliveState.Dead; 
				for(AliveEventListener e : listeners) 
					e.characterAliveStateChanged(CharacterOne.this, 
							CharacterOne.this.state, AliveState.Dead);
			}
		}
		); 
	}
	
	int getHeight()
	{
		return 76; 
	}
	
	int imageIndex = 0; 
	public void draw(Graphics g, Applet a) 
	{		
		move(); 
		
		g.drawImage(workingImages.get(imageIndex), x, y, a);  
	}
	
	public void directionSwitched() 
	{
		if (velX < 0) 
			aniState = new AnimationState(7, 3, -1, aniState.getFPS()); 
		else if (velX > 0)
			aniState = new AnimationState(4, 3, -1, aniState.getFPS()); 
	}
	public static void initialize(MediaTracker mediaTracker, 
            Applet applet)
	{
		images.add( applet.getImage(applet.getDocumentBase(), "images/bdeath1.gif") );
		mediaTracker.addImage(images.get(0), 0); 
		
		images.add( applet.getImage(applet.getDocumentBase(), "images/bdeath2.gif") );
		mediaTracker.addImage(images.get(1), 0); 
		
		images.add( applet.getImage(applet.getDocumentBase(), "images/bdeath3.gif") );
		mediaTracker.addImage(images.get(2), 0); 
		
		images.add( applet.getImage(applet.getDocumentBase(), "images/bdeath4.gif") );
		mediaTracker.addImage(images.get(3), 0);
		
		images.add( applet.getImage(applet.getDocumentBase(), "images/bill1r.gif") );
		mediaTracker.addImage(images.get(4), 0); 
		
		images.add( applet.getImage(applet.getDocumentBase(), "images/bill2r.gif") );
		mediaTracker.addImage(images.get(5), 0); 
		
		images.add( applet.getImage(applet.getDocumentBase(), "images/bill3r.gif") );
		mediaTracker.addImage(images.get(6), 0); 
		
		images.add( applet.getImage(applet.getDocumentBase(), "images/bill1l.gif") );
		mediaTracker.addImage(images.get(7), 0); 
		
		images.add( applet.getImage(applet.getDocumentBase(), "images/bill2l.gif") );
		mediaTracker.addImage(images.get(8), 0); 
		
		images.add( applet.getImage(applet.getDocumentBase(), "images/bill3l.gif") );
		mediaTracker.addImage(images.get(9), 0); 
	}	
	
	
}
