package Server;

import Controller.LoginController;
import Controller.ManagerController;
import Model.Customer;
import Model.Supporter;
import Model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.util.Pair;

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
        if (ServerRunner.supporters.containsKey(user))
            return formerSupporter(jsonObject, user);
        else return newSupporter(jsonObject, user);
    }

    private JsonObject formerSupporter(JsonObject jsonObject, User user) {
        String[] names = new Gson().fromJson(jsonObject.get("names").getAsString(), String[].class);
        String[] chats = new Gson().fromJson(jsonObject.get("chats").getAsString(), String[].class);
        for (int i = 0; i < names.length; i++) {
            Customer customer = (Customer) LoginController.getUserByUsername(names[i]);
            if (chats[i] != null)
                customer.chat += chats[i];
            chats[i] = customer.chat;
        }
        JsonObject answer = new JsonObject();
        answer.addProperty("names", new Gson().toJson(getData(user).getKey()));
        answer.addProperty("chats", new Gson().toJson(getData(user).getKey()));
        return answer;
    }

    private JsonObject newSupporter(JsonObject jsonObject, User user) {
        ServerRunner.supporters.put((Supporter) user, new ArrayList<>());
        while (ServerRunner.supporters.containsKey(user) && ServerRunner.supporters.get(user).size() == 0) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        JsonObject answer = new JsonObject();
        answer.addProperty("names", new Gson().toJson(getData(user).getKey()));
        answer.addProperty("chats", new Gson().toJson(getData(user).getKey()));
        return answer;
    }

    private Pair<String[], String[]> getData(User user) {
        ArrayList<User> users = ServerRunner.supporters.get(user);
        String[] names = new String[users.size()];
        String[] chats = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            names[i] = users.get(i).getUsername();
            chats[i] = ((Customer) users.get(i)).chat;
        }
        return new Pair<>(names, chats);
    }

    public JsonObject showAllUsers(User user) {
        JsonObject output = new JsonObject();
        JsonArray users = new JsonArray();
        for (User aUser : User.getAllUsers()) {
            JsonObject u = new JsonObject();
            u.addProperty("username", aUser.getUsername());
            u.addProperty("name", aUser.getPersonalInformation()[0]);
            u.addProperty("lastName", aUser.getPersonalInformation()[1]);
            users.add(u);
        }
        output.add("users", users);
        output.addProperty("username", user.getUsername());
        return output;
    }

    public JsonObject deleteUser(JsonObject jsonObject) {
        ManagerController.getInstance().deleteUser(jsonObject.get("username").getAsString());
        return new JsonObject();
    }

    public JsonObject addCategory(JsonObject input) {
        ArrayList<String> features = new ArrayList<>();
        for (JsonElement element : input.getAsJsonArray("features")) {
            features.add(element.getAsString());
        }
        ManagerController.getInstance().addCategory(input.get("name").getAsString(), features);
        return new JsonObject();
    }
}
