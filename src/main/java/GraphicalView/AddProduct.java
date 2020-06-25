package GraphicalView;

import Controller.*;
import Model.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.*;

import java.io.File;
import java.net.URL;
import java.util.*;

public class AddProduct implements Initializable {
    public VBox choiceBoxContainer;
    public Button categoryFeaturesButton;
    public TextField productName;
    public TextField companyName;
    public TextField price;
    public TextArea description;
    public TextField number;
    Runner runner = Runner.getInstance();
    ChoiceBox<String> choiceBox;
//    StringProperty categoryName = new SimpleStringProperty("");
    File photo;
    DataBase dataBase = DataBase.getInstance();
    HashMap<Label, TextField> data = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dropDownListSetUp();
        setCategoryFeatures();
    }

    private void dropDownListSetUp() {
        choiceBox = new ChoiceBox<>();
        ArrayList<String> categories = ManagerController.getInstance().showAllCategoriesForGUI();
        choiceBox.getItems().addAll(categories);
        choiceBoxContainer.getChildren().add(choiceBox);
//        categoryName.bind(choiceBox.valueProperty());
        Platform.runLater(() -> {
            SkinBase<ChoiceBox<String>> skin = (SkinBase<ChoiceBox<String>>) choiceBox.getSkin();
            for (Node child : skin.getChildren()) {
                if (child instanceof Label) {
                    Label label = (Label) child;
                    if (label.getText().isEmpty())
                        label.setText("select your category");
                    return;
                }
            }
        });
    }

    public void submit(ActionEvent actionEvent) throws Exception {
        SellerController sellerController = SellerController.getInstance();
        if (isInvalid() != null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please enter a valid " + isInvalid(), ButtonType.OK);
            error.show();
            return;
        }
        HashMap<String, String> dataToSend = new HashMap<>();
        String[] generalData = setData(dataToSend);
        Product product = sellerController.addProduct(generalData, dataBase.user, dataToSend);
        new Alert(Alert.AlertType.INFORMATION, "product created successfully", ButtonType.OK).show();
        runner.back();
        if (photo != null) {
            sellerController.changeProductPhoto(product, photo);
        }
    }

    private String[] setData(HashMap<String, String> dataToSend) {
        String[] generalData = new String[6];
        generalData[0] = productName.getText();
        generalData[1] = companyName.getText();
        generalData[2] = price.getText();
        generalData[3] = choiceBox.getValue();
        generalData[4] = description.getText();
        generalData[5] = number.getText();
        for (Map.Entry<Label, TextField> entry : data.entrySet()) {
            dataToSend.put(entry.getKey().getText(), entry.getValue().getText());
        }
        return generalData;
}

    public void back(ActionEvent actionEvent) {
        runner.back();
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

    public void setCategoryFeatures() {
        EventHandler<ActionEvent> event = (e) -> {
            if (choiceBox.getValue() == null) {
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
        else if (choiceBox.getValue().equals(""))
            return "category name";
        else if (data.size() == 0)
            return "category features";
        return null;
    }

    class GetCategoryInfo {
        public void display() throws Exception {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("category info");
            VBox layout = new VBox(10);
            ArrayList<String> features = SellerController.getInstance().getCategoryFeatures(choiceBox.getValue());
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
