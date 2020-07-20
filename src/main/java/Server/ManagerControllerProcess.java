package Server;

import Controller.LoginController;
import Controller.ManagerController;
import Controller.SellerController;
import GraphicalView.DataBase;
import Model.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.corba.se.spi.protocol.RequestDispatcherRegistry;
import javafx.scene.control.Label;
import javafx.util.Pair;

import javax.jws.soap.SOAPBinding;
import javax.swing.*;
import javax.xml.bind.util.JAXBSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ManagerControllerProcess {
    private static ManagerControllerProcess instance;

    public static ManagerControllerProcess getInstance() {
        if (instance == null)
            instance = new ManagerControllerProcess();
        return instance;
    }

    private ManagerControllerProcess() {
    }

    public JsonObject addSupporter(JsonObject jsonObject) {
        JsonObject output = new JsonObject();
        if (!LoginController.getInstance().isUsernameFree(jsonObject.get("username").getAsString())) {
            output.addProperty("type", "failed");
            output.addProperty("message", "this username is selected before");
            return output;
        }
        output.addProperty("type", "successful");
        new Supporter(jsonObject.get("firstName").getAsString(), jsonObject.get("lastName").getAsString(), jsonObject.get("username").getAsString(), jsonObject.get("email").getAsString(), jsonObject.get("phone").getAsString(), jsonObject.get("password").getAsString());
        return output;
    }

    public JsonObject supporter(JsonObject jsonObject, User user) {
        if (ServerRunner.supporters.containsKey(user))
            return formerSupporter(jsonObject, user);
        else return newSupporter(jsonObject, user);
    }

    private JsonObject formerSupporter(JsonObject jsonObject, User user) {
        String[] names = new Gson().fromJson(jsonObject.get("names").getAsString(), String[].class);
        String[] chats = new Gson().fromJson(jsonObject.get("chats").getAsString(), String[].class);
        for (int i = 0; i < names.length; i++) {
            Customer customer = (Customer) LoginController.getUserByUsername(names[i]);
            if (chats[i] != null)
                customer.chat += chats[i];
            chats[i] = customer.chat;
        }
        JsonObject answer = new JsonObject();
        answer.addProperty("names", new Gson().toJson(getData(user).getKey()));
        answer.addProperty("chats", new Gson().toJson(getData(user).getKey()));
        return answer;
    }

    private JsonObject newSupporter(JsonObject jsonObject, User user) {
        ServerRunner.supporters.put((Supporter) user, new ArrayList<>());
        while (ServerRunner.supporters.containsKey(user) && ServerRunner.supporters.get(user).size() == 0) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        JsonObject answer = new JsonObject();
        answer.addProperty("names", new Gson().toJson(getData(user).getKey()));
        answer.addProperty("chats", new Gson().toJson(getData(user).getKey()));
        return answer;
    }

    private Pair<String[], String[]> getData(User user) {
        ArrayList<User> users = ServerRunner.supporters.get(user);
        String[] names = new String[users.size()];
        String[] chats = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            names[i] = users.get(i).getUsername();
            chats[i] = ((Customer) users.get(i)).chat;
        }
        return new Pair<>(names, chats);
    }

    public JsonObject showAllUsers(User user) {
        JsonObject output = new JsonObject();
        JsonArray users = new JsonArray();
        for (User aUser : User.getAllUsers()) {
            JsonObject u = new JsonObject();
            u.addProperty("username", aUser.getUsername());
            u.addProperty("name", aUser.getPersonalInformation()[0]);
            u.addProperty("lastName", aUser.getPersonalInformation()[1]);
            users.add(u);
        }
        output.add("users", users);
        output.addProperty("username", user.getUsername());
        return output;
    }

    public JsonObject deleteUser(JsonObject jsonObject) {
        ManagerController.getInstance().deleteUser(jsonObject.get("username").getAsString());
        return new JsonObject();
    }

    public JsonObject addCategory(JsonObject input) {
        ArrayList<String> features = new ArrayList<>();
        for (JsonElement element : input.getAsJsonArray("features")) {
            features.add(element.getAsString());
        }
        ManagerController.getInstance().addCategory(input.get("name").getAsString(), features);
        return new JsonObject();
    }

    public JsonObject getAllCustomers() {
        JsonObject output = new JsonObject();
        JsonArray customers = new JsonArray();
        for (Customer customer : Customer.getAllCustomers()) {
            customers.add(customer.getUsername());
        }
        output.add("customers", customers);
        return output;
    }

    public JsonObject createDiscountCode(JsonObject input) {
        JsonObject output = new JsonObject();
        HashMap<String, Integer> usernameAndNumber = new HashMap<>();
        for (JsonElement element : input.getAsJsonArray("customers")) {
            JsonObject customer = element.getAsJsonObject();
            usernameAndNumber.put(customer.get("username").getAsString(), customer.get("number").getAsInt());
        }
        try {
            ManagerController.getInstance().createDiscountCode(input.get("code").getAsString(), castStringToDate(input.get("startDate").getAsString()), castStringToDate(input.get("endDate").getAsString()), input.get("percent").getAsInt(), input.get("maximum").getAsInt(), usernameAndNumber);
            output.addProperty("type", "successful");
        } catch (Exception e) {
            output.addProperty("type", "failed");
            output.addProperty("message", e.getMessage());
        }
        return output;
    }

    private Date castStringToDate(String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        return format.parse(dateString);
    }

    public JsonObject changeCategoryFeature(JsonObject jsonObject) {
        HashMap<String, String> changedFeatured = new HashMap<>();
        changedFeatured.put(jsonObject.get("oldName").getAsString(), jsonObject.get("newName").getAsString());
        ManagerController.getInstance().changeFeatureOfCategory(jsonObject.get("category").getAsString(), changedFeatured);
        return new JsonObject();
    }

    public JsonObject addCategoryFeature(JsonObject input) {
        ManagerController.getInstance().addFeature(ManagerController.getCategoryByName(input.get("category").getAsString()), input.get("feature").getAsString());
        return new JsonObject();
    }

    public JsonObject removeCategoryFeature(JsonObject input) {
        ManagerController.getInstance().removeFeature(ManagerController.getCategoryByName(input.get("category").getAsString()), input.get("feature").getAsString());
        return new JsonObject();
    }

    public JsonObject getAllDiscount() {
        JsonObject output = new JsonObject();
        JsonArray discounts = new JsonArray();
        for (DiscountCode discountCode : DiscountCode.getAllDiscountCodes()) {
            JsonObject discount = new JsonObject();
            discount.addProperty("code", discountCode.getDiscountCode());
            discounts.add(getDiscountCode(discount));
        }
        output.add("discounts", discounts);
        return output;
    }

    public JsonObject getDiscountCode(JsonObject input) {
        DiscountCode discountCode = null;
        try {
            discountCode = ManagerController.getInstance().getDiscountById(input.get("code").getAsString());
        } catch (Exception e) {
            System.out.printf("discount not found");
        }
        JsonObject discount = new JsonObject();
        discount.addProperty("code", discountCode.getDiscountCode());
        discount.addProperty("beginTime", discountCode.getBeginTime().toString());
        discount.addProperty("endTime", discountCode.getEndTime().toString());
        discount.addProperty("percent", discountCode.getDiscountPercent());
        discount.addProperty("maximum", discountCode.getMaximumDiscount());
        JsonArray customers = new JsonArray();
        for (Customer customer : discountCode.getDiscountTimesForEachCustomer().keySet()) {
            JsonObject cus = new JsonObject();
            cus.addProperty("username", customer.getUsername());
            cus.addProperty("number", discountCode.getDiscountTimesForEachCustomer().get(customer));
            customers.add(cus);
        }
        discount.add("customers", customers);
        return discount;
    }

    public JsonObject editDiscount(JsonObject input) {
        JsonObject output = new JsonObject();
        HashMap<String, Integer> usernameAndNumber = new HashMap<>();
        for (JsonElement element : input.getAsJsonArray("customers")) {
            JsonObject customer = element.getAsJsonObject();
            usernameAndNumber.put(customer.get("username").getAsString(), customer.get("number").getAsInt());
        }
        try {
            ManagerController.getInstance().editDiscountCode(input.get("code").getAsString(), castStringToDate(input.get("startDate").getAsString()), castStringToDate(input.get("endDate").getAsString()), input.get("percent").getAsInt(), input.get("maximum").getAsInt(), usernameAndNumber);
            output.addProperty("type", "successful");
        } catch (Exception e) {
            output.addProperty("type", "failed");
            output.addProperty("message", e.getMessage());
        }
        return output;
    }

    public JsonObject getAllRequests() {
        JsonObject output = new JsonObject();
        JsonArray requests = new JsonArray();
        for (Request request : Request.getAllRequest()) {
            requests.add(request.getRequestId());
        }
        output.add("requests", requests);
        return output;
    }

    public JsonObject showRequest(JsonObject input) {
        Request request = ManagerController.getInstance().getRequestById(input.get("id").getAsString());
        if (request instanceof SellerRequest)
            return showSellerRequest(request);
        if (request instanceof ProductRequest)
            return showProductRequest(request);
        if (request instanceof OffRequest)
            return showOffRequest(request);
        return showSellerOfProductRequest(request);
    }

    private JsonObject showSellerRequest(Request request) {
        SellerRequest sellerRequest = (SellerRequest) request;
        JsonObject output = new JsonObject();
        output.addProperty("type", "seller");
        output.addProperty("username", sellerRequest.getUsername());
        output.addProperty("name", sellerRequest.getInformation()[0]);
        output.addProperty("lastName", sellerRequest.getInformation()[1]);
        output.addProperty("company", sellerRequest.getInformation()[5]);
        output.addProperty("email", sellerRequest.getInformation()[2]);
        output.addProperty("phone", sellerRequest.getInformation()[3]);
        return output;
    }

    private JsonObject showProductRequest(Request request) {
        JsonObject output = new JsonObject();
        output.addProperty("type", "product");
        ProductRequest productRequest = (ProductRequest) request;
        if (productRequest.isEditing()) {
            output.addProperty("isEditing", true);
            output.addProperty("name", productRequest.getProduct().getName());
            output.addProperty("seller", productRequest.getSeller().getUsername());
            output.addProperty("price", productRequest.getPrice());
            output.addProperty("available", productRequest.getAvailable());
            output.addProperty("information", productRequest.getInformation());
            JsonArray features = new JsonArray();
            for (String s : productRequest.getSpecialPropertiesRelatedToCategory().keySet()) {
                JsonObject feature = new JsonObject();
                feature.addProperty("key", s);
                System.out.println("key:" + s + ",value:" + productRequest.getSpecialPropertiesRelatedToCategory().get(s));
                feature.addProperty("value", productRequest.getSpecialPropertiesRelatedToCategory().get(s));
                features.add(feature);
            }
            output.add("special", features);
        } else {
            Product productToCreate = productRequest.getProduct();
            output.addProperty("isEditing", false);
            output.addProperty("name", productToCreate.getName());
            Seller seller = productToCreate.getSellersOfThisProduct().keySet().stream().collect(Collectors.toList()).get(0);
            output.addProperty("seller", seller.getUsername());
            output.addProperty("price", productToCreate.getMinimumPrice());
            output.addProperty("available", productToCreate.getAvailable());
            output.addProperty("information", productToCreate.getInformation());
            JsonArray features = new JsonArray();
            for (String s : productToCreate.getSpecialPropertiesRelatedToCategory().keySet()) {
                JsonObject feature = new JsonObject();
                feature.addProperty("key", s);
                feature.addProperty("value", productRequest.getSpecialPropertiesRelatedToCategory().get(s));
                features.add(feature);
            }
            output.add("special", features);
        }
        return output;
    }

    private JsonObject showOffRequest(Request request) {
        JsonObject output = new JsonObject();
        output.addProperty("type", "off");
        OffRequest offRequest = (OffRequest) request;
        if (offRequest.getIsEditing()) {
            output.addProperty("isEditing", true);
            output.addProperty("offId", offRequest.getOff().getOffId());
            output.addProperty("beginTime", offRequest.getBeginTime().toString());
            output.addProperty("endTime", offRequest.getEndTime().toString());
            output.addProperty("percent", offRequest.getOffPercent());
            JsonArray products = new JsonArray();
            for (Product product : offRequest.getOnSaleProducts()) {
                products.add(product.getName());
            }
            output.add("products", products);
        } else {
            Off off = offRequest.getOff();
            output.addProperty("isEditing", false);
            output.addProperty("offId", off.getOffId());
            output.addProperty("beginTime", off.getBeginTime().toString());
            output.addProperty("endTime", off.getEndTime().toString());
            output.addProperty("percent", off.getOffPercent());
            JsonArray products = new JsonArray();
            for (Product product : off.getOnSaleProducts()) {
                products.add(product.getName());
            }
            output.add("products", products);
        }
        return output;
    }

    private JsonObject showSellerOfProductRequest(Request request) {
        JsonObject output = new JsonObject();
        output.addProperty("type", "sellerOfProduct");
        SellerOfProductRequest sellerOfProductRequest = (SellerOfProductRequest) request;
        output.addProperty("product", sellerOfProductRequest.getProduct().getProductId());
        output.addProperty("seller", sellerOfProductRequest.getSeller().getUsername());
        output.addProperty("price", sellerOfProductRequest.getPrice());
        return output;
    }

    public JsonObject acceptRequest(JsonObject input) {
        ManagerController.getInstance().acceptRequest(input.get("id").getAsString());
        return new JsonObject();
    }

    public JsonObject declineRequest(JsonObject input) {
        ManagerController.getInstance().declineRequest(input.get("id").getAsString());
        return new JsonObject();
    }

    public JsonObject deleteProduct(JsonObject input, User user) {
        if (user instanceof Seller) {
            try {
                SellerController.getInstance().removeProductFromUser(user, input.get("id").getAsString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                SellerController.getInstance().removeProduct(input.get("id").getAsString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new JsonObject();
    }

    public JsonObject getProductList(User user) {
        JsonObject output = new JsonObject();
        JsonArray products = new JsonArray();
        if (user instanceof Seller) {
            for (Product product : SellerController.getInstance().showProductsOfThisSellerForGUI(user)) {
                products.add(product.getProductId());
            }
        } else {
            for (Product product : Product.getAllProducts()) {
                products.add(product.getProductId());
            }
        }
        output.add("products", products);
        return output;
    }
}