package GraphicalView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerUserArea implements Initializable {
    public Label personalInfo;
    public Label discountCode;
    public Button logout;
    public Button editPersonalInfo;
    public Button addBalance;
    public Button buyingHistoryButton;
    public Button login;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();
    StringProperty data = new SimpleStringProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        runner.changeMusic("UserArea");
        showPersonalInfo();
        showDiscountCodes();
        editPersonalInfoAlert();
        addBalanceDialog();
        buyingHistory();
        loginAlert();
        logoutAlert();
    }

    private void showDiscountCodes() {
        if (dataBase.role.equals("none")) {
            discountCode.textProperty().setValue("no discount code yet!\n you have to log in first!");
            return;
        }
        ArrayList<String> information = ShowDiscountCodes();
        String toShow = "your discount codes : \n";
        for (String info : information)
            toShow += info + "\n";
        discountCode.textProperty().setValue(toShow);
    }

    private ArrayList<String> ShowDiscountCodes() {
        try {
            JsonObject jsonObject = runner.jsonMaker("customer", "showDiscountCodes");
            dataBase.dataOutputStream.writeUTF(jsonObject.toString());
            dataBase.dataOutputStream.flush();
            String data = dataBase.dataInputStream.readUTF();
            String[] codes = new Gson().fromJson(runner.jsonParser(data).get("content").getAsString(), String[].class);
            ArrayList<String> answer = new ArrayList<String>();
            answer.addAll(Arrays.asList(codes));
            return answer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showPersonalInfo() {
        if (dataBase.role.equals("none")) {
            personalInfo.textProperty().setValue("no personal info yet!\n you have to log in first!");
            return;
        }
        StringBuilder toShow = new StringBuilder("personal information : \n");
        String[] information = showPersonalInformation();
        toShow.append("first name : ").append(information[0]).append("\n");
        toShow.append("last name : ").append(information[1]).append("\n");
        toShow.append("username : ").append(information[2]).append("\n");
        toShow.append("email address: ").append(information[3]).append("\n");
        toShow.append("phone number : ").append(information[4]).append("\n");
        toShow.append("credit : ").append(information[6]).append("\n");
        personalInfo.textProperty().setValue(toShow.toString());
    }

    private String[] showPersonalInformation() {
        try {
            JsonObject jsonObject = runner.jsonMaker("login", "showPersonalInformation");
            dataBase.dataOutputStream.writeUTF(jsonObject.toString());
            dataBase.dataOutputStream.flush();
            JsonObject jsonObject1 = runner.jsonParser(dataBase.dataInputStream.readUTF());
            String[] info = new Gson().fromJson(jsonObject1.get("info").getAsString(), String[].class);
            return info;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addBalanceDialog() {
        if (dataBase.role.equals("none")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            EventHandler<ActionEvent> event = (e) -> {
                Runner.buttonSound();
                alert.show();
            };
            addBalance.setOnAction(event);
            alert.setContentText("You have to login");
        } else {
            TextInputDialog getNumber = new TextInputDialog();
            getNumber.getEditor().setPromptText("how mach do you want to add?");
            data.bind(getNumber.getEditor().textProperty());
            Button okButton = (Button) getNumber.getDialogPane().lookupButton(ButtonType.OK);
            EventHandler<ActionEvent> addBalanceEvent = (e) -> {
                Runner.buttonSound();
                addBalance();
            };
            okButton.setOnAction(addBalanceEvent);
            TextField inputField = getNumber.getEditor();
            BooleanBinding isInvalid = Bindings.createBooleanBinding(() -> isInvalid(inputField.getText()), inputField.textProperty());
            okButton.disableProperty().bind(isInvalid);
            EventHandler<ActionEvent> event = (e) -> {
                Runner.buttonSound();
                getNumber.show();
            };
            addBalance.setOnAction(event);
        }
    }

    private void addBalance() {
        try {
            JsonObject jsonObject = runner.jsonMaker("customer", "addCredit");
            jsonObject.addProperty("amount", data.getValue());
            dataBase.dataOutputStream.writeUTF(jsonObject.toString());
            dataBase.dataOutputStream.flush();
            dataBase.dataInputStream.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        }
        showPersonalInfo();
    }

    private boolean isInvalid(String text) {
        try {
            if (Integer.parseInt(text) > 0)
                return false;
        } catch (Exception ignored) {
        }
        return true;

    }

    private void editPersonalInfoAlert() {
        if (dataBase.role.equals("none")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            EventHandler<ActionEvent> event = (e) -> {
                Runner.buttonSound();
                alert.show();
            };
            editPersonalInfo.setOnAction(event);
            alert.setContentText("You have to login");
        } else {
            EventHandler<ActionEvent> event = (e) -> {
                Runner.buttonSound();
                runner.changeScene("EditPersonalInfo.fxml");
            };
            editPersonalInfo.setOnAction(event);
        }
    }

    private void logoutAlert() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        EventHandler<ActionEvent> event = (e) -> {
            Runner.buttonSound();
            if (DataBase.getInstance().role.equals("none")) {
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

    public void back(MouseEvent mouseEvent) {
        Runner.buttonSound();
        runner.back();
    }


    public void buyingHistory() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        EventHandler<ActionEvent> event = (e) -> {
            Runner.buttonSound();
            if (dataBase.role.equals("none")) {
                error.setContentText("You have not logged in!");
                error.show();
            } else {
                runner.changeScene("CustomerBuyingHistory.fxml");
            }
        };
        buyingHistoryButton.setOnAction(event);
    }

    public void loginAlert() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        EventHandler<ActionEvent> event = (e) -> {
            Runner.buttonSound();
            if (!dataBase.role.equals("none")) {
                error.setContentText("You have logged in!");
                error.show();
            } else {
                runner.changeScene("LoginMenu.fxml");
            }
        };
        login.setOnAction(event);
    }

    public void showCart(ActionEvent actionEvent) {
        Runner.buttonSound();
        runner.changeScene("ShowCart.fxml");
    }

    public void support(ActionEvent actionEvent) {
        runner.changeScene("CustomerViewSupporters.fxml");
    }
}
