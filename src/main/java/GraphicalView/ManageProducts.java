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

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ManageProducts implements Initializable {
    public Button login;
    public Button logout;
    public GridPane gridPane;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginAlert();
        logoutAlert();
        AddChart();
    }

    private void AddChart() {
        TableColumn<BuyingLogShow, String> productId = new TableColumn<>("productId");
        productId.setMinWidth(150);
        productId.setCellValueFactory(new PropertyValueFactory<>("productId"));

        TableColumn<BuyingLogShow, Button> view = new TableColumn<>("view");
        view.setMinWidth(100);
        view.setCellValueFactory(new PropertyValueFactory<>("view"));

        TableColumn<BuyingLogShow, Button> edit = new TableColumn<>("edit");
        edit.setMinWidth(100);
        edit.setCellValueFactory(new PropertyValueFactory<>("edit"));

        TableColumn<BuyingLogShow, Button> delete = new TableColumn<>("delete");
        delete.setMinWidth(100);
        delete.setCellValueFactory(new PropertyValueFactory<>("delete"));

        TableView tableView = new TableView();
        tableView.getColumns().addAll(productId, view, edit, delete);
        tableView.setItems(getProductInList());
        gridPane.getChildren().add(tableView);
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

    private ObservableList<ProductViewForSellerInGUI> getProductInList() {
        ObservableList<ProductViewForSellerInGUI> list = FXCollections.observableArrayList();
        try {
            dataBase.dataOutputStream.writeUTF(runner.jsonMaker("manager", "getProductList").toString());
            dataBase.dataOutputStream.flush();
            String input = dataBase.dataInputStream.readUTF();
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(input);
            for (JsonElement element : jsonObject.getAsJsonArray("products")) {
                list.add(new ProductViewForSellerInGUI(element.getAsString(), new Button("view"), new Button("edit"), new Button("delete")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void userArea(MouseEvent mouseEvent) {
        Runner.getInstance().setUserAreaScene();
    }
}
