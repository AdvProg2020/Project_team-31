package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    private String productId;
    private String name;
    private String company;
    private Double price;
    private ArrayList<Seller> sellersOfThisProduct;
    private Category category;
    private Double meanOfCustomersRate;
    private ArrayList<Comment> allComments;
    private Boolean off;
    private Boolean available;
    private ProductStatus productStatus;
    public static ArrayList<Product> allProducts;
    private String information;
    private HashMap<String , String > specialPropertiesRelatedToCategory;

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
}

enum ProductStatus {
    accepted, creating, editing

}

