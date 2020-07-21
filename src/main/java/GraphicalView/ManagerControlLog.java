package GraphicalView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManagerControlLog implements Initializable {

    public TableView tableOfLogs;
    public TableColumn idColumn;
    public TableColumn addressColumn;
    public Button login;
    public Button logout;
    private ArrayList<ManageBuyingLogsModel> allLogs = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginAlert();
        logoutAlert();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addButton();
        try {
            DataBase.getInstance().dataOutputStream.writeUTF(Runner.getInstance().jsonMaker("manager", "getAllLogs").toString());
            DataBase.getInstance().dataOutputStream.flush();
            String input = DataBase.getInstance().dataInputStream.readUTF();
            analyzeInput((JsonObject) new JsonParser().parse(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setTableOfLogs();
    }

    private void analyzeInput(JsonObject input) {
        JsonArray logs = input.getAsJsonArray("logs");
        for (JsonElement element : logs) {
            JsonObject log = element.getAsJsonObject();
            allLogs.add(new ManageBuyingLogsModel(log.get("id").getAsString(), log.get("address").getAsString()));
        }
    }

    private void setTableOfLogs() {
        ObservableList<ManageBuyingLogsModel> logs = FXCollections.observableArrayList();
        logs.addAll(allLogs);
        tableOfLogs.setItems(logs);
    }

    private void addButton() {
        TableColumn<ManageBuyingLogsModel, Void> colBtn = new TableColumn();
        Callback<TableColumn<ManageBuyingLogsModel, Void>, TableCell<ManageBuyingLogsModel, Void>> cellFactory = new Callback<TableColumn<ManageBuyingLogsModel, Void>, TableCell<ManageBuyingLogsModel, Void>>() {
            @Override
            public TableCell<ManageBuyingLogsModel, Void> call(final TableColumn<ManageBuyingLogsModel, Void> param) {
                final TableCell<ManageBuyingLogsModel, Void> cell = new TableCell<ManageBuyingLogsModel, Void>() {
                    private final Button btn = new Button("send");

                    {
                        btn.setMinWidth(75);
                        btn.setOnAction((ActionEvent event) -> {
                            Runner.buttonSound();
                            changeStatus(getTableView().getItems().get(getIndex()));
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
        tableOfLogs.getColumns().add(colBtn);
    }

    private void changeStatus(ManageBuyingLogsModel log) {
        JsonObject output = Runner.getInstance().jsonMaker("manager", "sendProduct");
        output.addProperty("id", log.getId());
        try {
            DataBase.getInstance().dataOutputStream.writeUTF(output.toString());
            DataBase.getInstance().dataOutputStream.flush();
            DataBase.getInstance().dataInputStream.readUTF();
            allLogs.remove(log);
            setTableOfLogs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void back(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Runner.getInstance().back();
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

}
