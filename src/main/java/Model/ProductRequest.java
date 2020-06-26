package Model;

import Controller.LoginController;
import Controller.ProductController;

import java.io.Serializable;
import java.util.HashMap;

public class ProductRequest extends Request implements Serializable {
    private String product;
    private String seller;
    private int price;
    private int available;
    private String information;
    private HashMap<String , String > specialPropertiesRelatedToCategory;
    private boolean isEditing;


    public ProductRequest(String id, Product product , boolean isEditing) {
        super(id);
        this.product = product.getProductId();
        this.isEditing = isEditing;
        specialPropertiesRelatedToCategory = new HashMap<>();
    }
    public void newProductFeatures(Seller seller, int price , int available , String information , HashMap<String , String> specialPropertiesRelatedToCategory){
        this.seller = seller.getUsername();
        this.price = price;
        this.available = available;
        this.information = information;
        this.specialPropertiesRelatedToCategory = specialPropertiesRelatedToCategory;
    }

    public Seller getSeller() {
        return (Seller) LoginController.getUserByUsername(seller);
    }

    public int getPrice() {
        return price;
    }

    public int getAvailable() {
        return available;
    }

    public String getInformation() {
        return information;
    }

    public HashMap<String, String> getSpecialPropertiesRelatedToCategory() {
        return specialPropertiesRelatedToCategory;
    }

    public boolean isEditing() {
        return isEditing;
    }

    public Product getProduct() {
        return ProductController.getProductById(product);
    }
}
