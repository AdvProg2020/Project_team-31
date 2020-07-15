package GraphicalView;

import Controller.LoginController;
import Model.User;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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
            try {
                DataBase.getInstance().user = LoginController.getInstance().login(usernameField.getText(), passwordField.getText(), DataBase.getInstance().tempUser.getCard());
                DataBase.getInstance().role = getRole(); //kaka added this field
                Runner.getInstance().back();
                Alert error = new Alert(Alert.AlertType.INFORMATION, "you have login successfully", ButtonType.OK);
                error.show();
            } catch (Exception e) {
                Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                error.show();
            }
        }
    }

    private String getRole() {
        // TODO
        return null;
    }

    public void register(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Runner.getInstance().changeScene("RegisterMenu.fxml");
    }
}
