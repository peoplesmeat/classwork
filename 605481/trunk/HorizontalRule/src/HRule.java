import java.awt.*; 
import java.applet.Applet;
import java.lang.reflect.*;

/**
 * 
 * @author bdavis
 * This class fills a background with a color. It can be used to create 
 * colored blocks across an html page. It takes as a parameter the color to use. 
 * 
 * Parameter "COLOR" 
 * Value Either a named color value from the java.awt.Color class or a 
 * hexadecimal value to be used. Examples include BLUE, DARK_GREY, 
 * GREEN, PINK, ORANGE, FF0000, 00FF000, 0000FF. If we are unable to match the color
 * we use GRAY as a default
 * 
 */
public class HRule extends Applet {
		
	/**
	 * Get the COLOR parameter and set the background
	 */
	public void init() {
		setBackground(parseColor(getParameter("COLOR")));	
	}
	
	/**
	 * The color is reflectively extracted form the Color class
	 * 
	 * @param A string denoting the color
	 * @return
	 */
	private Color parseColor(String color) 
	{
		Color parsedColor = Color.GRAY; //Defaults to grey color
		
		if (color != null) 
		{
			try { 
				Field colorField = Color.class.getField(color.toUpperCase()); 
				Color c = (Color)(colorField.get(null));
				parsedColor = c; 			
			}
			catch (Exception e) { 
				try { 
					int k = Integer.parseInt(color,16); 
					Color c = new Color(k);
					parsedColor = c; 
					
				}
				catch (NumberFormatException ex) 
				{
				
				}
			}
		}
		return parsedColor; 
	}
	

	
}
