package acmedriver;
import java.util.*;
import java.sql.*;


/** DDBS Connection
 *
 *	This is the connection object used by applications to 
 *	connect to the DDBS System.  This implements the 
 *	java.sql.Connection interface.
 *
 * <P>A Connection represents a session with a specific
 * database. Within the context of a Connection, SQL statements are
 * executed and results are returned.
 *
 * <P>A Connection's database is able to provide information
 * describing its tables, its supported SQL grammar, its stored
 * procedures, the capabilities of this connection, etc. This
 * information is obtained with the getMetaData method.
 *
 * <P><B>Note:</B> By default the Connection automatically commits
 * changes after executing each statement. If auto commit has been
 * disabled, an explicit commit must be done or database changes will
 * not be saved.
 *
 * @see DriverManager#getConnection
 * @see Statement 
 * @see ResultSet
 * @see DatabaseMetaData
 *	
 * @author David Silberberg
 * @author Kalman Hazins
 * @version 1.0
 */
	
public class DDBSConnection implements Connection {

    private static final int ORACLE_DATABASE = 1;
    private static final int MYSQL_DATABASE = 2;
    
    private static final String MYSQL_ADDRESS = "128.220.101.71";
    
    Connection oracleConnection; 
    Connection mysqlConnection; 
    
    
    /** 
     *Constructors 
     */	
    public DDBSConnection (Properties info) throws SQLException
    {
		String username = info.getProperty("user"); 
		String password = info.getProperty("password"); 
		
		try { 
			oracleConnection = connect(username, password, ORACLE_DATABASE);
			mysqlConnection  = connect(username, password, MYSQL_DATABASE);
		} catch (Exception e) { 
			throw new SQLException(e.getMessage()); 
		}
    }
    
    /**
     * Connects to a database.
     * @param username The username to connect with.
     * @param password The password to connect with.
     * @param db       The type of database to connect to.
     */
    public static Connection connect(String username,
            String password, int db) throws
            ClassNotFoundException,
            InstantiationException,
            SQLException,
            IllegalAccessException {
    	
        // Create and populate database properties
        Properties props = new Properties();

        props.put("user", username);
        props.put("password", password);

        String dbURL = "";

        // Load the Oracle Driver
        if (db == ORACLE_DATABASE) {
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
            //dbURL = "jdbc:mysql://" + MYSQL_ADDRESS + "/" + username;
            
            
            // Instantiate the Oracle driver
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            // Address of the Oracle database server
            dbURL = "jdbc:oracle:thin:@aplcen.apl.jhu.edu:1521:PTE";
        } else if (db == MYSQL_DATABASE) {
            // Instantiate the MySQL driver
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            dbURL = "jdbc:mysql://" + MYSQL_ADDRESS + "/" + username;
        }

        System.out.println(dbURL);
        // Get the connection
        Connection conn = DriverManager.getConnection(dbURL, props);
        

        return conn;

    }
	
    /**
     * Creates a <code>Statement</code> object for sending
     * SQL statements to the database.
     * SQL statements without parameters are normally
     * executed using Statement objects. If the same SQL statement 
     * is executed many times, it is more efficient to use a 
     * PreparedStatement
     *
     * JDBC 2.0
     *
     * Result sets created using the returned Statement will have
     * forward-only type, and read-only concurrency, by default.
     *
     * @return a new Statement object 
     * @exception SQLException if a database access error occurs
     */

    public Statement createStatement() 
    	throws SQLException
    {
    	return new DDBSStatement(this.oracleConnection, this.mysqlConnection);
    }

    /**
     * Creates a <code>PreparedStatement</code> object for sending
     * parameterized SQL statements to the database.
     * 
     * A SQL statement with or without IN parameters can be
     * pre-compiled and stored in a PreparedStatement object. This
     * object can then be used to efficiently execute this statement
     * multiple times.
     *
     * <P><B>Note:</B> This method is optimized for handling
     * parametric SQL statements that benefit from precompilation. If
     * the driver supports precompilation,
     * the method <code>prepareStatement</code> will send
     * the statement to the database for precompilation. Some drivers
     * may not support precompilation. In this case, the statement may
     * not be sent to the database until the <code>PreparedStatement</code> is
     * executed.  This has no direct effect on users; however, it does
     * affect which method throws certain SQLExceptions.
     *
     * JDBC 2.0
     *
     * Result sets created using the returned PreparedStatement will have
     * forward-only type and read-only concurrency, by default.
     *
     * @param sql a SQL statement that may contain one or more '?' IN
     * parameter placeholders
     * @return a new PreparedStatement object containing the
     * pre-compiled statement 
     * @exception SQLException if a database access error occurs
     */
    public PreparedStatement prepareStatement(String sql)
        throws SQLException
    {
        return null;
    }

