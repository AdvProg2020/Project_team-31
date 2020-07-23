package GraphicalView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SupporterUserArea implements Initializable {
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();
    HashMap<String, StringProperty> chats = new HashMap<>();
    HashMap<String, String> newMassages = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(() -> refresh(null, "")).start();
    }

    private void refresh(TextField textField, String username) {
        try {
            JsonObject jsonObject = runner.jsonMaker("manager", "supporter");
            jsonObject.addProperty("name", username);
            if (textField == null)
                jsonObject.addProperty("chat", "");
            else
                jsonObject.addProperty("chat", textField.getText());
            dataBase.dataOutputStream.writeUTF(jsonObject.toString());
            dataBase.dataOutputStream.flush();
            newMassages.values().forEach(k -> k = "");
            handleData(dataBase.dataInputStream.readUTF());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleData(String jsonString) {
        JsonObject jsonObject = runner.jsonParser(jsonString);
        String[] name = new Gson().fromJson(jsonObject.get("names").getAsString(), String[].class);
        String[] chat = new Gson().fromJson(jsonObject.get("chats").getAsString(), String[].class);
        for (int i = 0; i < name.length; i++) {
            if (chats.containsKey(name[i]))
                chats.get(name[i]).setValue(chat[i]);
            else {
                StringProperty chatP = new SimpleStringProperty(chat[i]);
                chats.put(name[i], chatP);
                newMassages.put(name[i], chatP.getValue());
                createNewChat(name[i], chatP);
            }
        }
    }

    private void createNewChat(String username, StringProperty chat) {
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Chat");
        stage.setHeight(500);
        stage.setWidth(300);

        Button refresh = new Button("refresh");
        refresh.setMinWidth(60);
        refresh.setLayoutX(200);
        refresh.setTranslateY(400);

        TextField textField = new TextField();
        textField.setLayoutX(20);
        textField.setTranslateY(400);
        refresh.setOnAction(e -> refresh(textField, username));

        Label label = new Label();
        label.setMinWidth(270);
        label.setLayoutX(10);
        label.setLayoutY(50);
        label.setMinHeight(320);
        label.textProperty().bind(chat);
        label.setStyle(
                "-fx-background-color: #DEDCDC; " +
                        "-fx-background-insets: 5; " +
                        "-fx-background-radius: 5; " +
                        "-fx-effect: dropshadow(three-pass-box, gray, 10, 0, 0, 0);" +
                        "-fx-alignment: bottom-left;" +
                        "-fx-padding: 10 20 10 20;" +
                        "-fx-font-size: 10px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: Times New Roman;"
        );

        Label name = new Label();
        name.setMinWidth(270);
        name.setLayoutX(10);
        name.setLayoutY(10);
        name.setMinHeight(40);

        name.setText("    " + username);
        name.setStyle("-fx-background-color: #DECD91; " +
                "-fx-background-insets: 5; " +
                "-fx-background-radius: 5; " +
                "-fx-effect: dropshadow(three-pass-box, gray, 10, 0, 0, 0);");

        Pane layout = new Pane(refresh, textField, label, name);
        Scene scene = new Scene(layout, 300, 500);
        stage.setScene(scene);
        stage.show();
    }

    public void back(ActionEvent actionEvent) {
        runner.back();
    }

    public void refreshPage(ActionEvent actionEvent) {
        refresh(null, "");
    }
}
