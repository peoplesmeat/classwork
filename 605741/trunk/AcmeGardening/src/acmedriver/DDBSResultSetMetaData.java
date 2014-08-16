package acmedriver;
/*
 * @(#)ResultSetMetaData.java	1.5 97/02/11
 * 
 * Copyright (c) 1995, 1996 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Sun
 * Microsystems, Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Sun.
 * 
 * SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 * 
 * CopyrightVersion 1.1_beta
 * 
 */


import java.sql.*;
import java.util.*;

/**
 * A ResultSetMetaData object can be used to find out about the types 
 * and properties of the columns in a ResultSet.
 */

public class DDBSResultSetMetaData
    implements ResultSetMetaData
{
    public DDBSResultSetMetaData() {
    }

    /**
     * What's the number of columns in the ResultSet?
     *
     * @return the number
     * @exception SQLException if a database-access error occurs.
     */
    public int getColumnCount() throws SQLException
    {
        return 0;
    }

    /**
     * Is the column automatically numbered, thus read-only?
     *
     * @param column the first column is 1, the second is 2, ...
     * @return true if so
     * @exception SQLException if a database-access error occurs.
     */
    public boolean isAutoIncrement(int column) throws SQLException
    {
        return false;
    }

    /**
     * Does a column's case matter?
     *
     * @param column the first column is 1, the second is 2, ...
     * @return true if so
     * @exception SQLException if a database-access error occurs.
     */
    public boolean isCaseSensitive(int column) throws SQLException
    {
        return false;
    }	

    /**
     * Can the column be used in a where clause?
     *
     * @param column the first column is 1, the second is 2, ...
     * @return true if so
     * @exception SQLException if a database-access error occurs.
     */
    public boolean isSearchable(int column) throws SQLException
    {
        return true;
    }

    /**
     * Is the column a cash value?
     *
     * @param column the first column is 1, the second is 2, ...
     * @return true if so
     * @exception SQLException if a database-access error occurs.
     */
    public boolean isCurrency(int column) throws SQLException
    {
        return false;
    }

    /**
     * Can you put a NULL in this column?		
     *
     * @param column the first column is 1, the second is 2, ...
     * @return columnNoNulls, columnNullable or columnNullableUnknown
     * @exception SQLException if a database-access error occurs.
     */
    public int isNullable(int column) throws SQLException
    {
        return 0;
    }

    /**
     * Does not allow NULL values.
     */
    int columnNoNulls = 0;

    /**
     * Allows NULL values.
     */
    int columnNullable = 1;

    /**
     * Nullability unknown.
     */
    int columnNullableUnknown = 2;

    /**
     * Is the column a signed number?
     *
     * @param column the first column is 1, the second is 2, ...
     * @return true if so
     * @exception SQLException if a database-access error occurs.
     */
    public boolean isSigned(int column) throws SQLException
    {
        return false;
    }

    /**
     * What's the column's normal max width in chars?
     *
     * @param column the first column is 1, the second is 2, ...
     * @return max width
     * @exception SQLException if a database-access error occurs.
     */
    // Return the maximum column display size for any of the ResultSetMetaDatas
    // we are holding on to.
    public int getColumnDisplaySize(int column) throws SQLException
    {
        return 0;
    }
    /**
     * What's the suggested column title for use in printouts and
     * displays?
     *
     * @param column the first column is 1, the second is 2, ...
     * @return true if so 
     * @exception SQLException if a database-access error occurs.
     */
    public String getColumnLabel(int column) throws SQLException  {
        return "";
    }

    /**
     * What's a column's name?
     *
     * @param column the first column is 1, the second is 2, ...
     * @return column name
     * @exception SQLException if a database-access error occurs.
     */
    public String getColumnName(int column) throws SQLException {
        return "";
    }

    /**
     * What's a column's table's schema?
     *
     * @param column the first column is 1, the second is 2, ...
     * @return schema name or "" if not applicable
     * @exception SQLException if a database-access error occurs.
     */
    public String getSchemaName(int column) throws SQLException
    {
        return "";
    }

    /**
     * What's a column's number of decimal digits?
     *
     * @param column the first column is 1, the second is 2, ...
     * @return precision
     * @exception SQLException if a database-access error occurs.
     */
    public int getPrecision(int column) throws SQLException
    {
        return 0;
    }

    /**
     * What's a column's number of digits to right of the decimal point?
     *
     * @param column the first column is 1, the second is 2, ...
     * @return scale
     * @exception SQLException if a database-access error occurs.
     */
    public int getScale(int column) throws SQLException
    {
        return 0;
    }
  
    /**
     * What's a column's table name? 
     *
     * @return table name or "" if not applicable
     * @exception SQLException if a database-access error occurs.
     */
    public String getTableName(int column) throws SQLException
    {
        return "";
    }
  
    /**
     * What's a column's table's catalog name?
     *
     * @param column the first column is 1, the second is 2, ...
     * @return column name or "" if not applicable.
     * @exception SQLException if a database-access error occurs.
     */
    public String getCatalogName(int column) throws SQLException
    {
        return "";
    }

    /**
     * What's a column's SQL type?
     *
     * @param column the first column is 1, the second is 2, ...
     * @return SQL type
     * @exception SQLException if a database-access error occurs.
     * @see Types
     */
    public int getColumnType(int column) throws SQLException
    {
        return 0;
    }

    /**
     * What's a column's data source specific type name?
     *
     * @param column the first column is 1, the second is 2, ...
     * @return type name
     * @exception SQLException if a database-access error occurs.
     */
    public String getColumnTypeName(int column) throws SQLException
    {
        return "";
    }
        
    /**
     * Is a column definitely not writable?
     *
     * @param column the first column is 1, the second is 2, ...
     * @return true if so
     * @exception SQLException if a database-access error occurs.
     */
    public boolean isReadOnly(int column) throws SQLException
    {
        return false;
    }

    /**
     * Is it possible for a write on the column to succeed?
     *
     * @param column the first column is 1, the second is 2, ...
     * @return true if so
     * @exception SQLException if a database-access error occurs.
     */
    public boolean isWritable(int column) throws SQLException
    {
        return false;
    }

    /**
     * Will a write on the column definitely succeed?	
     *
     * @param column the first column is 1, the second is 2, ...
     * @return true if so
     * @exception SQLException if a database-access error occurs.
     */
    public boolean isDefinitelyWritable(int column) throws SQLException
    {
        return false;
    }

    //--------------------------JDBC 2.0-----------------------------------

    /**
     * JDBC 2.0
     *
     * <p>Returns the fully-qualified name of the Java class whose instances 
     * are manufactured if the method <code>ResultSet.getObject</code>
     * is called to retrieve a value 
     * from the column.  <code>ResultSet.getObject</code> may return a subclass of the
     * class returned by this method.
     *
     * @return the fully-qualified name of the class in the Java programming
     *         language that would be used by the method 
     * <code>ResultSet.getObject</code> to retrieve the value in the specified
     * column. This is the class name used for custom mapping.
     * @exception SQLException if a database access error occurs
     */
    public String getColumnClassName(int column) throws SQLException { return ""; }

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
