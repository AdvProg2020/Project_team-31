package GraphicalView;

public class ProductInCartInGui {
    private String id;
    private String name;
    private int number;
    private int price;
    private int totalPrice;

    public ProductInCartInGui(String id, String name, int number, int price, int totalPrice) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public int getPrice() {
        return price;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
