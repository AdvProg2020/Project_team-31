package GraphicalView;

import com.google.gson.JsonObject;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FilterOffs implements Initializable {
    public VBox currentFilters;
    public ChoiceBox available, disable;
    public TextField valueOfFilter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCurrentFilters();
        available.setItems(FXCollections.observableList(Arrays.asList("minimumPrice", "company", "name", "rate", "availability")));
    }

    private void setCurrentFilters() {
        currentFilters.getChildren().clear();
        HashMap<String, String> current = OffMenu.filters;
        Label key = new Label("Key");
        Label value = new Label("Value");
        key.setMinWidth(100);
        value.setMinWidth(100);
        HBox title = new HBox();
        title.getChildren().addAll(key, value);
        currentFilters.getChildren().add(title);
        for (String s : current.keySet()) {
            HBox aFilter = new HBox();
            Label filterKey = new Label(s);
            filterKey.setMinWidth(100);
            Label filterValue = new Label(current.get(s));
            filterValue.setMinWidth(100);
            aFilter.getChildren().addAll(filterKey, filterValue);
            currentFilters.getChildren().add(aFilter);
        }
        disable.setItems(FXCollections.observableList(current.keySet().stream()
                .collect(Collectors.toList())));
    }

    public void setNewFilter(MouseEvent mouseEvent) {
        Runner.buttonSound();
        if (available.getValue() == null) {
            Alert emptyField = new Alert(Alert.AlertType.ERROR, "select key please!", ButtonType.OK);
            emptyField.show();
        } else if (valueOfFilter.getText().equals("")) {
            Alert emptyField = new Alert(Alert.AlertType.ERROR, "enter value please!", ButtonType.OK);
            emptyField.show();
        } else {
            OffMenu.filters.put(available.getValue().toString(), valueOfFilter.getText());
            JsonObject output = Runner.getInstance().jsonMaker("product", "addFilter");
            output.addProperty("key", available.getValue().toString());
            output.addProperty("value", valueOfFilter.getText());
            try {
                DataBase.getInstance().dataOutputStream.writeUTF(output.toString());
                DataBase.getInstance().dataOutputStream.flush();
                DataBase.getInstance().dataInputStream.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
            setCurrentFilters();
            OffMenu.setOffedProducts();
        }
    }

    public void removeFilter(MouseEvent mouseEvent) {
        Runner.buttonSound();
        if (disable.getValue() == null) {
            Alert emptyField = new Alert(Alert.AlertType.ERROR, "select key please!", ButtonType.OK);
            emptyField.show();
            return;
        }
        OffMenu.filters.remove(available.getValue().toString());
        JsonObject output = Runner.getInstance().jsonMaker("product", "removeFilter");
        output.addProperty("key", available.getValue().toString());
        try {
            DataBase.getInstance().dataOutputStream.writeUTF(output.toString());
            DataBase.getInstance().dataOutputStream.flush();
            DataBase.getInstance().dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setCurrentFilters();
        OffMenu.setOffedProducts();
    }

    public void exit(MouseEvent mouseEvent) {
        Runner.buttonSound();
        OffMenu.filterStageToSave.close();
    }
}
