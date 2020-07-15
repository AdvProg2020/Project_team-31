package GraphicalView;

import Model.Seller;
import Model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditPersonalInfo implements Initializable {
    public TextField firstName;
    public TextField lastName;
    public PasswordField password;
    public TextField email;
    public TextField phoneNumber;
    public TextField companyName;
    public Button logout;
    public Button login;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initializeFields();
            loginAlert();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logoutAlert();
    }

    private void initializeFields() throws Exception {
        String[] userData = dataBase.getUserInfo();
        firstName.setText(userData[0]);
        lastName.setText(userData[1]);
        email.setText(userData[3]);
        phoneNumber.setText(userData[4]);
        password.setText(userData[5]);
        if (dataBase.user instanceof Seller)
            companyName.setText(showCompanyInformation(dataBase.user));
        else companyName.setText("PLEASE ENTER NOTHING!");
    }

    private String showCompanyInformation(User user) {
        try {
            dataBase.dataOutputStream.writeUTF(runner.jsonMaker("seller", "showCompanyInformation").toString());
            dataBase.dataOutputStream.flush();
            return runner.jsonParser(dataBase.dataInputStream.readUTF()).get("company").getAsString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
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

    public void back(ActionEvent actionEvent) {
        Runner.buttonSound();
        runner.back();
    }

    public void submit(ActionEvent actionEvent) {
        Runner.buttonSound();
        if (!isEmpty().equals("none")) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please enter a valid " + isEmpty(), ButtonType.OK);
            error.show();
        } else {
            editPersonalInformation(dataBase.user, createNewInfo());
            back(null);
        }

    }

    private void editPersonalInformation(User user, String[] newInfo) {
        try {
            JsonObject jsonObject=runner.jsonMaker("login","editPersonalInformation");
            jsonObject.addProperty("newInfo",new Gson().toJson(newInfo));
            dataBase.dataOutputStream.writeUTF(jsonObject.toString());
            dataBase.dataOutputStream.flush();
            dataBase.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] createNewInfo() {
        String[] newData = new String[6];
        newData[0] = firstName.getText();
        newData[1] = lastName.getText();
        newData[2] = email.getText();
        newData[3] = phoneNumber.getText();
        newData[4] = password.getText();
        if (dataBase.user instanceof Seller)
            newData[5] = companyName.getText();
        return newData;
    }

    public void loginAlert() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        EventHandler<ActionEvent> event = (e) -> {
            Runner.buttonSound();
            if (dataBase.user != null) {
                error.setContentText("You have logged in!");
                error.show();
            } else {
                runner.changeScene("LoginMenu.fxml");
            }
        };
        login.setOnAction(event);
    }

    private String isEmpty() {
        if (password.getText().equals("") || !password.getText().matches("^(?=.*\\d).{4,8}$"))
            return "password";
        if (firstName.getText().equals(""))
            return "firstName";
        if (lastName.getText().equals(""))
            return "lastName";
        if (email.getText().equals("") || !email.getText().matches("^(.+)@(.+)$"))
            return "email address";
        if (phoneNumber.getText().equals("") || !phoneNumber.getText().matches("^[0-9]{6,14}$"))
            return "phone number";
        if (dataBase.user instanceof Seller && companyName.getText().equals(""))
            return "company";
        return "none";
    }

    public void userArea(MouseEvent mouseEvent) {
        Runner.getInstance().setUserAreaScene();
    }
}
