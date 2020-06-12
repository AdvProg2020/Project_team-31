package GraphicalView;

import Controller.ManagerController;
import Model.Category;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductsMenu implements Initializable {
    public VBox listOfProducts;
    public Button showButton;
    public ChoiceBox sorting;
    public ChoiceBox category;

    public void showProducts(MouseEvent mouseEvent) {
        //showButton.setDisable(true);
        System.out.println(sorting.getValue());
        System.out.println(category.getValue());
        if (category.getValue().equals("Save"))
            System.out.println("equal");
    }

    public void changeSort(ActionEvent actionEvent) {
        System.out.println("hello");
    }

    public void setCategories(MouseEvent mouseEvent) {
        ArrayList<String> features = new ArrayList<String>();
        features.add("rom");
        ManagerController.getInstance().addCategory("mobile",features);
        category.setItems(FXCollections.observableArrayList(Category.getAllCategories()));
        System.out.println("category");
    }

    public void changeCategory(ActionEvent actionEvent) {
        if (category.getValue() == null)
            return;
        else
            System.out.println(category.getValue());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
