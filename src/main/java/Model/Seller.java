package Model;

import Controller.ProductController;
import Controller.SellerController;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class Seller extends User implements Serializable{
    private String companyName;
    private ArrayList<SellingLog> allSellingLogs;
    private ArrayList<String> onSaleProducts;
    private ArrayList<String> sellerOffs;
    private static ArrayList<Seller> allSellers = new ArrayList<>();

    public Seller(String name, String lastName, String username, String emailAddress, String  phoneNumber, String password, String companyName) {
        super(name, lastName, username, emailAddress, phoneNumber, password);
        this.companyName = companyName;
        this.allSellingLogs = new ArrayList<>();
        this.onSaleProducts = new ArrayList<>();
        this.sellerOffs = new ArrayList<>();
    }

    public ArrayList<Off> getSellerOffs() {
        ArrayList<Off> output = new ArrayList<>();
        for (String offId : sellerOffs) {
            output.add(SellerController.getInstance().getOffById(offId));
        }
        return output;
    }

    public void addOffToThisSeller(Off newOff) {
        sellerOffs.add(newOff.getOffId());
    }

    public void removeOffFromThisSeller(Off removingOff) {
        sellerOffs.remove(removingOff.getOffId());
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void addSellingLog(SellingLog sellingLog) {
        allSellingLogs.add(sellingLog);
    }

    public void removeProduct(Product product){
        this.onSaleProducts.remove(product.getProductId());
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
        ArrayList<Product> outPut = new ArrayList<>();
        for (String product : onSaleProducts) {
            outPut.add(ProductController.getProductById(product));
        }
        return outPut;
    }

    public  void  addProduct(Product product){
        this.onSaleProducts.add(product.getProductId());
    }

    public static void logToFile(){
        try{
            FileOutputStream file = new FileOutputStream("src/project files/allSellers.txt");
            ObjectOutputStream allSellers = new ObjectOutputStream(file);

            allSellers.writeObject(getAllSellers());
            allSellers.flush();
            allSellers.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void fileToLog(){
        try{
            FileInputStream file = new FileInputStream("src/project files/allSellers.txt");
            ObjectInputStream allSellers = new ObjectInputStream(file);

            Seller.allSellers = (ArrayList<Seller>) allSellers.readObject();
            allSellers.close();
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
