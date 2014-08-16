import java.applet.Applet;
import java.awt.*;
import java.lang.reflect.Field;


/**
 * Displays text in an applet takes three optional parameters
 * COLOR, TEXT, POINTSIZE
 * 
 * @author bdavis
 *
 */
public class HnHeading extends Applet {
	public void init() {		
		Color c = parseColor(getParameter("COLOR"));
		int size = 12; 
		try { 
			size = Integer.parseInt(getParameter("POINTSIZE"));
			if (size > 120)
				size = 120;
			else if (size < 2) 
				size = 2; 
				
		}
		catch (Exception e) {}
		String text = getParameter("TEXT"); 
		if (text == null) 
			text = "Default Text"; 
		
		setBackground(Color.WHITE);
		setForeground(c); 
		Font f = new Font("SansSerif", Font.PLAIN, size);
		setFont(f); 
		add(new Label(text)); 
	
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
