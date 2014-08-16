package xbill;

import java.awt.Graphics; 

/**
 * 
 * @author bdavis
 *
 * Displays the score
 */
public class Hud {
	Game game; 
	
	public Hud(Game g)
	{
		game = g; 
	}
	public void draw(Graphics g)
	{
		g.drawString("Score " + game.getScore(), 10, 10); 
	}
}
