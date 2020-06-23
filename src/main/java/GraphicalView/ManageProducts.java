package GraphicalView;

import Controller.ManagerController;
import Controller.SellerController;
import Model.Category;
import Model.Product;
import Model.Seller;
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

public class ManageProducts implements Initializable {
    public Button login;
    public Button logout;
    public GridPane gridPane;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();
    SellerController controller = SellerController.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginAlert();
        logoutAlert();
        AddChart();
    }

    private void AddChart() {
//        TableColumn<BuyingLogShow, String> name = new TableColumn<>("name");
//        name.setMinWidth(150);
//        name.setCellValueFactory(new PropertyValueFactory<>("name"));
//
//        TableColumn<BuyingLogShow, String> properties = new TableColumn<>("properties");
//        properties.setMinWidth(350);
//        properties.setCellValueFactory(new PropertyValueFactory<>("specialProperties"));
//
//        TableView tableView = new TableView();
//        ManagerController managerController = ManagerController.getInstance();
//        ObservableList<Category> categories = FXCollections.observableArrayList(managerController.showAllCategoriesByList());
//        tableView.getColumns().addAll(name, properties);
//        tableView.setItems(categories);
//        gridPane.getChildren().add(tableView);
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

    private void logoutAlert() {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        EventHandler<ActionEvent> event = (e) -> {
            message.setContentText("you logged out successfully");
            message.show();
            dataBase.logout();
        };
        logout.setOnAction(event);
    }

    private ObservableList<ProductViewForSellerInGUI> getProductInList() {
        ObservableList<ProductViewForSellerInGUI> list = FXCollections.observableArrayList();
        ArrayList<Product> allProducts = controller.showProductsOfThisSellerForGUI(dataBase.user);
        for (Product product : allProducts)
            list.add(new ProductViewForSellerInGUI(product, new Button("view"), new Button("edit"), new Button("delete")));
        return list;
    }
}

class ProductViewForSellerInGUI {
    Product product;
    String productId;
    Button view;
    Button edit;
    Button delete;

    public ProductViewForSellerInGUI(Product product, Button view, Button edit, Button delete) {
        this.product = product;
        this.productId = product.getProductId();
        this.view = view;
        this.edit = edit;
        this.delete = delete;
    }

    public Product getProduct() {
        return product;
    }

    public String getProductId() {
        return productId;
    }

    public Button getView() {
        return view;
    }

    public Button getEdit() {
        return edit;
    }

    public Button getDelete() {
        return delete;
    }
}
