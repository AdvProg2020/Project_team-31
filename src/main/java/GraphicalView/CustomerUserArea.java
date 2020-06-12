package GraphicalView;

import Controller.CustomerController;
import Controller.LoginController;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerUserArea implements Initializable {
    public Label personalInfo;
    public Label discountCode;
    public Button logout;
    public Button editPersonalInfo;
    public Button addBalance;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();
    StringProperty data = new SimpleStringProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showPersonalInfo();
        logoutAlert();
        editPersonalInfoAlert();
        addBalanceDialog();
    }

    private void showPersonalInfo() {
        StringBuilder toShow = new StringBuilder();
        String[] information = LoginController.getInstance().showPersonalInformation(dataBase.user);
        toShow.append("first name    : ").append(information[0]+"\n");
        toShow.append("last  name    : ").append(information[1]+"\n");
        toShow.append("username      : ").append(information[2]+"\n");
        toShow.append("email address : ").append(information[3]+"\n");
        toShow.append("phone number  : ").append(information[4]+"\n");
        toShow.append("credit        : ").append(information[5]+"\n");
        personalInfo.textProperty().setValue(toShow.toString()+"\n");
    }

    private void addBalanceDialog() {
        if (dataBase.user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            EventHandler<ActionEvent> event = (e) -> alert.show();
            addBalance.setOnAction(event);
            alert.setContentText("You have to login");
        } else {
            TextInputDialog getNumber = new TextInputDialog("how much do you want to add?");
            data.bind(getNumber.getEditor().textProperty());
            Button okButton = (Button) getNumber.getDialogPane().lookupButton(ButtonType.OK);
            EventHandler<ActionEvent> addBalanceEvent = (e) -> addBalance();
            okButton.setOnAction(addBalanceEvent);
            TextField inputField = getNumber.getEditor();
            BooleanBinding isInvalid = Bindings.createBooleanBinding(() -> isInvalid(inputField.getText()), inputField.textProperty());
            okButton.disableProperty().bind(isInvalid);
            EventHandler<ActionEvent> event = (e) -> getNumber.show();
            addBalance.setOnAction(event);
        }
    }

    private void addBalance() {
        CustomerController.getInstance().addCredit(dataBase.user, Integer.parseInt(data.getValue()));
    }

    private boolean isInvalid(String text) {
        try {
            Integer.parseInt(text);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    private void editPersonalInfoAlert() {
        if (dataBase.user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            EventHandler<ActionEvent> event = (e) -> alert.show();
            editPersonalInfo.setOnAction(event);
            alert.setContentText("You have to login");
        } else {
            EventHandler<ActionEvent> event = (e) -> runner.changeScene("EditPersonalInfo.fxml");
            editPersonalInfo.setOnAction(event);
        }
    }

    private void logoutAlert() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        EventHandler<ActionEvent> event = (e) -> {
            if (dataBase.user == null) {
                error.setContentText("You have to login");
                error.show();
            } else {
                message.setContentText("you logged out successfully");
                message.show();
                dataBase.logout();
            }
        };
        logout.setOnAction(event);
    }

    public void back(MouseEvent mouseEvent) {
        runner.back();
    }


    public void buyingHistory(ActionEvent actionEvent) {
        runner.changeScene("CustomerBuyingHistory.fxml");
    }

    public void showCart(ActionEvent actionEvent) {
        runner.changeScene("ShowCart.fxml");
    }
}
