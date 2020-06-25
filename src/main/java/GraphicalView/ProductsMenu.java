package GraphicalView;

import Controller.ManagerController;
import Controller.ProductController;
import Model.Category;
import Model.Product;
import Model.Seller;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProductsMenu implements Initializable {
    public ChoiceBox category;
    public TableView tableOfProducts;
    public TableColumn rateColumn;
    public TableColumn nameColumn;
    public TableColumn viewColumn;
    public TableColumn priceColumn;
    public static String categoryName;
    private static TableView tempTable;
    public static Stage filterStageToSave;
    public TableColumn imageColumn;
    public static Product product;

    public void setCategories() {
        ArrayList<String> features = new ArrayList<>();
        features.add("rom");
        ManagerController.getInstance().addCategory("mobile", features);
        ArrayList<String> listOfCategories = (ArrayList<String>) Category.getAllCategories().stream()
                .map(e -> e.getName())
                .collect(Collectors.toList());
        listOfCategories.add(0, "all");
        ObservableList categories = FXCollections.observableArrayList(listOfCategories);
        category.setItems(categories);
        category.setValue("all");
        categoryName = "all";
    }

    public void changeCategory(ActionEvent actionEvent) {
        tableOfProducts.setItems(getProducts((String) category.getValue()));
        categoryName = (String) category.getValue();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tempTable = tableOfProducts;
        setCategories();
        addButtonToTable();
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imageView"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        viewColumn.setCellValueFactory(new PropertyValueFactory<>("views"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("minimumPrice"));
        rateColumn.setCellValueFactory(new PropertyValueFactory<>("rate"));
        User user = new Seller("mohammadali", "kakavand", "mali", "myemail", "999", "1234","samsung");
        HashMap<Seller, Integer> sellers = new HashMap<>();
        sellers.put((Seller)user, Integer.parseInt("50"));
        new Product(null, "a", "M9", "GLX", ManagerController.getCategoryByName("mobile"), "good", 10,sellers, new HashMap<>()).setMinimumPrice(50);
        new Product(null, "ab", "M10", "GLX", ManagerController.getCategoryByName("mobile"), "good", 10, new HashMap<>(), new HashMap<>());
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
                            product = getTableView().getItems().get(getIndex());
                            showDetail();
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

    private void showDetail() {
        product.addView();
        Runner.getInstance().changeScene("ProductArea.fxml");
    }

    private static ObservableList<Product> getProducts(String categoryName) {
        ObservableList<Product> products = FXCollections.observableArrayList();
        if (DataBase.getInstance().user != null)
            products.addAll(ProductController.getInstance().showProductInGui(DataBase.getInstance().user, categoryName));
        else
            products.addAll(ProductController.getInstance().showProductInGui(DataBase.getInstance().tempUser, categoryName));
        return products;
    }

    public void back(MouseEvent mouseEvent) {
        Runner.getInstance().back();
    }

    public void filterRequest(MouseEvent mouseEvent) {
        Stage filterStage = new Stage();
        URL url = getClass().getClassLoader().getResource("Filters.fxml");
        Parent root = null;
        try {
            root = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("not found");
        }
        filterStage.setScene(new Scene(root));
        filterStage.setTitle("filters");
        filterStageToSave = filterStage;
        filterStage.show();
    }

    public static void filterProducts() {
        tempTable.setItems(getProducts(categoryName));
    }
}
