package Server;

import Controller.CustomerController;
import Controller.LoginController;
import Model.User;
import com.google.gson.Gson;
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

    public String showDiscountCodes(JsonObject jsonObject) {
        User user = LoginController.getUserByUsername(jsonObject.get("username").getAsString());
        ArrayList<String> allCodes = customerController.showDiscountCodes(user);
      return new Gson().toJson(allCodes);
    }
}
