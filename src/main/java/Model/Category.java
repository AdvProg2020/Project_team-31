package Model;

import java.util.ArrayList;

public class Category {
    private static ArrayList<Category> allCategories = new ArrayList<>();
    private String name;
    private ArrayList<String> specialProperties;
    private ArrayList<Product> products;

    public Category(String name, ArrayList<String> specialProperties) {
        this.name = name;
        this.specialProperties = specialProperties;
        allCategories.add(this);
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getSpecialProperties() {
        return specialProperties;
    }

    public void addProduct(Product product){
        this.products.add(product);
    }

    public static ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public void deleteCategory(){
        allCategories.remove(this);
    }

    public void setSpecialProperties(ArrayList<String> specialProperties) {
        this.specialProperties = specialProperties;
    }
}
