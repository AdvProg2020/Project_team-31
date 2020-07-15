package GraphicalView;

//import Controller.CustomerController;

import Model.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomerBuyingHistory implements Initializable {
    public Button logout;
    public Button login;
    public GridPane gridPane;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();
    TableView<BuyingLogShow> table = new TableView<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadBuyingHistory();
        logoutAlert();
        loginAlert();
    }

    private void loadBuyingHistory() {
        TableColumn<BuyingLogShow, String> idColumn = new TableColumn<>("id");
        idColumn.setMinWidth(150);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<BuyingLogShow, String> priceColumn = new TableColumn<>("total price");
        priceColumn.setMinWidth(150);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        TableColumn<BuyingLogShow, String> discountColumn = new TableColumn<>("discount amount");
        discountColumn.setMinWidth(150);
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));

        TableColumn<BuyingLogShow, String> dateColumn = new TableColumn<>("date");
        dateColumn.setMinWidth(150);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<BuyingLogShow, Button> buttonColumn = new TableColumn<>("show");
        buttonColumn.setMinWidth(150);
        buttonColumn.setCellValueFactory(new PropertyValueFactory<>("button"));

        table.setItems(logsOfUser());
        table.getColumns().addAll(idColumn, priceColumn, discountColumn, dateColumn, buttonColumn);
        gridPane.getChildren().add(table);
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

    private ObservableList<BuyingLogShow> logsOfUser() {
//        ArrayList<BuyingLog> logs = CustomerController.getInstance().showAllOrdersByList(dataBase.user);
        ArrayList<BuyingLog> logs = getAllOrders(dataBase.user);
        ObservableList<BuyingLogShow> showingLogs = FXCollections.observableArrayList();
        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy hh:mm");
        for (BuyingLog log : logs) {
            String date = format.format(log.getDate());
            String price = String.valueOf(log.getTotalPrice());
            String discount = String.valueOf(log.getDiscountAmount());
            showingLogs.add(new BuyingLogShow(log.getLogId(), price, discount, date, log.getBuyingProducts()));
        }
        return showingLogs;
    }

    private ArrayList<BuyingLog> getAllOrders(User user) {
        try {
            JsonObject jsonObject = runner.jsonMaker("customer", "showAllOrdersByList");
            dataBase.dataOutputStream.writeUTF(jsonObject.toString());
            dataBase.dataOutputStream.flush();
            return new Gson().fromJson(runner.jsonParser(dataBase.dataInputStream.readUTF()).get("data").getAsString(), ArrayList.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void userArea(MouseEvent mouseEvent) {
        Runner.getInstance().setUserAreaScene();
    }
}