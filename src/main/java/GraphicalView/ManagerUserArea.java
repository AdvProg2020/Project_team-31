package GraphicalView;

import Controller.LoginController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerUserArea implements Initializable {
    public Label personalInfo;
    public Button logout;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoutAlert();
        showPersonalInfo();
    }

    private void logoutAlert() {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        EventHandler<ActionEvent> event = (e) -> {
            Runner.buttonSound();
            message.setContentText("you logged out successfully");
            message.show();
            dataBase.logout();
        };
        logout.setOnAction(event);
    }

    public void back(MouseEvent mouseEvent) {
        Runner.buttonSound();
        runner.back();
    }

    public void editPersonalInfo(ActionEvent actionEvent) {
        Runner.buttonSound();
        runner.changeScene("EditPersonalInfo.fxml");
    }

    private void showPersonalInfo() {
        if (dataBase.user == null) {
            personalInfo.textProperty().setValue("no personal info yet!\n you have to log in first!");
            return;
        }
        StringBuilder toShow = new StringBuilder("personal information : \n");
        String[] information = LoginController.getInstance().showPersonalInformation(dataBase.user);
        toShow.append("first name : ").append(information[0]).append("\n");
        toShow.append("last name : ").append(information[1]).append("\n");
        toShow.append("username : ").append(information[2]).append("\n");
        toShow.append("email address: ").append(information[3]).append("\n");
        toShow.append("phone number : ").append(information[4]).append("\n");
        personalInfo.textProperty().setValue(toShow.toString());
    }

    public void createDiscountCode(ActionEvent actionEvent) {
        Runner.buttonSound();
        runner.changeScene("CreateDiscountCode.fxml");
    }

    public void manageUsers(ActionEvent actionEvent) {
        Runner.buttonSound();
        runner.changeScene("ManageUsers.fxml");
    }

    public void manageRequests(ActionEvent actionEvent) {
        Runner.buttonSound();
        runner.changeScene("RequestMenu.fxml");
    }

    public void manageCategories(ActionEvent actionEvent) {
        Runner.buttonSound();
        runner.changeScene("ShowCategories.fxml");
    }

    public void manageAllProducts(ActionEvent actionEvent) {
        Runner.buttonSound();
        runner.changeScene("ManageAllProducts.fxml");
    }

    public void viewDiscountCodes(ActionEvent actionEvent) {
        Runner.buttonSound();
        runner.changeScene("ManagerViewDiscountCodes.fxml");
    }
}
