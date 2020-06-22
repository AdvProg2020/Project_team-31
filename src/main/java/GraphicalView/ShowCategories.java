package GraphicalView;

import Controller.ManagerController;
import Model.Category;
import com.sun.javafx.css.converters.StringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShowCategories implements Initializable {
    public Button logout;
    public Button login;
    public GridPane gridPane;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showCategories();
        loginAlert();
        logoutAlert();
    }

    private void showCategories() {
        TableColumn<BuyingLogShow, String> name = new TableColumn<>("name");
        name.setMinWidth(150);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<BuyingLogShow, String> properties = new TableColumn<>("properties");
        properties.setMinWidth(350);
        properties.setCellValueFactory(new PropertyValueFactory<>("specialProperties"));

        TableView tableView = new TableView();
        ManagerController managerController = ManagerController.getInstance();
        ObservableList<Category> categories = FXCollections.observableArrayList(managerController.showAllCategoriesByList());
        tableView.getColumns().addAll(name, properties);
        tableView.setItems(categories);
        gridPane.getChildren().add(tableView);
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
