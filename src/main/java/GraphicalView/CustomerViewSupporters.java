package GraphicalView;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerViewSupporters implements Initializable {
    Runner runner = Runner.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//load supporters table
        //create chat diagram
    }

    public void back(ActionEvent actionEvent) {
        runner.back();
    }
}
