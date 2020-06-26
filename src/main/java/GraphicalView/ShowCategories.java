package GraphicalView;

import Controller.ManagerController;
import Model.Category;
import Model.Manager;
import Model.Product;
import com.sun.javafx.css.converters.StringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShowCategories implements Initializable {
    public Button logout;
    public Button login;
    public GridPane gridPane;
    static Category categoryToEdit;
    public Button addCategory;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showCategories();
        addCategory.setDisable(!(dataBase.user instanceof Manager));
        addCategory.setOnMouseClicked(e -> runner.changeScene("AddCategory.fxml"));
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
        addButtonToTable(tableView);
        tableView.setItems(categories);
        gridPane.getChildren().add(tableView);
    }

    private void addButtonToTable(TableView tableView) {
        TableColumn<Category, Void> colBtn = new TableColumn();
        Callback<TableColumn<Category, Void>, TableCell<Category, Void>> cellFactory = new Callback<TableColumn<Category, Void>, TableCell<Category, Void>>() {
            @Override
            public TableCell<Category, Void> call(final TableColumn<Category, Void> param) {
                final TableCell<Category, Void> cell = new TableCell<Category, Void>() {
                    private final Button btn = new Button("edit");

                    {
                        btn.setMinWidth(75);
                        btn.setDisable(!(dataBase.user instanceof Manager));
                        btn.setOnAction((ActionEvent event) -> {
                            categoryToEdit = getTableView().getItems().get(getIndex());
                            runner.changeScene("EditCategory.fxml");
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        tableView.getColumns().add(colBtn);
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
}
