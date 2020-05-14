package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static Model.ProductStatus.*;

public class Product {
    private String productId;
    private String name;
    private String company;
    private Double price;
    private ArrayList<Seller> sellersOfThisProduct;
    private int customersWhoRated;
    private Category category;
    private Double sumOfCustomersRate;
    private ArrayList<Comment> allComments;
    private Boolean off;
    private int available;
    private ProductStatus productStatus;
    private String information;
    private Date date;
    private int views;
    private HashMap<String , String > specialPropertiesRelatedToCategory;
    public static ArrayList<Product> allProducts;

    public Product(String productId, String name, String company, Double price, Category category, String information, ArrayList<Seller> sellersOfThisProduct, HashMap<String, String> specialPropertiesRelatedToCategory) {
        views = 0;
        this.productId = productId;
        this.name = name;
        this.company = company;
        this.price = price;
        this.sellersOfThisProduct = sellersOfThisProduct;
        this.category = category;
        this.productStatus = creating;
        this.information = information;
        this.specialPropertiesRelatedToCategory = specialPropertiesRelatedToCategory;
    }

    public HashMap<String, String> getSpecialPropertiesRelatedToCategory() {
        return specialPropertiesRelatedToCategory;
    }

    public String getCompany() {
        return company;
    }

    public int getViews() {
        return views;
    }

    public void addView() {
        views ++;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Double getSumOfCustomersRate() {
        return this.sumOfCustomersRate;
    }

    public void addSumOfCustomersRate(Double sumOfCustomersRate) {
        this.sumOfCustomersRate += sumOfCustomersRate;
    }

    public int getCustomersWhoRated() {
        return this.customersWhoRated;
    }

    public void addNumberOfCustomerWhoRated(){
        this.customersWhoRated++;
    }

    public void removeProduct(){
        allProducts.remove(this);
    }

    public void acceptedStatus() {
        this.productStatus = accepted;
    }
    public void editingStatus(){
        this.productStatus = editing;
    }

    public Category getCategory() {
        return category;
    }

    public ArrayList<Comment> getAllComments() {
        return this.allComments;
    }
}

enum ProductStatus {
    accepted, creating, editing
}

