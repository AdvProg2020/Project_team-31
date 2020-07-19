package GraphicalView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class LoginMenu {
    public TextField usernameField;
    public PasswordField passwordField;

    public void back(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Runner.getInstance().back();
    }

    public void loginRequest(MouseEvent mouseEvent) {
        Runner.buttonSound();
        if (usernameField.getText().equals("") || passwordField.getText().equals("")) {
            Alert emptyField = new Alert(Alert.AlertType.ERROR, "please fill fields", ButtonType.OK);
            emptyField.show();
        } else {
            JsonObject jsonObject = null;
            JsonObject output = Runner.getInstance().jsonMaker("login", "login");
            output.addProperty("username", usernameField.getText());
            output.addProperty("password", passwordField.getText());
            try {
                DataBase.getInstance().dataOutputStream.writeUTF(output.toString());
                DataBase.getInstance().dataOutputStream.flush();
                String input = DataBase.getInstance().dataInputStream.readUTF();
                jsonObject = (JsonObject) new JsonParser().parse(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (jsonObject.get("type").getAsString().equals("failed")) {
                Alert error = new Alert(Alert.AlertType.ERROR, jsonObject.get("message").getAsString(), ButtonType.OK);
                error.show();
            } else {
                DataBase.getInstance().role = jsonObject.get("role").getAsString();
                DataBase.getInstance().token = jsonObject.get("token").getAsString();
                Runner.getInstance().back();
                Alert error = new Alert(Alert.AlertType.INFORMATION, "you have login successfully", ButtonType.OK);
                error.show();
            }
        }
    }

    public void register(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Runner.getInstance().changeScene("RegisterMenu.fxml");
    }
}
