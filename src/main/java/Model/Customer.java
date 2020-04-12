package Model;

import java.util.ArrayList;

public class Customer extends User {
    private ArrayList<DiscountCode> allDiscountCodes;
    private ArrayList<BuyingLog> allBuyingLogs;
    private ArrayList<Product> recentShoppingProducts;
    public static ArrayList<Customer> allCustomers;

    public Customer(String name, String lastname, String username, String emailAddress, int phoneNumber, String password) {
        super(name, lastname, username, emailAddress, phoneNumber, password);
    }
}
