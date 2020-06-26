package GraphicalView;

import Controller.ProductController;
import Model.User;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FilterOffs implements Initializable {
    public VBox currentFilters;
    public ChoiceBox available, disable;
    public TextField valueOfFilter;
    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (DataBase.getInstance().user != null)
            user = DataBase.getInstance().user;
        else
            user = DataBase.getInstance().tempUser;
        setCurrentFilters();
        available.setItems(FXCollections.observableList(ProductController.getInstance().showAvailableFiltersForUserGui("all")));
    }

    private void setCurrentFilters() {
        currentFilters.getChildren().clear();
        HashMap<String, String> current = user.getFilters();
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
            ProductController.getInstance().addFilterForUser(user, available.getValue().toString(), valueOfFilter.getText());
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
        try {
            ProductController.getInstance().disableFilterForUser(user, disable.getValue().toString());
            setCurrentFilters();
            OffMenu.setOffedProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exit(MouseEvent mouseEvent) {
        Runner.buttonSound();
        OffMenu.filterStageToSave.close();
    }
}
