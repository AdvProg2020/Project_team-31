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

    public void addSellingLog(SellingLog sellingLog) {
        allSellingLogs.add(sellingLog);
    }

    public void removeProduct(Product product){
        for (Product onSaleProduct : this.onSaleProducts) {
            if (product.getProductId().equals(onSaleProduct.getProductId())){
                this.onSaleProducts.remove(onSaleProduct);
                break;
            }
        }
    }

}
