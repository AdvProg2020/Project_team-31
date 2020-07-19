package GraphicalView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddCategory implements Initializable {
    public VBox properties;
    public TextField newFeature;
    public TextField removingFeature;
    public TextField categoryName;
    public ArrayList<String> features = new ArrayList<>();
    public Button login;
    public Button logout;

    public void create(MouseEvent mouseEvent) {
        Alert alert;
        Runner.buttonSound();
        if(categoryName.getText().equals("")) {
            alert = new Alert(Alert.AlertType.ERROR, "please write category name", ButtonType.OK);
        } else if(features.size() == 0) {
            alert = new Alert(Alert.AlertType.ERROR, "please specify at least on feature", ButtonType.OK);
        } else {
            JsonObject jsonObject = Runner.getInstance().jsonMaker("manager", "addCategory");
            jsonObject.addProperty("name", categoryName.getText());
            JsonArray featureArray = new JsonArray();
            for (String s : features) {
                featureArray.add(s);
            }
            jsonObject.add("features", featureArray);
            try {
                DataBase.getInstance().dataOutputStream.writeUTF(jsonObject.toString());
                DataBase.getInstance().dataOutputStream.flush();
                DataBase.getInstance().dataInputStream.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public void userArea(MouseEvent mouseEvent) {
        Runner.getInstance().setUserAreaScene();
    }

    public void loginAlert() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        EventHandler<ActionEvent> event = (e) -> {
            if (!DataBase.getInstance().role.equals("none")) {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginAlert();
        logoutAlert();
    }
}
