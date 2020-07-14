package Server;

import Controller.LoginController;
import Model.User;
import com.google.gson.*;

import javax.jws.soap.SOAPBinding;
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
}
