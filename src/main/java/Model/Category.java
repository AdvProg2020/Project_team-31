package Model;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<String> specialProperties;
    private ArrayList<Product> products;

    public Category(String name, ArrayList<String> specialProperties) {
        this.name = name;
        this.specialProperties = specialProperties;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getSpecialProperties() {
        return specialProperties;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
