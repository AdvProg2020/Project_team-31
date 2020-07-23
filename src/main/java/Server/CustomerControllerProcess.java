package Server;

import Controller.CustomerController;
import Controller.ManagerController;
import Controller.ProductController;
import Controller.SellerController;
import GraphicalView.DataBase;
import GraphicalView.ReceiveInformationForShopping;
import Model.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.xml.bind.util.JAXBSource;
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

    public JsonObject showAllOrdersByList(User user) {
        JsonObject output = new JsonObject();
        JsonArray data = new JsonArray();
        for (BuyingLog buyingLog : ((Customer) user).getAllBuyingLogs()) {
            JsonObject log = new JsonObject();
            log.addProperty("id", buyingLog.getLogId());
            log.addProperty("discount", buyingLog.getDiscountAmount());
            log.addProperty("date", buyingLog.getDate().toString());
            log.addProperty("totalPrice", buyingLog.getTotalPrice());
            log.addProperty("status", String.valueOf(buyingLog.getDeliveryStatus()));
            JsonArray products = new JsonArray();
            for (ProductInCard product : buyingLog.getBuyingProducts().values()) {
                JsonObject pro = new JsonObject();
                pro.addProperty("name", product.getProduct().getName());
                pro.addProperty("number", product.getNumber());
                pro.addProperty("seller", product.getSeller().getUsername());
                products.add(pro);
            }
            log.add("products", products);
            data.add(log);
        }
        output.add("data", data);
        return output;
    }

    public JsonObject getSupporters() {
        JsonObject jsonObject = new JsonObject();
        List<String> allUsername = ServerRunner.supporters.keySet().stream().map(User::getUsername).collect(Collectors.toList());
        String[] names = new String[allUsername.size()];
        for (int i = 0; i < allUsername.size(); i++)
            names[i] = allUsername.get(i);
        jsonObject.addProperty("names", new Gson().toJson(names));
        return jsonObject;
    }

    public JsonObject addProductToCart(User user, JsonObject jsonObject) {
        JsonObject output = new JsonObject();
        if (user.getCard() == null)
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

    public JsonObject showCart(User user) {
        if (user.getCard() == null)
            user.setCard(new Card());
        JsonObject output = new JsonObject();
        JsonArray products = new JsonArray();
        for (ProductInCard productInCard : user.getCard().getProductsInThisCard().values()) {
            JsonObject product = new JsonObject();
            product.addProperty("id", productInCard.getProduct().getProductId());
            product.addProperty("name", productInCard.getProduct().getName());
            product.addProperty("number", productInCard.getNumber());
            int percent = 100;
            SellerController.getInstance().checkTimeOfOffs();
            for (Off off : productInCard.getProduct().getOffs()) {
                if (off.getSeller().equals(productInCard.getSeller())) {
                    percent = 100 - off.getOffPercent();
                }
            }
            int price = (productInCard.getProduct().getSellersOfThisProduct().get(productInCard.getSeller()) * (percent) / 100);
            int totalPrice = price * productInCard.getNumber();
            product.addProperty("price", price);
            product.addProperty("totalPrice", totalPrice);
            products.add(product);
        }
        output.add("products", products);
        output.addProperty("total", CustomerController.getInstance().showTotalPrice(user.getCard()));
        return output;
    }

    public JsonObject changeNumberOfProductInCart(User user, JsonObject jsonObject) {
        JsonObject output = new JsonObject();
        try {
            CustomerController.getInstance().changeNumberOfProductInCard(user, user.getCard(), jsonObject.get("id").getAsString(), jsonObject.get("number").getAsInt());
            output.addProperty("type", "successful");
            output.add("cart", showCart(user));
        } catch (Exception e) {
            output.addProperty("type", "failed");
            output.addProperty("message", e.getMessage());
        }
        return output;
    }

    public JsonObject checkAvailableToPurchase(User user) {
        String message = CustomerController.getInstance().isAvailabilityOk(user);
        JsonObject output = new JsonObject();
        output.addProperty("message", message);
        return output;
    }

    public JsonObject createBuyingLog(User user, JsonObject input) {
        JsonObject output = new JsonObject();
        String data[] = new String[2];
        data[0] = input.get("address").getAsString();
        data[1] = input.get("phone").getAsString();
        try {
            BuyingLog buyingLog = CustomerController.getInstance().createBuyingLog(user, data);
            output.addProperty("type", "successful");
            output.addProperty("id", buyingLog.getLogId());
        } catch (Exception e) {
            output.addProperty("type", "failed");
            output.addProperty("message", e.getMessage());
        }
        return output;
    }

    public JsonObject putDiscount(User user, JsonObject input) {
        JsonObject output = new JsonObject();
        try {
            CustomerController.getInstance().putDiscount(user, input.get("id").getAsString() , input.get("code").getAsString());
            output.addProperty("type", "successful");
        } catch (Exception e) {
            output.addProperty("type", "failed");
            output.addProperty("message", e.getMessage());
        }
        return output;
    }

    public JsonObject payMoney(User user, JsonObject jsonObject) {
        JsonObject output = new JsonObject();
        try {
            CustomerController.getInstance().payMoney(user, jsonObject.get("id").getAsString());
            output.addProperty("type", "successful");
        } catch (Exception e) {
            output.addProperty("type", "failed");
            output.addProperty("message", e.getMessage());
        }
        return output;
    }
}
