package GraphicalView;

import Controller.LoginController;
import Model.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Calendar;
import java.util.Stack;

public class DataBase {
    public static DataBase dataBase = null;
    static Runner runner = Runner.getInstance();
    public Card card;
    //    LoginController loginController = LoginController.getInstance();
    Off editingOff = null;
    public DiscountCode editingDiscountCode = null;
    boolean isAddingManager = false;
    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    String token;

    public static DataBase getInstance() {
        if (dataBase == null)
            dataBase = new DataBase();
        return dataBase;
    }

    private DataBase() {
    }

    ///////////////////////////////////////////////////////////
    Stack<String> pages = new Stack();
    User tempUser = new User();

    //    User user = new Customer("mohammadali", "kakavand", "malikakavand", "myemail", "999", "1234");
//    User user = new Seller("mohammadali", "kakavand", "malikakavand", "myemail", "999", "1234", "samsung");
//      User user = new Manager("mohammadali", "kakavand", "malikakavand", "myemail", "999", "1234");
    User user = null;

    public void logout() {
        user = null;
        pages.clear();
        runner.changeScene("MainMenu.fxml");
        runner.changeScene("MainMenu.fxml");
    }

    public String[] getUserInfo() throws IOException {
        dataOutputStream.writeUTF(runner.jsonMaker("login", "showPersonalInformation").toString());
        dataOutputStream.flush();
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(dataInputStream.readUTF());
        jsonObject.get("data").getAsJsonArray().get(0).getAsString();
        String[] info = new String[7];
        int iterator = 0;
        for (JsonElement jsonElement : jsonObject.get("data").getAsJsonArray()) {
            info[iterator] = jsonElement.getAsString();
            iterator++;
        }
        return info;
    }
}
