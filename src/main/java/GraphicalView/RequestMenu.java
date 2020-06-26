package GraphicalView;

import Model.Product;
import Model.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class RequestMenu implements Initializable {
    public TableColumn idColumn;
    public TableView tableOfRequest;
    public static Request request;
    public Button logout;
    public Button login;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginAlert();
        logoutAlert();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        addButton();
        setTable();
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

    private void setTable() {
        ObservableList<Request> requests = FXCollections.observableArrayList();
        requests.addAll(Request.getAllRequest());
        tableOfRequest.setItems(requests);
    }

    private void addButton() {
        TableColumn<Request, Void> colBtn = new TableColumn();
        Callback<TableColumn<Request, Void>, TableCell<Request, Void>> cellFactory = new Callback<TableColumn<Request, Void>, TableCell<Request, Void>>() {
            @Override
            public TableCell<Request, Void> call(final TableColumn<Request, Void> param) {
                final TableCell<Request, Void> cell = new TableCell<Request, Void>() {
                    private final Button btn = new Button("detail");

                    {
                        btn.setMinWidth(75);
                        btn.setOnAction((ActionEvent event) -> {
                            Runner.buttonSound();
                            request = getTableView().getItems().get(getIndex());
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