    /**
     * Creates a <code>CallableStatement</code> object for calling
     * database stored procedures.
     * The CallableStatement provides
     * methods for setting up its IN and OUT parameters, and
     * methods for executing the call to a stored procedure.
     *
     * <P><B>Note:</B> This method is optimized for handling stored
     * procedure call statements. Some drivers may send the call
     * statement to the database when the method <code>prepareCall</code>
     * is done; others
     * may wait until the CallableStatement is executed. This has no
     * direct effect on users; however, it does affect which method
     * throws certain SQLExceptions.
     *
     * JDBC 2.0
     *
     * Result sets created using the returned CallableStatement will have
     * forward-only type and read-only concurrency, by default.
     *
     * @param sql a SQL statement that may contain one or more '?'
     * parameter placeholders. Typically this  statement is a JDBC
     * function call escape string.
     * @return a new CallableStatement object containing the
     * pre-compiled SQL statement 
     * @exception SQLException if a database access error occurs
     */
    public CallableStatement prepareCall(String sql) 
    	throws SQLException
    {
    	return new DDBSCallableStatement(this.oracleConnection,
    			this.mysqlConnection); // for now
    }
						
    /**
     * Converts the given SQL statement into the system's native SQL grammar.
     * A driver may convert the JDBC sql grammar into its system's
     * native SQL grammar prior to sending it; this method returns the
     * native form of the statement that the driver would have sent.
     *
     * @param sql a SQL statement that may contain one or more '?'
     * parameter placeholders
     * @return the native form of this statement
     * @exception SQLException if a database access error occurs
     */
    public String nativeSQL(String sql) 
    	throws SQLException
    {
    	return new String(""); // for now
    }

    /**
     * Sets this connection's auto-commit mode.
     * If a connection is in auto-commit mode, then all its SQL
     * statements will be executed and committed as individual
     * transactions.  Otherwise, its SQL statements are grouped into
     * transactions that are terminated by a call to either
     * the method <code>commit</code> or the method <code>rollback</code>.
     * By default, new connections are in auto-commit
     * mode.
     *
     * The commit occurs when the statement completes or the next
     * execute occurs, whichever comes first. In the case of
     * statements returning a ResultSet, the statement completes when
     * the last row of the ResultSet has been retrieved or the
     * ResultSet has been closed. In advanced cases, a single
     * statement may return multiple results as well as output
     * parameter values. In these cases the commit occurs when all results and
     * output parameter values have been retrieved.
     *
     * @param autoCommit true enables auto-commit; false disables
     * auto-commit.  
     * @exception SQLException if a database access error occurs
     */
    public void setAutoCommit(boolean autoCommit) 
    	throws SQLException
    {
    	return;
    }

    /**
     * Get the current auto-commit state.
     *
     * @return Current state of auto-commit mode.
     * @exception SQLException if a database-access error occurs.
     * @see #setAutoCommit 
     */
     
    public boolean getAutoCommit() 
    	throws SQLException
    {
    	return false; // for now
    }

    /**
     * Makes all changes made since the previous
     * commit/rollback permanent and releases any database locks
     * currently held by the Connection. This method should be
     * used only when auto-commit mode has been disabled.
     *
     * @exception SQLException if a database access error occurs
     * @see #setAutoCommit 
     */
    public void commit() 
    	throws SQLException
    {
    }

    /**
     * Drops all changes made since the previous
     * commit/rollback and releases any database locks currently held
     * by this Connection. This method should be used only when auto-
     * commit has been disabled.
     *
     * @exception SQLException if a database access error occurs
     * @see #setAutoCommit 
     */
    public void rollback() 
    	throws SQLException
    {
    }

    /**
     * Releases a Connection's database and JDBC resources
     * immediately instead of waiting for
     * them to be automatically released.
     *
     * <P><B>Note:</B> A Connection is automatically closed when it is
     * garbage collected. Certain fatal errors also result in a closed
     * Connection.
     *
     * @exception SQLException if a database access error occurs
     */
    public void close() 
    	throws SQLException
    {
    	oracleConnection.close(); 
    	mysqlConnection.close(); 
    }

