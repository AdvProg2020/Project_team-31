package Server;

import Controller.LoginController;
import Model.Customer;
import Model.Supporter;
import Model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class ManagerControllerProcess {
    private static ManagerControllerProcess instance;

    public static ManagerControllerProcess getInstance() {
        if (instance == null)
            instance = new ManagerControllerProcess();
        return instance;
    }

    private ManagerControllerProcess() {
    }

    public JsonObject supporter(JsonObject jsonObject, User user) {
        if (ServerRunner.supporters.containsKey((Supporter) user))
            return formerSupporter(jsonObject, user);
        else return newSupporter(jsonObject, user);
    }

    private JsonObject formerSupporter(JsonObject jsonObject, User user) {
        String[] names = new Gson().fromJson(jsonObject.get("names").getAsString(), String[].class);
        String[] chats = new Gson().fromJson(jsonObject.get("chats").getAsString(), String[].class);
        for (int i = 0; i < names.length; i++) {
           Customer customer = (Customer) LoginController.getUserByUsername(names[i]);
           customer.chat=chats[i];
        }
        return null;  ///////check this!
    }

    private JsonObject newSupporter(JsonObject jsonObject, User user) {
        ServerRunner.supporters.put((Supporter) user, new ArrayList<>());
        JsonObject answer = new JsonObject();
        answer.addProperty("names", new Gson().toJson(new String[0]));
        answer.addProperty("chats", new Gson().toJson(new String[0]));
        return answer;
    }
}
