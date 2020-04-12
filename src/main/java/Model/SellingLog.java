package Model;

import java.util.ArrayList;
import java.util.Date;

public class SellingLog {
    private String logId;
    private Date date;
    private Double recievedAmount;
    private Double discountAmount;
    private String customerName;
    private Seller seller;
    private DeliveryStatus deliveryStatus;
    private ArrayList<Product> allSoldProducts;

    public SellingLog(String logId, Date date, Double recievedAmount, Double discountAmount, String customerName, Seller seller, DeliveryStatus deliveryStatus) {
        this.logId = logId;
        this.date = date;
        this.recievedAmount = recievedAmount;
        this.discountAmount = discountAmount;
        this.customerName = customerName;
        this.seller = seller;
        this.deliveryStatus = deliveryStatus;
    }
}
