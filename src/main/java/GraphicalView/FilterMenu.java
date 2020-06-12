package GraphicalView;

import Controller.ProductController;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.jws.soap.SOAPBinding;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FilterMenu implements Initializable {
    public VBox currentFilters;
    public ChoiceBox available, disable;
    public TextField valueOfFilter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HashMap<String, String> current = DataBase.getInstance().user.getFilters();
        setCurrentFilters();
        disable.setItems(FXCollections.observableList(current.keySet().stream()
                .collect(Collectors.toList())));
        available.setItems(FXCollections.observableList(ProductController.getInstance().showAvailableFiltersForUserGui(ProductsMenu.categoryName)));
    }

    private void setCurrentFilters() {
        HashMap<String, String> current = DataBase.getInstance().user.getFilters();
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
    }

    public void setNewFilter(MouseEvent mouseEvent) {
        if (available.getValue() == null) {
            Alert nextTurnError = new Alert(Alert.AlertType.ERROR, "select key please!", ButtonType.OK);
            nextTurnError.show();
        } else if (valueOfFilter.getText().equals("")) {
            Alert nextTurnError = new Alert(Alert.AlertType.ERROR, "enter value please!", ButtonType.OK);
            nextTurnError.show();
        } else{
            ProductController.getInstance().addFilterForUser(DataBase.getInstance().user, available.getValue().toString(), valueOfFilter.getText());
            setCurrentFilters();
        }
    }
}
