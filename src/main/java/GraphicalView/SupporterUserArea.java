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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(this::refresh).start();
    }

    private void refresh() {
        try {
            JsonObject jsonObject = runner.jsonMaker("manager", "supporter");
            jsonObject.addProperty("names", new Gson().toJson(createArrays().getKey()));
            jsonObject.addProperty("chats", new Gson().toJson(createArrays().getValue()));
            dataBase.dataOutputStream.writeUTF(jsonObject.toString());
            dataBase.dataOutputStream.flush();
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

    private Pair<String[], String[]> createArrays() {
        String[] names = new String[chats.size()];
        String[] content = new String[chats.size()];
        int i = 0;
        for (Map.Entry<StringProperty, StringProperty> entry : chats.entrySet()) {
            names[i] = entry.getKey().getValue();
            content[i] = entry.getValue().getValue();
            i++;
        }
        return new Pair<String[], String[]>(names, content);
    }

    private void createNewChat(StringProperty username, StringProperty chat) {
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Chat");
        stage.setHeight(500);
        stage.setWidth(300);

        Button send = new Button("send");
        send.setMinWidth(60);
        send.setLayoutX(200);
        send.setTranslateY(410);


        Button refresh = new Button("refresh");
        refresh.setMinWidth(60);
        refresh.setLayoutX(200);
        refresh.setTranslateY(380);
        refresh.setOnAction(e -> refresh());

        TextArea textField = new TextArea();
        textField.setLayoutX(20);
        textField.setTranslateY(380);
        textField.setMaxHeight(60);
        textField.setMaxWidth(160);
        send.setOnAction(event -> {
            addText(username, textField);
            refresh();
        });

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

        Pane layout = new Pane(send, refresh, textField, label, name);
        Scene scene = new Scene(layout, 300, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void addText(StringProperty username, TextArea textArea) {
        chats.get(username).setValue(chats.get(username).getValue() + textArea.getText());
    }

    public void back(ActionEvent actionEvent) {
        runner.back();
    }
}
