package GraphicalView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {
    public Button mainMenuLogout;
    public Button userArea;
    DataBase dataBase = DataBase.getInstance();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // if there is not any manager make one...
        /////////////////////////////////////////////////////////
        userAreaAlert();
        logoutAlert();

    }

    private void userAreaAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        EventHandler<ActionEvent> event = (e) -> alert.show();
        if (dataBase.user == null)
            userArea.setOnAction(event);
        alert.setContentText("You have to login");
    }

    private void logoutAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        EventHandler<ActionEvent> event = (e) -> alert.show();
        if (dataBase.user == null)
            mainMenuLogout.setOnAction(event);
        alert.setContentText("You have to login");
    }

}
