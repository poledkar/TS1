package shop;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Order is created, when an user purchases the content of the shopping cart.
 * 
 */
public class Order {

    private ArrayList<Item> items;
    private String customerName;
    private String customerAddress;
    private int state;

    public Order(ShoppingCart cart, String customerName, String customerAddress, int state) {
        items = cart.getCartItems();
        this.customerName = Objects.requireNonNull(customerName, "customerName must not be null");
        this.customerAddress = Objects.requireNonNull(customerAddress, "customerAddress must not be null");
        this.state = state;
    }

    public Order(ShoppingCart cart, String customerName, String customerAddress) {
        /*
        items = cart.getCartItems();
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.state = 0;
         */
        this(cart, customerName, customerAddress, 0);
    }
    
    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> goods) {
        this.items = goods;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = Objects.requireNonNull(customerAddress, "customerAddress must not be null");
    }

    
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = Objects.requireNonNull(customerName, "customerName must not be null");
    }
    
    
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
