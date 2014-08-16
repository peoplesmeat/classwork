/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package acmegardening;

import java.sql.*;
import java.util.ArrayList; 
import java.util.Properties;   // Properties

/**
 *
 * @author davis
 */
public class AcmeDataAdapter {

    private static final int ORACLE_DATABASE = 1;
    private static final int MYSQL_DATABASE = 2;
    private static final int DDSWWW_DATABASE = 3; 
    
    private static final String MYSQL_ADDRESS = "128.220.101.71";

    /**
     * Connects to a database.
     * @param username The username to connect with.
     * @param password The password to connect with.
     * @param db       The type of database to connect to.
     */
    public static Connection connect(String username,
            String password) throws
            ClassNotFoundException,
            InstantiationException,
            SQLException,
            IllegalAccessException {
        int db = DDSWWW_DATABASE;
        // Create and populate database properties
        Properties props = new Properties();

        props.put("user", username);
        props.put("password", password);

        String dbURL = "";

        // Load the Oracle Driver
        if (db == ORACLE_DATABASE) {
            // Instantiate the Oracle driver
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            // Address of the Oracle database server
            dbURL = "jdbc:oracle:thin:@aplcen.apl.jhu.edu:1521:PTE";
        } else if (db == MYSQL_DATABASE) {
            // Instantiate the MySQL driver
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            dbURL = "jdbc:mysql://" + MYSQL_ADDRESS + "/" + username;
        } else if (db == DDSWWW_DATABASE) { 
        	Class.forName("acmedriver.DDBSDriver").newInstance(); 
        	dbURL = "ddswww://data"; 
        }

        System.out.println(dbURL);
        // Get the connection
        Connection conn = DriverManager.getConnection(dbURL, props);

        return conn;

    }

    /**
     * creates the customers tabler
     * @param conn
     * @throws java.sql.SQLException if the customers table alread exists
     */
    public static void createCustomersTable(Connection conn) throws
            SQLException {
        Statement create = conn.createStatement();

        create.executeUpdate("CREATE TABLE customers (" +
                "cust_id INT NOT NULL ," +
                "lastname varchar(20)," +
                "firstname varchar(20)," +
                "address varchar(60)," +
                "city varchar(20)," +
                "state varchar(2)," +
                "zip integer," +
                "PRIMARY KEY(cust_id)" +
                ")");
    }

    /**
     * Creates the orders table
     * @param conn
     * @throws java.sql.SQLException if the orders table already exists
     */
    public static void createOrdersTable(Connection conn)
            throws SQLException {
        Statement create = conn.createStatement();

        create.executeUpdate("CREATE TABLE orders (" +
                "cust_id integer," +
                "item_no integer," +
                "qty integer" +
                ")");
    }

    /**
     * Create the items table
     * @param conn
     * @throws java.sql.SQLException if the items table already exists
     */
    public static void createItemsTable(Connection conn)
            throws SQLException {
        Statement create = conn.createStatement();

        create.executeUpdate("CREATE TABLE items (" +
                "item_no INT NOT NULL ," +
                "item_name varchar(20), " +
                "price integer, " +
                "PRIMARY KEY(item_no)" +
                ")");

    }
    
    /**
     * Deletes all customers
     * @param conn
     * @throws java.sql.SQLException if the customers table doesn't exist
     */
    public static void deleteCustomers(Connection conn)
    	throws SQLException
    {
    	Statement delete = conn.createStatement(); 
    	
    	delete.executeUpdate("DELETE FROM customers"); 
    }
    
    /**
     * Delete a specific customer
     * @param conn
     * @param lastname
     * @param firstname
     * @throws java.sql.SQLException
     */
    public static void deleteCustomer(Connection conn, 
    		String lastname, String firstname)
    throws SQLException
    {
    	Statement delete = conn.createStatement(); 
    	
    	String s = "DELETE from customers WHERE "  + 
    	           "lastname = '"+lastname+
    	           "' AND firstname = '"+firstname+"'";
    	delete.executeUpdate(s); 	
    }
    
    /**
     * Deletes all orders
     * @param conn
     * @throws java.sql.SQLException if the orders table doesn't exist
     */
    public static void deleteOrders(Connection conn) 
    	throws SQLException 
    {
    	Statement delete = conn.createStatement(); 
    	delete.executeUpdate("DELETE FROM orders"); 
    }

    /**
     * Drop the customer table
     * @param conn
     * @throws java.sql.SQLException if the customer table doesn't exist
     */
    public static void dropCustomersTable(Connection conn)
            throws SQLException {
        Statement drop = conn.createStatement();

        drop.executeUpdate("DROP TABLE customers");
    }

