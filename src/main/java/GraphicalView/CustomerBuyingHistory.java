package GraphicalView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
        idColumn.setMinWidth(200);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<BuyingLogShow, String> priceColumn = new TableColumn<>("total price");
        priceColumn.setMinWidth(75);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        TableColumn<BuyingLogShow, String> discountColumn = new TableColumn<>("discount amount");
        discountColumn.setMinWidth(75);
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));

        TableColumn<BuyingLogShow, String> dateColumn = new TableColumn<>("date");
        dateColumn.setMinWidth(200);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<BuyingLogShow, String> statusColumn = new TableColumn<>("status");
        statusColumn.setMinWidth(100);
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<BuyingLogShow, Button> buttonColumn = new TableColumn<>("show");
        buttonColumn.setMinWidth(150);
        buttonColumn.setCellValueFactory(new PropertyValueFactory<>("button"));

        table.setItems(getAllOrders());
        table.getColumns().addAll(idColumn, priceColumn, discountColumn, dateColumn, statusColumn, buttonColumn);
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
            if (!DataBase.getInstance().role.equals("none")) {
                error.setContentText("You have logged in!");
                error.show();
            } else {
                runner.changeScene("LoginMenu.fxml");
            }
        };
        login.setOnAction(event);
    }


    private ObservableList<BuyingLogShow> getAllOrders() {
        JsonArray data = null;
        try {
            JsonObject jsonObject = runner.jsonMaker("customer", "showAllOrdersByList");
            dataBase.dataOutputStream.writeUTF(jsonObject.toString());
            dataBase.dataOutputStream.flush();
            data = runner.jsonParser(dataBase.dataInputStream.readUTF()).getAsJsonArray("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObservableList<BuyingLogShow> showingLogs = FXCollections.observableArrayList();
        for (JsonElement element : data) {
            JsonObject log = element.getAsJsonObject();
            ArrayList<ProductShowInLog> products = new ArrayList<>();
            for (JsonElement jsonElement : log.getAsJsonArray("products")) {
                JsonObject product = jsonElement.getAsJsonObject();
                products.add(new ProductShowInLog(product.get("name").getAsString(), String.valueOf(product.get("number").getAsInt()), product.get("seller").getAsString()));
            }
            showingLogs.add(new BuyingLogShow(log.get("id").getAsString(), String.valueOf(log.get("totalPrice").getAsInt()), String.valueOf(log.get("discount").getAsInt()), log.get("date").getAsString(), log.get("status").getAsString(), products));
        }
        return showingLogs;
    }

    public void userArea(MouseEvent mouseEvent) {
        Runner.getInstance().setUserAreaScene();
    }
}