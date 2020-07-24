package GraphicalView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageUsers implements Initializable {
    public TableColumn nameColumn;
    public TableView tableOfUsers;
    public TableColumn userNameColumn;
    public TableColumn lastColumn;
    public TableColumn status;
    public Button logout;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ArrayList<UserInTable> allUsersInTable = new ArrayList<>();
    private UserInTable deletingUser;
    private String thisUserUsername;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoutAlert();
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        dataInputStream = DataBase.getInstance().dataInputStream;
        dataOutputStream = DataBase.getInstance().dataOutputStream;
        String input = null;
        try {
            dataOutputStream.writeUTF(Runner.getInstance().jsonMaker("manager", "showAllUsers").toString());
            dataOutputStream.flush();
            input = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(input);
        analyzeInput(jsonObject);
        addButtonToTable();
        setTableOfUsers();
    }

    private void analyzeInput(JsonObject jsonObject) {
        for (JsonElement element : jsonObject.getAsJsonArray("users")) {
            JsonObject user = element.getAsJsonObject();
            allUsersInTable.add(new UserInTable(user.get("username").getAsString(), user.get("name").getAsString(), user.get("lastName").getAsString(), status(user.get("status").getAsBoolean())));
        }
        thisUserUsername = jsonObject.get("username").getAsString();
    }

    private String status(boolean status) {
        if (status)
            return "online";
        return "offline";
    }

    public void userArea(MouseEvent mouseEvent) {
        Runner.getInstance().setUserAreaScene();
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

    private void setTableOfUsers() {
        ObservableList<UserInTable> allUsers = FXCollections.observableArrayList();
        allUsers.addAll(allUsersInTable);
        tableOfUsers.setItems(allUsers);
    }

    private void addButtonToTable() {
        TableColumn<UserInTable, Void> colBtn = new TableColumn();
        Callback<TableColumn<UserInTable, Void>, TableCell<UserInTable, Void>> cellFactory = new Callback<TableColumn<UserInTable, Void>, TableCell<UserInTable, Void>>() {
            @Override
            public TableCell<UserInTable, Void> call(final TableColumn<UserInTable, Void> param) {
                final TableCell<UserInTable, Void> cell = new TableCell<UserInTable, Void>() {
                    private final Button btn = new Button("delete");

                    {
                        btn.setMinWidth(75);
                        btn.setOnAction((ActionEvent event) -> {
                            deletingUser = getTableView().getItems().get(getIndex());
                            deleteUser();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        tableOfUsers.getColumns().add(colBtn);
    }

    private void deleteUser() {
        if (deletingUser.getUsername().equals(thisUserUsername)) {
            return;
        }
        Stage warningStage = new Stage();
        Group root = new Group();
        GridPane gridPane = new GridPane();
        gridPane.setMinHeight(200);
        gridPane.setMinWidth(200);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(new Label("Are you sure to delete " + deletingUser.getUsername() + "?"), 0, 0);
        Button cancel = new Button("Cancel");
        cancel.setMinWidth(100);
        cancel.setOnMouseClicked(e -> warningStage.close());
        gridPane.add(cancel, 0, 1);
        Button Ok = new Button("Delete");
        Ok.setMinWidth(100);
        Ok.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Runner.buttonSound();
                delete();
                warningStage.close();
            }
        });
        gridPane.add(Ok, 0, 2);
        root.getChildren().add(gridPane);
        warningStage.setScene(new Scene(root, 200, 200));
        warningStage.show();
    }

    private void delete() {
        JsonObject jsonObject = Runner.getInstance().jsonMaker("manager", "deleteUser");
        jsonObject.addProperty("username", deletingUser.getUsername());
        try {
            dataOutputStream.writeUTF(jsonObject.toString());
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        allUsersInTable.remove(deletingUser);
        setTableOfUsers();
    }

    public void back(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Runner.getInstance().back();
    }

    public void AddManager(MouseEvent mouseEvent) {
        Runner.buttonSound();
        DataBase.getInstance().isAddingManager = true;
        Runner.getInstance().changeScene("RegisterMenu.fxml");
    }

    public void addSupporter(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Runner.getInstance().changeScene("AddSupporter.fxml");
    }
}
