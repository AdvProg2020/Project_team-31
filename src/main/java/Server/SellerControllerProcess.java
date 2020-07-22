package Server;

import Controller.ProductController;
import Controller.SellerController;
import Model.Auction;
import Model.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.ParseException;
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
            Date endDate = format.parse(jsonObject.get("endDate").getAsString());
            sellerController.addOff(user, productZ, startDate, endDate, Integer.parseInt(jsonObject.get("percentage").getAsString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonObject();
    }

    public JsonObject getOffInfo(JsonObject jsonObject, User user) {
        Off off = sellerController.getOffById(jsonObject.get("offId").getAsString());
        String data = "off ID : " + off.getOffId() + "\n" +
                "start time : " + off.getBeginTime() + "\n" +
                "end time : " + off.getEndTime() + "\n" +
                "off percentage : " + off.getOffPercent() + "\n" +
                "products : \n";
        for (Product product : off.getOnSaleProducts())
            data += product.getName() + "\n";
        JsonObject dataToSend = new JsonObject();
        dataToSend.addProperty("data", data);
        return dataToSend;
    }

    public JsonObject getAllOffs(JsonObject jsonObject, User user) {
        ArrayList<Off> allOffs = sellerController.showAllOffsForGUI(user);
        String[] allId = new String[allOffs.size()];
        for (int i = 0; i < allId.length; i++)
            allId[i] = allOffs.get(i).getOffId();
        JsonObject data = new JsonObject();
        data.addProperty("data", new Gson().toJson(allId));
        return data;
    }

    public JsonObject editOff(JsonObject jsonObject, User user) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        try {
            String id = jsonObject.get("id").getAsString();
            Date startDate = format.parse(jsonObject.get("startDate").getAsString());
            Date endDate = format.parse(jsonObject.get("endDate").getAsString());
            int percentage = Integer.parseInt(jsonObject.get("percentage").getAsString());
            String[] products = new Gson().fromJson(jsonObject.get("products").getAsString(), String[].class);
            ArrayList<String> productZ = new ArrayList<>();
            productZ.addAll(Arrays.asList(products));
            sellerController.editOff(user, id, productZ, startDate, endDate, percentage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JsonObject getOffInfoForEdit(JsonObject jsonObject, User user) {
        Off off = sellerController.getOffById(jsonObject.get("id").getAsString());
        JsonObject dataToSend = new JsonObject();
        dataToSend.addProperty("percentage", String.valueOf(off.getOffPercent()));
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        dataToSend.addProperty("startDate", format.format(off.getBeginTime()));
        dataToSend.addProperty("endDate", format.format(off.getEndTime()));
        ArrayList<Product> products = off.getOnSaleProducts();
        String[] productZ = new String[products.size()];
        for (int i = 0; i < productZ.length; i++)
            productZ[i] = products.get(i).getProductId();
        dataToSend.addProperty("products", new Gson().toJson(productZ));
        return dataToSend;
    }

    public JsonObject showSalesHistory(User user) {
        JsonObject output = new JsonObject();
        JsonArray logs = new JsonArray();
        for (SellingLog sellingLog : SellerController.getInstance().showSalesHistoryByList(user)) {
            JsonObject log = new JsonObject();
            log.addProperty("total", sellingLog.getTotalPriceArrived());
            log.addProperty("off", sellingLog.getAmountOfOff());
            log.addProperty("product", sellingLog.getProductName());
            logs.add(log);
        }
        output.add("logs", logs);
        return output;
    }

    public JsonObject addSellerToProduct(User user, JsonObject input) {
        JsonObject output = new JsonObject();
        try {
            SellerController.getInstance().addSellerToProduct(user, input.get("id").getAsString(), input.get("price").getAsInt());
            output.addProperty("type", "successful");
        } catch (Exception e) {
            output.addProperty("type", "failed");
            output.addProperty("message", e.getMessage());
        }
        return output;
    }

    public JsonObject createAuction(User user, JsonObject input) {
        JsonObject output = new JsonObject();
        Product product = ProductController.getProductById(input.get("product").getAsString());
        if (product == null) {
            output.addProperty("type", "failed");
            output.addProperty("message", "There is not any product with this id");
            return output;
        } else if (!product.getSellersOfThisProduct().keySet().contains(user)) {
            output.addProperty("type", "failed");
            output.addProperty("message", "You don't have this product to sell");
            return output;
        }
        for (Auction auction : product.getAuctions()) {
            if(auction.getSeller().equals(user.getUsername())) {
                output.addProperty("type", "failed");
                output.addProperty("message", "This product is in auction");
                return output;
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        try {
            new Auction(user.getUsername(), product.getProductId(), format.parse(input.get("start").getAsString()), format.parse(input.get("end").getAsString()), product.getSellersOfThisProduct().get(user));
            output.addProperty("type", "successful");
        } catch (ParseException e) {
            output.addProperty("type", "failed");
            output.addProperty("message", e.getMessage());
        }
        return output;
    }
}
