package GraphicalView;

import Controller.CustomerController;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BuyingHistory implements Initializable {
    public Label buyingHistory;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();
    CustomerController customerController = CustomerController.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadBuyingHistory();

    }

    private void loadBuyingHistory() {
        ArrayList<String> logs = customerController.showAllOrders(dataBase.user);
        StringBuilder data = new StringBuilder();
        for (String log : logs) {
            data.append(log).append("\n");
        }
        buyingHistory.setText(data.toString());
    }

    public void back(ActionEvent actionEvent) {
        runner.back();
    }


}
