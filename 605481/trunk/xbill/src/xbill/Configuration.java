package xbill;

public class Configuration {
	private static Configuration config = new Configuration(); 
	private Configuration() { } 
	public static Configuration getConfiguration() { return config; } 
	public int width; 
	public int height; 
}
