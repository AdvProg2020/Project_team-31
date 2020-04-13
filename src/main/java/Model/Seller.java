package Model;

import java.util.ArrayList;

public class Seller extends User {
    private String companyName;
    private ArrayList<SellingLog> allSellingLogs;
    private ArrayList<Product> onSaleProducts;
    public static ArrayList<Seller> allSellers = new ArrayList<Seller>();

    public Seller(String name, String lastName, String username, String emailAddress, String  phoneNumber, String password, String companyName) {
        super(name, lastName, username, emailAddress, phoneNumber, password);
        this.companyName = companyName;
        this.allSellingLogs = new ArrayList<SellingLog>();
        this.onSaleProducts = new ArrayList<Product>();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
