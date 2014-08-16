/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package acmegardening;

/**
 *
 * @author davis
 */
public class AcmeItem {
    private String name; 
    private int price;
    private int itemNo = -1; 
    
    public AcmeItem(String name, int price)
    {
        this.name = name;
        this.price = price;        
    }
    public AcmeItem(String name, int price,
            int itemNo)
    {
        this(name, price); 
        this.itemNo = itemNo; 
    }       
    public int getItemNo() {
        return itemNo;         
    }
    public void setItemNo(int itemNo) {
        this.itemNo = itemNo; 
    }
    public String getName() {
        return name; 
    }
    
    public int getPrice() {
        return price; 
    }
    
    @Override
    public boolean equals(Object item) 
    {
        if (item.getClass() == AcmeItem.class) {
            AcmeItem aItem = (AcmeItem)item; 
            if (aItem.name.equals(name) &&
                    aItem.price == price) {
                return true;
            }
        }
        
        return false; 
    }
}

