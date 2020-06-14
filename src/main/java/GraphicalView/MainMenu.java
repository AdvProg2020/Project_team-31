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
    public Button login;
    DataBase dataBase = DataBase.getInstance();
    Runner runner = Runner.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//         if there is not any manager make one...
        ///////////////////////////////////////////////////////
        logoutAlert();
        loginAlert();
    }


    private void logoutAlert() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        EventHandler<ActionEvent> event = (e) -> {
            if (dataBase.user == null) {
                error.setContentText("You have not logged in!");
                error.show();
            } else {
                message.setContentText("you logged out successfully");
                message.show();
                dataBase.logout();
            }
        };
        logout.setOnAction(event);
    }

    public void userAreaChangeScene(MouseEvent mouseEvent) {
        runner.setUserAreaScene();
    }

    public void loginAlert() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        EventHandler<ActionEvent> event = (e) -> {
            if (dataBase.user != null) {
                error.setContentText("You have logged in!");
                error.show();
            } else {
                runner.changeScene("LoginMenu.fxml");
            }
        };
        login.setOnAction(event);
    }

    public void products(MouseEvent mouseEvent) {
        Runner.getInstance().changeScene("Products.fxml");
    }

    public void offs(MouseEvent mouseEvent) {
        Runner.getInstance().changeScene("OffMenu.fxml");
    }
}
