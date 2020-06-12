package GraphicalView;

import Controller.LoginController;
import Controller.SellerController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerUserArea implements Initializable {
    public Label personalInfo;
    public Button editPersonaInfo;
    public Button logout;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoutAlert();
        showPersonalInfo();
        editPersonalInfoAlert();
    }

    private void showPersonalInfo() {
        if (dataBase.user == null) {
            personalInfo.textProperty().setValue("no personal info yet!\n you have to log in first!");
            return;
        }
        String company = "";
        try {
            company = SellerController.getInstance().showCompanyInformation(dataBase.user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        StringBuilder toShow = new StringBuilder("personal information : \n");
        String[] information = LoginController.getInstance().showPersonalInformation(dataBase.user);
        toShow.append("first name : ").append(information[0]).append("\n");
        toShow.append("last name : ").append(information[1]).append("\n");
        toShow.append("username : ").append(information[2]).append("\n");
        toShow.append("email address: ").append(information[3]).append("\n");
        toShow.append("phone number : ").append(information[4]).append("\n");
        toShow.append("credit : ").append(information[6]).append("\n");
        toShow.append("company Info : ").append(company);
        personalInfo.textProperty().setValue(toShow.toString());
    }

    public void back(MouseEvent mouseEvent) {
        runner.back();
    }


    private void editPersonalInfoAlert() {
        if (dataBase.user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            EventHandler<ActionEvent> event = (e) -> alert.show();
            editPersonaInfo.setOnAction(event);
            alert.setContentText("You have to login");
        } else {
            EventHandler<ActionEvent> event = (e) -> runner.changeScene("EditPersonalInfo.fxml");
            editPersonaInfo.setOnAction(event);
        }
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
}
