package Server;

import Controller.SellerController;
import Model.Category;
import Model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
    public JsonObject getAllCategories(JsonObject jsonObject, User user) {
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
}
