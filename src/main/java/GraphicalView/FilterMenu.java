package GraphicalView;
import com.google.gson.JsonObject;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FilterMenu implements Initializable {
    public VBox currentFilters;
    public ChoiceBox available, disable;
    public TextField valueOfFilter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCurrentFilters();
        available.setItems(FXCollections.observableList(showAvailableFiltersForUserGui(ProductsMenu.categoryName)));
    }

    private ArrayList<String> showAvailableFiltersForUserGui(String categoryName) {
        ArrayList<String> availableFilters = new ArrayList<>();
        if (!categoryName.equals("all"))
            availableFilters.addAll(ProductsMenu.categoriesHash.get(categoryName));
        availableFilters.addAll(Arrays.asList("minimumPrice", "company", "name", "rate", "availability"));
        return availableFilters;
    }

    private void setCurrentFilters() {
        currentFilters.getChildren().clear();
        HashMap<String, String> current = ProductsMenu.filters;
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
            ProductsMenu.filters.put(available.getValue().toString(), valueOfFilter.getText());
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
            ProductsMenu.filterProducts();
        }
    }

    public void removeFilter(MouseEvent mouseEvent) {
        Runner.buttonSound();
        if (disable.getValue() == null) {
            Alert emptyField = new Alert(Alert.AlertType.ERROR, "select key please!", ButtonType.OK);
            emptyField.show();
            return;
        }
        ProductsMenu.filters.remove(disable.getValue().toString());
        JsonObject output = Runner.getInstance().jsonMaker("product", "removeFilter");
        output.addProperty("key", disable.getValue().toString());
        try {
            DataBase.getInstance().dataOutputStream.writeUTF(output.toString());
            DataBase.getInstance().dataOutputStream.flush();
            DataBase.getInstance().dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //ProductController.getInstance().disableFilterForUser(user, disable.getValue().toString());
        setCurrentFilters();
        ProductsMenu.filterProducts();
    }

    public void exit(MouseEvent mouseEvent) {
        Runner.buttonSound();
        ProductsMenu.filterStageToSave.close();
    }
}
