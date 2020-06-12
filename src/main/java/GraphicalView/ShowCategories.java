package GraphicalView;

import Controller.ManagerController;
import com.sun.javafx.css.converters.StringConverter;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShowCategories implements Initializable {
    public Label buyingHistory;
    Runner runner = Runner.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCategories();
    }

    private void initCategories() {
        String toShow = "all categories : \n";
        ArrayList<String> allCategories = ManagerController.getInstance().showAllCategories();
        for (String category : allCategories) {
            toShow += category + "\n";
        }
        buyingHistory.setText(toShow);
    }

    public void back(ActionEvent actionEvent) {
        runner.back();
    }

}
