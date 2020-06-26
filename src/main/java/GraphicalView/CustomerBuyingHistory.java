package GraphicalView;

import Controller.CustomerController;
import Model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.Pair;

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
        idColumn.setMinWidth(150);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<BuyingLogShow, String> priceColumn = new TableColumn<>("total price");
        priceColumn.setMinWidth(150);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        TableColumn<BuyingLogShow, String> discountColumn = new TableColumn<>("discount amount");
        discountColumn.setMinWidth(150);
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));

        TableColumn<BuyingLogShow, String> dateColumn = new TableColumn<>("date");
        dateColumn.setMinWidth(150);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<BuyingLogShow, Button> buttonColumn = new TableColumn<>("show");
        buttonColumn.setMinWidth(150);
        buttonColumn.setCellValueFactory(new PropertyValueFactory<>("button"));

        table.setItems(logsOfUser());
        table.getColumns().addAll(idColumn, priceColumn, discountColumn, dateColumn, buttonColumn);
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
            if (dataBase.user != null) {
                error.setContentText("You have logged in!");
                error.show();
            } else {
                runner.changeScene("LoginMenu.fxml");
            }
        };
        login.setOnAction(event);
    }

    private ObservableList<BuyingLogShow> logsOfUser() {
        ArrayList<BuyingLog> logs = CustomerController.getInstance().showAllOrdersByList(dataBase.user);
        ObservableList<BuyingLogShow> showingLogs = FXCollections.observableArrayList();
        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy hh:mm");
        for (BuyingLog log : logs) {
            String date = format.format(log.getDate());
            String price = String.valueOf(log.getTotalPrice());
            String discount = String.valueOf(log.getDiscountAmount());
            showingLogs.add(new BuyingLogShow(log.getLogId(), price, discount, date, log.getBuyingProducts()));
        }
        return showingLogs;
    }
}

class BuyingLogShow {
    String totalPrice;
    String discount;
    String date;
    Button button;
    String id;
    HashMap<Product, ProductInCard> buyingProducts;

    public String getId() {
        return id;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public String getDate() {
        return date;
    }

    public HashMap<Product, ProductInCard> getBuyingProducts() {
        return buyingProducts;
    }

    public Button getButton() {
        return button;
    }

    public BuyingLogShow(String id, String totalPrice, String discount, String date, HashMap<Product, ProductInCard> buyingProducts) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.date = date;
        this.buyingProducts = buyingProducts;
        button = new Button("show");
        button.setOnAction(event -> {
            Runner.buttonSound();
            this.showProducts();
        });
    }

    private void showProducts() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("products");
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(loginButtonType);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));
        gridPane.getChildren().add(createTable());
        dialog.getDialogPane().setContent(gridPane);
        dialog.showAndWait();
    }

    private TableView createTable() {
        TableView<ProductShowInLog> table = new TableView<>();

        TableColumn<ProductShowInLog, String> nameColumn = new TableColumn<>("product name");
        nameColumn.setMinWidth(150);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));

        TableColumn<ProductShowInLog, String> numberColumn = new TableColumn<>("number");
        numberColumn.setMinWidth(150);
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<ProductShowInLog, String> sellerColumn = new TableColumn<>("seller");
        sellerColumn.setMinWidth(150);
        sellerColumn.setCellValueFactory(new PropertyValueFactory<>("seller"));
        table.getColumns().addAll(nameColumn, numberColumn, sellerColumn);
        table.setItems(getProducts());
        return table;
    }

    private ObservableList<ProductShowInLog> getProducts() {
        ObservableList<ProductShowInLog> data = FXCollections.observableArrayList();
        for (Map.Entry<Product, ProductInCard> entry : buyingProducts.entrySet()) {
            data.add(new ProductShowInLog(entry.getKey().getName(), String.valueOf(entry.getValue().getNumber()), entry.getValue().getSeller().getCompanyName()));
        }
        return data;
    }
}

class ProductShowInLog {
    String productName;
    String number;

    public String getProductName() {
        return productName;
    }

    public String getNumber() {
        return number;
    }

    public String getSeller() {
        return seller;
    }

    String seller;

    public ProductShowInLog(String productName, String number, String seller) {
        this.productName = productName;
        this.number = number;
        this.seller = seller;
    }
}