package acmegardening;

public class AcmeOrder {
	AcmeCustomer customer; 
	AcmeItem item; 
	int quantity; 
	
	public AcmeOrder(AcmeCustomer c, AcmeItem i, int quantity) {
		this.customer = c; 
		this.item = i; 
		this.quantity = quantity; 
	}
	
	public AcmeCustomer getCustomer() 
	{
		return customer; 
	}
	
	public AcmeItem getItem() 
	{
		return item; 
	}
	
	public int getQuantity() 
	{
		return quantity; 
	}
	
}
