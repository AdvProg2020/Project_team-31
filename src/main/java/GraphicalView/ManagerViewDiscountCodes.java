package GraphicalView;

import Controller.ManagerController;
import Model.Category;
import Model.Customer;
import Model.DiscountCode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ManagerViewDiscountCodes implements Initializable {
    public Button logout;
    public Button login;
    public GridPane gridPane;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginAlert();
        logoutAlert();
        addChart();
    }

    private void addChart() {
        TableColumn<DiscountCodeViewOnGUI, String> code = new TableColumn<>("code");
        code.setMinWidth(150);
        code.setCellValueFactory(new PropertyValueFactory<>("code"));

        TableColumn<DiscountCodeViewOnGUI, String> beginTime = new TableColumn<>("begin time");
        beginTime.setMinWidth(150);
        beginTime.setCellValueFactory(new PropertyValueFactory<>("beginTime"));

        TableColumn<DiscountCodeViewOnGUI, String> endTime = new TableColumn<>("end time");
        endTime.setMinWidth(150);
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        TableColumn<DiscountCodeViewOnGUI, String> discountPercent = new TableColumn<>("discount percent");
        discountPercent.setMinWidth(150);
        discountPercent.setCellValueFactory(new PropertyValueFactory<>("discountPercent"));

        TableColumn<DiscountCodeViewOnGUI, String> maximumDiscount = new TableColumn<>("maximum discount");
        maximumDiscount.setMinWidth(150);
        maximumDiscount.setCellValueFactory(new PropertyValueFactory<>("maximumDiscount"));

        TableColumn<DiscountCodeViewOnGUI, Button> showCustomers = new TableColumn<>("show customers");
        showCustomers.setMinWidth(150);
        showCustomers.setCellValueFactory(new PropertyValueFactory<>("showCustomers"));

        TableColumn<DiscountCodeViewOnGUI, Button> edit = new TableColumn<>("edit");
        edit.setMinWidth(150);
        edit.setCellValueFactory(new PropertyValueFactory<>("edit"));

        TableView tableView = new TableView();
        tableView.getColumns().addAll(code, beginTime, endTime, discountPercent, maximumDiscount, showCustomers, edit);
        tableView.setItems(discountCodesLoader());
        gridPane.getChildren().add(tableView);
    }

    private ObservableList<DiscountCodeViewOnGUI> discountCodesLoader() {
        ArrayList<DiscountCode> codes = ManagerController.getInstance().showAllDiscountCodesForGUI();
        ObservableList<DiscountCodeViewOnGUI> list = FXCollections.observableArrayList();
        for (DiscountCode code : codes)
            list.add(new DiscountCodeViewOnGUI(code));
        return list;
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

class DiscountCodeViewOnGUI {
    DiscountCode discountCode;
    Button showCustomers;
    Button edit;

    public DiscountCodeViewOnGUI(DiscountCode discountCode) {
        this.discountCode = discountCode;
        this.showCustomers = new Button("show customers");
        this.edit = new Button("edit");
        showCustomers.setOnAction(event -> show());
        edit.setOnAction(event -> edit());
    }

    private void edit() {
        DataBase.dataBase.editingDiscountCode = discountCode;
        Runner.runner.changeScene("EditDiscountCode.fxml");
    }

    private void show() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("customers");
        String data = "customers and number that they can use code : \n";
        for (Map.Entry<Customer, Integer> entry : discountCode.getDiscountTimesForEachCustomer().entrySet())
            data += entry.getKey().getUsername() + "--->" + entry.getValue() + "\n";
        alert.setContentText(data);
        alert.showAndWait();
    }

    public String getCode() {
        return discountCode.getDiscountCode();
    }

    public String getBeginTime() {
        return discountCode.getBeginTime().toString();
    }

    public String getEndTime() {
        return discountCode.getEndTime().toString();
    }

    public String getDiscountPercent() {
        return String.valueOf(discountCode.getDiscountPercent());
    }

    public String getMaximumDiscount() {
        return String.valueOf(discountCode.getMaximumDiscount());
    }
}
