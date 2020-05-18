package Model;

import java.util.HashMap;

public class ProductRequest extends Request {
    private Product product;
    private Seller seller;
    private int price;
    private int available;
    private String information;
    private HashMap<String , String > specialPropertiesRelatedToCategory;
    private boolean isEditing;


    public ProductRequest(String id, Product product , boolean isEditing) {
        super(id);
        this.product = product;
        this.isEditing = isEditing;
    }
    public void newProductFeatures(Seller seller, int price , int available , String information , HashMap<String , String> specialPropertiesRelatedToCategory){
        this.seller = seller;
        this.price = price;
        this.available = available;
        this.information = information;
        this.specialPropertiesRelatedToCategory = specialPropertiesRelatedToCategory;
    }

    public Seller getSeller() {
        return seller;
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

    public void setEditing(boolean editing) {
        isEditing = editing;
    }

    public Product getProduct() {
        return product;
    }
}
