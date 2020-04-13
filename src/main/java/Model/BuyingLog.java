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
        buyingProducts = new ArrayList<Product>();
    }

    public ArrayList<Product> getBuyingProducts() {
        return buyingProducts;
    }

    public String getLogId() {
        return logId;
    }

    public Date getDate() {
        return date;
    }

    public Double getPayedAmount() {
        return payedAmount;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public String getSellerName() {
        return SellerName;
    }

    public Customer getCustomer() {
        return customer;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public void addProducttoLog(Product product){
        this.buyingProducts.add(product);
    }
}
enum DeliveryStatus{
    sending , recieved
}