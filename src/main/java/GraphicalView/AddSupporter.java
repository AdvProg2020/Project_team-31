package GraphicalView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AddSupporter {
    public TextField usernameField;
    public TextField passwordField;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField emailField;
    public TextField phoneField;
    Runner runner = Runner.getInstance();

    public void back(MouseEvent mouseEvent) {
        runner.getInstance().back();
    }

    public void registerRequest(MouseEvent mouseEvent) {
        Runner.buttonSound();
        if (!isEmpty().equals("none")) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please enter " + isEmpty(), ButtonType.OK);
            error.show();
        } else {
            JsonObject output = runner.jsonMaker("manager", "addSupporter");
            output.addProperty("username", usernameField.getText());
            output.addProperty("firstName", firstNameField.getText());
            output.addProperty("lastName", lastNameField.getText());
            output.addProperty("email", emailField.getText());
            output.addProperty("phone", phoneField.getText());
            output.addProperty("password", passwordField.getText());
            try {
                DataBase.getInstance().dataOutputStream.writeUTF(output.toString());
                DataBase.getInstance().dataOutputStream.flush();
                String input = DataBase.getInstance().dataInputStream.readUTF();
                JsonObject jsonObject = (JsonObject) new JsonParser().parse(input);
                if(jsonObject.get("type").getAsString().equals("failed")) {
                    Alert error = new Alert(Alert.AlertType.ERROR, jsonObject.get("message").getAsString(), ButtonType.OK);
                    error.show();
                } else {
                    Runner.getInstance().back();
                    new Alert(Alert.AlertType.INFORMATION, "new supporter registered successfully!", ButtonType.OK).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



    private String isEmpty() {
        if (usernameField.getText().equals(""))
            return "username";
        if (passwordField.getText().equals(""))
            return "password";
        if (firstNameField.getText().equals(""))
            return "firstName";
        if (lastNameField.getText().equals(""))
            return "lastName";
        if (emailField.getText().equals(""))
            return "email address";
        if (phoneField.getText().equals(""))
            return "phone number";
        return "none";
    }


}
