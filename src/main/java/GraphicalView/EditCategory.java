package GraphicalView;

import Controller.ManagerController;
import Model.Category;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EditCategory implements Initializable {
    public VBox properties;
    public TextField newName;
    public TextField oldName;
    public TextField newFeature;
    public TextField removingFeature;
    public Button login;
    public Button logout;
    private Category category;
    private ManagerController managerController = ManagerController.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginAlert();
        logoutAlert();
        category = ShowCategories.categoryToEdit;
        setProperties();
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

    private void setProperties() {
        properties.getChildren().clear();
        properties.getChildren().add(new Label("Features"));
        for (String specialProperty : category.getSpecialProperties()) {
            Label feature = new Label(specialProperty);
            properties.getChildren().add(feature);
        }
    }

    public void change(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Alert alert = null;
        if (!category.getSpecialProperties().contains(oldName.getText())) {
            alert = new Alert(Alert.AlertType.ERROR, "This category doesn't have this feature to change", ButtonType.OK);
        } else if (category.getSpecialProperties().contains(newName.getText())) {
            alert = new Alert(Alert.AlertType.ERROR, "This category doesn't have this feature to change", ButtonType.OK);
        } else {
            HashMap changedFeatured = new HashMap();
            changedFeatured.put(oldName.getText(), newName.getText());
            managerController.changeFeatureOfCategory(category.getName(), changedFeatured);
            alert = new Alert(Alert.AlertType.INFORMATION, "feature changed successfully", ButtonType.OK);
            setProperties();
        }
        alert.show();
    }

    public void add(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Alert alert = null;
        if (newFeature.getText().equals("")) {
            alert = new Alert(Alert.AlertType.ERROR, "please fill field", ButtonType.OK);
        } else if (category.getSpecialProperties().contains(newFeature.getText())) {
            alert = new Alert(Alert.AlertType.ERROR, "This feature exist", ButtonType.OK);
        } else {
            managerController.addFeature(category, newFeature.getText());
            alert = new Alert(Alert.AlertType.INFORMATION, "feature added successfully", ButtonType.OK);
            setProperties();
        }
        alert.show();
    }

    public void remove(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Alert alert = null;
        if (removingFeature.getText().equals("")) {
            alert = new Alert(Alert.AlertType.ERROR, "please fill field", ButtonType.OK);
        } else if (!category.getSpecialProperties().contains(removingFeature.getText())) {
            alert = new Alert(Alert.AlertType.ERROR, "This feature doesn't exist", ButtonType.OK);
        } else {
            managerController.removeFeature(category, removingFeature.getText());
            alert = new Alert(Alert.AlertType.INFORMATION, "feature deleted successfully", ButtonType.OK);
            setProperties();
        }
        alert.show();
    }

    public void back(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Runner.getInstance().back();
    }
}
