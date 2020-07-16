package GraphicalView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

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
            objects.add(new StringAndButtonTable(supporter, "connect"));
        return objects;
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
