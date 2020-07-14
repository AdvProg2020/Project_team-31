package Server;

import Controller.LoginController;
import Model.User;
import com.google.gson.*;

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

    public String managerStatus() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("managerStatus", loginController.isThereAnyManager());
        return jsonObject.toString();
    }

    public String showPersonalInformation(JsonObject jsonObject) {
        User user = LoginController.getUserByUsername(jsonObject.get("username").getAsString());
        String[] info = loginController.showPersonalInformation(user);
        JsonObject answer = new JsonObject();
        answer.addProperty("info", new Gson().toJson(info));
        return answer.toString();
    }
}
