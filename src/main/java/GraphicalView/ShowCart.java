package GraphicalView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowCart implements Initializable {
    public Button logout;
    public Button login;
    public Label totalPrice;
    public TableView tableOfProducts;
    public TableColumn productName;
    public TableColumn number;
    public TableColumn price;
    public TableColumn total;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private int totalprices;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginAlert();
        logoutAlert();
        addShowButtonToTable();
        addDecreaseButtonToTable();
        addIncreaseButtonToTable();
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        total.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        dataInputStream = DataBase.getInstance().dataInputStream;
        dataOutputStream = DataBase.getInstance().dataOutputStream;
        try {
            dataOutputStream.writeUTF(Runner.getInstance().jsonMaker("customer", "showCart").toString());
            dataOutputStream.flush();
            String input = dataInputStream.readUTF();
            setTableOfProducts((JsonObject) new JsonParser().parse(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addShowButtonToTable() {
        TableColumn<ProductInCartInGui, Void> colBtn = new TableColumn("detail");
        Callback<TableColumn<ProductInCartInGui, Void>, TableCell<ProductInCartInGui, Void>> cellFactory = param -> {
            final TableCell<ProductInCartInGui, Void> cell = new TableCell<ProductInCartInGui, Void>() {
                private final Button btn = new Button("show");
                {
                    btn.setMinWidth(75);
                    btn.setOnAction((ActionEvent event) -> {
                        Runner.buttonSound();
                        ProductInCartInGui productInCartInGui = getTableView().getItems().get(getIndex());
                        ProductsMenu.productId = productInCartInGui.getId();
                        runner.changeScene("ProductArea.fxml");
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
        };
        colBtn.setCellFactory(cellFactory);
        tableOfProducts.getColumns().add(colBtn);
    }

    private void addDecreaseButtonToTable() {
        TableColumn<ProductInCartInGui, Void> colBtn = new TableColumn();
        colBtn.setMaxWidth(40);
        Callback<TableColumn<ProductInCartInGui, Void>, TableCell<ProductInCartInGui, Void>> cellFactory = new Callback<TableColumn<ProductInCartInGui, Void>, TableCell<ProductInCartInGui, Void>>() {
            @Override
            public TableCell<ProductInCartInGui, Void> call (final TableColumn<ProductInCartInGui, Void> param) {
                final TableCell<ProductInCartInGui, Void> cell = new TableCell<ProductInCartInGui, Void>() {
                    private final Button btn = new Button("-");
                    {
                        btn.setMinWidth(40);
                        btn.setOnAction((ActionEvent event) -> {
                            ProductInCartInGui productInCartInGui = getTableView().getItems().get(getIndex());
                            changeNumber(productInCartInGui.getId(), -1);
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

    private void addIncreaseButtonToTable() {
        TableColumn<ProductInCartInGui, Void> colBtn = new TableColumn();
        colBtn.setMaxWidth(40);
        Callback<TableColumn<ProductInCartInGui, Void>, TableCell<ProductInCartInGui, Void>> cellFactory = new Callback<TableColumn<ProductInCartInGui, Void>, TableCell<ProductInCartInGui, Void>>() {
            @Override
            public TableCell<ProductInCartInGui, Void> call (final TableColumn<ProductInCartInGui, Void> param) {
                final TableCell<ProductInCartInGui, Void> cell = new TableCell<ProductInCartInGui, Void>() {
                    private final Button btn = new Button("+");
                    {
                        btn.setMinWidth(40);
                        btn.setOnAction((ActionEvent event) -> {
                            ProductInCartInGui productInCartInGui = getTableView().getItems().get(getIndex());
                            changeNumber(productInCartInGui.getId(), +1);
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


    private void logoutAlert() {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        EventHandler<ActionEvent> event = (e) -> {
            Runner.buttonSound();
            if (DataBase.getInstance().role.equals("none")) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("You have not logged in!");
                error.show();
            } else {
                message.setContentText("you logged out successfully");
                message.show();
                DataBase.getInstance().logout();
            }
        };
        logout.setOnAction(event);
    }

    public void loginAlert() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        EventHandler<ActionEvent> event = (e) -> {
            Runner.buttonSound();
            if (!DataBase.getInstance().role.equals("none")) {
                error.setContentText("You have logged in!");
                error.show();
            } else {
                runner.changeScene("LoginMenu.fxml");
            }
        };
        login.setOnAction(event);
    }

    public void purchase(MouseEvent mouseEvent) {
        Runner.buttonSound();
        if(totalprices == 0) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Cart is empty", ButtonType.OK);
            error.show();
        } else if(DataBase.getInstance().role.equals("none")) {
            runner.changeScene("LoginMenu.fxml");
        } else {
            try {
                dataOutputStream.writeUTF(Runner.getInstance().jsonMaker("customer", "checkAvailableToPurchase").toString());
                dataOutputStream.flush();
                String input = dataInputStream.readUTF();
                JsonObject jsonObject = (JsonObject) new JsonParser().parse(input);
                if(jsonObject.get("message").getAsString().equalsIgnoreCase("Ok")) {
                    runner.changeScene("ReceiveInformationForShopping.fxml");
                } else {
                    Alert error = new Alert(Alert.AlertType.ERROR, jsonObject.get("message").getAsString(), ButtonType.OK);
                    error.show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void changeNumber(String id, int changing) {
        JsonObject output = Runner.getInstance().jsonMaker("customer", "changeNumber");
        output.addProperty("id", id);
        output.addProperty("number", changing);
        try {
            dataOutputStream.writeUTF(output.toString());
            dataOutputStream.flush();
            String input = dataInputStream.readUTF();
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(input);
            if(jsonObject.get("type").getAsString().equals("failed")) {
                Alert error = new Alert(Alert.AlertType.ERROR, jsonObject.get("message").getAsString(), ButtonType.OK);
                error.show();
            } else {
                setTableOfProducts(jsonObject.getAsJsonObject("cart"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTableOfProducts(JsonObject jsonObject) {
        ObservableList<ProductInCartInGui> allProducts = FXCollections.observableArrayList();
        for (JsonElement element : jsonObject.getAsJsonArray("products")) {
            JsonObject product = element.getAsJsonObject();
            allProducts.add(new ProductInCartInGui(product.get("id").getAsString(), product.get("name").getAsString(), product.get("number").getAsInt(), product.get("price").getAsInt(), product.get("totalPrice").getAsInt()));
        }
        tableOfProducts.setItems(allProducts);
        totalprices = jsonObject.get("total").getAsInt();
        totalPrice.setText("total price: " + jsonObject.get("total").getAsInt());
    }

    public void back(MouseEvent mouseEvent) {
        runner.back();
    }

    public void userArea(MouseEvent mouseEvent) {
        Runner.getInstance().setUserAreaScene();
    }
}
