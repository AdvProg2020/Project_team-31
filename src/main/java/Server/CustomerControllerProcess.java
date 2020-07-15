package Server;

import Controller.CustomerController;
import Controller.LoginController;
import Model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

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
}
