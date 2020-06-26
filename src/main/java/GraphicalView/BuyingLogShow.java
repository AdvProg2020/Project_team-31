package GraphicalView;

import Model.Product;
import Model.ProductInCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class BuyingLogShow {
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