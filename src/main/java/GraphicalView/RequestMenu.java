package GraphicalView;

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

import javax.xml.bind.util.JAXBSource;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RequestMenu implements Initializable {
    public TableColumn idColumn;
    public TableView tableOfRequest;
    public static String requestToView;
    public Button logout;
    public Button login;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginAlert();
        logoutAlert();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        addButton();
        try {
            DataBase.getInstance().dataOutputStream.writeUTF(Runner.getInstance().jsonMaker("manager","getAllRequests").toString());
            DataBase.getInstance().dataOutputStream.flush();
            String input = DataBase.getInstance().dataInputStream.readUTF();
            setTable((JsonObject) new JsonParser().parse(input));
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    private void setTable(JsonObject input) {
        ObservableList<StringAndButtonTable> requests = FXCollections.observableArrayList();
        for (JsonElement element : input.getAsJsonArray("requests")) {
            requests.add(new StringAndButtonTable(element.getAsString(), element.getAsString()));
        }
        tableOfRequest.setItems(requests);
    }

    private void addButton() {
        TableColumn<StringAndButtonTable, Void> colBtn = new TableColumn();
        Callback<TableColumn<StringAndButtonTable, Void>, TableCell<StringAndButtonTable, Void>> cellFactory = new Callback<TableColumn<StringAndButtonTable, Void>, TableCell<StringAndButtonTable, Void>>() {
            @Override
            public TableCell<StringAndButtonTable, Void> call(final TableColumn<StringAndButtonTable, Void> param) {
                final TableCell<StringAndButtonTable, Void> cell = new TableCell<StringAndButtonTable, Void>() {
                    private final Button btn = new Button("detail");

                    {
                        btn.setMinWidth(75);
                        btn.setOnAction((ActionEvent event) -> {
                            Runner.buttonSound();
                            requestToView = getTableView().getItems().get(getIndex()).getData();
                            showDetail();
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
        tableOfRequest.getColumns().add(colBtn);
    }

    private void showDetail() {
        Runner.buttonSound();
        Runner.getInstance().changeScene("RequestArea.fxml");
    }

    public void back(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Runner.getInstance().back();
    }
}
