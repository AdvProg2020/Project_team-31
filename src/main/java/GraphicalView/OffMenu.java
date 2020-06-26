package GraphicalView;

import Controller.ProductController;
import Model.Off;
import Model.OffedProduct;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class OffMenu implements Initializable {
    public TableColumn imageColumn;
    public TableColumn nameColumn;
    public TableColumn viewColumn;
    public TableColumn priceColumn;
    public TableColumn rateColumn;
    public TableColumn timeColumn;
    public TableColumn percentColumn;
    public TableView tableOfProducts;

    public void back(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Runner.getInstance().back();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
                            ProductsMenu.product = offedProduct.getProduct();
                            Runner.getInstance().changeScene("Products.fxml");
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

    private void setOffedProducts() {
        ObservableList<OffedProduct> products = FXCollections.observableArrayList();
        products.addAll(ProductController.getInstance().showOffProductInGui());
        tableOfProducts.setItems(products);
    }

    public void filterRequest(MouseEvent mouseEvent) {
    }
}
