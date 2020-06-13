package GraphicalView;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterMenu implements Initializable {
    public ChoiceBox role;

    public void changeRole(ActionEvent actionEvent) {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        role.setValue("customer");

    }
}
