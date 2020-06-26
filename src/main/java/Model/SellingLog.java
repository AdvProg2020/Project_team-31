package Model;

import Controller.ProductController;

import java.util.Date;

public class SellingLog extends Log {
    private int totalPriceArrived;
    private int amountOfOff;
    private String buyingProduct;

    public SellingLog(String id, Date date, int totalPriceArrived, int amountOfOff, Product buyingProducts, Customer customer) {
        super(id, date, customer);
        this.totalPriceArrived = totalPriceArrived;
        this.amountOfOff = amountOfOff;
        this.buyingProduct = buyingProducts.getProductId();
    }

    public int getTotalPriceArrived() {
        return totalPriceArrived;
    }

    public int getAmountOfOff() {
        return amountOfOff;
    }

    public String getProductName() {
        return ProductController.getProductById(buyingProduct).getName();
    }

    public Product getBuyingProducts() {
        return ProductController.getProductById(buyingProduct);
    }
}
