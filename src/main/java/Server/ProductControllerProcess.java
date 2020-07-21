package Server;

import Controller.ProductController;
import Controller.SellerController;
import GraphicalView.OffedProduct;
import Model.*;
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

    public JsonObject showProduct(JsonObject jsonObject) {
        JsonObject output = new JsonObject();
        Product product = ProductController.getProductById(jsonObject.get("id").getAsString());
        output.addProperty("name", product.getName());
        output.addProperty("rate", product.getRate());
        output.addProperty("minimumPrice", product.getMinimumPrice());
        output.addProperty("available", product.getAvailable());
        output.addProperty("views", product.getViews());
        output.addProperty("information", product.getInformation());
        output.addProperty("status", product.getProductStatus().toString());
        JsonArray jsonArray = new JsonArray();
        for (String s : product.getSpecialPropertiesRelatedToCategory().keySet()) {
            JsonObject feature = new JsonObject();
            feature.addProperty("key", s);
            feature.addProperty("value", product.getSpecialPropertiesRelatedToCategory().get(s));
            jsonArray.add(feature);
        }
        output.add("specialProperties", jsonArray);
        JsonArray comments = new JsonArray();
        for (String s : ProductController.getInstance().showCommentAboutProduct(product)) {
            comments.add(s);
        }
        output.add("comments", comments);
        output.add("sellers", sellerOfProduct(product));
        return output;
    }

    private JsonArray sellerOfProduct(Product product) {
        JsonArray sellers = new JsonArray();
        for (Seller seller : product.getSellersOfThisProduct().keySet()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("username", seller.getUsername());
            jsonObject.addProperty("price", product.getSellersOfThisProduct().get(seller));
            int offPercent = 0;
            for (Off off : product.getOffs()) {
                if (off.getSeller().equals(seller)) {
                    offPercent = off.getOffPercent();
                }
            }
            jsonObject.addProperty("offPercent", offPercent);
            sellers.add(jsonObject);
        }
        return sellers;
    }

}
