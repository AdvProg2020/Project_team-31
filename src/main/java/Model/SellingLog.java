package Model;

import java.util.Date;

public class SellingLog {
    private String logId;
    private Date date;
    private Double totalPriceArrived;
    private Double amountOfOff;
    private Product buyingProducts;
    private DeliveryStatus deliveryStatus;
    private Customer customer;
    private static int numberOfSellingLog;

    public SellingLog(Date date, Double totalPriceArrived, Double amountOfOff, Product buyingProducts, Customer customer) {
        numberOfSellingLog ++;
        this.logId = "SellingLog" + numberOfSellingLog;
        this.date = date;
        this.totalPriceArrived = totalPriceArrived;
        this.amountOfOff = amountOfOff;
        this.buyingProducts = buyingProducts;
        this.customer = customer;
    }
}
