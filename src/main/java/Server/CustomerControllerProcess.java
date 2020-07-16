package Server;

import Controller.CustomerController;
import Controller.ProductController;
import Model.Card;
import Model.Product;
import Model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import sun.nio.cs.US_ASCII;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerControllerProcess {
    CustomerController customerController = CustomerController.getInstance();
    private static CustomerControllerProcess instance;

    public static CustomerControllerProcess getInstance() {
        if (instance == null)
            instance = new CustomerControllerProcess();
        return instance;
    }

    private CustomerControllerProcess() {
    }

    public JsonObject showDiscountCodes(JsonObject jsonObject, User user) {
        String data = new Gson().toJson(customerController.showDiscountCodes(user).toArray());
        JsonObject answer = new JsonObject();
        answer.addProperty("content", data);
        return answer;
    }

    public JsonObject addCredit(JsonObject jsonObject, User user) {
        int amount = Integer.parseInt(jsonObject.get("amount").getAsString());
        customerController.addCredit(user, amount);
        return new JsonObject();
    }

    public JsonObject showAllOrdersByList(JsonObject jsonObject, User user) {
        String data = new Gson().toJson(customerController.showAllOrdersByList(user).toArray());
        JsonObject dataToSend = new JsonObject();
        dataToSend.addProperty("data", data);
        return dataToSend;
    }

    public JsonObject getSupporters() {
        JsonObject jsonObject = new JsonObject();
        List<String> allUsername = ServerRunner.supporters.keySet().stream().map(User::getUsername).collect(Collectors.toList());
        String[] names = new String[allUsername.size()];
        for (int i = 0; i < allUsername.size(); i++)
            names[i] = allUsername.get(i);
        jsonObject.addProperty("names",new Gson().toJson(names));
        return jsonObject;
    }

    public JsonObject addProductToCart(User user, JsonObject jsonObject) {
        JsonObject output = new JsonObject();
        if(user.getCard() == null)
            user.setCard(new Card());
        try {
            customerController.addProductToCard(user, user.getCard(), ProductController.getProductById(jsonObject.get("id").getAsString()), jsonObject.get("seller").getAsString());
            output.addProperty("type", "successful");
        } catch (Exception e) {
            output.addProperty("type", "failed");
            output.addProperty("message", e.getMessage());
        }
        return output;
    }

    public JsonObject rateProduct(User user, JsonObject jsonObject) {
        JsonObject output = new JsonObject();
        try {
            customerController.rateProduct(user, jsonObject.get("id").getAsString(), jsonObject.get("rate").getAsInt());
            output.addProperty("type", "successful");
            output.addProperty("newRate", ProductController.getProductById(jsonObject.get("id").getAsString()).getRate());
        } catch (Exception e) {
            output.addProperty("type", "failed");
            output.addProperty("message", e.getMessage());
        }
        return output;
    }

    public JsonObject addComment(User user, JsonObject jsonObject) {
        Product product = ProductController.getProductById(jsonObject.get("id").getAsString());
        ProductController.getInstance().addComment(user, product, jsonObject.get("title").getAsString(), jsonObject.get("content").getAsString());
        JsonArray comments = new JsonArray();
        for (String s : ProductController.getInstance().showCommentAboutProduct(product)) {
            comments.add(s);
        }
        JsonObject output = new JsonObject();
        output.add("comments", comments);
        return output;
    }
}
