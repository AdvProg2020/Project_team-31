package GraphicalView;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditPersonalInfo implements Initializable {
    public TextField firstName;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] userData = dataBase.getUserInfo();
        firstName.setText("mamal");
    }

    public void back(ActionEvent actionEvent) {
        runner.back();
    }

    public void submit(ActionEvent actionEvent) {
        // TODO
    }

}
