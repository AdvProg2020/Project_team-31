package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;

public class Customer extends User implements Serializable {
    private ArrayList<DiscountCode> allDiscountCodes;
    private ArrayList<BuyingLog> allBuyingLogs;
    private ArrayList<Product> recentShoppingProducts;
    private static ArrayList<Customer> allCustomers = new ArrayList<>();

    public Customer(String name, String lastName, String username, String emailAddress, String phoneNumber, String password) {
        super(name, lastName, username, emailAddress, phoneNumber, password);
        allCustomers.add(this);
        recentShoppingProducts = new ArrayList<>();
        allBuyingLogs = new ArrayList<>();
        allDiscountCodes = new ArrayList<>();
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
    public static void logToFile(){
        try{
            FileOutputStream file = new FileOutputStream("src/project files/allCustomers.txt");
            ObjectOutputStream allCustomers = new ObjectOutputStream(file);

            allCustomers.writeObject(getAllCustomers());
            allCustomers.flush();
            allCustomers.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void fileToLog(){
        try{
            FileInputStream file = new FileInputStream("src/project files/allCustomers.txt");
            ObjectInputStream allCustomers = new ObjectInputStream(file);

            Customer.allCustomers = (ArrayList<Customer>) allCustomers.readObject();
            allCustomers.close();
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}