package GraphicalView;

import Controller.ManagerController;
import Controller.ProductController;
import Model.Category;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProductsMenu implements Initializable {
    public Button showButton;
    public ChoiceBox sorting;
    public ChoiceBox category;
    public TableView tableOfProducts;
    public TableColumn rateColumn;
    public TableColumn nameColumn;
    public TableColumn viewColumn;
    public TableColumn priceColumn;

    public void showProducts(MouseEvent mouseEvent) {
        System.out.println(sorting.getValue());
        System.out.println(category.getValue());
        if (category.getValue().equals("Save"))
            System.out.println("equal");
    }

    public void changeSort(ActionEvent actionEvent) {
        System.out.println("hello");
    }

    public void setCategories() {
        ArrayList<String> features = new ArrayList<String>();
        features.add("rom");
        ManagerController.getInstance().addCategory("mobile", features);
        ArrayList
                <String> listOfCategories = (ArrayList<String>) Category.getAllCategories().stream()
                .map(e -> e.getName())
                .collect(Collectors.toList());
        listOfCategories.add(0, "all");
        category.setItems(FXCollections.observableList(listOfCategories));
        category.setValue("all");
    }

    public void changeCategory(ActionEvent actionEvent) {
        System.out.println(category.getValue());
        tableOfProducts.setItems(getProducts((String) category.getValue()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCategories();
        addButtonToTable();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        viewColumn.setCellValueFactory(new PropertyValueFactory<>("views"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("minimumPrice"));
        rateColumn.setCellValueFactory(new PropertyValueFactory<>("rate"));
        tableOfProducts.setItems(getProducts("all"));
    }

    private void addButtonToTable() {
        TableColumn<Product, Void> colBtn = new TableColumn();
        Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {
                    private final Button btn = new Button("view");
                    {
                        btn.setMinWidth(75);
                        btn.setOnAction((ActionEvent event) -> {
                            Product product = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + product.getName());
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
        tableOfProducts.getColumns().add(colBtn);
    }

    private ObservableList<Product> getProducts(String categoryName) {
        ObservableList<Product> products = FXCollections.observableArrayList();
        products.addAll(ProductController.getInstance().showProductInGui(DataBase.getInstance().user, categoryName));
        products.add(new Product("a", "s10", "samsung", ManagerController.getCategoryByName("mobile"), "good", 10, null, null));
        products.add(new Product("a", "a10", "samsung", ManagerController.getCategoryByName("mobile"), "good", 10, null, null));
        return products;
    }

    public void back(MouseEvent mouseEvent) {
        Runner.getInstance().back();
    }
}
