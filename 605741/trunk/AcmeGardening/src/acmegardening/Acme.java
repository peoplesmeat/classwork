package acmegardening;

import java.sql.*;
import java.util.*;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 * 
 * @author Bill.Davis
 * This is the main driver for the Acme Gardening Application
 */
public class Acme {

    /**
     * @param args
     */
    public static void main(String[] args) {

        Connection conn = null;

        while (conn == null) {
            Logon l = new Logon(null, true);
            if (l.act()) {        	 
                try {
                    conn = AcmeDataAdapter.connect(
                            l.getUsername(), l.getPassword());


                } catch (ClassNotFoundException e) {
                    JOptionPane.showMessageDialog(null,
                            "Missing Driver: " + e.getMessage(),
                            "Connection Error ", JOptionPane.ERROR_MESSAGE);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(),
                            "Connection Error ", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                return;
            }
        }

        AcmeWindow aw = new AcmeWindow(conn);
        aw.setVisible(true);
    //Here we've ensured a good connection
    }

    public static void test() {
        Connection conn = null;
        try {
            conn = AcmeDataAdapter.connect("wdavis", "wdavis");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn == null) {
                return;
            }
        }

        try {
            AcmeDataAdapter.dropCustomersTable(conn);
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            AcmeDataAdapter.dropOrdersTable(conn);
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            AcmeDataAdapter.dropItemsTable(conn);
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            AcmeDataAdapter.createCustomersTable(conn);
            AcmeDataAdapter.createOrdersTable(conn);
            AcmeDataAdapter.createItemsTable(conn);
        } catch (SQLException e) {
            System.out.println(e);
        }

        AcmeItem[] items = new AcmeItem[]{new AcmeItem("Rose Bush", 10),
            new AcmeItem("Lilac Bush", 20), new AcmeItem("Elm Tree", 30),
            new AcmeItem("Maple Tree", 40), new AcmeItem("Evergreen", 50)
        };

        for (AcmeItem a : items) {
            try {
                AcmeDataAdapter.addItem(conn, a);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        AcmeCustomer c = new AcmeCustomer("Davis", "Bill", "1001",
                "Westminster", "MD", 21157);
        try {
            AcmeDataAdapter.addCustomer(conn, c);
            c = AcmeDataAdapter.findCustomer(conn, "Davis", "Bill");
            c = new AcmeCustomer("Davis", "Bill", "10011",
                    "Westminster1", "MD1", 21117);
            AcmeDataAdapter.updateCustomer(conn, c);
            c = AcmeDataAdapter.findCustomer(conn, "Davis", "Bill");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            ArrayList<AcmeItem> itemsList = AcmeDataAdapter.getItems(conn);
            for (AcmeItem i : itemsList) {
                System.out.println(i.getName() + " " + i.getPrice());
            }
            AcmeDataAdapter.addOrder(conn, new AcmeOrder(
                    c, itemsList.get(0), 4));
            AcmeOrder order = AcmeDataAdapter.findOrder(conn,
                    c, itemsList.get(0));

            AcmeDataAdapter.updateOrder(conn, c,
                    itemsList.get(0), 198);

            order = AcmeDataAdapter.findOrder(conn,
                    c, itemsList.get(1));

            order = AcmeDataAdapter.findOrder(conn,
                    c, itemsList.get(2));

            AcmeDataAdapter.deleteCustomer(conn, "Davis", "Bill");

        } catch (Exception e) {
            System.out.println(e);
        }




        try {
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
