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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SupporterUserArea implements Initializable {
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();
    HashMap<StringProperty, StringProperty> chats = new HashMap<>();
    HashMap<StringProperty, StringProperty> newMassages = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(() -> refresh(null, null)).start();
    }

    private void refresh(TextField textField, StringProperty username) {
        try {
            JsonObject jsonObject = runner.jsonMaker("manager", "supporter");
            jsonObject.addProperty("names", new Gson().toJson(createArrays(textField, username).getKey()));
            jsonObject.addProperty("chats", new Gson().toJson(createArrays(textField, username).getValue()));
            dataBase.dataOutputStream.writeUTF(jsonObject.toString());
            dataBase.dataOutputStream.flush();
            newMassages.keySet().forEach(k -> k.setValue(null));
            handleData(dataBase.dataInputStream.readUTF());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleData(String jsonString) {
        JsonObject jsonObject = runner.jsonParser(jsonString);
        String[] name = new Gson().fromJson(jsonObject.get("names").getAsString(), String[].class);
        String[] chat = new Gson().fromJson(jsonObject.get("chats").getAsString(), String[].class);
        Integer[] newCustomers = new Gson().fromJson(jsonObject.get("newCustomers").getAsString(), Integer[].class);
        int iterator = 0;
        for (Map.Entry<StringProperty, StringProperty> entry : chats.entrySet())
            entry.getValue().setValue(chat[iterator++]);
        for (Integer newCustomer : newCustomers) {
            SimpleStringProperty nameP = new SimpleStringProperty(name[newCustomer]);
            SimpleStringProperty chatP = new SimpleStringProperty(chat[newCustomer]);
            chats.put(nameP, chatP);
            createNewChat(nameP, chatP);
        }
    }

    private Pair<String[], String[]> createArrays(TextField textField, StringProperty username) {
        String[] names = new String[chats.size()];
        String[] content = new String[chats.size()];
        if (textField != null) {
            newMassages.get(username).setValue(textField.getText());
        }
        return new Pair<String[], String[]>(names, content);
    }

    private void createNewChat(StringProperty username, StringProperty chat) {
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
                        "-fx-effect: dropshadow(three-pass-box, gray, 10, 0, 0, 0);"
        );

        Label name = new Label();
        name.setMinWidth(270);
        name.setLayoutX(10);
        name.setLayoutY(10);
        name.setMinHeight(40);

        label.textProperty().bind(username);
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
}
