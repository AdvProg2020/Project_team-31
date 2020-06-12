package GraphicalView;

import Controller.ManagerController;
import Model.Manager;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddProduct implements Initializable {
    public VBox choiceBoxContainer;
    Runner runner = Runner.getInstance();
    ChoiceBox<String> choiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dropDownListSetUp();
    }

    private void dropDownListSetUp() {
        choiceBox = new ChoiceBox<>();
        ArrayList<String> categories = ManagerController.getInstance().showAllCategories();
        choiceBox.getItems().addAll(categories);
        choiceBoxContainer.getChildren().add(choiceBox);
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

    public void submit(ActionEvent actionEvent) {

    }

    public void back(ActionEvent actionEvent) {
        runner.back();
    }

}