    /**
     * Tests to see if a Connection is closed.
     *
     * @return true if the connection is closed; false if it's still open
     * @exception SQLException if a database-access error occurs.
     */
     
    public boolean isClosed() 
    	throws SQLException
    {
    	return false; // for now
    }

    //======================================================================
    // Advanced features:

    //======================================================================
    // Advanced features:

    /**
     * Gets the metadata regarding this connection's database.
     * A Connection's database is able to provide information
     * describing its tables, its supported SQL grammar, its stored
     * procedures, the capabilities of this connection, and so on. This
     * information is made available through a DatabaseMetaData
     * object.
     *
     * @return a DatabaseMetaData object for this Connection 
     * @exception SQLException if a database access error occurs
     */
    public DatabaseMetaData getMetaData() 
    	throws SQLException
    {
    	return new DDBSDatabaseMetaData();
    }

    /**
     * Puts this connection in read-only mode as a hint to enable 
     * database optimizations.
     *
     * <P><B>Note:</B> This method cannot be called while in the
     * middle of a transaction.
     *
     * @param readOnly true enables read-only mode; false disables
     * read-only mode.  
     * @exception SQLException if a database access error occurs
     */
    public void setReadOnly(boolean readOnly) 
    	throws SQLException
    {
    }

    /**
     * Tests to see if the connection is in read-only mode.
     *
     * @return true if connection is read-only
     * @exception SQLException if a database-access error occurs.
     */
    public boolean isReadOnly() 
    	throws SQLException
    {
    	return true; // for now
    }

    /**
     * Sets a catalog name in order to select 	
     * a subspace of this Connection's database in which to work.
     * If the driver does not support catalogs, it will
     * silently ignore this request.
     *
     * @exception SQLException if a database access error occurs
     */
    public void setCatalog(String catalog) 
    	throws SQLException
    {
    }

    /**
     * Return the Connection's current catalog name.
     *
     * @return the current catalog name or null
     * @exception SQLException if a database-access error occurs.
     */
    public String getCatalog() 
    	throws SQLException
    {
    	return new String("DDBS");
    }
	
    /**
     * Transactions are not supported. 
     */
    static int TRANSACTION_NONE	     = 0;

    /**
     * Dirty reads, non-repeatable reads and phantom reads can occur.
     */
    static int TRANSACTION_READ_UNCOMMITTED = 1;

    /**
     * Dirty reads are prevented; non-repeatable reads and phantom
     * reads can occur.
     */
    static int TRANSACTION_READ_COMMITTED   = 2;

    /**
     * Dirty reads and non-repeatable reads are prevented; phantom
     * reads can occur.     
     */
    static int TRANSACTION_REPEATABLE_READ  = 4;

    /**
     * Dirty reads, non-repeatable reads and phantom reads are prevented.
     */
    static int TRANSACTION_SERIALIZABLE     = 8;

    /**
     * Attempts to change the transaction
     * isolation level to the one given.
     * The constants defined in the interface <code>Connection</code>
     * are the possible transaction isolation levels.
     *
     * <P><B>Note:</B> This method cannot be called while
     * in the middle of a transaction.
     *
     * @param level one of the TRANSACTION_* isolation values with the
     * exception of TRANSACTION_NONE; some databases may not support
     * other values
     * @exception SQLException if a database access error occurs
     * @see DatabaseMetaData#supportsTransactionIsolationLevel 
     */
    public void setTransactionIsolation(int level) throws SQLException {}

    /**
     * Gets this Connection's current transaction isolation level.
     *
     * @return the current TRANSACTION_* mode value
     * @exception SQLException if a database access error occurs
     */
    public int getTransactionIsolation() throws SQLException { return 0; }

    /**
     * Returns the first warning reported by calls on this Connection.
     *
     * <P><B>Note:</B> Subsequent warnings will be chained to this
     * SQLWarning.
     *
     * @return the first SQLWarning or null 
     * @exception SQLException if a database access error occurs
     */
    public SQLWarning getWarnings() throws SQLException { return new SQLWarning(""); }

    /**
     * Clears all warnings reported for this <code>Connection</code> object.	
     * After a call to this method, the method <code>getWarnings</code>
     * returns null until a new warning is
     * reported for this Connection.  
     *
     * @exception SQLException if a database access error occurs
     */
    public void clearWarnings() throws SQLException {}

    //--------------------------JDBC 2.0-----------------------------

