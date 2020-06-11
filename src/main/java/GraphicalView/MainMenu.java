package GraphicalView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {
    public Button logout;
    public Button userArea;
    DataBase dataBase = DataBase.getInstance();
    Runner runner = Runner.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//         if there is not any manager make one...
        ///////////////////////////////////////////////////////
        logoutAlert();
    }


    private void logoutAlert() {
        if (dataBase.user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            EventHandler<ActionEvent> event = (e) -> alert.show();
            logout.setOnAction(event);
            alert.setContentText("You have to login");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            EventHandler<ActionEvent> event = (e) -> alert.show();
            logout.setOnAction(event);
            dataBase.logout();
            alert.setContentText("you logged out successfully");
        }
    }

    public void userAreaChangeScene(MouseEvent mouseEvent) {
        runner.setUserAreaScene();
    }
}
