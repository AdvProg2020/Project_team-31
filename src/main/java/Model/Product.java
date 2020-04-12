package Model;

import java.util.ArrayList;

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
//    hasmap creating



}

enum ProductStatus {
    accepted, creating, editing;


}

