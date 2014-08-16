package AcmeDriver;
import java.sql.*;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * The interface used to execute SQL 
 * stored procedures.  JDBC provides a stored procedure 
 * SQL escape that allows stored procedures to be called in a standard 
 * way for all RDBMSs. This escape syntax has one form that includes 
 * a result parameter and one that does not. If used, the result 
 * parameter must be registered as an OUT parameter. The other parameters
 * can be used for input, output or both. Parameters are referred to 
 * sequentially, by number. The first parameter is 1.
 * <P>
 * <blockquote><pre>
 *   {?= call &lt;procedure-name&gt;[&lt;arg1&gt;,&lt;arg2&gt;, ...]}
 *   {call &lt;procedure-name&gt;[&lt;arg1&gt;,&lt;arg2&gt;, ...]}
 * </pre></blockquote>
 * <P>
 * IN parameter values are set using the set methods inherited from
 * {@link PreparedStatement}.  The type of all OUT parameters must be
 * registered prior to executing the stored procedure; their values
 * are retrieved after execution via the <code>get</code> methods provided here.
 * <P>
 * A <code>CallableStatement</code> can return one {@link ResultSet} or 
 * multiple <code>ResultSet</code> objets.  Multiple 
 * <code>ResultSet</code> objects are handled using operations
 * inherited from {@link Statement}.
 * <P>
 * For maximum portability, a call's <code>ResultSet</code> objects and 
 * update counts should be processed prior to getting the values of output
 * parameters.
 *
 * @see Connection#prepareCall
 * @see ResultSet 
 */
