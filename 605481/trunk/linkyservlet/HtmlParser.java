package linky;
import java.util.ArrayList;
import java.util.regex.*;

/**
 * Uses a Regular Expression to parse an HTML file for links
 * @author bdavis
 */
public class HtmlParser {
	String html;
	
	public ArrayList<String> links = new ArrayList<String>(); 
	public ArrayList<String> anchors = new ArrayList<String>(); 
	
	public HtmlParser(String html) 
	{
		this.html = html; 

		Pattern pattern2 = Pattern.compile("<a.*?href\\s*=\"((.*?))\".*?>",
				Pattern.MULTILINE | Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		
		Matcher m =  pattern2.matcher(html); 
		while (m.find())
		{			
			links.add(m.group(1)); 
		}
		int groupCount = m.groupCount();
	}
	
	public ArrayList<String> getLinks() 
	{
		return links; 
	}
	
	public ArrayList<String> getAnchors() 
	{
		return anchors; 
	}
	
	 	
}
