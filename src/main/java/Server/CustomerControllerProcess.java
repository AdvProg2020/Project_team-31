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
        JsonArray allCodes = new JsonArray();
        for (String discountCode : customerController.showDiscountCodes(user)) {
            allCodes.add(discountCode);
        }
        JsonObject answer = new JsonObject();
        answer.add("content", allCodes);
        return answer;
    }

    public JsonObject addCredit(JsonObject jsonObject, User user) {
        int amount = Integer.parseInt(jsonObject.get("amount").getAsString());
        customerController.addCredit(user, amount);
        return new JsonObject();
    }
}
