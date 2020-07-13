package Server;

import Controller.LoginController;
import com.google.gson.JsonObject;

public class LoginControllerProcess {
    LoginController loginController=LoginController.getInstance();
    private static LoginControllerProcess instance;

    public static LoginControllerProcess getInstance() {
        if (instance == null)
            instance = new LoginControllerProcess();
        return instance;
    }

    private LoginControllerProcess() {
    }

    public String managerStatus() {
        System.out.println("manager");
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("managerStatus", loginController.isThereAnyManager());
        return jsonObject.toString();
    }
}
