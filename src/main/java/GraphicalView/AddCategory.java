package GraphicalView;

import Controller.ManagerController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class AddCategory {
    public VBox properties;
    public TextField newFeature;
    public TextField removingFeature;
    public TextField categoryName;
    public ArrayList<String> features = new ArrayList<>();

    public void create(MouseEvent mouseEvent) {
        Alert alert;
        Runner.buttonSound();
        if(categoryName.getText().equals("")) {
            alert = new Alert(Alert.AlertType.ERROR, "please write category name", ButtonType.OK);
        } else if(features.size() == 0) {
            alert = new Alert(Alert.AlertType.ERROR, "please specify at least on feature", ButtonType.OK);
        } else {
            ManagerController.getInstance().addCategory(categoryName.getText(), features);
            alert = new Alert(Alert.AlertType.INFORMATION, "category created successfully", ButtonType.OK);
            Runner.getInstance().back();
        }
        alert.show();
    }

    public void back(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Runner.getInstance().back();
    }

    public void addFeature(MouseEvent mouseEvent) {
        Runner.buttonSound();
        if(newFeature.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "please fill field", ButtonType.OK);
            alert.show();
        } else if(features.contains(newFeature.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "you have added this feature before", ButtonType.OK);
            alert.show();
        } else {
            features.add(newFeature.getText());
            setFeatures();
        }
    }

    public void removeFeature(MouseEvent mouseEvent) {
        Runner.buttonSound();
        if(removingFeature.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "please fill field", ButtonType.OK);
            alert.show();
        } else if(!features.contains(removingFeature.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "you haven't add this feature before", ButtonType.OK);
            alert.show();
        } else {
            features.remove(removingFeature.getText());
            setFeatures();
        }
    }

    private void setFeatures() {
        properties.getChildren().clear();
        properties.getChildren().add(new Label("features:"));
        for (String s : features) {
            properties.getChildren().add(new Label(s));
        }
    }
}
