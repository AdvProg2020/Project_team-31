package Server;

import Controller.ProductController;
import Model.Category;
import Model.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class ProductControllerProcess {
    private static ProductControllerProcess instance;

    public static ProductControllerProcess getInstance() {
        if (instance == null)
            instance = new ProductControllerProcess();
        return instance;
    }

    private ProductControllerProcess() {
    }

    public JsonObject showAllProducts(User user) {
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
        JsonArray filters = new JsonArray();
        for (String s : user.getFilters().keySet()) {
            JsonObject fil = new JsonObject();
            fil.addProperty("key", s);
            fil.addProperty("value", user.getFilters().get(s));
            filters.add(fil);
        }
        output.add("filters", filters);
        return output;
    }

    public JsonObject addFilter(JsonObject jsonObject, User user) {
        ProductController.getInstance().addFilterForUser(user, jsonObject.get("key").getAsString(), jsonObject.get("value").getAsString());
        return new JsonObject();
    }

    public JsonObject removeFilter(JsonObject jsonObject, User user) {
        try {
            ProductController.getInstance().disableFilterForUser(user, jsonObject.get("key").getAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonObject();
    }

    public JsonObject addView(JsonObject jsonObject) {
        ProductController.getProductById(jsonObject.get("id").getAsString()).addView();
        return new JsonObject();
    }

}
