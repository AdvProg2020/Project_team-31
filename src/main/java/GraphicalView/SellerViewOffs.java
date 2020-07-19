package GraphicalView;

import Model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class SellerViewOffs implements Initializable {
    public Button logout;
    public Button login;
    public GridPane gridPane;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginAlert();
        logoutAlert();
        showOffs();
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

    private void showOffs() {
        TableColumn<ShowOffsOnGUI, String> id = new TableColumn<>("off ID");
        id.setMinWidth(150);
        id.setCellValueFactory(new PropertyValueFactory<>("offId"));

        TableColumn<ShowOffsOnGUI, Button> showButton = new TableColumn<>("show");
        showButton.setMinWidth(150);
        showButton.setCellValueFactory(new PropertyValueFactory<>("showButton"));

        TableColumn<ShowOffsOnGUI, Button> editButton = new TableColumn<>("edit");
        editButton.setMinWidth(150);
        editButton.setCellValueFactory(new PropertyValueFactory<>("editButton"));

        TableView tableView = new TableView();

        ObservableList<ShowOffsOnGUI> list = generateOffsList();
        tableView.getColumns().addAll(id, showButton, editButton);
        tableView.setItems(list);
        gridPane.getChildren().add(tableView);

    }

    private ObservableList generateOffsList() {
        ObservableList<ShowOffsOnGUI> list = FXCollections.observableArrayList();
        ArrayList<String> allOffs = showAllOffsForGUI(dataBase.user);
        for (String offId : allOffs)
            list.add(new ShowOffsOnGUI(new Button("show"), new Button("edit"), offId));
        return list;
    }

    private ArrayList<String> showAllOffsForGUI(User user) {
        try {
            JsonObject jsonObject = runner.jsonMaker("seller", "getAllOffs");
            dataBase.dataOutputStream.writeUTF(jsonObject.toString());
            dataBase.dataOutputStream.flush();
            JsonObject serverMassage = runner.jsonParser(dataBase.dataInputStream.readUTF());
            String[] data = new Gson().fromJson(serverMassage, String[].class);
            return new ArrayList<>(Arrays.asList(data));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void userArea(MouseEvent mouseEvent) {
        runner.setUserAreaScene();
    }
}
