package GraphicalView;

import Controller.CustomerController;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
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
    private User user;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addShowButtonToTable();
        addDecreaseButtonToTable();
        addIncreaseButtonToTable();
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        setTableOfProducts();
    }

    private void addShowButtonToTable() {
        TableColumn<ProductInCartInGui, Void> colBtn = new TableColumn("detail");
        Callback<TableColumn<ProductInCartInGui, Void>, TableCell<ProductInCartInGui, Void>> cellFactory = new Callback<TableColumn<ProductInCartInGui, Void>, TableCell<ProductInCartInGui, Void>>() {
            @Override
            public TableCell<ProductInCartInGui, Void> call (final TableColumn<ProductInCartInGui, Void> param) {
                final TableCell<ProductInCartInGui, Void> cell = new TableCell<ProductInCartInGui, Void>() {
                    private final Button btn = new Button("show");
                    {
                        btn.setMinWidth(75);
                        btn.setOnAction((ActionEvent event) -> {
                            ProductInCartInGui productInCartInGui = getTableView().getItems().get(getIndex());
                            ProductsMenu.product = productInCartInGui.getProduct();
                            runner.changeScene("ProductArea.fxml");
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                    }
                };
                return cell;
            }
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
                            changeNumber(productInCartInGui.getProduct(), -1);
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
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
                            changeNumber(productInCartInGui.getProduct(), +1);
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
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

    public void purchase(MouseEvent mouseEvent) {
        if(CustomerController.getInstance().showTotalPrice(user.getCard()) == 0) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Cart is empty", ButtonType.OK);
            error.show();
        } else if(dataBase.user == null) {
            runner.changeScene("LoginMenu.fxml");
        } else {
            String message = CustomerController.getInstance().isAvailabilityOk(dataBase.user);
            if(message.equalsIgnoreCase("ok")) {
                runner.changeScene("ReceiveInformationForShopping.fxml");
            } else {
                Alert error = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
                error.show();
            }
        }
    }

    private void changeNumber(Product product, int changing) {
        try {
            CustomerController.getInstance().changeNumberOfProductInCard(dataBase.user, dataBase.tempUser.getCard(),product.getProductId(),changing);
            setTableOfProducts();
        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            error.show();
        }
    }

    private void setTableOfProducts() {
        user = dataBase.tempUser;
        if(dataBase.user != null)
            user = dataBase.user;
        if(user.getCard() == null) {
            user.setCard(new Card());
        }
        ObservableList allProducts = FXCollections.observableArrayList();
        for (ProductInCard productInCard : user.getCard().getProductsInThisCard().values()) {
            allProducts.addAll(new ProductInCartInGui(productInCard));
        }
        tableOfProducts.setItems(allProducts);
        totalPrice.setText("total price: " + CustomerController.getInstance().showTotalPrice(user.getCard()));
    }

    public void back(MouseEvent mouseEvent) {
        runner.back();
    }
}
