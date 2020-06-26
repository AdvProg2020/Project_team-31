package Model;

import Controller.LoginController;
import Controller.ManagerController;
import Controller.SellerController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Product implements Serializable {
    private transient Image image;
    private String imageFile;
    private String productId;
    private String name;
    private String company;
    private String rate;
    private HashMap<String, Integer> sellersOfThisProduct;
    private int minimumPrice;
    private int customersWhoRated;
    private String categoryName;
    private int sumOfCustomersRate;
    private ArrayList<Comment> allComments = new ArrayList<>();
    private ArrayList<String> offs;
    private int available;
    private ProductAndOffStatus productStatus;
    private String information;
    private int views;
    private HashMap<String, String> specialPropertiesRelatedToCategory;
    public static ArrayList<Product> allProducts = new ArrayList<>();

    public Product(String productId, String name, String company, Category category, String information, int available, HashMap<Seller, Integer> sellersOfThisProduct, HashMap<String, String> specialPropertiesRelatedToCategory) {
        views = 0;
        rate = "0.0";
        offs = new ArrayList<>();
        this.available = available;
        this.productId = productId;
        this.name = name;
        this.company = company;
        this.sellersOfThisProduct = new HashMap<>();
        for (Seller seller : sellersOfThisProduct.keySet()) {
            this.sellersOfThisProduct.put(seller.getUsername(), sellersOfThisProduct.get(seller));
        }
        this.categoryName = category.getName();
        this.productStatus = ProductAndOffStatus.CREATING;
        this.information = information;
        this.specialPropertiesRelatedToCategory = new HashMap<>();
        this.specialPropertiesRelatedToCategory = specialPropertiesRelatedToCategory;
        allProducts.add(this);
    }

    public String getImageFile() {
        return imageFile;
    }

    public Image getImage() {
        if (image != null)
            return image;
        return new Image(imageFile);
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public ImageView getImageViewBig() {
        ImageView imageView;
        if (image != null) {
            imageView = new ImageView(image);
        } else {
            imageView = new ImageView(new Image(imageFile));
        }
        if (available == 0)
            imageView.setOpacity(0.5);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        return imageView;
    }

    public ImageView getImageViewSmall() {
        ImageView imageView;
        if (image != null) {
            imageView = new ImageView(image);
        } else {
            imageView = new ImageView(new Image(imageFile));
        }
        if (available == 0)
            imageView.setOpacity(0.5);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        return imageView;
    }

    public String getRate() {
        return rate;
    }

    public static ArrayList<Product> getAllProducts() {
        return allProducts;
    }

   public static int getNumberOfProductCreated() {
        if(allProducts.size() == 0)
            return 0;
        return Integer.parseInt(allProducts.get(allProducts.size()-1).getProductId().substring(7));
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
        ArrayList<Off> output = new ArrayList<>();
        for (String s : offs) {
            output.add(SellerController.getInstance().getOffById(s));
        }
        return output;
    }

    public void addOff(Off off) {
        offs.add(off.getOffId());
    }

    public void removeOff(Off off) {
        offs.remove(off.getOffId());
    }

    public void removeSeller(Seller seller) {
        sellersOfThisProduct.remove(seller.getUsername());
    }

    public void addSeller(Seller seller, int price) {
        sellersOfThisProduct.put(seller.getUsername(), price);
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
        HashMap<Seller, Integer> output = new HashMap<>();
        for (String s : sellersOfThisProduct.keySet()) {
            output.put((Seller) LoginController.getUserByUsername(s), sellersOfThisProduct.get(s));
        }
        return output;
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
        return ManagerController.getCategoryByName(categoryName);
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

