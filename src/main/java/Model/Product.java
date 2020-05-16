package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Product {
    private String productId;
    private String name;
    private String company;
    private HashMap<Seller, Integer> sellersOfThisProduct;
    private int customersWhoRated;
    private Category category;
    private Double sumOfCustomersRate;
    private ArrayList<Comment> allComments;
    private Off off;
    private int available;
    private ProductAndOffStatus productStatus;
    private String information;
    private Date date;
    private int views;
    private HashMap<String , String > specialPropertiesRelatedToCategory;
    public static ArrayList<Product> allProducts;

    public Product(String productId, String name, String company, Category category, String information, HashMap<Seller, Integer> sellersOfThisProduct, HashMap<String, String> specialPropertiesRelatedToCategory) {
        views = 0;
        off = null;
        this.productId = productId;
        this.name = name;
        this.company = company;
        this.sellersOfThisProduct = sellersOfThisProduct;
        this.category = category;
        this.productStatus = ProductAndOffStatus.creating;
        this.information = information;
        this.specialPropertiesRelatedToCategory = specialPropertiesRelatedToCategory;
        allProducts.add(this);
    }

    public String getInformation() {
        return information;
    }



    public Off getOff() {
        return off;
    }

    public void setOff(Off off) {
        this.off = off;
    }

    public void removeSeller(Seller seller) {
        sellersOfThisProduct.remove(seller);
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

    public HashMap<Seller, Integer> getSellersOfThisProduct() {
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

    public ProductAndOffStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductAndOffStatus productStatus) {
        this.productStatus = productStatus;
    }

    public Category getCategory() {
        return category;
    }

    public ArrayList<Comment> getAllComments() {
        return this.allComments;
    }
}

