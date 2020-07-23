package GraphicalView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerViewSupporters implements Initializable {
    public Pane pane;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            createTable(getAllSupporters());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTable(String[] allSupporters) {
        TableColumn<StringAndButtonTable, String> username = new TableColumn<>("username");
        username.setMinWidth(150);
        username.setCellValueFactory(new PropertyValueFactory<>("data"));

        TableColumn<StringAndButtonTable, Button> button = new TableColumn<>("button");
        button.setMinWidth(150);
        button.setCellValueFactory(new PropertyValueFactory<>("button"));

        TableView<StringAndButtonTable> table = new TableView<>();
        table.setItems(getObjects(allSupporters));
        table.getColumns().addAll(username, button);
        pane.getChildren().add(table);
    }

    private ObservableList<StringAndButtonTable> getObjects(String[] allSupporters) {
        ObservableList<StringAndButtonTable> objects = FXCollections.observableArrayList();
        for (String supporter : allSupporters)
            objects.add(new StringAndButtonTable(supporter, initButton(supporter)));
        return objects;
    }

    private Button initButton(String supporter) {
        Button button = new Button("connect");
        button.setOnAction(e -> {
            JsonObject jsonObject = runner.jsonMaker("customer", "supportMe");
            jsonObject.addProperty("name", supporter);
            try {
                dataBase.dataOutputStream.writeUTF(jsonObject.toString());
                dataBase.dataOutputStream.flush();
                dataBase.dataInputStream.readUTF();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
            createChatDialog(supporter);
        });
        return button;
    }

    private void createChatDialog(String supporter) {
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
        refresh.setOnAction(e -> {
            try {
                dataBase.dataOutputStream.writeUTF(runner.jsonMaker("customer", "getMyChat").toString());
                dataBase.dataOutputStream.flush();
                JsonObject data = runner.jsonParser(dataBase.dataInputStream.readUTF());
                dataBase.chat.setValue(data.get("chat").getAsString());
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        });

        Label label = new Label();
        label.setMinWidth(270);
        label.setLayoutX(10);
        label.setLayoutY(50);
        label.setMinHeight(320);
        label.textProperty().bind(dataBase.chat);
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
        name.setText(supporter);
        name.setStyle("-fx-background-color: #DECD91; " +
                "-fx-background-insets: 5; " +
                "-fx-background-radius: 5; " +
                "-fx-effect: dropshadow(three-pass-box, gray, 10, 0, 0, 0);");

        Pane layout = new Pane(refresh, textField, label, name);
        Scene scene = new Scene(layout, 300, 500);
        stage.setScene(scene);
        stage.show();
    }

    private String[] getAllSupporters() throws Exception {
        JsonObject jsonObject = runner.jsonMaker("customer", "getSupporters");
        dataBase.dataOutputStream.writeUTF(jsonObject.toString());
        dataBase.dataOutputStream.flush();
        String gSonString = runner.jsonParser(dataBase.dataInputStream.readUTF()).get("names").getAsString();
        return new Gson().fromJson(gSonString, String[].class);
    }

    public void back(ActionEvent actionEvent) {
        runner.back();
    }
}
