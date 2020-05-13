package Model;

import java.util.ArrayList;
import java.util.Set;

public class Customer extends User {
    private ArrayList<DiscountCode> allDiscountCodes;
    private ArrayList<BuyingLog> allBuyingLogs;
    private ArrayList<Product> recentShoppingProducts;
    private static ArrayList<Customer> allCustomers;

    public Customer(String name, String lastName, String username, String emailAddress, String phoneNumber, String password) {
        super(name, lastName, username, emailAddress, phoneNumber, password);
        allCustomers.add(this);
    }

    public ArrayList<DiscountCode> getAllDiscountCodes() {
        return allDiscountCodes;
    }

    public void addBuyingLog(BuyingLog buyingLog) {
        allBuyingLogs.add(buyingLog);
    }

    public void addRecentShoppingProducts(Set<Product> products) {
        recentShoppingProducts.addAll(products);
    }

    public ArrayList<BuyingLog> getAllBuyingLogs() {
        return this.allBuyingLogs;
    }

    public ArrayList<Product> getRecentShoppingProducts() {
        return this.recentShoppingProducts;
    }

    public static ArrayList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public void deleteCustomer(){
        allCustomers.remove(this);
    }

    public void addDiscountCode(DiscountCode discountCode){
        this.allDiscountCodes.add(discountCode);
    }

    public void removeDiscountCode(DiscountCode discountCode){
        this.allDiscountCodes.remove(discountCode);
    }
}