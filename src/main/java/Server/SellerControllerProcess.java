package Server;

import Controller.ProductController;
import Controller.SellerController;
import Model.Category;
import Model.Product;
import Model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class SellerControllerProcess {
    SellerController sellerController = SellerController.getInstance();
    private static SellerControllerProcess instance;

    public static SellerControllerProcess getInstance() {
        if (instance == null)
            instance = new SellerControllerProcess();
        return instance;
    }

    private SellerControllerProcess() {
    }

    public JsonObject showCompanyInformation(JsonObject jsonObject, User user) {
        try {
            JsonObject data = new JsonObject();
            data.addProperty("company", sellerController.showCompanyInformation(user));
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public JsonObject newProduct(JsonObject jsonObject, User user) {
        try {
            HashMap<String, String> specialInformation = new HashMap<>();
            String[] generalData = new Gson().fromJson(jsonObject.get("generalData").getAsString(), String[].class);
            String[] first = new Gson().fromJson(jsonObject.get("first").getAsString(), String[].class);
            String[] second = new Gson().fromJson(jsonObject.get("second").getAsString(), String[].class);
            for (int i = 0; i < first.length; i++)
                specialInformation.put(first[i], second[i]);
            sellerController.addProduct(generalData, user, specialInformation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JsonObject getAllCategories() {
        JsonObject output = new JsonObject();
        JsonArray categories = new JsonArray();
        for (Category category : Category.getAllCategories()) {
            JsonObject jCategory = new JsonObject();
            JsonArray features = new JsonArray();
            for (String property : category.getSpecialProperties()) {
                features.add(property);
            }
            jCategory.addProperty("name", category.getName());
            jCategory.add("features", features);
            categories.add(jCategory);
        }
        output.add("categories", categories);
        return output;
    }

    public JsonObject getProductData(JsonObject jsonObject, User user) {
        Product product = ProductController.getProductById(jsonObject.get("productId").getAsString());
        JsonObject data = new JsonObject();
        data.addProperty("name", product.getName());
        data.addProperty("company", product.getCompany());
        data.addProperty("price", product.getSellersOfThisProduct().get(user).toString());
        data.addProperty("information", product.getInformation());
        data.addProperty("number", String.valueOf(product.getAvailable()));
        data.addProperty("category", product.getCategory().getName());
        return data;
    }

    public JsonObject editProduct(JsonObject jsonObject, User user) {
        String productId = jsonObject.get("id").getAsString();
        int price = Integer.parseInt(jsonObject.get("price").getAsString());
        int available = Integer.parseInt(jsonObject.get("available").getAsString());
        String text = jsonObject.get("text").getAsString();
        String[] first = new Gson().fromJson(jsonObject.get("first").getAsString(), String[].class);
        String[] second = new Gson().fromJson(jsonObject.get("second").getAsString(), String[].class);
        HashMap<String, String> data = new HashMap<>();
        for (int i = 0; i < first.length; i++)
            data.put(first[i], second[i]);
        try {
            sellerController.editProduct(user, productId, price, available, text, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JsonObject addOff(JsonObject jsonObject, User user) {
        String[] products = new Gson().fromJson(jsonObject.get("products").getAsString(), String[].class);
        ArrayList<String> productZ = new ArrayList<>();
        productZ.addAll(Arrays.asList(products));
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        try {
            Date startDate = format.parse(jsonObject.get("startDate").getAsString());
            Date endDate = format.parse(jsonObject.get("enddate").getAsString());
            sellerController.addOff(user, productZ, startDate, endDate, Integer.parseInt(jsonObject.get("percentage").getAsString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
