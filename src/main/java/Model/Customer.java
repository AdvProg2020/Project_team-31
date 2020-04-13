package Model;

import java.util.ArrayList;

public class Customer extends User {
    private ArrayList<DiscountCode> allDiscountCodes;
    private ArrayList<BuyingLog> allBuyingLogs;
    private ArrayList<Product> recentShoppingProducts;
    public static ArrayList<Customer> allCustomers;

    public Customer(String name, String lastName, String username, String emailAddress, int phoneNumber, String password) {
        super(name, lastName, username, emailAddress, phoneNumber, password);
    }
}
