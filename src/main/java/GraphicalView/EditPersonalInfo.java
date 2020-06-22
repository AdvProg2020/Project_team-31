package GraphicalView;

import Controller.LoginController;
import Controller.SellerController;
import Model.Seller;
import Model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class EditPersonalInfo implements Initializable {
    public TextField firstName;
    public TextField lastName;
    public PasswordField password;
    public TextField email;
    public TextField phoneNumber;
    public TextField companyName;
    public Button logout;
    public Button login;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();
    LoginController loginController = LoginController.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initializeFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logoutAlert();
    }

    private void initializeFields() throws Exception {
        SellerController sellerController = SellerController.getInstance();
        String[] userData = dataBase.getUserInfo();
        firstName.setText(userData[0]);
        lastName.setText(userData[1]);
        email.setText(userData[3]);
        phoneNumber.setText(userData[4]);
        password.setText(userData[5]);
        if (dataBase.user instanceof Seller)
            companyName.setText(sellerController.showCompanyInformation(dataBase.user));
        else companyName.setText("PLEASE ENTER NOTHING!");
    }

    private void logoutAlert() {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        EventHandler<ActionEvent> event = (e) -> {
            message.setContentText("you logged out successfully");
            message.show();
            dataBase.logout();
        };
        logout.setOnAction(event);
    }

    public void back(ActionEvent actionEvent) {
        runner.back();
    }

    public void submit(ActionEvent actionEvent) {
        if (!isEmpty().equals("none")) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please enter " + isEmpty(), ButtonType.OK);
            error.show();
        } else {
            loginController.editPersonalInformation(dataBase.user, createNewInfo());
            back(null);
        }

    }

    private String[] createNewInfo() {
        String[] newData = new String[6];
        newData[0] = firstName.getText();
        newData[1] = lastName.getText();
        newData[2] = email.getText();
        newData[3] = phoneNumber.getText();
        newData[4] = password.getText();
        if (dataBase.user instanceof Seller)
            newData[5] = companyName.getText();
        return newData;
    }

    public void loginAlert() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        EventHandler<ActionEvent> event = (e) -> {
            if (dataBase.user != null) {
                error.setContentText("You have logged in!");
                error.show();
            } else {
                runner.changeScene("LoginMenu.fxml");
            }
        };
        login.setOnAction(event);
    }

    private String isEmpty() {
        if (password.getText().equals(""))
            return "password";
        if (firstName.getText().equals(""))
            return "firstName";
        if (lastName.getText().equals(""))
            return "lastName";
        if (email.getText().equals(""))
            return "email address";
        if (phoneNumber.getText().equals(""))
            return "phone number";
        if (dataBase.user instanceof Seller && companyName.getText().equals(""))
            return "company";
        return "none";
    }
}
