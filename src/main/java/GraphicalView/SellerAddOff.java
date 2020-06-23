package GraphicalView;

import Controller.ManagerController;
import Controller.SellerController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class SellerAddOff implements Initializable {
    public Button login;
    public Button logout;
    public DatePicker startDate;
    public DatePicker endDate;
    public TextField percentage;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();
    ArrayList<String> products = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoutAlert();
        loginAlert();
    }

    public void back(ActionEvent actionEvent) {
        runner.back();
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

    private void logoutAlert() {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        EventHandler<ActionEvent> event = (e) -> {
            message.setContentText("you logged out successfully");
            message.show();
            dataBase.logout();
        };
        logout.setOnAction(event);
    }

    public void submit(ActionEvent actionEvent) throws Exception {
        if (isInvalid() != null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please enter a valid " + isInvalid(), ButtonType.OK);
            error.show();
        } else if (getEndDate().before(getStartDate())) {
            Alert error = new Alert(Alert.AlertType.ERROR, "end date is before start date!", ButtonType.OK);
            error.show();
        } else if (getStartDate().before(new Date())) {
            Alert error = new Alert(Alert.AlertType.ERROR, "start date has passed!", ButtonType.OK);
            error.show();
        } else {
            SellerController sellerController = SellerController.getInstance();
            sellerController.addOff(dataBase.user, products, getStartDate(), getEndDate(), Integer.parseInt(percentage.getText()));
        }
    }

    private Date getStartDate() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        String dateString = startDate.getEditor().getText() + " 00:00";
        return format.parse(dateString);
    }

    private Date getEndDate() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm");
        String dateString = endDate.getEditor().getText() + " 23:59";
        return format.parse(dateString);
    }

    public void addProduct(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.getEditor().setPromptText("product ID");
        dialog.setTitle("add product");
        dialog.setContentText("product ID : ");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(id ->products.add(id));
    }

    private String isInvalid() {
        if (startDate.getEditor().getText().equals(""))
            return "start date";
        else if (endDate.getEditor().getText().equals(""))
            return "end date";
        else if (percentage.getText().equals(""))
            return "percentage";
        return null;
    }
}
