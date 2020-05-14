package Model;

import java.util.HashMap;

public class ProductRequest extends Request {
    private Product product;
    private Double price;
    private int available;
    private String information;
    private HashMap<String , String > specialPropertiesRelatedToCategory;
    private boolean isEditing;


    public ProductRequest(Product product , boolean isEditing) {
        super("productRequest" + allRequests.size()+1);
        this.product = product;
        this.isEditing = isEditing;
        allRequests.add(this);
    }
    public void erProduct(Double price , int available , String information , HashMap<String , String> specialPropertiesRelatedToCategory){
        this.price = price;
        this.available = available;
        this.information = information;
        this.specialPropertiesRelatedToCategory = specialPropertiesRelatedToCategory;
    }

    public Double getPrice() {
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
