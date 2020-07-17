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
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShowCategories implements Initializable {
    public Button logout;
    public Button login;
    public GridPane gridPane;
    static CategoryInTable categoryToEdit;
    public Button addCategory;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();
    ArrayList<CategoryInTable> allCategories = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showCategories();
        addCategory.setDisable(!(DataBase.getInstance().role.equals("manager")));
        addCategory.setOnMouseClicked(e -> runner.changeScene("AddCategory.fxml"));
        try {
            DataBase.getInstance().dataOutputStream.writeUTF(Runner.getInstance().jsonMaker("seller", "getAllCategories").toString());
            DataBase.getInstance().dataOutputStream.flush();
            String input = DataBase.getInstance().dataInputStream.readUTF();
            analyzeInput((JsonObject) new JsonParser().parse(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
        loginAlert();
        logoutAlert();
    }

    private void analyzeInput(JsonObject input) {
        for (JsonElement element : input.getAsJsonArray("categories")) {
            JsonObject category = element.getAsJsonObject();
            ArrayList<String> features = new ArrayList<>();
            for (JsonElement jsonElement : category.getAsJsonArray("features")) {
                features.add(jsonElement.getAsString());
            }
            allCategories.add(new CategoryInTable(category.get("name").getAsString(), features));
        }
    }



    private void showCategories() {
        TableColumn<BuyingLogShow, String> name = new TableColumn<>("name");
        name.setMinWidth(150);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<BuyingLogShow, String> properties = new TableColumn<>("properties");
        properties.setMinWidth(350);
        properties.setCellValueFactory(new PropertyValueFactory<>("specialProperties"));

        TableView tableView = new TableView();
        ObservableList<CategoryInTable> categories = FXCollections.observableArrayList(allCategories);
        tableView.getColumns().addAll(name, properties);
        addButtonToTable(tableView);
        tableView.setItems(categories);
        gridPane.getChildren().add(tableView);
    }

    private void addButtonToTable(TableView tableView) {
        TableColumn<CategoryInTable, Void> colBtn = new TableColumn();
        Callback<TableColumn<CategoryInTable, Void>, TableCell<CategoryInTable, Void>> cellFactory = new Callback<TableColumn<CategoryInTable, Void>, TableCell<CategoryInTable, Void>>() {
            @Override
            public TableCell<CategoryInTable, Void> call(final TableColumn<CategoryInTable, Void> param) {
                final TableCell<CategoryInTable, Void> cell = new TableCell<CategoryInTable, Void>() {
                    private final Button btn = new Button("edit");

                    {
                        btn.setMinWidth(75);
                        btn.setDisable(!(dataBase.role.equals("manager")));
                        btn.setOnAction((ActionEvent event) -> {
                            categoryToEdit = getTableView().getItems().get(getIndex());
                            runner.changeScene("EditCategory.fxml");
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
        tableView.getColumns().add(colBtn);
    }

    private void logoutAlert() {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        EventHandler<ActionEvent> event = (e) -> {
            Runner.buttonSound();
            message.setContentText("you logged out successfully");
            message.show();
            dataBase.logout();
        };
        logout.setOnAction(event);
    }

    public void back(ActionEvent actionEvent) {
        Runner.buttonSound();
        runner.back();
    }

    public void loginAlert() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        EventHandler<ActionEvent> event = (e) -> {
            Runner.buttonSound();
            if (dataBase.user != null) {
                error.setContentText("You have logged in!");
                error.show();
            } else {
                runner.changeScene("LoginMenu.fxml");
            }
        };
        login.setOnAction(event);
    }

    public void userArea(MouseEvent mouseEvent) {
        Runner.getInstance().setUserAreaScene();
    }
}
