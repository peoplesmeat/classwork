package linky;
import java.util.*; 
import java.io.*; 
import java.net.*; 
import linky.HtmlPage.PageMovedException;

/**
 * 
 * @author bdavis
 * This is the main class for the link verification
 * Command line is 
 * linky URL -i where -i optionally puts it into interactive mode
 */
public class Linky {

	public static void main(String [] args)
	{
		if (args.length == 0 ) 
		{
			System.out.println("Correct Usage : linky urltoverify -i(nteractive mode)");
			return; 
		}
		if (args.length > 1) 
			verifyLinks(args[0], true, System.out);
		else
			verifyLinks(args[0], false, System.out);
		
	}
	/**
	 * @param args
	 */
	public static void verifyLinks(String args, boolean interactive, 
                                  PrintStream output) {
		// = System.out;		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		output.println("<p>Link Verifier v0.1");
		String u = args; 
		do 
		{
			try 
			{
				
				URL url = readURL(u);
				output.print("Reading " + u + " ("+url+")...."); 
				HtmlPage page = new HtmlPage(url, 0);
				output.println("done");
				output.println("Located " + page.getLinks().size() + " links, verifying...</p>");
				ArrayList<HtmlPage.LinkInfo> links = page.verifyLinks();
				
				int count=1; 
        output.println("<table>"); 
        
				for (HtmlPage.LinkInfo l : links) 
				{
          output.println("<tr>");
          output.println("<td>" + count++         + "</td>"); 
					output.println("<td>" + l.getLink()   + "</td>");
					output.println("<td>" + l.getURL()    + "</td>"); 
					output.println("<td>" + l.getStatus() + "</td>");
					output.println("<td>" + l.getMessage()+ "</td>");
					output.println(""); 
          output.println("</td>"); 
          //output.println("<br/>"); 
					
				}
        output.println("</table>"); 
        output.println("</tr>"); 
			}
			catch (PageMovedException e) 
			{
				output.println(""); 
				output.println(u + " moved to " + e.getMessage()); 
				u = e.getMessage(); 
				continue;
			}
			catch (MalformedURLException e) 
			{
				output.println(""); 
				output.println("{" + u + "} is not a well-formed URL"); 
			}
			catch (UnknownHostException e) 
			{
				output.println(""); 
				output.println("ERROR: Unable to connect to " + e.getMessage());
			}
			catch(Exception e) 
			{ 
				output.println(""); 
				output.println(e);
			} 
			try { 
				if (interactive) 
				{
					output.print("Enter URL (or q to quit): "); 
					u=in.readLine();
				}
			}
			catch (Exception e) { return; } 
		} while (!u.equals("q") && interactive); 

	}
	
	public static URL readURL(String line) throws MalformedURLException
	{
		if (line.equals("")) 
			throw new MalformedURLException(); 
		if ( !line.startsWith("http://"))
			line = "http://" + line; 
		return new URL(line); 
	}
	enum LinkStatus { Good, Moved, Bad , UnknownHost,  Exception, NotFound} 




}

