package Server;

import Controller.CustomerController;
import Model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
}
