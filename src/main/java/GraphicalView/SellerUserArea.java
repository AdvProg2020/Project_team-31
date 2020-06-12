package GraphicalView;

import Controller.LoginController;
import Controller.SellerController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerUserArea implements Initializable {
    public Label personalInfo;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showPersonalInfo();
    }

    private void showPersonalInfo() {
        if (dataBase.user == null) {
            personalInfo.textProperty().setValue("no personal info yet!\n you have to log in first!");
            return;
        }
        String company="";
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


}
