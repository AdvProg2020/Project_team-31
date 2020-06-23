package GraphicalView;

import Controller.ManagerController;
import Controller.SellerController;
import Model.Product;
import Model.Seller;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class EditProduct implements Initializable {
    public TextField productName;
    public TextField companyName;
    public TextField price;
    public TextArea description;
    public TextField number;
    public VBox choiceBoxContainer;
    public Button categoryFeaturesButton;
    HashMap<Label, TextField> data = new HashMap<>();
    StringProperty categoryName = new SimpleStringProperty("");
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();
    Product product = ProductsMenu.product;
    ChoiceBox<String> choiceBox;
    File photo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCategoryFeatures();
        dropDownListSetUp();
        initValues();
    }

    private void initValues() {
        productName.setText(product.getName());
        companyName.setText(product.getCompany());
        price.setText(product.getSellersOfThisProduct().get(dataBase.user).toString());
        description.setText(product.getInformation());
        number.setText(String.valueOf(product.getAvailable()));
    }

    public void back(ActionEvent actionEvent) {
        runner.back();
    }

    public void submit(ActionEvent actionEvent) throws Exception {
        SellerController sellerController = SellerController.getInstance();
        if (isInvalid() != null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please enter a valid " + isInvalid(), ButtonType.OK);
            error.show();
            return;
        }
        HashMap<String, String> dataToSend = new HashMap<>();
        String id = this.product.getProductId();
        int price = Integer.parseInt(this.price.getText());
        int available = Integer.parseInt(this.number.getText());
        Product product = sellerController.editProduct(dataBase.user, id, price, available, description.getText(),dataToSend);
        if (photo != null) {
            sellerController.changeProductPhoto(product, photo);
        }
    }


    private String isInvalid() {
        if (productName.getText().equals(""))
            return "product name";
        else if (companyName.getText().equals(""))
            return "company name";
        else if (price.getText().equals("") || !price.getText().matches("^\\d+$"))
            return "price";
        else if (description.getText().equals(""))
            return "description";
        else if (number.getText().equals("") || !number.getText().matches("^\\d+$") && Integer.parseInt(number.getText()) > 0)
            return "number";
        else if (categoryName.getValue().equals(""))
            return "category name";
        else if (data.size() == 0)
            return "category features";
        return null;
    }

    public void setCategoryFeatures() {
        EventHandler<ActionEvent> event = (e) -> {
            if (categoryName.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("you have to select the category");
                alert.show();
            } else {
                try {
                    new GetCategoryInfo().display();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        };
        categoryFeaturesButton.setOnAction(event);
    }

    public void selectFile(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("select photo");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpeg Files", "*.jpeg"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("bmp Files", "*.bmp"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("gif Files", "*.gif"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("png Files", "*.png"));
        photo = fileChooser.showOpenDialog(Runner.stage);
    }

    private void dropDownListSetUp() {
        choiceBox = new ChoiceBox<>();
        ArrayList<String> categories = ManagerController.getInstance().showAllCategories();
        choiceBox.getItems().addAll(categories);
        choiceBoxContainer.getChildren().add(choiceBox);
        categoryName.bind(choiceBox.valueProperty());
        choiceBox.setValue(product.getCategory().getName());
        choiceBox.setOnAction(event -> data.clear());
    }

    class GetCategoryInfo {
        public void display() throws Exception {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("category info");
            VBox layout = new VBox(10);
            ArrayList<String> features = SellerController.getInstance().getCategoryFeatures(categoryName.getValue());
            Button closeButton = new Button("submit");
            for (String feature : features) {
                Label label = new Label(feature);
                TextField textField = new TextField();
                data.put(label, textField);
                layout.getChildren().addAll(label, textField);
            }
            layout.getChildren().add(closeButton);
            closeButton.setOnAction(e -> {
                for (Node node : layout.getChildren()) {
                    if (node instanceof TextField) {
                        TextField textField = (TextField) node;
                        if (textField.getText().equals("")) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("please fill the blank text fields");
                            alert.show();
                        } else window.close();
                    }
                }
            });
            layout.setAlignment(Pos.CENTER);
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        }
    }
}
