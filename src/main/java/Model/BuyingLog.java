package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BuyingLog extends Log{
    private int totalPrice;
    private int discountAmount;
    private HashMap<Product, ProductInCard> buyingProducts;
    private String[] personalInformation;

    public BuyingLog(String id, int totalPrice, Customer customer, HashMap<Product, ProductInCard> buyingProducts, String[] personalInformation) {
        super(id, null, customer);
        this.totalPrice = totalPrice;
        this.discountAmount = 0;
        this.customer = customer;
        this.buyingProducts = buyingProducts;
        this.personalInformation = personalInformation;
    }

    public void finishBuying(Date date) {
        this.date = date;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public HashMap<Product, ProductInCard> getBuyingProducts() {
        return buyingProducts;
    }
}
