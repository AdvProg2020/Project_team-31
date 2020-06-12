package GraphicalView;

import Controller.ManagerController;
import Controller.ProductController;
import Controller.SellerController;
import Model.Manager;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
    StringProperty categoryName = new SimpleStringProperty("");
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
        ArrayList<String> categories = ManagerController.getInstance().showAllCategories();
        choiceBox.getItems().addAll(categories);
        choiceBoxContainer.getChildren().add(choiceBox);
        categoryName.bind(choiceBox.valueProperty());
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
        String[] generalData = new String[6];
        generalData[0] = productName.getText();
        generalData[1] = companyName.getText();
        generalData[3] = price.getText();
        generalData[4] = categoryName.getValue();
        generalData[0] = description.getText();
        generalData[0] = number.getText();
        HashMap<String, String> dataToSend = new HashMap<>();
        for (Map.Entry<Label, TextField> entry : data.entrySet()) {
            dataToSend.put(entry.getKey().getText(), entry.getValue().getText());
        }
        SellerController.getInstance().addProduct(generalData, dataBase.user, dataToSend);
    }

    public void back(ActionEvent actionEvent) {
        runner.back();
    }

    public void selectFile(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("select photo");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpg Files", "*.jpg"));
        photo = fileChooser.showOpenDialog(Runner.stage);
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
                window.close();
            });
            layout.setAlignment(Pos.CENTER);
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        }
    }
}
