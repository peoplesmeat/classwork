package linky;

import java.util.ArrayList;

import linky.HtmlPage.LinkInfo;
import linky.Linky.LinkStatus;

/**
 * This is a manager used to verify the links
 * This class will spin up NUM_WORKER_THREADS threads
 * to verify if the links are active. 
 * @author bdavis
 *
 */
class LinkVerifier
{	
	final int NUM_WORKER_THREADS = 4;
	
	HttpWorkerThread [] workers;
	ArrayList<LinkInfo> links;
	int verified = 0; 
	int started = 0; 
	
	public LinkVerifier(ArrayList<LinkInfo> info) 
	{
		workers = new HttpWorkerThread[NUM_WORKER_THREADS];
		this.links = info;
		for (int i=0; i<NUM_WORKER_THREADS; i++) 
			workers[i] = new HttpWorkerThread(); 
	}



	public ArrayList<LinkInfo> getLinkInfo()
	{
		return links; 
	}
	public synchronized void done(HttpWorkerThread w) 
	{
		verified++; 	
		if (started < links.size())
		{
			for (int i=0; i<NUM_WORKER_THREADS; i++) 
				if (workers[i] == w) 
				{
					workers[i] = new HttpWorkerThread(); 
					workers[i].setRequest(links.get(started));
					workers[i].start();
					started++;
				}
		}
		if (verified == links.size())
			this.notify(); 
	}
	
	public synchronized void verify() 
	{		
		for (HttpWorkerThread w : workers)
		{
			if (started < links.size())
			{					
				w.setRequest(links.get(started));
				w.start();
				started++; 
			}
		}
		try { 
			if (verified < links.size())
				this.wait(25000);
		}
		catch(Exception e) 
		{

		}
		return; 
	}

	/**
	 * An individual worker thread. Connects to a host and 
	 * verifies the response
	 * @author bdavis
	 *
	 */
	class HttpWorkerThread extends Thread 
	{
		String server; 
		String resource; 
		String anchor; 
		public void setRequest(	LinkInfo info) 
		{
			this.info = info; 
			server = info.url.getHost();  
			resource = info.url.getPath();
			anchor = info.url.getRef(); 
		}
		LinkInfo info; 

		public void run() 
		{

			try { 
				HTTPClient h = new HTTPClient(server, resource); 
				h.setRequestMethod("HEAD"); 
				h.connect(); 
				if (h.responseCode.equals("200"))
					info.status =  LinkStatus.Good; 
				else if (h.responseCode.equals("302") || 
						h.responseCode.equals("301")) 
				{
					info.message = "Link moved to " + h.getHeaderField("location"); 
					info.status =  LinkStatus.Moved; 
				}
				else if (h.responseCode.equals("404"))
					info.status =  LinkStatus.NotFound;
				else
					info.status = LinkStatus.Bad;
				info.headers = h.getHeaders();
			}
			catch (java.net.UnknownHostException e) 
			{
				info.status = LinkStatus.UnknownHost; 	
			}
			catch (Exception e) 
			{
				info.status = LinkStatus.Exception; 
			}


			LinkVerifier.this.done(this );
		}
	}
}