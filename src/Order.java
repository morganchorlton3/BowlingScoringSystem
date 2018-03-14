public class Order {
    private String item;
    private String quantity;
    private Double price;

    public Order(){
        this.item = "";
        this.quantity = "";
        this.price = 0.0;
    }

    public Order( String item, String quantity) {
        this.item = item;
        this.quantity = quantity;

    }
    public String getItem() {
        return item;
    }
    public void setItem(String Item) {
        this.item = Item;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPriceString() {
        return "£" + price;
    }
    public void setPriceString(Double Price) {
        this.price = Price;
    }
    public double getPrice(){
        return price;
    }
    public void setPrice(Double Price) {
        this.price = Price;
    }

   @Override
    public String toString () {
        return "Item{" +
                "Item ='" + item + '\'' +
                "Quantity ='" + quantity + '\'' +
                "Price ='" + price + '\'' +
                '}';
    }
}

