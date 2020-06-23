package GraphicalView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerAddOff implements Initializable {
    public Button login;
    public Button logout;
    public DatePicker startDate;
    public DatePicker endDate;
    public TextField percentage;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoutAlert();
        loginAlert();
    }

    public void back(ActionEvent actionEvent) {
        runner.back();
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

    private void logoutAlert() {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        EventHandler<ActionEvent> event = (e) -> {
            message.setContentText("you logged out successfully");
            message.show();
            dataBase.logout();
        };
        logout.setOnAction(event);
    }

    public void submit(ActionEvent actionEvent) {

    }

    public void addProduct(ActionEvent actionEvent) {

    }
}
