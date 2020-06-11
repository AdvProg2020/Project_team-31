package GraphicalView;

import Model.Seller;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditPersonalInfo implements Initializable {
    public TextField firstName;
    public TextField lastName;
    public PasswordField password;
    public TextField email;
    public TextField phoneNumber;
    public TextField companyName;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] userData = dataBase.getUserInfo();
        firstName.setText(userData[0]);
        lastName.setText(userData[1]);
        password.setText(userData[2]);
        email.setText(userData[3]);
        phoneNumber.setText(userData[4]);
        if (dataBase.user instanceof Seller)
            companyName.setText(userData[5]);
        else companyName.setText("PLEASE ENTER NOTHING!");

    }

    public void back(ActionEvent actionEvent) {
        runner.back();
    }

    public void submit(ActionEvent actionEvent) {
        // TODO
    }

}
