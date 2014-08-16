/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package acmegardening;

/**
 *
 * @author davis
 */
public class AcmeCustomer {
    private String lastname; 
    private String firstname; 
    private String address; 
    private String city; 
    private String state; 
    private int zip; 
    
    private int custId = -1; 
    
    public AcmeCustomer(String lname, String fname, 
            String address, String city, 
            String state, int zip)
    {
        this.lastname = lname; 
        this.firstname = fname; 
        this.address = address; 
        this.city = city; 
        this.state = state; 
        this.zip = zip; 
    }
    
    public AcmeCustomer(String lname, String fname, 
            String address, String city, 
            String state, int zip, int custId) 
    {
        this(lname, fname, address, city, state, zip); 
        this.custId = custId; 
    }
    
    public String getLastName() { return lastname; } 
    public String getFirstName() { return firstname; } 
    public String getAddress() { return address; } 
    public String getCity() { return city; } 
    public String getState() { return state; } 
    public int getZip() { return zip; } 
    public int getCustomerID() { return custId; } 
    
    public void setAddress(String a) { address = a; } 
    public void setCity(String c) { city = c; } 
    public void setState(String s) { state = s; } 
    public void setZip(int z) { zip = z; } 
    
}
