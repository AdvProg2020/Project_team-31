package GraphicalView;

import Model.Customer;
import Model.Manager;
import Model.Seller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {
    public Button mainMenuLogout;
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
        Alert alert = new Alert(Alert.AlertType.ERROR);
        EventHandler<ActionEvent> event = (e) -> alert.show();
        if (dataBase.user == null)
            mainMenuLogout.setOnAction(event);
        alert.setContentText("You have to login");
    }

    public void userAreaChangeScene(MouseEvent mouseEvent) {
        try {
            runner.setUserAreaScene();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
