package GraphicalView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerUserArea implements Initializable {
    public Label personalInfo;
    public Label credit;
    public Label discountCode;
    public Button logout;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoutAlert();
    }

    private void logoutAlert() {
        if (dataBase.user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            EventHandler<ActionEvent> event = (e) -> alert.show();
            logout.setOnAction(event);
            alert.setContentText("You have to login");
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            EventHandler<ActionEvent> event = (e) -> alert.show();
            logout.setOnAction(event);
            dataBase.logout();
            alert.setContentText("you logged out successfully");
        }
    }

    public void back(MouseEvent mouseEvent) {
        runner.back();
    }

    public void editPersonalInfo(ActionEvent actionEvent) {
        runner.changeScene("EditPersonalInfo.fxml");
    }

    public void buyingHistory(ActionEvent actionEvent) {
        runner.changeScene("BuyingHistory.fxml");
    }
}