    /**
     * JDBC 2.0
     *
     * Creates a <code>Statement</code> object that will generate
     * <code>ResultSet</code> objects with the given type and concurrency.
     * This method is the same as the <code>createStatement</code> method
     * above, but it allows the default result set
     * type and result set concurrency type to be overridden.
     *
     * @param resultSetType a result set type; see ResultSet.TYPE_XXX
     * @param resultSetConcurrency a concurrency type; see ResultSet.CONCUR_XXX
     * @return a new Statement object 
     * @exception SQLException if a database access error occurs
     */
    public Statement createStatement(int resultSetType, int resultSetConcurrency) 
        throws SQLException { return null; }

    /**
     * JDBC 2.0
     *
     * Creates a <code>PreparedStatement</code> object that will generate
     * <code>ResultSet</code> objects with the given type and concurrency.
     * This method is the same as the <code>prepareStatement</code> method
     * above, but it allows the default result set
     * type and result set concurrency type to be overridden.
     *
     * @param resultSetType a result set type; see ResultSet.TYPE_XXX
     * @param resultSetConcurrency a concurrency type; see ResultSet.CONCUR_XXX
     * @return a new PreparedStatement object containing the
     * pre-compiled SQL statement 
     * @exception SQLException if a database access error occurs
     */
    public PreparedStatement prepareStatement(String sql, int resultSetType, 
                                              int resultSetConcurrency)
        throws SQLException { return null; }

    /**
     * JDBC 2.0
     *
     * Creates a <code>CallableStatement</code> object that will generate
     * <code>ResultSet</code> objects with the given type and concurrency.
     * This method is the same as the <code>prepareCall</code> method
     * above, but it allows the default result set
     * type and result set concurrency type to be overridden.
     *
     * @param resultSetType a result set type; see ResultSet.TYPE_XXX
     * @param resultSetConcurrency a concurrency type; see ResultSet.CONCUR_XXX
     * @return a new CallableStatement object containing the
     * pre-compiled SQL statement 
     * @exception SQLException if a database access error occurs
     */
    public CallableStatement prepareCall(String sql, int resultSetType, 
                                         int resultSetConcurrency)
        throws SQLException
    { return null; }

    /**
     * JDBC 2.0
     *
     * Gets the type map object associated with this connection.
     * Unless the application has added an entry to the type map,
     * the map returned will be empty.
     *
     * @return the <code>java.util.Map</code> object associated 
     *         with this <code>Connection</code> object
     */
    public java.util.Map getTypeMap() throws SQLException { return null; }

    /**
     * JDBC 2.0
     *
     * Installs the given type map as the type map for
     * this connection.  The type map will be used for the
     * custom mapping of SQL structured types and distinct types.
     *
     * @param the <code>java.util.Map</code> object to install
     *        as the replacement for this <code>Connection</code>
     *        object's default type map
     */
//    public void setTypeMap(java.util.Map map) throws SQLException {}
    
   public void setHoldability(int i) {}
   public int getHoldability() { return 0; }
   public Savepoint setSavepoint() { return null; }
   public Savepoint setSavepoint(String s) {return null; }
   public void rollback(Savepoint p) {}
   public void releaseSavepoint(Savepoint p) {}
   public Statement createStatement(int i, int j, int k) { return null; }
   public PreparedStatement prepareStatement(String s, int i, int j, int k) 
   { return null; }
   public CallableStatement prepareCall(String s, int i, int j, int k) 
   { return null; }
   public PreparedStatement prepareStatement(String s, int i) { return null; }
   public PreparedStatement prepareStatement(String s, int[] ia) {return null;}
   public PreparedStatement prepareStatement(String s, String[] sa) 
   { return null; }

@Override
public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Blob createBlob() throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Clob createClob() throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public NClob createNClob() throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public SQLXML createSQLXML() throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Struct createStruct(String arg0, Object[] arg1) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Properties getClientInfo() throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public String getClientInfo(String arg0) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean isValid(int arg0) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}

@Override
public void setClientInfo(Properties arg0) throws SQLClientInfoException {
	// TODO Auto-generated method stub
	
}

@Override
public void setClientInfo(String arg0, String arg1)
		throws SQLClientInfoException {
	// TODO Auto-generated method stub
	
}

@Override
public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
	// TODO Auto-generated method stub
	
}

@Override
public boolean isWrapperFor(Class<?> arg0) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}

@Override
public <T> T unwrap(Class<T> arg0) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}

}
	
