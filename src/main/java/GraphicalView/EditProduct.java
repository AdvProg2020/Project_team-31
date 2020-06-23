package GraphicalView;

import Controller.ManagerController;
import Model.Product;
import Model.Seller;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditProduct implements Initializable {
    public TextField productName;
    public TextField companyName;
    public TextField price;
    public TextArea description;
    public TextField number;
    public VBox choiceBoxContainer;
    public Button categoryFeaturesButton;
    StringProperty categoryName = new SimpleStringProperty("");
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();
    Product product = ProductsMenu.product;
    ChoiceBox<String> choiceBox;
    File photo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dropDownListSetUp();
        initValues();
    }

    private void initValues() {
        productName.setText(product.getName());
        companyName.setText(product.getCompany());
        price.setText(product.getSellersOfThisProduct().get((Seller) dataBase.user);
        description.setText(product.getInformation());
        number.setText(String.valueOf(product.getAvailable()));
        //... init choice box

    }

    public void back(ActionEvent actionEvent) {
        runner.back();
    }

    public void submit(ActionEvent actionEvent) {

    }

    public void selectFile(MouseEvent mouseEvent) {

    }

    private void dropDownListSetUp() {
        choiceBox = new ChoiceBox<>();
        ArrayList<String> categories = ManagerController.getInstance().showAllCategories();
        choiceBox.getItems().addAll(categories);
        choiceBoxContainer.getChildren().add(choiceBox);
        categoryName.bind(choiceBox.valueProperty());
        choiceBox.setValue(product.getCategory().getName());
    }
}
