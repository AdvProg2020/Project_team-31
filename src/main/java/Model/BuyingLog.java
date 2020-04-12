package Model;

import java.util.ArrayList;
import java.util.Date;

public class BuyingLog {
    private String logId;
    private Date date;
    private Double payedAmount;
    private Double discountAmount;
    private String SellerName;
    private DeliveryStatus deliveryStatus;
    private Customer customer;
    private ArrayList<Product> buyingProducts;

    public BuyingLog(String logId, Date date, Double payedAmount, Double discountAmount, String sellerName, DeliveryStatus deliveryStatus, Customer customer, ArrayList<Product> buyingProducts) {
        this.logId = logId;
        this.date = date;
        this.payedAmount = payedAmount;
        this.discountAmount = discountAmount;
        this.SellerName = sellerName;
        this.deliveryStatus = deliveryStatus;
        this.customer = customer;
        this.buyingProducts = buyingProducts;
    }
}
enum DeliveryStatus{

}