package GraphicalView;

import Controller.ProductController;
import Model.Product;

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
import java.util.ResourceBundle;

public class ManageAllProducts implements Initializable {
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

        TableColumn<BuyingLogShow, Button> delete = new TableColumn<>("delete");
        delete.setMinWidth(100);
        delete.setCellValueFactory(new PropertyValueFactory<>("delete"));

        TableView tableView = new TableView();
        tableView.getColumns().addAll(productId, view, delete);
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

    private ObservableList<GraphicalView.ProductViewForSellerInGUI> getProductInList() {
        ObservableList<GraphicalView.ProductViewForSellerInGUI> list = FXCollections.observableArrayList();
        ArrayList<Product> allProducts = ProductController.getInstance().showProductInGui(dataBase.user, "all");
        for (Product product : allProducts)
            list.add(new GraphicalView.ProductViewForSellerInGUI(product, new Button("view"), new Button("edit"), new Button("delete")));
        return list;
    }
}