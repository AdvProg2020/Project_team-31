package Model;

import Controller.ManagerController;
import Controller.ProductController;
import Controller.SellerController;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;

public class Customer extends User implements Serializable {
    private ArrayList<String> allDiscountCodes;
    private ArrayList<BuyingLog> allBuyingLogs;
    private ArrayList<String> recentShoppingProducts;
    private static ArrayList<Customer> allCustomers = new ArrayList<>();
    transient public String chat= "";

    public Customer(String name, String lastName, String username, String emailAddress, String phoneNumber, String password) {
        super(name, lastName, username, emailAddress, phoneNumber, password);
        allCustomers.add(this);
        recentShoppingProducts = new ArrayList<>();
        allBuyingLogs = new ArrayList<>();
        allDiscountCodes = new ArrayList<>();
    }

    public ArrayList<DiscountCode> getAllDiscountCodes() {
        ArrayList<DiscountCode> output = new ArrayList<>();
        for (String code : allDiscountCodes) {
            try {
                output.add(ManagerController.getInstance().getDiscountById(code));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    public void addBuyingLog(BuyingLog buyingLog) {
        allBuyingLogs.add(buyingLog);
    }

    public void addRecentShoppingProducts(Set<Product> products) {
        for (Product product : products) {
            recentShoppingProducts.add(product.getProductId());
        }
    }

    public ArrayList<BuyingLog> getAllBuyingLogs() {
        return this.allBuyingLogs;
    }

    public ArrayList<Product> getRecentShoppingProducts() {
        ArrayList<Product> output = new ArrayList<>();
        for (String s : recentShoppingProducts) {
            output.add(ProductController.getProductById(s));
        }
        return output;
    }

    public static ArrayList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public void deleteCustomer() {
        allCustomers.remove(this);
    }

    public void addDiscountCode(DiscountCode discountCode) {
        this.allDiscountCodes.add(discountCode.getDiscountCode());
    }

    public void removeDiscountCode(DiscountCode discountCode) {
        this.allDiscountCodes.remove(discountCode.getDiscountCode());
    }

    public static void logToFile() {
        try {
            FileOutputStream file = new FileOutputStream("src/project files/allCustomers.txt");
            ObjectOutputStream allCustomers = new ObjectOutputStream(file);

            allCustomers.writeObject(getAllCustomers());
            allCustomers.flush();
            allCustomers.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileToLog() {
        try {
            FileInputStream file = new FileInputStream("src/project files/allCustomers.txt");
            ObjectInputStream allCustomers = new ObjectInputStream(file);

            Customer.allCustomers = (ArrayList<Customer>) allCustomers.readObject();
            allCustomers.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}