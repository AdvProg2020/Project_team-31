package Model;

import java.util.Date;

public class SellingLog extends Log{
    private int totalPriceArrived;
    private int amountOfOff;
    private Product buyingProducts;
    private static int numberOfSellingLog;

    public SellingLog(Date date, int totalPriceArrived, int amountOfOff, Product buyingProducts, Customer customer) {
        super("SellingLog" + numberOfSellingLog++, date, customer);
        this.totalPriceArrived = totalPriceArrived;
        this.amountOfOff = amountOfOff;
        this.buyingProducts = buyingProducts;
    }

    public int getTotalPriceArrived() {
        return totalPriceArrived;
    }

    public int getAmountOfOff() {
        return amountOfOff;
    }

    public Product getBuyingProducts() {
        return buyingProducts;
    }
}
