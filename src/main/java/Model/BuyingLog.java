package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BuyingLog extends Log{
    private int totalPrice;
    private int discountAmount;
    private HashMap<Product, ProductInCard> buyingProducts;
    private static ArrayList<BuyingLog> allBuyingLog;
    private String[] personalInformation;

    public BuyingLog(int totalPrice, Customer customer, HashMap<Product, ProductInCard> buyingProducts, String[] personalInformation) {
        super(null, null, customer);
        this.totalPrice = totalPrice;
        this.discountAmount = 0;
        this.customer = customer;
        this.buyingProducts = buyingProducts;
        this.personalInformation = personalInformation;
    }

    public static ArrayList<BuyingLog> getAllBuyingLog() {
        return allBuyingLog;
    }

    public void finishBuying(String logId, Date date) {
        this.logId = logId;
        this.date = date;
        allBuyingLog.add(this);
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
