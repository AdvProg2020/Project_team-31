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
import java.util.stream.Collectors;

public class OffMenu implements Initializable {
    public TableColumn imageColumn;
    public TableColumn nameColumn;
    public TableColumn viewColumn;
    public TableColumn priceColumn;
    public TableColumn rateColumn;
    public TableColumn timeColumn;
    public TableColumn percentColumn;
    public TableView tableOfProducts;
    public static TableView tempTable;
    public static Stage filterStageToSave;
    public Button login;
    public Button logout;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    static HashMap<String, String> filters = new HashMap<>();
    private static ArrayList<OffedProduct> allOffedProducts = new ArrayList<>();

    public void back(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Runner.getInstance().back();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        SellerController.getInstance().tempPhoto();
        loginAlert();
        logoutAlert();
        tempTable = tableOfProducts;
        Runner.getInstance().changeMusic("OffMenu");
        dataInputStream = DataBase.getInstance().dataInputStream;
        dataOutputStream = DataBase.getInstance().dataOutputStream;
        String input = null;
        try {
            dataOutputStream.writeUTF(Runner.getInstance().jsonMaker("product", "showAllOffedProducts").toString());
            dataOutputStream.flush();
            input = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        analyzeInput(input);
        addButtonToTable();
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imageViewSmall"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        viewColumn.setCellValueFactory(new PropertyValueFactory<>("view"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        rateColumn.setCellValueFactory(new PropertyValueFactory<>("rate"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        percentColumn.setCellValueFactory(new PropertyValueFactory<>("percent"));
        setOffedProducts();
    }

    private void analyzeInput(String input) {
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(input);
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
            ProductInTable productInTable = new ProductInTable(aProduct.get("id").getAsString(), aProduct.get("name").getAsString(), aProduct.get("rate").getAsString(), aProduct.get("minimumPrice").getAsInt(), aProduct.get("categoryName").getAsString(), aProduct.get("available").getAsInt(), aProduct.get("views").getAsInt(), aProduct.get("company").getAsString(), speFea);
            allOffedProducts.add(new OffedProduct(productInTable, aProduct.get("endTime").getAsString(), aProduct.get("price").getAsInt(), aProduct.get("percent").getAsInt()));
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
        TableColumn<OffedProduct, Void> colBtn = new TableColumn();
        Callback<TableColumn<OffedProduct, Void>, TableCell<OffedProduct, Void>> cellFactory = new Callback<TableColumn<OffedProduct, Void>, TableCell<OffedProduct, Void>>() {
            @Override
            public TableCell<OffedProduct, Void> call(final TableColumn<OffedProduct, Void> param) {
                final TableCell<OffedProduct, Void> cell = new TableCell<OffedProduct, Void>() {
                    private final Button btn = new Button("view");

                    {
                        btn.setMinWidth(75);
                        btn.setOnAction((ActionEvent event) -> {
                            OffedProduct offedProduct = getTableView().getItems().get(getIndex());
                            ProductsMenu.productInTable = offedProduct.getProduct();
                            ProductsMenu.showDetail();
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

    public static void setOffedProducts() {
        ObservableList<OffedProduct> products = FXCollections.observableArrayList();
        products.addAll(showOffProductInGui());
        tempTable.setItems(products);
    }

    private static ArrayList<OffedProduct> showOffProductInGui() {
        ArrayList<OffedProduct> filteredProducts;
        filteredProducts = (ArrayList<OffedProduct>) allOffedProducts.stream()
                .filter(product -> isContainThisProduct(product.getProduct()))
                .collect(Collectors.toList());
        return filteredProducts;
    }

    private static Boolean isContainThisProduct(ProductInTable product) {
        HashMap<String, String> specialPropertiesOfProduct = product.getSpecialPropertiesRelatedToCategory();
        for (String key : filters.keySet()) {
            if (specialPropertiesOfProduct.keySet().contains(key)) {
                if (!ProductsMenu.doesMatchWithFilter(filters.get(key), specialPropertiesOfProduct.get(key)))
                    return false;
            } else if (key.equalsIgnoreCase("minimumPrice") && !ProductsMenu.doesMatchWithFilter(filters.get(key), String.valueOf(product.getMinimumPrice()))) {
                return false;
            } else if (key.equalsIgnoreCase("company") && !ProductsMenu.doesMatchWithFilter(filters.get(key), product.getCompany())) {
                return false;
            } else if (key.equalsIgnoreCase("name") && !ProductsMenu.doesMatchWithFilter(filters.get(key), product.getName())) {
                return false;
            } else if (key.equalsIgnoreCase("rate") && !ProductsMenu.doesMatchWithFilter(filters.get(key), product.getRate())) {
                return false;
            } else if (key.equalsIgnoreCase("availability") && product.getAvailable() == 0) {
                return false;
            }
        }
        return true;
    }

    public void filterRequest(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Stage filterStage = new Stage();
        URL url = getClass().getClassLoader().getResource("FilterOffs.fxml");
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
}