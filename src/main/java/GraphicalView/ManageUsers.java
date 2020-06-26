package GraphicalView;

import Controller.ManagerController;
import Model.User;
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

import java.net.URL;
import java.util.ResourceBundle;

public class ManageUsers implements Initializable {
    public TableColumn nameColumn;
    public TableView tableOfUsers;
    public TableColumn userNameColumn;
    public TableColumn lastColumn;
    public Button logout;
    private User deletingUser;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoutAlert();
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addButtonToTable();
        setTableOfUsers();
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
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        allUsers.addAll(User.getAllUsers());
        tableOfUsers.setItems(allUsers);
    }

    private void addButtonToTable() {
        TableColumn<User, Void> colBtn = new TableColumn();
        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {
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
        if(deletingUser.equals(DataBase.getInstance().user)) {
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
        ManagerController.getInstance().deleteUser(deletingUser.getUsername());
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
}
