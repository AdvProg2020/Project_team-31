package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    private String productId;
    private String name;
    private String company;
    private Double price;
    private ArrayList<Seller> sellers;
    private Category category;
    private Double meanOfCustomersRate;
    private ArrayList<Comment> allComments;
    private Boolean off;
    private Boolean available;
    private ProductStatus productStatus;
    public static ArrayList<Product> allProducts;
    private String information;
    private HashMap<String , String> valuesForSpiecalProperties;

    public Product(String productId, String name, String company, Double price, Category category, Boolean available, ProductStatus productStatus, String information , Seller seller) {
        this.productId = productId;
        this.name = name;
        this.company = company;
        this.price = price;
        this.category = category;
        this.available = available;
        this.productStatus = productStatus;
        this.information = information;
        this.off = false;
        sellers = new ArrayList<Seller>();
        this.sellers.add(seller);
        this.valuesForSpiecalProperties = new HashMap<String, String>();
        this.allComments = new ArrayList<Comment>();

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Seller> getSellers() {
        return sellers;
    }

    public Category getCategory() {
        return category;
    }

    public Double getMeanOfCustomersRate() {
        return meanOfCustomersRate;
    }

    public String getInformation() {
        return information;
    }

    public void setMeanOfCustomersRate(Double meanOfCustomersRate) {
        this.meanOfCustomersRate = meanOfCustomersRate;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ArrayList<Comment> getAllComments() {
        return allComments;
    }

    public void setAllComments(ArrayList<Comment> allComments) {
        this.allComments = allComments;
    }

    public Boolean getOff() {
        return off;
    }

    public void setOff(Boolean off) {
        this.off = off;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public void addSeller(Seller seller){
        this.sellers.add(seller);
    }
}

enum ProductStatus {
    accepted, creating, editing
}

