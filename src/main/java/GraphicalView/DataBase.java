package GraphicalView;

import Model.*;
import com.google.gson.Gson;
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
    User user = null;
    String role = null;

    public void logout() {
//        user = null;
        role = null;
        pages.clear();
        runner.changeScene("MainMenu.fxml");
        runner.changeScene("MainMenu.fxml");
    }

    public String[] getUserInfo() throws IOException {
        JsonObject dataToSend = runner.jsonMaker("login", "showPersonalInformation");
        dataOutputStream.writeUTF(dataToSend.toString());
        dataOutputStream.flush();
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(dataInputStream.readUTF());
        return new Gson().fromJson(jsonObject.get("info"), String[].class);
    }
}
