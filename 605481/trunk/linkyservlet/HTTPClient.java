package linky;

import java.net.*;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Map; 
import java.util.Set; 
import java.io.*;

/**
 * Contains methods to connect to an HTTP server
 * and download information
 * @author bdavis
 *
 */
public class HTTPClient {
	protected String server ;
	protected int port = 80; 
	protected String resource; 
	protected String requestMethod = "GET"; 
	
	Socket client;
	BufferedReader reader; 
	TreeMap<String, String> headers = new TreeMap<String, String>();
	
	String version; 
	String responseCode; 
	String responseString; 
	
	public HTTPClient(String server, String resource) 
	{
		this.server = server; 
		this.resource = resource; 
	}
	
	public void setRequestMethod(String method) 
	{
		requestMethod = method; 
	}
		
	/**
	 * Connects using the initialized request
	 * This will download the HTTP header, but not the
	 * data
	 * @throws Exception
	 */
	public void connect() throws Exception
	{			
		client = new Socket(server, port);
	    reader = new BufferedReader(new InputStreamReader(client.getInputStream())); 
		PrintWriter writer = new PrintWriter(client.getOutputStream(), true); 
		writer.println(requestMethod + " " + resource + " HTTP/1.1");
		writer.println("Host: " + server); 
		writer.println(""); 
		
		String line = reader.readLine();
		StringTokenizer tokenizer = new StringTokenizer(line); 
		version=tokenizer.nextToken(); 
		responseCode = tokenizer.nextToken();
		responseString = tokenizer.nextToken(); 		
		line = reader.readLine(); 
		String response = ""; 
		while (!line.equals(""))
		{
			response += line + newline; 
			int k = line.indexOf(":"); 
			if  (k != -1)
			{
				headers.put(line.substring(0,k).trim().toLowerCase(), 
						line.substring(k+1).trim());
				//System.out.println("Adding header " + line.substring(0,k).trim() + 
				//		" " + line.substring(k+1).trim() );
				
			}
			line = reader.readLine();
 
		}
		return; 
	}
	
	public BufferedReader getInputStream() throws IOException
	{
		return reader; 		
	}
	
	public Set<Map.Entry<String,String>> getHeaders() { 
		return headers.entrySet(); 
	}
	
	public String getHeaderField(String name)
	{
		return headers.get(name.toLowerCase()); 
	}
	
	public static String newline = System.getProperty("line.separator");

}
