package GraphicalView;

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
import java.util.ResourceBundle;

public class SellerHistory implements Initializable {
    public Button login;
    public Button logout;
    public GridPane gridPane;

    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginAlert();
        logoutAlert();
        showLogs();
    }

    private void showLogs() {
        TableColumn<BuyingLogShow, String> price = new TableColumn<>("total price arrived");
        price.setMinWidth(150);
        price.setCellValueFactory(new PropertyValueFactory<>("totalPriceArrived"));

        TableColumn<BuyingLogShow, String> off = new TableColumn<>("amount of off");
        off.setMinWidth(150);
        off.setCellValueFactory(new PropertyValueFactory<>("amountOfOff"));

        TableColumn<BuyingLogShow, String> product = new TableColumn<>("Product");
        product.setMinWidth(150);
        product.setCellValueFactory(new PropertyValueFactory<>("productName"));

        TableView tableView = new TableView();
        tableView.setItems(logsOfUser());
        tableView.getColumns().addAll(price, off, product);
        gridPane.getChildren().add(tableView);
    }

    private ObservableList<ProductInSellingLog> logsOfUser() {
        JsonObject output = Runner.getInstance().jsonMaker("seller", "showSalesHistory");
        JsonObject jsonObject = null;
        try {
            DataBase.getInstance().dataOutputStream.writeUTF(output.toString());
            DataBase.getInstance().dataOutputStream.flush();
            String input = DataBase.getInstance().dataInputStream.readUTF();
            jsonObject = (JsonObject) new JsonParser().parse(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObservableList<ProductInSellingLog> newLogs = FXCollections.observableArrayList();
        for (JsonElement jsonElement : jsonObject.getAsJsonArray("logs")) {
            JsonObject log = jsonElement.getAsJsonObject();
            newLogs.add(new ProductInSellingLog(log.get("total").getAsInt(), log.get("off").getAsInt(), log.get("product").getAsString()));
        }
        return newLogs;
    }

    public void back(ActionEvent actionEvent) {
        Runner.buttonSound();
        runner.back();
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
