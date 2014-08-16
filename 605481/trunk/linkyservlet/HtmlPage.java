package linky;

import java.net.URL;
import java.io.*; 
import java.util.*; 
import linky.Linky.LinkStatus;
import java.util.concurrent.*; 
/**
 * Container class which represents a single 
 * html page at a remote location
 * @author bdavis
 *
 */
public class HtmlPage {
	URL url; 
	String html; 
	int depth = 0; 
	HtmlParser parser; 
	
	public HtmlPage(URL url, int depth) throws Exception {
		this.url = url; 
		HTTPClient h = getHTTPClient(url.getHost(), url.getPath());
		html = getPage(h); 
		if (h.responseCode.equals("301") || 
		    h.responseCode.equals("301"))
		{
			throw new PageMovedException(h.getHeaderField("location")); 
		}
		else if (h.responseCode.equals("200"))
		{
			parser = new HtmlParser(html);
		}
		else if (html != null) 
		{
			throw new Exception("Unable to get info");
		}
	}
	
	public HTTPClient getHTTPClient(String server, String resource) throws Exception
	{
		if (resource.equals(""))
			resource="/"; 
		HTTPClient h = new HTTPClient(server, resource);
		h.connect(); 
		return h; 
	}
	
	/**
	 * Downloads a page from the HTTPClient
	 * Can handle (although its a little spotty) chunked encodings
	 */
	public static String getPage(HTTPClient h/*, String server, String resource*/) throws Exception
	{

		String response = "";
		String transferEncoding = h.getHeaderField("Transfer-Encoding"); 
		if (h.responseCode.equals("200") && 
				(transferEncoding==null || !transferEncoding.equals("chunked")) )
		{
			
		
			BufferedReader reader = h.getInputStream(); //new BufferedReader(new InputStreamReader(h.getInputStream()));
			String k = h.getHeaderField("Content-Length"); 
			int length = Integer.parseInt(h.getHeaderField("Content-Length"));			
			char [] buffer = new char[length];
			int read = reader.read(buffer);
			response = new String(buffer);
			reader.close(); 
			//System.out.println(response); 
			return response; 				

			
		}
		else if ( h.responseCode.equals("200") && 
				  transferEncoding!=null && 
				  transferEncoding.equals("chunked") )
		{
			BufferedReader reader = h.getInputStream(); 
			String chunkSizeString = reader.readLine(); 
			int chunkSize = Integer.parseInt(chunkSizeString,16);
			 
			while (chunkSize != 0) 
			{
				char [] buffer = new char[chunkSize]; 
				int read = reader.read(buffer); 
				String data = new String(buffer); 
				response += data; 
				chunkSizeString = reader.readLine();
				while (!chunkSizeString.equals(""))
					chunkSizeString = reader.readLine();
				chunkSizeString = reader.readLine();
				try { 
					chunkSize = Integer.parseInt(chunkSizeString,16);					
				}
				catch (NumberFormatException e) 
				{
					chunkSize = 0; 
				}
			}
			return response; 
		}
		Set<Map.Entry<String,String>> headers = h.headers.entrySet();
	
		return null; 
	}
	public ArrayList<String> getLinks() { return parser.getLinks(); } 
	public ArrayList<LinkInfo>  verifyLinks() throws Exception
	{
		ArrayList<LinkInfo> info = new ArrayList<LinkInfo>(); 
		
		for (String s : parser.getLinks())
		{
			LinkInfo l = new LinkInfo(); 
			l.page = this; 
			l.link = s; 
			String path; 
			if (url.getPath().lastIndexOf('/')!=-1)
				path = url.getPath().substring(0,url.getPath().lastIndexOf('/'));
			else 
				path = url.getPath(); 
			if (!s.startsWith("http://"))
			{
				if (s.startsWith("/"))
						s = "http://"+url.getHost() + s; 
				else
					s = "http://" + url.getHost() + path + "/" +  s; 
			}
			
			l.url = new URL(s);			
			info.add(l); 
		}

		LinkVerifier l = new LinkVerifier(info); 
		l.verify(); 
		return info;
	}
	
	public static LinkStatus verifyLink(String server, String resource) throws Exception
	{
		HTTPClient h = new HTTPClient(server, resource); 
		h.setRequestMethod("HEAD"); 
		h.connect();		
		if (h.responseCode.equals("200"))
			return LinkStatus.Good; 
		else if (h.responseCode.equals("302") || 
				 h.responseCode.equals("301")) 
		{
			return LinkStatus.Moved; 
		}
		else if (h.responseCode.equals("404"))
			return LinkStatus.NotFound; 
		
		return LinkStatus.Bad; 
		
	}
	
	class PageMovedException extends Exception
	{
		public PageMovedException(String s) 
		{
			super(s); 
		}
	}
	
	public class LinkInfo
	{
		HtmlPage page; 
		String link; 
		URL url; 
		LinkStatus status;
		String message = ""; 
		Set<Map.Entry<String, String>> headers;
		public HtmlPage getPage() { return page; } 
		public String getLink() { return link; } 
		public URL getURL() { return url; } 
		public LinkStatus getStatus() { return status; }
		public Set<Map.Entry<String, String>> getHeaders() { return headers; }
		public String getMessage() { return message; } 
	}


}
