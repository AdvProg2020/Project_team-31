package Model;

import Controller.ProductController;

import java.io.*;
import java.util.ArrayList;

public class Category implements Serializable{
    private static ArrayList<Category> allCategories = new ArrayList<>();
    private String name;
    private ArrayList<String> specialProperties;
    private ArrayList<String> products;

    public Category(String name, ArrayList<String> specialProperties) {
        this.name = name;
        this.specialProperties = new ArrayList<>();
        this.specialProperties = specialProperties;
        allCategories.add(this);
        products = new ArrayList<>();
    }

    public ArrayList<Product> getProducts() {
        ArrayList<Product> output = new ArrayList<>();
        for (String s : products) {
            output.add(ProductController.getProductById(s));
        }
        return output;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getSpecialProperties() {
        return specialProperties;
    }

    public void addProduct(Product product){
        this.products.add(product.getProductId());
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

    public void removeProduct(Product product){
        products.remove(product.getProductId());
    }

    public static void logToFile(){
        try{
            FileOutputStream file = new FileOutputStream("src/project files/allCategories.txt");
            ObjectOutputStream allCategories = new ObjectOutputStream(file);

            allCategories.writeObject(getAllCategories());
            allCategories.flush();
            allCategories.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void fileToLog(){
        try{
            FileInputStream file = new FileInputStream("src/project files/allCategories.txt");
            ObjectInputStream allCategories = new ObjectInputStream(file);

            Category.allCategories = (ArrayList<Category>) allCategories.readObject();
            allCategories.close();
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
