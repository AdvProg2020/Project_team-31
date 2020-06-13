package GraphicalView;

import Controller.CustomerController;
import Model.BuyingLog;
import Model.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomerBuyingHistory implements Initializable {
    public Label buyingHistory;
    public Button logout;
    public Button login;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();
    CustomerController customerController = CustomerController.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadBuyingHistory();
        logoutAlert();
        loginAlert();
    }

    private void loadBuyingHistory() {
        ArrayList<String> logs = customerController.showAllOrders(dataBase.user);
        StringBuilder data = new StringBuilder("your buying history : ");
        for (String log : logs) {
            data.append(log).append("\n");
        }
        buyingHistory.setText(data.toString());
    }

    public void back(ActionEvent actionEvent) {
        runner.back();
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

}
