package GraphicalView;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
        try {
            dataBase.dataOutputStream.writeUTF(runner.jsonMaker("manager" , "getAllDiscount").toString());
            dataBase.dataOutputStream.flush();
            String input = dataBase.dataInputStream.readUTF();
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(input);
            addChart(jsonObject.getAsJsonArray("discounts"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addChart(JsonArray discounts) {
        TableColumn<DiscountCodeViewOnGUI, String> code = new TableColumn<>("code");
        code.setMinWidth(50);
        code.setCellValueFactory(new PropertyValueFactory<>("code"));

        TableColumn<DiscountCodeViewOnGUI, String> beginTime = new TableColumn<>("begin time");
        beginTime.setMinWidth(50);
        beginTime.setCellValueFactory(new PropertyValueFactory<>("beginTime"));

        TableColumn<DiscountCodeViewOnGUI, String> endTime = new TableColumn<>("end time");
        endTime.setMinWidth(50);
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        TableColumn<DiscountCodeViewOnGUI, String> discountPercent = new TableColumn<>("discount percent");
        discountPercent.setMinWidth(50);
        discountPercent.setCellValueFactory(new PropertyValueFactory<>("discountPercent"));

        TableColumn<DiscountCodeViewOnGUI, String> maximumDiscount = new TableColumn<>("maximum discount");
        maximumDiscount.setMinWidth(50);
        maximumDiscount.setCellValueFactory(new PropertyValueFactory<>("maximumDiscount"));

        TableColumn<DiscountCodeViewOnGUI, Button> showCustomers = new TableColumn<>("show customers");
        showCustomers.setMinWidth(50);
        showCustomers.setCellValueFactory(new PropertyValueFactory<>("showCustomers"));

        TableColumn<DiscountCodeViewOnGUI, Button> edit = new TableColumn<>("edit");
        edit.setMinWidth(50);
        edit.setCellValueFactory(new PropertyValueFactory<>("edit"));

        TableView tableView = new TableView();
        tableView.getColumns().addAll(code, beginTime, endTime, discountPercent, maximumDiscount, showCustomers, edit);
        tableView.setItems(discountCodesLoader(discounts));
        gridPane.getChildren().add(tableView);
    }

    private ObservableList<DiscountCodeViewOnGUI> discountCodesLoader(JsonArray discounts) {
        ObservableList<DiscountCodeViewOnGUI> list = FXCollections.observableArrayList();
        for (JsonElement element : discounts) {
            JsonObject discount = element.getAsJsonObject();
            HashMap<String , Integer> customers = new HashMap<>();
            for (JsonElement jsonElement : discount.getAsJsonArray("customers")) {
                JsonObject customer =jsonElement.getAsJsonObject();
                customers.put(customer.get("username").getAsString() , customer.get("number").getAsInt());
            }
            list.add(new DiscountCodeViewOnGUI(discount.get("code").getAsString()  ,discount.get("beginTime").getAsString() , discount.get("endTime").getAsString() , discount.get("percent").getAsInt() ,discount.get("maximum").getAsInt() ,customers ));
        }
        return list;
    }

    private void logoutAlert() {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        EventHandler<ActionEvent> event = (e) -> {
            Runner.buttonSound();
            message.setContentText("you logged out successfully");
            message.show();
            dataBase.logout();
        };
        logout.setOnAction(event);
    }

    public void back(ActionEvent actionEvent) {
        Runner.buttonSound();
        runner.back();
    }

    public void loginAlert() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        EventHandler<ActionEvent> event = (e) -> {
            Runner.buttonSound();
            if (dataBase.user != null) {
                error.setContentText("You have logged in!");
                error.show();
            } else {
                runner.changeScene("LoginMenu.fxml");
            }
        };
        login.setOnAction(event);
    }

    public void userArea(MouseEvent mouseEvent) {
        Runner.getInstance().setUserAreaScene();
    }
}
