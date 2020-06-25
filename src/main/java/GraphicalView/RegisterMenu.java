package GraphicalView;

import Controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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
    }

    public void back(MouseEvent mouseEvent) {
        DataBase.getInstance().isAddingManager = false;
        Runner.getInstance().back();
    }

    public void registerRequest(MouseEvent mouseEvent) {
        if (!isEmpty().equals("none")) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please enter " + isEmpty(), ButtonType.OK);
            error.show();
        } else {
            if (role.getValue().equals("manager") && LoginController.getInstance().isThereAnyManager() && !isAddingManager) {
                Alert error = new Alert(Alert.AlertType.ERROR, "One manager registered before", ButtonType.OK);
                error.show();
            } else if (!LoginController.getInstance().isUsernameFree(usernameField.getText())) {
                Alert error = new Alert(Alert.AlertType.ERROR, "This username is token before", ButtonType.OK);
                error.show();
            } else {
                String information[] = new String[6];
                information[0] = firstNameField.getText();
                information[1] = lastNameField.getText();
                information[2] = emailField.getText();
                information[3] = phoneField.getText();
                information[4] = passwordField.getText();
                information[5] = companyField.getText();
                try {
                    LoginController.getInstance().register(usernameField.getText(), (String) role.getValue(), information);
                    Alert inform = null;
                    if (isAddingManager) {
                        DataBase.getInstance().isAddingManager = false;
                        Runner.getInstance().back();
                        inform = new Alert(Alert.AlertType.INFORMATION, "You have added manager successfully!", ButtonType.OK);
                    } else {
                        Runner.getInstance().back();
                        inform = new Alert(Alert.AlertType.INFORMATION, "You have registered successfully!", ButtonType.OK);
                    }
                    inform.show();

                } catch (Exception e) {
                    e.printStackTrace();
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
