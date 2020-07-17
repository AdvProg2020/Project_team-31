package Model;

import Controller.ProductController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BuyingLog extends Log implements Serializable {
    private int totalPrice;
    private int discountAmount;
    private HashMap<String, ProductInCard> buyingProducts;
    private String[] personalInformation;
    public  transient static ArrayList<BuyingLog> notCompleted = new ArrayList<>();

    public BuyingLog(String id, int totalPrice, Customer customer, HashMap<Product, ProductInCard> buyingProducts, String[] personalInformation) {
        super(id, null, customer);
        this.totalPrice = totalPrice;
        this.discountAmount = 0;
        this.buyingProducts  = new HashMap<>();
        for (Product product : buyingProducts.keySet()) {
            this.buyingProducts.put(product.getProductId(),buyingProducts.get(product));
        }
        this.personalInformation = personalInformation;
        notCompleted.add(this);
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
        HashMap<Product, ProductInCard> output = new HashMap<>();
        for (String s : buyingProducts.keySet()) {
            output.put(ProductController.getProductById(s), buyingProducts.get(s));
        }
        return output;
    }
}