public class DDSWWWCallableStatement extends DDSWWWPreparedStatement
    implements CallableStatement {

    /**
     * Constructor
     */
    public DDSWWWCallableStatement()
    {
    }
    
    /**
     * Registers the OUT parameter in ordinal position 
     * <code>parameterIndex</code> to the JDBC type 
     * <code>sqlType</code>.  All OUT parameters must be registered
     * before a stored procedure is executed.
     * <p>
     * The JDBC type specified by <code>sqlType</code> for an OUT
     * parameter determines the Java type that must be used
     * in the <code>get</code> method to read the value of that parameter.
     * <p>
     * If the JDBC type expected to be returned to this output parameter
     * is specific to this particular database, <code>sqlType</code>
     * should be <code>java.sql.Types.OTHER</code>.  The method 
     * {@link #getObject} retrieves the value.
     * @param parameterIndex the first parameter is 1, the second is 2, 
     * and so on
     * @param sqlType the JDBC type code defined by <code>java.sql.Types</code>.
     * If the parameter is of type Numeric or Decimal, the version of
     * <code>registerOutParameter</code> that accepts a scale value 
     * should be used.
     * @exception SQLException if a database access error occurs
     * @see Types 
     */
    public void registerOutParameter(int parameterIndex, int sqlType)
        throws SQLException {}

    /**
     * Registers the parameter in ordinal position
     * <code>parameterIndex</code> to be of JDBC type
     * <code>sqlType</code>.  This method must be called
     * before a stored procedure is executed.
     * <p>
     * The JDBC type specified by <code>sqlType</code> for an OUT
     * parameter determines the Java type that must be used
     * in the <code>get</code> method to read the value of that parameter.
     * <p>
     * This version of <code>registerOutParameter</code> should be
     * used when the parameter is of JDBC type <code>NUMERIC</code>
     * or <code>DECIMAL</code>.
     * @param parameterIndex the first parameter is 1, the second is 2,
     * and so on
     * @param sqlType SQL type code defined by <code>java.sql.Types</code>.
     * @param scale the desired number of digits to the right of the
     * decimal point.  It must be greater than or equal to zero.
     * @exception SQLException if a database access error occurs
     * @see Types 
     */
    public void registerOutParameter(int parameterIndex,
                                     int sqlType, int scale)
        throws SQLException {}

   public void registerOutParameter(String s, int i) {}
   public void registerOutParameter(String s, int i, int j) {}
   public void registerOutParameter(String s, int i, String s2) {}
    /**
     * Indicates whether or not the last OUT parameter read had the value of
     * SQL NULL.  Note that this method should be called only after
     * calling the <code>get</code> method; otherwise, there is no value to use in 
     * determining whether it is <code>null</code> or not.
     * @return <code>true</code> if the last parameter read was SQL NULL;
     * <code>false</code> otherwise. 
     * @exception SQLException if a database access error occurs
     */
    public boolean wasNull() throws SQLException
    {
    	return false; // for now
    }
    /**
     * Retrieves the value of a JDBC <code>CHAR</code>, <code>VARCHAR</code>, 
     * or <code>LONGVARCHAR</code> parameter as a <code>String</code> in 
     * the Java programming language.
     * <p>
     * For the fixed-length type JDBC CHAR, the <code>String</code> object
     * returned has exactly the same value the JDBC CHAR value had in the
     * database, including any padding added by the database.
     * @param parameterIndex the first parameter is 1, the second is 2, 
     * and so on
     * @return the parameter value. If the value is SQL NULL, the result 
     * is <code>null</code>.
     * @exception SQLException if a database access error occurs
     */
    public String getString(int parameterIndex) throws SQLException { return "";}
   public String getString(String s) { return ""; }
    /**
     * Gets the value of a JDBC BIT parameter as a <code>boolean</code> 
     * in the Java programming language.
     * @param parameterIndex the first parameter is 1, the second is 2, 
     * and so on
     * @return the parameter value.  If the value is SQL NULL, the result 
     * is <code>false</code>.
     * @exception SQLException if a database access error occurs
     */
    public boolean getBoolean(int parameterIndex) throws SQLException { return true; }
   public boolean getBoolean (String s) { return true; }
    /**
     * Gets the value of a JDBC TINYINT parameter as a <code>byte</code> 
     * in the Java programming language.
     * @param parameterIndex the first parameter is 1, the second is 2, 
     * and so on
     * @return the parameter value.  If the value is SQL NULL, the result 
     * is 0.
     * @exception SQLException if a database access error occurs
     */
    public byte getByte(int parameterIndex) throws SQLException { return (byte) 0; }
   public byte getByte(String s) { return (byte)0; }

    /**
     * Gets the value of a JDBC SMALLINT parameter as a <code>short</code>
     * in the Java programming language.
     * @param parameterIndex the first parameter is 1, the second is 2, 
     * and so on
     * @return the parameter value.  If the value is SQL NULL, the result 
     * is 0.
     * @exception SQLException if a database access error occurs
     */
    public short getShort(int parameterIndex) throws SQLException { return 0; }
   public short getShort(String s) { return (short)0; }
    /**
     * Gets the value of a JDBC INTEGER parameter as an <code>int</code>
     * in the Java programming language.
     * @param parameterIndex the first parameter is 1, the second is 2, 
     * and so on
     * @return the parameter value.  If the value is SQL NULL, the result 
     * is 0.
     * @exception SQLException if a database access error occurs
     */
    public int getInt(int parameterIndex) throws SQLException { return 0; }
   public int getInt(String s) {return 0; }
    /**
     * Gets the value of a JDBC BIGINT parameter as a <code>long</code>
     * in the Java programming language.
     * @param parameterIndex the first parameter is 1, the second is 2, 
     * and so on
     * @return the parameter value.  If the value is SQL NULL, the result 
     * is 0.
     * @exception SQLException if a database access error occurs
     */
    public long getLong(int parameterIndex) throws SQLException { return 0; }
   public long getLong(String s) { return 0L; }
    /**
     * Gets the value of a JDBC FLOAT parameter as a <code>float</code>
     * in the Java programming language.
     * @param parameterIndex the first parameter is 1, the second is 2, 
     * and so on
     * @return the parameter value.  If the value is SQL NULL, the result 
     * is 0.
     * @exception SQLException if a database access error occurs
     */
    public float getFloat(int parameterIndex) throws SQLException { return 0; }
   public float getFloat(String s) {return 0.F; }
    /**
     * Gets the value of a JDBC DOUBLE parameter as a <code>double</code>
     * in the Java programming language.
     * @param parameterIndex the first parameter is 1, the second is 2,
     * and so on
     * @return the parameter value.  If the value is SQL NULL, the result 
     * is 0.
     * @exception SQLException if a database access error occurs
     */
    public double getDouble(int parameterIndex) throws SQLException { return 0; }
   public double getDouble(String s) {return 0.;}
   public java.net.URL getURL(int i) { return null; }
   public java.net.URL getURL(String s) {return null; }


    /** 
     * Gets the value of a JDBC <code>NUMERIC</code> parameter as a 
     * <code>java.math.BigDecimal</code> object with scale digits to
     * the right of the decimal point.
     * @param parameterIndex the first parameter is 1, the second is 2, 
     * and so on
     * @param scale the number of digits to the right of the decimal point 
     * @return the parameter value.  If the value is SQL NULL, the result is
     * <code>null</code>. 
     * @exception SQLException if a database access error occurs
     * @deprecated
     */
    public BigDecimal getBigDecimal(int parameterIndex, int scale)
        throws SQLException { return null; }

    /**
     * Gets the value of a JDBC <code>BINARY</code> or <code>VARBINARY</code> 
     * parameter as an array of <code>byte</code> vlaures in the Java 
     * programming language.
     * @param parameterIndex the first parameter is 1, the second is 2, 
     * and so on
     * @return the parameter value.  If the value is SQL NULL, the result is 
     *  <code>null</code>.
     * @exception SQLException if a database access error occurs
     */
    public byte[] getBytes(int parameterIndex) throws SQLException { return new byte[0]; }
   public byte[] getBytes(String s) { return new byte[0]; }
    /**
     * Gets the value of a JDBC <code>DATE</code> parameter as a 
     * <code>java.sql.Date</code> object.
     * @param parameterIndex the first parameter is 1, the second is 2, 
     * and so on
     * @return the parameter value.  If the value is SQL NULL, the result 
     * is <code>null</code>.
     * @exception SQLException if a database access error occurs
     */
    public java.sql.Date getDate(int parameterIndex) throws SQLException { return null; }
   public java.sql.Date getDate(String s) {return null; }
    /**
     * Get the value of a JDBC <code>TIME</code> parameter as a 
     * <code>java.sql.Time</code> object.
     * @param parameterIndex the first parameter is 1, the second is 2, 
     * and so on
     * @return the parameter value.  If the value is SQL NULL, the result 
     * is <code>null</code>.
     * @exception SQLException if a database access error occurs
     */
    public java.sql.Time getTime(int parameterIndex) throws SQLException { return null; }
   public java.sql.Time getTime(String s) {return null; }
    /**
     * Gets the value of a JDBC <code>TIMESTAMP</code> parameter as a 
     * <code>java.sql.Timestamp</code> object.
     * @param parameterIndex the first parameter is 1, the second is 2, 
     * and so on
     * @return the parameter value.  If the value is SQL NULL, the result 
     * is <code>null</code>.
     * @exception SQLException if a database access error occurs
     */
    public java.sql.Timestamp getTimestamp(int parameterIndex) 
        throws SQLException { return null; }
   public java.sql.Timestamp getTimestamp(String s) { return null; }
    //----------------------------------------------------------------------
    // Advanced features:


    /**
     * Gets the value of a parameter as an object in the Java 
     * programming language.
     * <p>
     * This method returns a Java object whose type corresponds to the JDBC
     * type that was registered for this parameter using the method
     * <code>registerOutParameter</code>.  By registering the target JDBC
     * type as <code>java.sql.Types.OTHER</code>, this method can be used
     * to read database-specific abstract data types.
     * @param parameterIndex The first parameter is 1, the second is 2, 
     * and so on
     * @return A <code>java.lang.Object</code> holding the OUT parameter value.
     * @exception SQLException if a database access error occurs
     * @see Types 
     */
    public Object getObject(int parameterIndex) throws SQLException { return null; }
   public Object getObject(String s) { return null; }

    //--------------------------JDBC 2.0-----------------------------

    /**
     * JDBC 2.0
     *
     * Gets the value of a JDBC <code>NUMERIC</code> parameter as a 
     * <code>java.math.BigDecimal</code> object with as many digits to the
     * right of the decimal point as the value contains.
     * @param parameterIndex the first parameter is 1, the second is 2,
     * and so on
     * @return the parameter value in full precision.  If the value is 
     * SQL NULL, the result is <code>null</code>. 
     * @exception SQLException if a database access error occurs
     */
    public BigDecimal getBigDecimal(int parameterIndex) throws SQLException { return null; }
   public BigDecimal getBigDecimal(String s) { return null; }
    /**
     * JDBC 2.0
     *
     * Returns an object representing the value of OUT parameter 
     * <code>i</code> and uses <code>map</code> for the custom
     * mapping of the parameter value.
     * <p>
     * This method returns a Java object whose type corresponds to the
     * JDBC type that was registered for this parameter using the method
     * <code>registerOutParameter</code>.  By registering the target
     * JDBC type as <code>java.sql.Types.OTHER</code>, this method can
     * be used to read database-specific abstract data types.  
     * @param i the first parameter is 1, the second is 2, and so on
     * @param map the mapping from SQL type names to Java classes
     * @return a java.lang.Object holding the OUT parameter value.
     * @exception SQLException if a database access error occurs
     */
    public Object  getObject (int i, java.util.Map map) throws SQLException { return null; }
   public Object getObject (String s, java.util.Map map) { return null; }
    /**
     * JDBC 2.0
     *
     * Gets the value of a JDBC <code>REF(&lt;structured-type&gt;)</code>
     * parameter as a {@link Ref} object in the Java programming language.
     * @param i the first parameter is 1, the second is 2, 
     * and so on
     * @return the parameter value as a <code>Ref</code> object in the
     * Java programming language.  If the value was SQL NULL, the value
     * <code>null</code> is returned.
     * @exception SQLException if a database access error occurs
     */
    public Ref getRef (int i) throws SQLException { return null; }
   public Ref getRef(String s) {return null; }
    /**
     * JDBC 2.0
     *
     * Gets the value of a JDBC <code>BLOB</code> parameter as a
     * {@link Blob} object in the Java programming language.
     * @param i the first parameter is 1, the second is 2, and so on
     * @return the parameter value as a <code>Blob</code> object in the
     * Java programming language.  If the value was SQL NULL, the value
     * <code>null</code> is returned.
     * @exception SQLException if a database access error occurs
     */
    public Blob getBlob (int i) throws SQLException { return null; }
   public Blob getBlob(String s) {return null; }
    /**
     * JDBC 2.0
     *
     * Gets the value of a JDBC <code>CLOB</code> parameter as a
     * <code>Clob</code> object in the Java programming language.
     * @param i the first parameter is 1, the second is 2, and
     * so on
     * @return the parameter value as a <code>Clob</code> object in the
     * Java programming language.  If the value was SQL NULL, the
     * value <code>null</code> is returned.
     * @exception SQLException if a database access error occurs
     */
    public Clob getClob (int i) throws SQLException { return null; }
   public Clob getClob(String s) {return null; }
    /**
     * JDBC 2.0
     *
     * Gets the value of a JDBC <code>ARRAY</code> parameter as an
     * {@link Array} object in the Java programming language.
     * @param i the first parameter is 1, the second is 2, and 
     * so on
     * @return the parameter value as an <code>Array</code> object in
     * the Java programming language.  If the value was SQL NULL, the
     * value <code>null</code> is returned.
     * @exception SQLException if a database access error occurs
     */
    public Array getArray (int i) throws SQLException { return null; }
   public Array getArray(String s) { return null; }
    /**
     * Gets the value of a JDBC <code>DATE</code> parameter as a 
     * <code>java.sql.Date</code> object, using
     * the given <code>Calendar</code> object
     * to construct the date.
     * With a <code>Calendar</code> object, the driver
     * can calculate the date taking into account a custom timezone and locale.
     * If no <code>Calendar</code> object is specified, the driver uses the
     * default timezone and locale.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, 
     * and so on
     * @param cal the <code>Calendar</code> object the driver will use
     *            to construct the date
     * @return the parameter value.  If the value is SQL NULL, the result is 
     * <code>null</code>.
     * @exception SQLException if a database access error occurs
     */
    public java.sql.Date getDate(int parameterIndex, Calendar cal) 
        throws SQLException { return null; }
   public java.sql.Date getDate(String s, Calendar cal) {return null; }
    /**
     * Gets the value of a JDBC <code>TIME</code> parameter as a 
     * <code>java.sql.Time</code> object, using
     * the given <code>Calendar</code> object
     * to construct the time.
     * With a <code>Calendar</code> object, the driver
     * can calculate the time taking into account a custom timezone and locale.
     * If no <code>Calendar</code> object is specified, the driver uses the
     * default timezone and locale.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,
     * and so on
     * @param cal the <code>Calendar</code> object the driver will use
     *            to construct the time
     * @return the parameter value; if the value is SQL NULL, the result is 
     * <code>null</code>.
     * @exception SQLException if a database access error occurs
     */
    public java.sql.Time getTime(int parameterIndex, Calendar cal) 
        throws SQLException { return null; }
   public java.sql.Time getTime(String s, Calendar cal) {return null; }
    /**
     * Gets the value of a JDBC <code>TIMESTAMP</code> parameter as a
     * <code>java.sql.Timestamp</code> object, using
     * the given <code>Calendar</code> object to construct
     * the <code>Timestamp</code> object.
     * With a <code>Calendar</code> object, the driver
     * can calculate the timestamp taking into account a custom timezone and locale.
     * If no <code>Calendar</code> object is specified, the driver uses the
     * default timezone and locale.
     *
     *
     * @param parameterIndex the first parameter is 1, the second is 2, 
     * and so on
     * @param cal the <code>Calendar</code> object the driver will use
     *            to construct the timestamp
     * @return the parameter value.  If the value is SQL NULL, the result is 
     * <code>null</code>.
     * @exception SQLException if a database access error occurs
     */
    public java.sql.Timestamp getTimestamp(int parameterIndex, Calendar cal) 
        throws SQLException { return null; }
   public java.sql.Timestamp getTimestamp(String s, Calendar cal) 
   {return null; }

    /**
     * JDBC 2.0
     *
     * Registers the designated output parameter.  This version of 
     * the method <code>registerOutParameter</code>
     * should be used for a user-named or REF output parameter.  Examples
     * of user-named types include: STRUCT, DISTINCT, JAVA_OBJECT, and
     * named array types.
     *
     * Before executing a stored procedure call, you must explicitly
     * call <code>registerOutParameter</code> to register the type from
     * <code>java.sql.Types</code> for each
     * OUT parameter.  For a user-named parameter the fully-qualified SQL
     * type name of the parameter should also be given, while a REF
     * parameter requires that the fully-qualified type name of the
     * referenced type be given.  A JDBC driver that does not need the
     * type code and type name information may ignore it.   To be portable,
     * however, applications should always provide these values for
     * user-named and REF parameters.
     *
     * Although it is intended for user-named and REF parameters,
     * this method may be used to register a parameter of any JDBC type.
     * If the parameter does not have a user-named or REF type, the
     * typeName parameter is ignored.
     *
     * <P><B>Note:</B> When reading the value of an out parameter, you
     * must use the <code>getXXX</code> method whose Java type XXX corresponds to the
     * parameter's registered SQL type.
     *
     * @param parameterIndex the first parameter is 1, the second is 2,...
     * @param sqlType a value from {@link java.sql.Types}
     * @param typeName the fully-qualified name of an SQL structured type
     * @exception SQLException if a database-access error occurs
     * @see Types
     */
    public void registerOutParameter (int paramIndex, int sqlType, String typeName)
        throws SQLException {}

    /**
     * Executes the SQL query in this <code>PreparedStatement</code> object
     * and returns the result set generated by the query.
     *
     * @return a ResultSet that contains the data produced by the
     * query; never null
     * @exception SQLException if a database access error occurs
     */
    public ResultSet executeQuery() throws SQLException { return null; }

    /**     * Executes the SQL INSERT, UPDATE or DELETE statement
     * in this <code>PreparedStatement</code> object.
     * In addition,
     * SQL statements that return nothing, such as SQL DDL statements,
     * can be executed.
     *
     * @return either the row count for INSERT, UPDATE or DELETE statements;
     * or 0 for SQL statements that return nothing
     * @exception SQLException if a database access error occurs
     */
    public int executeUpdate() throws SQLException { return 0; }

    /**
     * Sets the designated parameter to SQL NULL.
     *
     * <P><B>Note:</B> You must specify the parameter's SQL type.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param sqlType the SQL type code defined in java.sql.Types
     * @exception SQLException if a database access error occurs
     */
    public void setNull(int parameterIndex, int sqlType) throws SQLException {}

   public void setNull(String s, int i) {}

    /**
     * Sets the designated parameter to a Java boolean value.  The driver converts this
     * to an SQL BIT value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {}

   public void setBoolean(String s, boolean b) {}

    /**
     * Sets the designated parameter to a Java byte value.  The driver converts this
     * to an SQL TINYINT value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setByte(int parameterIndex, byte x) throws SQLException {}

   public void setByte(String s, byte b) {}

    /**
     * Sets the designated parameter to a Java short value.  The driver converts this
     * to an SQL SMALLINT value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setShort(int parameterIndex, short x) throws SQLException {}

   public void setShort(String s, short x) {}

    /**
     * Sets the designated parameter to a Java int value.  The driver converts this
     * to an SQL INTEGER value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setInt(int parameterIndex, int x) throws SQLException {}

   public void setInt(String s, int x) {}

    /**
     * Sets the designated parameter to a Java long value.  The driver converts this
     * to an SQL BIGINT value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setLong(int parameterIndex, long x) throws SQLException {}

   public void setLong(String s, long x) {}

    /**
     * Sets the designated parameter to a Java float value.  The driver converts this
     * to an SQL FLOAT value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setFloat(int parameterIndex, float x) throws SQLException {}
   public void setFloat(String s, float x) {}
    /**
     * Sets the designated parameter to a Java double value.  The driver converts this
     * to an SQL DOUBLE value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setDouble(int parameterIndex, double x) throws SQLException {}
   public void setDouble(String s, double x) {}
    /**
     * Sets the designated parameter to a java.lang.BigDecimal value.  
     * The driver converts this to an SQL NUMERIC value when
     * it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {}
   public void setBigDecimal(String s, BigDecimal x) {}
    /**
     * Sets the designated parameter to a Java String value.  The driver converts this
     * to an SQL VARCHAR or LONGVARCHAR value (depending on the argument's
     * size relative to the driver's limits on VARCHARs) when it sends
     * it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setString(int parameterIndex, String x) throws SQLException {}
   public void setString(String s, String x) {}
    /**
     * Sets the designated parameter to a Java array of bytes.  The driver converts
     * this to an SQL VARBINARY or LONGVARBINARY (depending on the
     * argument's size relative to the driver's limits on VARBINARYs)
     * when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value 
     * @exception SQLException if a database access error occurs
     */
    public void setBytes(int parameterIndex, byte x[]) throws SQLException {}
   public void setBytes(String s, byte[] x) {}
    /**
     * Sets the designated parameter to a java.sql.Date value.  The driver converts this
     * to an SQL DATE value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setDate(int parameterIndex, java.sql.Date x)
        throws SQLException {}
   public void setDate(String s, java.sql.Date x) {}
    /**
     * Sets the designated parameter to a java.sql.Time value.  The driver converts this
     * to an SQL TIME value when it sends it to the database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @exception SQLException if a database access error occurs
     */
    public void setTime(int parameterIndex, java.sql.Time x) 
        throws SQLException {}
   public void setTime(String s, java.sql.Time x) {}
    /**
     * Sets the designated parameter to a java.sql.Timestamp value.  The driver
     * converts this to an SQL TIMESTAMP value when it sends it to the
     * database.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value 
     * @exception SQLException if a database access error occurs
     */
    public void setTimestamp(int parameterIndex, java.sql.Timestamp x)
        throws SQLException {}
   public void setTimestamp(String s, java.sql.Timestamp x) {}
    /**
     * Sets the designated parameter to the given input stream, which will have 
     * the specified number of bytes.
     * When a very large ASCII value is input to a LONGVARCHAR
     * parameter, it may be more practical to send it via a
     * java.io.InputStream. JDBC will read the data from the stream
     * as needed, until it reaches end-of-file.  The JDBC driver will
     * do any necessary conversion from ASCII to the database char format.
     * 
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the Java input stream that contains the ASCII parameter value
     * @param length the number of bytes in the stream 
     * @exception SQLException if a database access error occurs
     */
    public void setAsciiStream(int parameterIndex, java.io.InputStream x, int length)
        throws SQLException {}
   public void setAsciiStream(String s, java.io.InputStream x, int length) {}
    /**
     * Sets the designated parameter to the given input stream, which will have 
     * the specified number of bytes.
     * When a very large UNICODE value is input to a LONGVARCHAR
     * parameter, it may be more practical to send it via a
     * java.io.InputStream. JDBC will read the data from the stream
     * as needed, until it reaches end-of-file.  The JDBC driver will
     * do any necessary conversion from UNICODE to the database char format.
     * The byte format of the Unicode stream must be Java UTF-8, as
     * defined in the Java Virtual Machine Specification.
     * 
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...  
     * @param x the java input stream which contains the
     * UNICODE parameter value 
     * @param length the number of bytes in the stream 
     * @exception SQLException if a database access error occurs
     * @deprecated
     */
    public void setUnicodeStream(int parameterIndex, java.io.InputStream x, 
                                 int length) throws SQLException {}

    /**
     * Sets the designated parameter to the given input stream, which will have 
     * the specified number of bytes.
     * When a very large binary value is input to a LONGVARBINARY
     * parameter, it may be more practical to send it via a
     * java.io.InputStream. JDBC will read the data from the stream
     * as needed, until it reaches end-of-file.
     * 
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the java input stream which contains the binary parameter value
     * @param length the number of bytes in the stream 
     * @exception SQLException if a database access error occurs
     */
    public void setBinaryStream(int parameterIndex, java.io.InputStream x, 
                                int length) throws SQLException {}
   public void setBinaryStream(String s, java.io.InputStream x, int length) {}
    /**
     * Clears the current parameter values immediately.
     * <P>In general, parameter values remain in force for repeated use of a
     * Statement. Setting a parameter value automatically clears its
     * previous value.  However, in some cases it is useful to immediately
     * release the resources used by the current parameter values; this can
     * be done by calling clearParameters.
     *
     * @exception SQLException if a database access error occurs
     */
    public void clearParameters() throws SQLException {}

    //----------------------------------------------------------------------
    // Advanced features:

    /**
     * <p>Sets the value of a parameter using an object. The second
     * argument must be an object type; for integral values, the
     * java.lang equivalent objects should be used.
     *
     * <p>The given Java object will be converted to the targetSqlType
     * before being sent to the database.
     *
     * If the object has a custom mapping (is of a class implementing SQLData),
     * the JDBC driver should call its method <code>writeSQL</code> to write it 
     * to the SQL data stream.
     * If, on the other hand, the object is of a class implementing
     * Ref, Blob, Clob, Struct, 
     * or Array, the driver should pass it to the database as a value of the 
     * corresponding SQL type.
     *
     * <p>Note that this method may be used to pass datatabase-
     * specific abstract data types. 
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the object containing the input parameter value
     * @param targetSqlType the SQL type (as defined in java.sql.Types) to be 
     * sent to the database. The scale argument may further qualify this type.
     * @param scale for java.sql.Types.DECIMAL or java.sql.Types.NUMERIC types,
     *          this is the number of digits after the decimal point.  For all other
     *          types, this value will be ignored.
     * @exception SQLException if a database access error occurs
     * @see Types 
     */
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scale)
        throws SQLException {}
   public void setObject(String s, Object x, int i, int j) {}

    /**
     * Sets the value of the designated parameter with the given object.
     * This method is like setObject above, except that it assumes a scale of zero.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the object containing the input parameter value
     * @param targetSqlType the SQL type (as defined in java.sql.Types) to be 
     *                      sent to the database
     * @exception SQLException if a database access error occurs
     */
    public void setObject(int parameterIndex, Object x, int targetSqlType) 
        throws SQLException {}

   public void setObject(String s, Object x, int i) {}

    /**
     * <p>Sets the value of a parameter using an object; use the
     * java.lang equivalent objects for integral values.
     *
     * <p>The JDBC specification specifies a standard mapping from
     * Java Object types to SQL types.  The given argument java object
     * will be converted to the corresponding SQL type before being
     * sent to the database.
     *
     * <p>Note that this method may be used to pass datatabase-
     * specific abstract data types, by using a Driver-specific Java
     * type.
     *
     * If the object is of a class implementing SQLData,
     * the JDBC driver should call its method <code>writeSQL</code> to write it 
     * to the SQL data stream.
     * If, on the other hand, the object is of a class implementing
     * Ref, Blob, Clob, Struct, 
     * or Array, then the driver should pass it to the database as a value of the 
     * corresponding SQL type.
     *
     * This method throws an exception if there is an ambiguity, for example, if the
     * object is of a class implementing more than one of those interfaces.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the object containing the input parameter value 
     * @exception SQLException if a database access error occurs
     */
    public void setObject(int parameterIndex, Object x) throws SQLException {}

   public void setObject(String s, Object x) {}

    /**
     * Executes any kind of SQL statement.
     * Some prepared statements return multiple results; the execute
     * method handles these complex statements as well as the simpler
     * form of statements handled by executeQuery and executeUpdate.
     *
     * @exception SQLException if a database access error occurs
     * @see Statement#execute
     */
    public boolean execute() throws SQLException { return true; }

    //--------------------------JDBC 2.0-----------------------------

    /**
     * JDBC 2.0
     *
     * Adds a set of parameters to the batch.
     * 
     * @exception SQLException if a database access error occurs
     * @see Statement#addBatch
     */
    public void addBatch() throws SQLException {}

    /**
     * JDBC 2.0
     *
     * Sets the designated parameter to the given <code>Reader</code>
     * object, which is the given number of characters long.
     * When a very large UNICODE value is input to a LONGVARCHAR
     * parameter, it may be more practical to send it via a
     * java.io.Reader. JDBC will read the data from the stream
     * as needed, until it reaches end-of-file.  The JDBC driver will
     * do any necessary conversion from UNICODE to the database char format.
     * 
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the java reader which contains the UNICODE data
     * @param length the number of characters in the stream 
     * @exception SQLException if a database access error occurs
     */
    public void setCharacterStream(int parameterIndex,
                                   java.io.Reader reader,
                                   int length) throws SQLException {}
   public void setCharacterStream(String s, java.io.Reader reader, int length) {}
    /**
     * JDBC 2.0
     *
     * Sets a REF(&lt;structured-type&gt;) parameter.
     *
     * @param i the first parameter is 1, the second is 2, ...
     * @param x an object representing data of an SQL REF Type
     * @exception SQLException if a database access error occurs
     */
    public void setRef (int i, Ref x) throws SQLException {}

    /**
     * JDBC 2.0
     *
     * Sets a BLOB parameter.
     *
     * @param i the first parameter is 1, the second is 2, ...
     * @param x an object representing a BLOB
     * @exception SQLException if a database access error occurs
     */
    public void setBlob (int i, Blob x) throws SQLException {}

    /**
     * JDBC 2.0
     *
     * Sets a CLOB parameter.
     *
     * @param i the first parameter is 1, the second is 2, ...
     * @param x an object representing a CLOB
     * @exception SQLException if a database access error occurs
     */
    public void setClob (int i, Clob x) throws SQLException {}

    /**
     * JDBC 2.0
     *
     * Sets an Array parameter.
     *
     * @param i the first parameter is 1, the second is 2, ...
     * @param x an object representing an SQL array
     * @exception SQLException if a database access error occurs
     */
    public void setArray (int i, Array x) throws SQLException {}

    /**
     * JDBC 2.0
     *
     * Gets the number, types and properties of a ResultSet's columns.
     *
     * @return the description of a ResultSet's columns
     * @exception SQLException if a database access error occurs
     */
    public ResultSetMetaData getMetaData() throws SQLException { return null; }

    /**
     * JDBC 2.0
     *
     * Sets the designated parameter to a java.sql.Date value,
     * using the given <code>Calendar</code> object.  The driver uses
     * the <code>Calendar</code> object to construct an SQL DATE,
     * which the driver then sends to the database.  With a
     * a <code>Calendar</code> object, the driver can calculate the date
     * taking into account a custom timezone and locale.  If no
     * <code>Calendar</code> object is specified, the driver uses the default
     * timezone and locale.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @param cal the <code>Calendar</code> object the driver will use
     *            to construct the date
     * @exception SQLException if a database access error occurs
     */
    public void setDate(int parameterIndex, java.sql.Date x, Calendar cal)
        throws SQLException {}
   public void setDate(String s, java.sql.Date x, Calendar cal) {}


    /**
     * JDBC 2.0
     *
     * Sets the designated parameter to a java.sql.Time value,
     * using the given <code>Calendar</code> object.  The driver uses
     * the <code>Calendar</code> object to construct an SQL TIME,
     * which the driver then sends to the database.  With a
     * a <code>Calendar</code> object, the driver can calculate the time
     * taking into account a custom timezone and locale.  If no
     * <code>Calendar</code> object is specified, the driver uses the default
     * timezone and locale.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @param cal the <code>Calendar</code> object the driver will use
     *            to construct the time
     * @exception SQLException if a database access error occurs
     */
    public void setTime(int parameterIndex, java.sql.Time x, Calendar cal) 
        throws SQLException {}
   public void setTime(String s, java.sql.Time x, Calendar cal) {}
    /**
     * JDBC 2.0
     *
     * Sets the designated parameter to a java.sql.Timestamp value,
     * using the given <code>Calendar</code> object.  The driver uses
     * the <code>Calendar</code> object to construct an SQL TIMESTAMP,
     * which the driver then sends to the database.  With a
     * a <code>Calendar</code> object, the driver can calculate the timestamp
     * taking into account a custom timezone and locale.  If no
     * <code>Calendar</code> object is specified, the driver uses the default
     * timezone and locale.
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value 
     * @param cal the <code>Calendar</code> object the driver will use
     *            to construct the timestamp
     * @exception SQLException if a database access error occurs
     */
    public void setTimestamp(int parameterIndex, java.sql.Timestamp x, Calendar cal)
        throws SQLException {}
   public void setTimestamp(String s, java.sql.Timestamp x, Calendar cal) {}
    /**
     * JDBC 2.0
     *
     * Sets the designated parameter to SQL NULL.  This version of setNull should
     * be used for user-named types and REF type parameters.  Examples
     * of user-named types include: STRUCT, DISTINCT, JAVA_OBJECT, and 
     * named array types.
     *
     * <P><B>Note:</B> To be portable, applications must give the
     * SQL type code and the fully-qualified SQL type name when specifying
     * a NULL user-defined or REF parameter.  In the case of a user-named type 
     * the name is the type name of the parameter itself.  For a REF 
     * parameter the name is the type name of the referenced type.  If 
     * a JDBC driver does not need the type code or type name information, 
     * it may ignore it.     
     *
     * Although it is intended for user-named and Ref parameters,
     * this method may be used to set a null parameter of any JDBC type.
     * If the parameter does not have a user-named or REF type, the given
     * typeName is ignored.
     *
     *
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param sqlType a value from java.sql.Types
     * @param typeName the fully-qualified name of an SQL user-named type,
     *  ignored if the parameter is not a user-named type or REF 
     * @exception SQLException if a database access error occurs
     */
    public void setNull (int paramIndex, int sqlType, String typeName) 
        throws SQLException {}
   public void setNull(String s, int i, String d) {}
    public ResultSet executeQuery(String sql) throws SQLException { return null; }

    /**
     * Executes an SQL INSERT, UPDATE or DELETE statement. In addition,
     * SQL statements that return nothing, such as SQL DDL statements,
     * can be executed.
     *
     * @param sql a SQL INSERT, UPDATE or DELETE statement or a SQL
     * statement that returns nothing
     * @return either the row count for INSERT, UPDATE or DELETE or 0
     * for SQL statements that return nothing
     * @exception SQLException if a database access error occurs
     */
    public int executeUpdate(String sql) throws SQLException { return 0;}

    /**
     * Releases this <code>Statement</code> object's database 
     * and JDBC resources immediately instead of waiting for
     * this to happen when it is automatically closed.
     * It is generally good practice to release resources as soon as
     * you are finished with them to avoid tying up database
     * resources.
     * <P><B>Note:</B> A Statement is automatically closed when it is
     * garbage collected. When a Statement is closed, its current
     * ResultSet, if one exists, is also closed.  
     *
     * @exception SQLException if a database access error occurs
     */
    public void close() throws SQLException {}

    //----------------------------------------------------------------------

    /**
     * Returns the maximum number of bytes allowed
     * for any column value. 
     * This limit is the maximum number of bytes that can be
     * returned for any column value.
     * The limit applies only to BINARY,
     * VARBINARY, LONGVARBINARY, CHAR, VARCHAR, and LONGVARCHAR
     * columns.  If the limit is exceeded, the excess data is silently
     * discarded.
     *
     * @return the current max column size limit; zero means unlimited 
     * @exception SQLException if a database access error occurs
     */
    public int getMaxFieldSize() throws SQLException { return 0;}
    
    /**
     * Sets the limit for the maximum number of bytes in a column to
     * the given number of bytes.  This is the maximum number of bytes 
     * that can be returned for any column value.  This limit applies
     * only to BINARY, VARBINARY, LONGVARBINARY, CHAR, VARCHAR, and
     * LONGVARCHAR fields.  If the limit is exceeded, the excess data
     * is silently discarded. For maximum portability, use values
     * greater than 256.
     *
     * @param max the new max column size limit; zero means unlimited 
     * @exception SQLException if a database access error occurs
     */
    public void setMaxFieldSize(int max) throws SQLException {}

    /**
     * Retrieves the maximum number of rows that a
     * ResultSet can contain.  If the limit is exceeded, the excess
     * rows are silently dropped.
     *
     * @return the current max row limit; zero means unlimited
     * @exception SQLException if a database access error occurs
     */
    public int getMaxRows() throws SQLException { return 0;}

    /**
     * Sets the limit for the maximum number of rows that any
     * ResultSet can contain to the given number.
     * If the limit is exceeded, the excess
     * rows are silently dropped.
     *
     * @param max the new max rows limit; zero means unlimited 
     * @exception SQLException if a database access error occurs
     */
    public void setMaxRows(int max) throws SQLException {}

    /**
     * Sets escape processing on or off.
     * If escape scanning is on (the default), the driver will do
     * escape substitution before sending the SQL to the database.
     *
     * Note: Since prepared statements have usually been parsed prior
     * to making this call, disabling escape processing for prepared
     * statements will have no effect.
     *
     * @param enable true to enable; false to disable
     * @exception SQLException if a database access error occurs
     */
    public void setEscapeProcessing(boolean enable) throws SQLException {}

    /**
     * Retrieves the number of seconds the driver will
     * wait for a Statement to execute. If the limit is exceeded, a
     * SQLException is thrown.
     *
     * @return the current query timeout limit in seconds; zero means unlimited 
     * @exception SQLException if a database access error occurs
     */
    public int getQueryTimeout() throws SQLException { return 0;}

    /**
     * Sets the number of seconds the driver will
     * wait for a Statement to execute to the given number of seconds.
     * If the limit is exceeded, a SQLException is thrown.
     *
     * @param seconds the new query timeout limit in seconds; zero means 
     * unlimited 
     * @exception SQLException if a database access error occurs
     */
    public void setQueryTimeout(int seconds) throws SQLException {}

    /**
     * Cancels this <code>Statement</code> object if both the DBMS and
     * driver support aborting an SQL statement.
     * This method can be used by one thread to cancel a statement that
     * is being executed by another thread.
     *
     * @exception SQLException if a database access error occurs
     */
    public void cancel() throws SQLException {}

    /**
     * Retrieves the first warning reported by calls on this Statement.
     * Subsequent Statement warnings will be chained to this
     * SQLWarning.
     *
     * <p>The warning chain is automatically cleared each time
     * a statement is (re)executed.
     *
     * <P><B>Note:</B> If you are processing a ResultSet, any
     * warnings associated with ResultSet reads will be chained on the
     * ResultSet object.
     *
     * @return the first SQLWarning or null 
     * @exception SQLException if a database access error occurs
     */
    public SQLWarning getWarnings() throws SQLException { return new SQLWarning(""); }

    /**
     * Clears all the warnings reported on this <code>Statement</code>
     * object. After a call to this method,
     * the method <code>getWarnings</code> will return 
     * null until a new warning is reported for this Statement.  
     *
     * @exception SQLException if a database access error occurs
     */
    public void clearWarnings() throws SQLException {}

    /**
     * Defines the SQL cursor name that will be used by
     * subsequent Statement <code>execute</code> methods. This name can then be
     * used in SQL positioned update/delete statements to identify the
     * current row in the ResultSet generated by this statement.  If
     * the database doesn't support positioned update/delete, this
     * method is a noop.  To insure that a cursor has the proper isolation
     * level to support updates, the cursor's SELECT statement should be
     * of the form 'select for update ...'. If the 'for update' phrase is 
     * omitted, positioned updates may fail.
     *
     * <P><B>Note:</B> By definition, positioned update/delete
     * execution must be done by a different Statement than the one
     * which generated the ResultSet being used for positioning. Also,
     * cursor names must be unique within a connection.
     *
     * @param name the new cursor name, which must be unique within
     *             a connection
     * @exception SQLException if a database access error occurs
     */
    public void setCursorName(String name) throws SQLException {}
	
    //----------------------- Multiple Results --------------------------

    /**
     * Executes a SQL statement that may return multiple results.
     * Under some (uncommon) situations a single SQL statement may return
     * multiple result sets and/or update counts.  Normally you can ignore
     * this unless you are (1) executing a stored procedure that you know may
     * return multiple results or (2) you are dynamically executing an
     * unknown SQL string.  The  methods <code>execute</code>,
     * <code>getMoreResults</code>, <code>getResultSet</code>,
     * and <code>getUpdateCount</code> let you navigate through multiple results.
     *
     * The <code>execute</code> method executes a SQL statement and indicates the
     * form of the first result.  You can then use getResultSet or
     * getUpdateCount to retrieve the result, and getMoreResults to
     * move to any subsequent result(s).
     *
     * @param sql any SQL statement
     * @return true if the next result is a ResultSet; false if it is
     * an update count or there are no more results
     * @exception SQLException if a database access error occurs
     * @see #getResultSet
     * @see #getUpdateCount
     * @see #getMoreResults 
     */
    public boolean execute(String sql) throws SQLException { return true; }
	
    /**
     *  Returns the current result as a <code>ResultSet</code> object. 
     *  This method should be called only once per result.
     *
     * @return the current result as a ResultSet; null if the result
     * is an update count or there are no more results
     * @exception SQLException if a database access error occurs
     * @see #execute 
     */
    public ResultSet getResultSet() throws SQLException { return null; } 

    /**
     *  Returns the current result as an update count;
     *  if the result is a ResultSet or there are no more results, -1
     *  is returned. 
     *  This method should be called only once per result.
     * 
     * @return the current result as an update count; -1 if it is a
     * ResultSet or there are no more results
     * @exception SQLException if a database access error occurs
     * @see #execute 
     */
    public int getUpdateCount() throws SQLException { return 0;}

    /**
     * Moves to a Statement's next result.  It returns true if 
     * this result is a ResultSet.  This method also implicitly
     * closes any current ResultSet obtained with getResultSet.
     *
     * There are no more results when (!getMoreResults() &&
     * (getUpdateCount() == -1)
     *
     * @return true if the next result is a ResultSet; false if it is
     * an update count or there are no more results
     * @exception SQLException if a database access error occurs
     * @see #execute 
     */
    public boolean getMoreResults() throws SQLException { return true; } 


    //--------------------------JDBC 2.0-----------------------------


    /**
     * JDBC 2.0
     *
     * Gives the driver a hint as to the direction in which
     * the rows in a result set
     * will be processed. The hint applies only to result sets created 
     * using this Statement object.  The default value is 
     * ResultSet.FETCH_FORWARD.
     * <p>Note that this method sets the default fetch direction for 
     * result sets generated by this <code>Statement</code> object.
     * Each result set has its own methods for getting and setting
     * its own fetch direction.
     * @param direction the initial direction for processing rows
     * @exception SQLException if a database access error occurs
     * or the given direction
     * is not one of ResultSet.FETCH_FORWARD, ResultSet.FETCH_REVERSE, or
     * ResultSet.FETCH_UNKNOWN
     */
    public void setFetchDirection(int direction) throws SQLException {}

    /**
     * JDBC 2.0
     *
     * Retrieves the direction for fetching rows from
     * database tables that is the default for result sets
     * generated from this <code>Statement</code> object.
     * If this <code>Statement</code> object has not set
     * a fetch direction by calling the method <code>setFetchDirection</code>,
     * the return value is implementation-specific.
     *
     * @return the default fetch direction for result sets generated
     *          from this <code>Statement</code> object
     * @exception SQLException if a database access error occurs
     */
    public int getFetchDirection() throws SQLException { return 0;}

    /**
     * JDBC 2.0
     *
     * Gives the JDBC driver a hint as to the number of rows that should 
     * be fetched from the database when more rows are needed.  The number 
     * of rows specified affects only result sets created using this 
     * statement. If the value specified is zero, then the hint is ignored.
     * The default value is zero.
     *
     * @param rows the number of rows to fetch
     * @exception SQLException if a database access error occurs, or the
     * condition 0 <= rows <= this.getMaxRows() is not satisfied.
     */
    public void setFetchSize(int rows) throws SQLException {}
  
    /**
     * JDBC 2.0
     *
     * Retrieves the number of result set rows that is the default 
     * fetch size for result sets
     * generated from this <code>Statement</code> object.
     * If this <code>Statement</code> object has not set
     * a fetch size by calling the method <code>setFetchSize</code>,
     * the return value is implementation-specific.
     * @return the default fetch size for result sets generated
     *          from this <code>Statement</code> object
     * @exception SQLException if a database access error occurs
     */
    public int getFetchSize() throws SQLException { return 0;}
    /**
     * JDBC 2.0
     *
     * Retrieves the result set concurrency.
     */
    public int getResultSetConcurrency() throws SQLException { return 0;}

    /**
     * JDBC 2.0
     *
     * Determine the result set type.
     */
    public int getResultSetType()  throws SQLException { return 0;}

    /**
     * JDBC 2.0
     *
     * Adds a SQL command to the current batch of commmands for the statement.
     * This method is optional.
     *
     * @param sql typically this is a static SQL INSERT or UPDATE statement
     * @exception SQLException if a database access error occurs, or the
     * driver does not support batch statements
     */
    public void addBatch( String sql ) throws SQLException {}

    /**
     * JDBC 2.0
     *
     * Makes the set of commands in the current batch empty.
     * This method is optional.
     *
     * @exception SQLException if a database access error occurs or the
     * driver does not support batch statements
     */
    public void clearBatch() throws SQLException {}

    /**
     * JDBC 2.0
     * 
     * Submits a batch of commands to the database for execution.
     * This method is optional.
     *
     * @return an array of update counts containing one element for each
     * command in the batch.  The array is ordered according 
     * to the order in which commands were inserted into the batch.
     * @exception SQLException if a database access error occurs or the
     * driver does not support batch statements
     */
    public int[] executeBatch() throws SQLException { return new int[0];}

    /**
     * JDBC 2.0
     * 
     * Returns the <code>Connection</code> object
     * that produced this <code>Statement</code> object.
     * @return the connection that produced this statement
     * @exception SQLException if a database access error occurs
     */
    public Connection getConnection()  throws SQLException { return null; }
}