    /**
     * Drops the orders table
     * @param conn
     * @throws java.sql.SQLException if the orders table doesn't exist
     */
    public static void dropOrdersTable(Connection conn)
            throws SQLException {
        Statement drop = conn.createStatement();

        drop.executeUpdate("DROP TABLE orders");
    }
    
    /**
     * Drop the items table
     * @param conn
     * @throws java.sql.SQLException if the items table doesn't exist
     */
    public static void dropItemsTable(Connection conn)
            throws SQLException {
        Statement drop = conn.createStatement();

        drop.executeUpdate("DROP TABLE items");
    }
    
    /**
     * Adds an item to the database
     * @param conn
     * @param item
     * @throws java.sql.SQLException
     */
    public static void addItem(Connection conn, AcmeItem item)  
            throws SQLException
    {
    	int k = (int)(Math.random()*Integer.MAX_VALUE); 
        Statement drop = conn.createStatement(); 
        drop.executeUpdate("" +
                "INSERT INTO items (item_no, item_name, price) VALUES (" +
                "" + k + "," + 
                "'" + item.getName() + "'," +
                "" + item.getPrice() + ")");
        
    }
    
    /**
     * Add a customer to the database
     * @param conn
     * @param customer
     * @throws java.sql.SQLException
     */
    public static void addCustomer(Connection conn, AcmeCustomer customer)
            throws SQLException
    {
        Statement insert = conn.createStatement();
        insert.executeUpdate("INSERT INTO customers (" +
                "cust_id, lastname, firstname, address, " +
                "city, state, zip) VALUES (" +
                ""  + (int)(Math.random()*Integer.MAX_VALUE) + "," +  
                "'" + customer.getLastName() + "'," +
                "'" + customer.getFirstName() + "'," +
                "'" + customer.getAddress() + "'," +
                "'" + customer.getCity() + "'," +
                "'" + customer.getState() + "'," +
                "" + customer.getZip() +
                ")");
    }
    
    /**
     * Finds a customer by name
     * @param conn
     * @param lastname
     * @param firstname
     * @return The customer or null if one doesn't exist
     * @throws java.sql.SQLException
     */
    public static AcmeCustomer findCustomer(Connection conn, String lastname, 
            String firstname) 
            throws SQLException
    {
        Statement select = conn.createStatement(); 
        ResultSet results = 
                select.executeQuery("SELECT * from customers WHERE " +
                "lastname='"+lastname+"' AND " +
                "firstname='"+firstname+"'"); 
        while (results.next())
        {
            AcmeCustomer c = new 
                    AcmeCustomer(results.getString("lastname"), 
                    results.getString("firstname"),
                    results.getString("address"),
                    results.getString("city"),
                    results.getString("state"), 
                    results.getInt("zip"), 
                    results.getInt("cust_id"));
            return c; 
                    
        }
        return null; 
        
    }
    
    /**
     * 
     * @param conn
     * @param cust_id
     * @return
     * @throws java.sql.SQLException
     */
    public static AcmeCustomer findCustomer(Connection conn, int cust_id) 
            throws SQLException
    {
        Statement select = conn.createStatement(); 
        ResultSet results = 
                select.executeQuery("SELECT * from customers WHERE " +
                "cust_id="+cust_id); 
        while (results.next())
        {
            AcmeCustomer c = new 
                    AcmeCustomer(results.getString("lastname"), 
                    results.getString("firstname"),
                    results.getString("address"),
                    results.getString("city"),
                    results.getString("state"), 
                    results.getInt("zip"), 
                    results.getInt("cust_id"));
            return c; 
                    
        }
        return null; 
        
    }
    
    /**
     * Returns all items
     * @param conn
     * @return A list of items, the list will be empty if there no items
     * @throws java.sql.SQLException
     */
    public static ArrayList<AcmeItem> getItems(Connection conn)
            throws SQLException {
        ArrayList<AcmeItem> items = new ArrayList<AcmeItem>();
        Statement select = conn.createStatement();
        String s = "SELECT * FROM items";
        ResultSet results = select.executeQuery(s);

        while (results.next()) {
            items.add(new AcmeItem(results.getString("item_name"),
                    results.getInt("price"),
                    results.getInt("item_no")));
        }
        return items; 
    }
    
    /**
     * Returns all orders
     * @param conn The DB Connection
     * @return All orders, the list will be empty if there are no orders
     * @throws java.sql.SQLException
     */
    public static ArrayList<AcmeOrder> getOrders(Connection conn) 
            throws SQLException
    {
        ArrayList<AcmeOrder> orders = new ArrayList<AcmeOrder>(); 
        Statement select = conn.createStatement(); 
        String s = "SELECT * FROM orders"; 
        
        ResultSet results = select.executeQuery(s); 
        while(results.next())
        {
            AcmeCustomer c = AcmeDataAdapter.findCustomer(conn, 
                    results.getInt("cust_id")); 
            AcmeItem i = AcmeDataAdapter.findItem(conn,
                    results.getInt("item_no")); 
            orders.add(new AcmeOrder(c, i, 
                    results.getInt("qty")));
        }
        
        return orders; 
        
    }
    
