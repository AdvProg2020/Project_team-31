package Server;

import Controller.LoginController;
import GraphicalView.DataBase;
import Model.Customer;
import Model.Manager;
import Model.Seller;
import Model.User;
import com.google.gson.*;

import javax.jws.soap.SOAPBinding;
import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class LoginControllerProcess {
    LoginController loginController = LoginController.getInstance();
    private static LoginControllerProcess instance;

    public static LoginControllerProcess getInstance() {
        if (instance == null)
            instance = new LoginControllerProcess();
        return instance;
    }

    private LoginControllerProcess() {
    }

    public JsonObject managerStatus() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("managerStatus", loginController.isThereAnyManager());
        return jsonObject;
    }

    public JsonObject showPersonalInformation(JsonObject jsonObject, User user) {
        String[] info = loginController.showPersonalInformation(user);
        JsonObject answer = new JsonObject();
        answer.addProperty("info", new Gson().toJson(info));
        return answer;
    }

    public JsonObject editPersonalInformation(JsonObject jsonObject, User user) {
        String[] newInfo = new Gson().fromJson(jsonObject.get("newInfo").getAsString(), String[].class);
        loginController.editPersonalInformation(user, newInfo);
        return jsonObject;
    }

    public JsonObject login(User user, JsonObject input) {
        JsonObject output = new JsonObject();
        try {
            User newUser = LoginController.getInstance().login(input.get("username").getAsString(), input.get("password").getAsString(), user.getCard());
            output.addProperty("type", "successful");
            output.addProperty("token", createToken());
            output.addProperty("username", newUser.getUsername());
            String role;
            if(newUser instanceof Customer)
                role = "customer";
            else if(newUser instanceof Seller)
                role = "seller";
            else if (newUser instanceof Manager)
                role = "manager";
            else
                role = "supporter";
            output.addProperty("role", role);
        } catch (Exception e) {
            output.addProperty("type", "failed");
            output.addProperty("message", e.getMessage());
        }
        return output;
    }

    private String createToken() {
        String token = "";
        for (int i = 0 ; i < 30; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(97, 123);
            token += (char)randomNum;
        }
        return token;
    }
}
