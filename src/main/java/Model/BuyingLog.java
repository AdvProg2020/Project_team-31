package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BuyingLog extends Log{
    private Double totalPrice;
    private Double discountAmount;
    private HashMap<Product, ProductInCard> buyingProducts;
    private static ArrayList<BuyingLog> allBuyingLog;
    private String[] personalInformation;

    public BuyingLog(Double totalPrice, Customer customer, HashMap<Product, ProductInCard> buyingProducts, String[] personalInformation) {
        super(null, null, customer);
        this.totalPrice = totalPrice;
        this.discountAmount = 0.0;
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

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public HashMap<Product, ProductInCard> getBuyingProducts() {
        return buyingProducts;
    }
}
