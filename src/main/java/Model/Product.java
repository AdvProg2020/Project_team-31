package Model;

import javafx.scene.image.ImageView;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Product implements Serializable {
    private ImageView imageView;
    private String productId;
    private String name;
    private String company;
    private String  rate;
    private HashMap<Seller, Integer> sellersOfThisProduct;
    private int minimumPrice;
    private int customersWhoRated;
    private Category category;
    private int sumOfCustomersRate;
    private ArrayList<Comment> allComments = new ArrayList<>();
    private ArrayList<Off> offs;
    private int available;
    private ProductAndOffStatus productStatus;
    private String information;
    private int views;
    private HashMap<String, String> specialPropertiesRelatedToCategory;
    public static ArrayList<Product> allProducts = new ArrayList<>();
    private static int numberOfProductCreated = 0;

    public Product(String productId, String name, String company, Category category, String information, int available, HashMap<Seller, Integer> sellersOfThisProduct, HashMap<String, String> specialPropertiesRelatedToCategory) {
        views = 0;
        rate = "0.0";
        offs = new ArrayList<>();
        offs = null;
        numberOfProductCreated++;
        this.available = available;
        this.productId = productId;
        this.name = name;
        this.company = company;
        this.sellersOfThisProduct = new HashMap<>();
        this.sellersOfThisProduct = sellersOfThisProduct;
        this.category = category;
        this.productStatus = ProductAndOffStatus.CREATING;
        this.information = information;
        this.specialPropertiesRelatedToCategory = new HashMap<>();
        this.specialPropertiesRelatedToCategory = specialPropertiesRelatedToCategory;
        allProducts.add(this);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public String getRate() {
        return rate;
    }

    public Product(ImageView imageView, String productId, String name, String company, Category category, String information, int available, HashMap<Seller, Integer> sellersOfThisProduct, HashMap<String, String> specialPropertiesRelatedToCategory) {
        this.imageView = imageView;
        views = 0;
        rate = "0.0";
        offs = new ArrayList<>();
        offs = null;
        numberOfProductCreated++;
        this.available = available;
        this.productId = productId;
        this.name = name;
        this.company = company;
        this.sellersOfThisProduct = new HashMap<>();
        this.sellersOfThisProduct = sellersOfThisProduct;
        this.category = category;
        this.productStatus = ProductAndOffStatus.CREATING;
        this.information = information;
        this.specialPropertiesRelatedToCategory = new HashMap<>();
        this.specialPropertiesRelatedToCategory = specialPropertiesRelatedToCategory;
        allProducts.add(this);
    }

    public static ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public static int getNumberOfProductCreated() {
        return numberOfProductCreated;
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

    public void addSpecialFeature(String key, String value) {
        specialPropertiesRelatedToCategory.put(key, value);
    }

    public String getCompany() {
        return company;
    }

    public int getViews() {
        return views;
    }

    public void addView() {
        views++;
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
        rate = String.valueOf(1.0 * this.sumOfCustomersRate / getCustomersWhoRated());
    }

    public int getCustomersWhoRated() {
        if (customersWhoRated == 0)
            return 1;
        return this.customersWhoRated;
    }

    public void addNumberOfCustomerWhoRated() {
        this.customersWhoRated++;
        rate = String.valueOf(1.0 * sumOfCustomersRate / getCustomersWhoRated());
    }

    public void removeProduct() {
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

    public void addComment(Comment comment) {
        allComments.add(comment);
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

    public static void logToFile() {
        try {
            FileOutputStream file = new FileOutputStream("src/project files/allProducts.txt");
            ObjectOutputStream allProducts = new ObjectOutputStream(file);

            allProducts.writeObject(getAllProducts());
            allProducts.flush();
            allProducts.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileToLog() {
        try {
            FileInputStream file = new FileInputStream("src/project files/allProducts.txt");
            ObjectInputStream allProducts = new ObjectInputStream(file);

            Product.allProducts = (ArrayList<Product>) allProducts.readObject();
            allProducts.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}

