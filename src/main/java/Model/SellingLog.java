package Model;

import java.util.Date;

public class SellingLog extends Log{
    private Double totalPriceArrived;
    private Double amountOfOff;
    private Product buyingProducts;
    private static int numberOfSellingLog;

    public SellingLog(Date date, Double totalPriceArrived, Double amountOfOff, Product buyingProducts, Customer customer) {
        super("SellingLog" + numberOfSellingLog++, date, customer, null );
        this.totalPriceArrived = totalPriceArrived;
        this.amountOfOff = amountOfOff;
        this.buyingProducts = buyingProducts;
    }

    public Double getTotalPriceArrived() {
        return totalPriceArrived;
    }

    public Double getAmountOfOff() {
        return amountOfOff;
    }

    public Product getBuyingProducts() {
        return buyingProducts;
    }
}
