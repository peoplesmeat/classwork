package acmedriver;

import java.sql.*;
import java.util.*;

/**
 * The interface that every driver class must implement.
 * <P>The Java SQL framework allows for multiple database drivers.
 *
 * <P>Each driver should supply a class that implements
 * the Driver interface.
 *
 * <P>The DriverManager will try to load as many drivers as it can
 * find and then for any given connection request, it will ask each
 * driver in turn to try to connect to the target URL.
 *
 * <P>It is strongly recommended that each Driver class should be
 * small and standalone so that the Driver class can be loaded and
 * queried without bringing in vast quantities of supporting code.
 *
 * <P>When a Driver class is loaded, it should create an instance of
 * itself and register it with the DriverManager. This means that a
 * user can load and register a driver by calling
 * <pre>
 *   <code>Class.forName("foo.bah.Driver")</code>
 * </pre>
 *
 * @see DriverManager
 * @see Connection 
 */

/** DDBS Driver

	This is the driver which is linked to applications using
	DDBS.  It supports JDBC calls.
	
	@see DriverManager
        @see Connection 
    
	@author David Silberberg
	@version 1.0
	*/
	
public class DDBSDriver implements Driver {

    static int majorVersion = 1;
    static int minorVersion = 0;
  
    static { 
    	try { 
    		DriverManager.registerDriver(new DDBSDriver()); 
    	} catch (Exception e) { 
    		System.out.println("Unable To Register Driver"); 
    	}
    }
  
    /** 
     *Constructor
     */
    public DDBSDriver() 
    { 
    }
  
    /**
     * Attempts to make a database connection to the given URL.
     * The driver should return "null" if it realizes it is the wrong kind
     * of driver to connect to the given URL.  This will be common, as when
     * the JDBC driver manager is asked to connect to a given URL it passes
     * the URL to each loaded driver in turn.
     *
     * <P>The driver should raise a SQLException if it is the right 
     * driver to connect to the given URL, but has trouble connecting to
     * the database.
     *
     * <P>The java.util.Properties argument can be used to passed arbitrary
     * string tag/value pairs as connection arguments.
     * Normally at least "user" and "password" properties should be
     * included in the Properties.
     *
     * @param url the URL of the database to which to connect
     * @param info a list of arbitrary string tag/value pairs as
     * connection arguments. Normally at least a "user" and
     * "password" property should be included.
     * @return a <code>Connection</code> object that represents a
     *         connection to the URL
     * @exception SQLException if a database access error occurs
     */

    public Connection connect(String url, Properties info)
        throws SQLException
    {
    	if (url.split(":")[0].equals("ddswww")) {
    		return new DDBSConnection(info);
    	}
    	return null; 
    }

    /**
     * Returns true if the driver thinks that it can open a connection
     * to the given URL.  Typically drivers will return true if they
     * understand the subprotocol specified in the URL and false if
     * they don't.
     *
     * @param url The URL of the database.
     * @return True if this driver can connect to the given URL.  
     * @exception SQLException if a database-access error occurs.
     */
    public boolean acceptsURL(String url) 
    	throws SQLException
    {
        return true;
    }
  
    /**
     * <p>The getPropertyInfo method is intended to allow a generic GUI tool to 
     * discover what properties it should prompt a human for in order to get 
     * enough information to connect to a database.  Note that depending on
     * the values the human has supplied so far, additional values may become
     * necessary, so it may be necessary to iterate though several calls
     * to getPropertyInfo.
     *
     * @param url The URL of the database to connect to.
     * @param info A proposed list of tag/value pairs that will be sent on
     *          connect open.
     * @return An array of DriverPropertyInfo objects describing possible
     *          properties.  This array may be an empty array if no properties
     *          are required.
     * @exception SQLException if a database-access error occurs.
     */
  
    public DriverPropertyInfo[] getPropertyInfo(String url, 
                                                Properties info)
        throws SQLException
    {
        return new DriverPropertyInfo[1];
    }
  
    /**
     * Gets the driver's major version number. Initially this should be 1.
     * @return this driver's major version number
     */
    public int getMajorVersion()
    {
        return majorVersion;
    }
  
    /**
     * Gets the driver's minor version number. Initially this should be 0.
     * @return this driver's minor version number
     */
    public int getMinorVersion()
    {
        return minorVersion;
    }

    /**
     * Reports whether this driver is a genuine JDBC
     * COMPLIANT<sup><font size=-2>TM</font></sup> driver.
     * A driver may only report true here if it passes the JDBC compliance
     * tests; otherwise it is required to return false.
     *
     * JDBC compliance requires full support for the JDBC API and full support
     * for SQL 92 Entry Level.  It is expected that JDBC compliant drivers will
     * be available for all the major commercial databases.
     *
     * This method is not intended to encourage the development of non-JDBC
     * compliant drivers, but is a recognition of the fact that some vendors
     * are interested in using the JDBC API and framework for lightweight
     * databases that do not support full database functionality, or for
     * special databases such as document information retrieval where a SQL
     * implementation may not be feasible.
     */
    public boolean jdbcCompliant()
    {
        return false;
    }
} 
