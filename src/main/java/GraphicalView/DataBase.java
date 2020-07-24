package GraphicalView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Stack;

public class DataBase {
    public static DataBase dataBase = null;
    static Runner runner = Runner.getInstance();
    String editingOffId;
    public String editingDiscountCode = null;
    boolean isAddingManager = false;
    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    String token = "null";
    StringProperty chat = new SimpleStringProperty();
    byte[] fileToBuy = null;
    String fileName = "";

    public static DataBase getInstance() {
        if (dataBase == null)
            dataBase = new DataBase();
        return dataBase;
    }

    private DataBase() {
    }

    ///////////////////////////////////////////////////////////
    Stack<String> pages = new Stack();
    String role = "none";

    public void logout() {
        try {
            dataOutputStream.writeUTF(runner.jsonMaker("login", "logout").toString());
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        role = "none";
        token = "null";
        pages.clear();
        runner.changeScene("MainMenu.fxml");
        runner.changeScene("MainMenu.fxml");
    }

    public String[] getUserInfo() throws IOException {
        JsonObject dataToSend = runner.jsonMaker("login", "showPersonalInformation");
        dataOutputStream.writeUTF(dataToSend.toString());
        dataOutputStream.flush();
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(dataInputStream.readUTF());
        return new Gson().fromJson(jsonObject.get("info").getAsString(), String[].class);
    }
}
