package Model;

import java.util.ArrayList;

public class Seller extends User {
    private String companyName;
    private ArrayList<SellingLog> allSellingLogs;
    private ArrayList<Product> onSaleProducts;
    private ArrayList<Off> sellerOffs;
    private static ArrayList<Seller> allSellers = new ArrayList<>();

    public Seller(String name, String lastName, String username, String emailAddress, String  phoneNumber, String password, String companyName) {
        super(name, lastName, username, emailAddress, phoneNumber, password);
        this.companyName = companyName;
        this.allSellingLogs = new ArrayList<>();
        this.onSaleProducts = new ArrayList<>();
        this.sellerOffs = new ArrayList<>();
    }

    public ArrayList<Off> getSellerOffs() {
        return sellerOffs;
    }

    public void addOffToThisSeller(Off newOff) {
        sellerOffs.add(newOff);
    }

    public void removeOffFromThisSeller(Off removingOff) {
        sellerOffs.remove(removingOff);
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void addSellingLog(SellingLog sellingLog) {
        allSellingLogs.add(sellingLog);
    }

    public void removeProduct(Product product){
        this.onSaleProducts.remove(product);
    }

    public static ArrayList<Seller> getAllSellers() {
        return allSellers;
    }

    public void deleteSeller(){
        allSellers.remove(this);
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public ArrayList<SellingLog> getAllSellingLogs() {
        return this.allSellingLogs;
    }

    public ArrayList<Product> getOnSaleProducts() {
        return this.onSaleProducts;
    }

    public  void  addProduct(Product product){
        this.onSaleProducts.add(product);
    }
}