    public static ResultSet getReport(Connection conn) throws SQLException { 
    	Statement report = conn.createStatement(); 
    	String s = "SELECT * FROM orders INNER JOIN items, customers";
    	ResultSet results = report.executeQuery(s);
    	return results; 
    }
    
    /**
     * Finds an item
     * @param conn
     * @param item_no
     * @return The ITem or null if it can't be found
     * @throws java.sql.SQLException
     */
    public static AcmeItem findItem(Connection conn, 
    		int item_no)
			throws SQLException {
    	AcmeItem item = null;

		Statement select = conn.createStatement();

		String s = "SELECT * FROM items " +
				"WHERE item_no = " + item_no;

		ResultSet results = select.executeQuery(s);

		while (results.next()) {
			item = new AcmeItem(results.getString("item_name"), results
					.getInt("price"), results.getInt("item_no"));
		}

		return item;
	}
    
    
    /**
     * Add an order for a customer and item
     * @param conn The connection
     * @param order The AcmeOrder
     * @throws java.sql.SQLException
     */
    public static void addOrder(Connection conn, AcmeOrder order) 
    	throws SQLException
    {
    	Statement update = conn.createStatement(); 
    	
    	String s= "" + 
    	          "INSERT INTO orders (" +
    	          "cust_id, item_no, qty)" +
    	          " VALUES ( " + order.getCustomer().getCustomerID() + 
    	          "," + order.getItem().getItemNo() + "," +
    	          order.getQuantity() + ")"; 
    	update.executeUpdate(s); 
    }
    
    /**
     * Determines if there are any orders for a specific customer
     * @param conn The DB Connection
     * @param cust_id
     * @return True if orders exist false otherwise
     * @throws java.sql.SQLException
     */
    public static boolean orderExistsForCustomer(Connection conn, 
            int cust_id) 
            throws SQLException
    {
        Statement select = conn.createStatement(); 
        
        String s = "" + 
                "SELECT * FROM orders WHERE " +
                "cust_id = " + cust_id; 
        
        ResultSet results = select.executeQuery(s); 
        while (results.next()) 
            return true; 
        return false; 
    }
    
    /**
     * Finds an order for an item and customer
     * @param conn The DB Connection
     * @param c The customer
     * @param i The Item ordered
     * @return The Order or null if it can't be found
     * @throws java.sql.SQLException
     */
    public static AcmeOrder findOrder(Connection conn, 
    		AcmeCustomer c, AcmeItem i)
    throws SQLException
    {
    	Statement select = conn.createStatement(); 
    	
    	String s = "" + 
    	           "SELECT * FROM orders WHERE " +
    	           "item_no = "  + i.getItemNo() + 
    	           " AND cust_id = "+ c.getCustomerID(); 
    	
    	ResultSet results = select.executeQuery(s); 
    	
    	while (results.next()) 
    	{
    		AcmeCustomer customer = AcmeDataAdapter.findCustomer(conn,
    				results.getInt("cust_id")); 
    		AcmeItem item = AcmeDataAdapter.findItem(conn, 
    				results.getInt("item_no")); 
    		return new AcmeOrder(customer, item, 
    				results.getInt("qty")); 
    		
    	}
    	
    	return null; 
    }
    
    /**
     * 
     * @param conn The DB Connection
     * @param c The customer
     * @param i The Item Ordered
     * @param q The quantity
     * @throws java.sql.SQLException 
     */
    public static void updateOrder(Connection conn, 
    		AcmeCustomer c, AcmeItem i, int q)
    throws SQLException
    {
    	Statement update = conn.createStatement(); 
    	
    	String s = "UPDATE orders set " +
    			"qty = " + q + " WHERE " + 
    			"item_no = " + i.getItemNo() +  
    			" AND cust_id = " + c.getCustomerID(); 
    	
    	update.executeUpdate(s); 
    	           
    }
    		
    /**
     * Updates an existing customer
     * @param conn The DB Connection
     * @param c Customer with updated Info
     * @throws java.sql.SQLException When the customer does not already exist
     */
    public static void updateCustomer(Connection conn, AcmeCustomer c)
    	throws SQLException
    {
    	Statement update = conn.createStatement(); 
    	String s = "" +
		"UPDATE customers set " +
		"address='"+c.getAddress()+"'," +
		"city='"+c.getCity()+"'," +
		"state='"+c.getState()+"'," +
		"zip="+c.getZip()+
		" WHERE lastname='"+c.getLastName()+
		"' AND firstname='"+c.getFirstName()+"'";
    	update.executeUpdate(s); 
    }
}
