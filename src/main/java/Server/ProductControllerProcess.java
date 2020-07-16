package Server;

import Controller.ProductController;
import Controller.SellerController;
import GraphicalView.OffedProduct;
import Model.Category;
import Model.Off;
import Model.Product;
import Model.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;


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
        output.add("products", createArrayOfAllProducts());
        return output;
    }

    public JsonObject showAllOffedProducts(User user) {
        JsonObject output = new JsonObject();
        JsonArray filters = new JsonArray();
        for (String s : user.getFilters().keySet()) {
            JsonObject fil = new JsonObject();
            fil.addProperty("key", s);
            fil.addProperty("value", user.getFilters().get(s));
            filters.add(fil);
        }
        output.add("filters", filters);
        SellerController.getInstance().checkTimeOfOffs();
        JsonArray offedProducts = new JsonArray();
        for (Off off : Off.getAllOffs()) {
            for (Product product : off.getOnSaleProducts()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("id", product.getProductId());
                jsonObject.addProperty("name", product.getName());
                jsonObject.addProperty("rate", product.getRate());
                jsonObject.addProperty("minimumPrice", product.getMinimumPrice());
                jsonObject.addProperty("categoryName", product.getCategory().getName());
                jsonObject.addProperty("available", product.getAvailable());
                jsonObject.addProperty("views", product.getViews());
                jsonObject.addProperty("company", product.getCompany());
                JsonArray jsonArray = new JsonArray();
                for (String s : product.getSpecialPropertiesRelatedToCategory().keySet()) {
                    JsonObject feature = new JsonObject();
                    feature.addProperty("key", s);
                    feature.addProperty("value", product.getSpecialPropertiesRelatedToCategory().get(s));
                    jsonArray.add(feature);
                }
                jsonObject.add("specialProperties", jsonArray);
                jsonObject.addProperty("endTime", off.getEndTime().toString());
                jsonObject.addProperty("price", product.getSellersOfThisProduct().get(off.getSeller()));
                jsonObject.addProperty("percent", off.getOffPercent());
                offedProducts.add(jsonObject);
            }
        }
        output.add("products", offedProducts);
        return output;
    }

    private JsonArray createArrayOfAllProducts() {
        JsonArray allProducts = new JsonArray();
        for (Product product : Product.getAllProducts()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", product.getProductId());
            jsonObject.addProperty("name", product.getName());
            jsonObject.addProperty("rate", product.getRate());
            jsonObject.addProperty("minimumPrice", product.getMinimumPrice());
            jsonObject.addProperty("categoryName", product.getCategory().getName());
            jsonObject.addProperty("available", product.getAvailable());
            jsonObject.addProperty("views", product.getViews());
            jsonObject.addProperty("company", product.getCompany());
            JsonArray jsonArray = new JsonArray();
            for (String s : product.getSpecialPropertiesRelatedToCategory().keySet()) {
                JsonObject feature = new JsonObject();
                feature.addProperty("key", s);
                feature.addProperty("value", product.getSpecialPropertiesRelatedToCategory().get(s));
                jsonArray.add(feature);
            }
            jsonObject.add("specialProperties", jsonArray);
            allProducts.add(jsonObject);
        }
        return allProducts;
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
