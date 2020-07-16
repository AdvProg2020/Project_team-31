package GraphicalView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    public Button login;
    public Button logout;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    static HashMap<String, ArrayList<String>> categories = new HashMap<>();
    static HashMap<String, String> filters = new HashMap<>();
    private static ArrayList<ProductInTable> allProducts = new ArrayList<>();
    static ProductInTable productInTable;

    public void setCategories() {
        ArrayList<String> listOfCategories = (ArrayList<String>) categories.keySet();
        listOfCategories.add(0, "all");
        ObservableList categories = FXCollections.observableArrayList(listOfCategories);
        category.setItems(categories);
        category.setValue("all");
        categoryName = "all";
    }

    public void changeCategory(ActionEvent actionEvent) {
        Runner.buttonSound();
        categoryName = (String) category.getValue();
        tableOfProducts.setItems(getProducts());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginAlert();
        logoutAlert();
        Runner.getInstance().changeMusic("ProductMenu");
        tempTable = tableOfProducts;
        dataInputStream = DataBase.getInstance().dataInputStream;
        dataOutputStream = DataBase.getInstance().dataOutputStream;
        String input = null;
        try {
            dataOutputStream.writeUTF(Runner.getInstance().jsonMaker("product", "showAllProducts").toString());
            dataOutputStream.flush();
            input = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        analyzeInput(input);
        setCategories();
        addButtonToTable();
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imageViewSmall"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        viewColumn.setCellValueFactory(new PropertyValueFactory<>("views"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("minimumPrice"));
        rateColumn.setCellValueFactory(new PropertyValueFactory<>("rate"));
        tableOfProducts.setItems(getProducts());
    }

    private void analyzeInput(String input) {
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(input);
        for (JsonElement jsonElement : jsonObject.get("categories").getAsJsonArray()) {
            JsonObject aCategory = jsonElement.getAsJsonObject();
            ArrayList<String> features = new ArrayList<>();
            for (JsonElement element : aCategory.get("features").getAsJsonArray()) {
                features.add(element.getAsString());
            }
            categories.put(aCategory.get("name").getAsString(), features);
        }
        for (JsonElement jsonElement : jsonObject.get("filters").getAsJsonArray()) {
            JsonObject aFeature = jsonElement.getAsJsonObject();
            filters.put(aFeature.get("key").getAsString(), aFeature.get("value").getAsString());
        }
        for (JsonElement jsonElement : jsonObject.getAsJsonArray("products")) {
            JsonObject aProduct = jsonElement.getAsJsonObject();
            HashMap<String, String> speFea = new HashMap<>();
            for (JsonElement element : aProduct.get("specialProperties").getAsJsonArray()) {
                speFea.put(element.getAsJsonObject().get("key").getAsString(), element.getAsJsonObject().get("value").getAsString());
            }
            allProducts.add(new ProductInTable(aProduct.get("id").getAsString(), aProduct.get("name").getAsString(), aProduct.get("rate").getAsString(), aProduct.get("minimumPrice").getAsInt(), aProduct.get("categoryName").getAsString(), aProduct.get("available").getAsInt(), aProduct.get("views").getAsInt(), aProduct.get("company").getAsString(), speFea));
        }

    }

    public void userArea(MouseEvent mouseEvent) {
        Runner.getInstance().setUserAreaScene();
    }

    public void loginAlert() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        EventHandler<ActionEvent> event = (e) -> {
            if (DataBase.getInstance().user != null) {
                error.setContentText("You have logged in!");
                error.show();
            } else {
                Runner.getInstance().changeScene("LoginMenu.fxml");
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
            DataBase.getInstance().logout();
        };
        logout.setOnAction(event);
    }

    private void addButtonToTable() {
        TableColumn<ProductInTable, Void> colBtn = new TableColumn();
        Callback<TableColumn<ProductInTable, Void>, TableCell<ProductInTable, Void>> cellFactory = new Callback<TableColumn<ProductInTable, Void>, TableCell<ProductInTable, Void>>() {
            @Override
            public TableCell<ProductInTable, Void> call(final TableColumn<ProductInTable, Void> param) {
                final TableCell<ProductInTable, Void> cell = new TableCell<ProductInTable, Void>() {
                    private final Button btn = new Button("view");

                    {
                        btn.setMinWidth(75);
                        btn.setOnAction((ActionEvent event) -> {
                            Runner.buttonSound();
                            productInTable = getTableView().getItems().get(getIndex());
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
        JsonObject jsonObject = Runner.getInstance().jsonMaker("product", "addView");
        jsonObject.addProperty("id", productInTable.getProductId());
        try {
            dataOutputStream.writeUTF(jsonObject.toString());
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Runner.getInstance().changeScene("ProductArea.fxml");
    }

    private static ObservableList<ProductInTable> getProducts() {
        ObservableList<ProductInTable> products = FXCollections.observableArrayList();
        products.addAll(showProductInGui(categoryName));
        return products;
    }

    private static ArrayList<ProductInTable> showProductInGui(String categoryName) {
        ArrayList<ProductInTable> products = new ArrayList<>();
        if (categoryName.equals("all")) {
            products = allProducts;
        } else {
            for (ProductInTable product : allProducts) {
                if (product.getCategoryName().equals(categoryName))
                    products.add(product);
            }
        }
        ArrayList<ProductInTable> filteredProducts;
        filteredProducts = (ArrayList<ProductInTable>) products.stream()
                .filter(product -> isContainThisProduct(product, categoryName))
                .collect(Collectors.toList());
        return filteredProducts;
    }

    private static Boolean isContainThisProduct(ProductInTable product, String categoryName) {
        HashMap<String, String> specialPropertiesOfProduct = product.getSpecialPropertiesRelatedToCategory();
        for (String key : filters.keySet()) {
            if (categoryName.equals("all")) {
                if (specialPropertiesOfProduct.keySet().contains(key)) {
                    if (!doesMatchWithFilter(filters.get(key), specialPropertiesOfProduct.get(key)))
                        return false;
                }
            } else if (key.equalsIgnoreCase("minimumPrice") && !doesMatchWithFilter(filters.get(key), String.valueOf(product.getMinimumPrice()))) {
                return false;
            } else if (key.equalsIgnoreCase("company") && !doesMatchWithFilter(filters.get(key), product.getCompany())) {
                return false;
            } else if (key.equalsIgnoreCase("name") && !doesMatchWithFilter(filters.get(key), product.getName())) {
                return false;
            } else if (key.equalsIgnoreCase("rate") && !doesMatchWithFilter(filters.get(key), product.getRate())) {
                return false;
            } else if (key.equalsIgnoreCase("availability") && product.getAvailable() == 0) {
                return false;
            }
        }
        return true;
    }

    static Boolean doesMatchWithFilter(String filterValue, String productValue) {
        if (!filterValue.startsWith("[") && filterValue.equalsIgnoreCase(productValue)) {
            return true;
        } else if (filterValue.startsWith("[")) {
            Pattern pattern = Pattern.compile("\\[(\\d+)\\-(\\d+)\\]");
            Matcher matcher = pattern.matcher(filterValue);
            if (!matcher.find())
                return false;
            if (Integer.parseInt(productValue) <= Integer.parseInt(matcher.group(2)) && Integer.parseInt(productValue) >= Integer.parseInt(matcher.group(1))) {
                return true;
            }
        }
        return false;
    }

    public void back(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Runner.getInstance().back();
    }

    public void filterRequest(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Stage filterStage = new Stage();
        URL url = getClass().getClassLoader().getResource("Filters.fxml");
        Parent root = null;
        try {
            root = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        filterStage.setScene(new Scene(root));
        filterStage.setTitle("filters");
        filterStageToSave = filterStage;
        filterStage.show();
    }

    public static void filterProducts() {
        tempTable.setItems(getProducts());
    }
}
