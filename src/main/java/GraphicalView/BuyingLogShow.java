package GraphicalView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.ArrayList;

public class BuyingLogShow {
    String totalPrice;
    String discount;
    String date;
    Button button;
    String id;
    ArrayList<ProductShowInLog> allProducts;

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

    public Button getButton() {
        return button;
    }

    public BuyingLogShow(String id, String totalPrice, String discount, String date, ArrayList<ProductShowInLog> allProducts) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.date = date;
        this.allProducts = allProducts;
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
        ObservableList<ProductShowInLog> data = FXCollections.observableArrayList();
        data.addAll(allProducts);
        table.setItems(data);
        return table;
    }
}