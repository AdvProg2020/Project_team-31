package GraphicalView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterMenu implements Initializable {
    public ChoiceBox role;
    public TextField usernameField;
    public TextField passwordField;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField emailField;
    public TextField phoneField;
    public TextField companyField;
    private boolean isAddingManager;
    private boolean isThereAnyManager;

    public void changeRole(ActionEvent actionEvent) {
        if (role.getValue().equals("seller"))
            companyField.setDisable(false);
        else
            companyField.setDisable(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isAddingManager = DataBase.getInstance().isAddingManager;
        role.setValue("customer");
        if (isAddingManager) {
            role.setValue("manager");
            role.setDisable(true);
        }
        companyField.setDisable(true);
        isThereAnyManager = isThereAnyManager();
        firstManager();
    }

    private void firstManager() {
        if (!isThereAnyManager) {
            role.setValue("manager");
            role.setDisable(true);
        }
    }

    private boolean isThereAnyManager() {
        try {
            DataBase.getInstance().dataOutputStream.writeUTF(Runner.getInstance().jsonMaker("login", "isThereAnyManager").toString());
            DataBase.getInstance().dataOutputStream.flush();
            return Runner.getInstance().jsonParser(DataBase.getInstance().dataInputStream.readUTF()).get("managerStatus").getAsBoolean();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void back(MouseEvent mouseEvent) {
        Runner.buttonSound();
        if (isThereAnyManager) {
            DataBase.getInstance().isAddingManager = false;
            Runner.getInstance().back();
        }
    }

    public void registerRequest(MouseEvent mouseEvent) {
        Runner.buttonSound();
        if (!isEmpty().equals("none")) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please enter " + isEmpty(), ButtonType.OK);
            error.show();
        } else {
            if (role.getValue().equals("manager") && isThereAnyManager && !isAddingManager) {
                Alert error = new Alert(Alert.AlertType.ERROR, "One manager registered before", ButtonType.OK);
                error.show();
            } else {
                JsonObject output = Runner.getInstance().jsonMaker("login", "register");
                output.addProperty("username", usernameField.getText());
                output.addProperty("role", (String) role.getValue());
                output.addProperty("firstName", firstNameField.getText());
                output.addProperty("lastName", lastNameField.getText());
                output.addProperty("email", emailField.getText());
                output.addProperty("phone", phoneField.getText());
                output.addProperty("password", passwordField.getText());
                output.addProperty("company", companyField.getText());
                JsonObject jsonObject = null;
                try {
                    DataBase.getInstance().dataOutputStream.writeUTF(output.toString());
                    DataBase.getInstance().dataOutputStream.flush();
                    String input = DataBase.getInstance().dataInputStream.readUTF();
                    jsonObject = (JsonObject) new JsonParser().parse(input);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(jsonObject.get("type").getAsString().equals("failed")) {
                    Alert error = new Alert(Alert.AlertType.ERROR, jsonObject.get("message").getAsString(), ButtonType.OK);
                    error.show();
                } else {
                    Alert inform;
                    if (isAddingManager) {
                        DataBase.getInstance().isAddingManager = false;
                        Runner.getInstance().back();
                        inform = new Alert(Alert.AlertType.INFORMATION, "You have added manager successfully!", ButtonType.OK);
                    } else {
                        Runner.getInstance().back();
                        inform = new Alert(Alert.AlertType.INFORMATION, "You have registered successfully!", ButtonType.OK);
                    }
                    inform.show();
                }
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
        if (role.getValue().equals("seller") && companyField.getText().equals(""))
            return "company";
        return "none";
    }
}
