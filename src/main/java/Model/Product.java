package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Product {
    private String productId;
    private String name;
    private String company;
    private HashMap<Seller, Integer> sellersOfThisProduct;
    private int minimumPrice;
    private int customersWhoRated;
    private Category category;
    private int sumOfCustomersRate;
    private ArrayList<Comment> allComments;
    private ArrayList<Off> offs;
    private int available;
    private ProductAndOffStatus productStatus;
    private String information;
    private int views;
    private HashMap<String , String > specialPropertiesRelatedToCategory;
    public static ArrayList<Product> allProducts;

    public Product(String productId, String name, String company, Category category, String information, HashMap<Seller, Integer> sellersOfThisProduct, HashMap<String, String> specialPropertiesRelatedToCategory) {
        views = 0;
        offs = null;
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

    public int getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(int minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public String getInformation() {
        return information;
    }


    public ArrayList<Off> getOffs() {
        return offs;
    }

    public void addOff(Off off) {
        offs.add(off);
    }

    public void removeOff(Off off) {
        offs.remove(off);
    }

    public void removeSeller(Seller seller) {
        sellersOfThisProduct.remove(seller);
    }

    public void addSeller(Seller seller, int price) {
        sellersOfThisProduct.put(seller, price);
    }

    public HashMap<String, String> getSpecialPropertiesRelatedToCategory() {
        return specialPropertiesRelatedToCategory;
    }

    public void removeSpecialFeature(String key) {
        specialPropertiesRelatedToCategory.remove(key);
    }

    public void addSpecialFeature(String key, String  value) {
        specialPropertiesRelatedToCategory.put(key, value);
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

    public int getSumOfCustomersRate() {
        return this.sumOfCustomersRate;
    }

    public void addSumOfCustomersRate(int sumOfCustomersRate) {
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

    public void setAvailable(int available) {
        this.available = available;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void setSpecialPropertiesRelatedToCategory(HashMap<String, String> specialPropertiesRelatedToCategory) {
        this.specialPropertiesRelatedToCategory = specialPropertiesRelatedToCategory;
    }
}

