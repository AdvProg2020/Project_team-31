package GraphicalView;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
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

    public void changeRole(ActionEvent actionEvent) {
        if(role.getValue().equals("seller"))
            companyField.setDisable(false);
        else
            companyField.setDisable(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        role.setValue("customer");
        companyField.setDisable(true);
    }

    public void back(MouseEvent mouseEvent) {
        Runner.getInstance().back();
    }

    public void registerRequest(MouseEvent mouseEvent) {

    }
}
