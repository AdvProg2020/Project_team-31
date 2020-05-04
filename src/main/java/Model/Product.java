package Model;

import java.util.ArrayList;
import java.util.HashMap;

import static Model.ProductStatus.*;

public class Product {
    private String productId;
    private String name;
    private String company;
    private Double price;
    private ArrayList<Seller> sellersOfThisProduct;
    private ArrayList<Customer> customersWhoRated;
    private Category category;
    private Double meanOfCustomersRate;
    private ArrayList<Comment> allComments;
    private Boolean off;
    private int available;
    private ProductStatus productStatus;
    private String information;
    private HashMap<String , String > specialPropertiesRelatedToCategory;
    public static ArrayList<Product> allProducts;

    public Product(String productId, String name, String company, Double price, ArrayList<Seller> sellersOfThisProduct, Category category, ProductStatus productStatus, String information, HashMap<String, String> specialPropertiesRelatedToCategory) {
        this.productId = productId;
        this.name = name;
        this.company = company;
        this.price = price;
        this.sellersOfThisProduct = sellersOfThisProduct;
        this.category = category;
        this.productStatus = productStatus;
        this.information = information;
        this.specialPropertiesRelatedToCategory = specialPropertiesRelatedToCategory;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public ArrayList<Seller> getSellersOfThisProduct() {
        return sellersOfThisProduct;
    }

    public int getAvailable() {
        return available;
    }

    public void decreaseNumberOfProduct(int number) {
        available -= number;
    }

    public Double getMeanOfCustomersRate() {
        return this.meanOfCustomersRate;
    }

    public void setMeanOfCustomersRate(Double meanOfCustomersRate) {
        this.meanOfCustomersRate = meanOfCustomersRate;
    }

    public ArrayList<Customer> getCustomersWhoRated() {
        return this.customersWhoRated;
    }

    public void setCustomersWhoRated(ArrayList<Customer> customersWhoRated) {
        this.customersWhoRated = customersWhoRated;
    }

    public void addCustomerWhoRated(Customer customer){
        this.customersWhoRated.add(customer);
    }

    public void removeProduct(){
        allProducts.remove(this);
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = accepted;
    }
}

enum ProductStatus {
    accepted, creating, editing
}

