package shop;


import java.util.Objects;

/**
 * The basic class for item in the EShop.
 */


public abstract class Item {
    private int id;
    private String name;
    private float price;
    private String category;
    
    public Item(int id, String name, float price, String category) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.price = price;
        this.category = Objects.requireNonNull(category, "category must not be null");
    }
    
    @Override
    public String toString() {
        return "Item   ID "+id+"   NAME "+name+"   CATEGORY "+category;
    }
    
    public int getID() {
        return id;
    }
    
    public void setID(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }
    
    public float getPrice() {
        return price;
    }
    
    public void setPrice(float price) {
        this.price = price;
    }
    
    
    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(String category) {
        this.category = Objects.requireNonNull(category, "category must not be null");
    }
    
    @Override
    public boolean equals(Object object){
        if(object instanceof Item){
            Item zbozi = (Item) object;
            if( id == zbozi.getID()
                && name.equals(zbozi.getName())
                && price == zbozi.getPrice()
                && category.equals(zbozi.getCategory())
               ) {
                return true;
            }
        }
        return false;
    }
}

