package GraphicalView;

import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
    private CategoryInTable category;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginAlert();
        logoutAlert();
        dataInputStream = DataBase.getInstance().dataInputStream;
        dataOutputStream = DataBase.getInstance().dataOutputStream;
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
            alert = new Alert(Alert.AlertType.ERROR, "This category have had this feature before", ButtonType.OK);
        } else {
            JsonObject jsonObject = Runner.getInstance().jsonMaker("manager", "changeCategoryFeature");
            jsonObject.addProperty("category", category.getName());
            jsonObject.addProperty("oldName", oldName.getText());
            jsonObject.addProperty("newName", newName.getText());
            try {
                dataOutputStream.writeUTF(jsonObject.toString());
                dataOutputStream.flush();
                dataInputStream.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            category.getSpecialProperties().remove(oldName.getText());
            category.getSpecialProperties().add(newName.getText());
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
            JsonObject jsonObject = Runner.getInstance().jsonMaker("manager", "addCategoryFeature");
            jsonObject.addProperty("category", category.getName());
            jsonObject.addProperty("feature", newFeature.getText());
            try {
                dataOutputStream.writeUTF(jsonObject.toString());
                dataOutputStream.flush();
                dataInputStream.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            category.getSpecialProperties().add(newFeature.getText());
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
            JsonObject jsonObject = Runner.getInstance().jsonMaker("manager", "removeCategoryFeature");
            jsonObject.addProperty("category", category.getName());
            jsonObject.addProperty("feature", removingFeature.getText());
            try {
                dataOutputStream.writeUTF(jsonObject.toString());
                dataOutputStream.flush();
                dataInputStream.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            category.getSpecialProperties().remove(removingFeature.getText());
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
