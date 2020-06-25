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

import javax.xml.crypto.Data;
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
    SellerController controller = SellerController.getInstance();

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
    static Runner runner = Runner.getInstance();

    public ProductViewForSellerInGUI(Product product, Button view, Button edit, Button delete) {
        this.product = product;
        this.productId = product.getProductId();
        this.view = view;
        this.edit = edit;
        this.delete = delete;
        view.setOnAction(event -> viewProduct());
        edit.setOnAction(event -> editProduct());
        delete.setOnAction(event -> deleteProduct());
    }

    private void deleteProduct() {
        try {
            if (DataBase.getInstance().user instanceof Seller)
                SellerController.getInstance().removeProductFromUser(DataBase.getInstance().user, productId);
            else {
                SellerController.getInstance().removeProduct(productId);
            }
            runner.back();
            runner.changeScene("ManageProducts.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editProduct() {
        ProductsMenu.product = product;
        runner.changeScene("EditProduct.fxml");
    }

    private void viewProduct() {
        ProductsMenu.product = product;
        runner.changeScene("ProductArea.fxml");
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
