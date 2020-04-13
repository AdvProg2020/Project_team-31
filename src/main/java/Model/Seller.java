package Model;

import java.util.ArrayList;

public class Seller extends User {
    private String companyName;
    private ArrayList<SellingLog> allSellingLogs;
    private ArrayList<Product> onSaleProducts;
    public static ArrayList<Seller> allSellers = new ArrayList<Seller>();

    public Seller(String name, String lastname, String username, String emailAddress, int phoneNumber, String password, String companyName) {
        super(name, lastname, username, emailAddress, phoneNumber, password);
        this.companyName = companyName;
        this.allSellingLogs = new ArrayList<SellingLog>();
        this.onSaleProducts = new ArrayList<Product>();
    }
}
