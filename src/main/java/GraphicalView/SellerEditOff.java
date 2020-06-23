package GraphicalView;

import Controller.SellerController;
import Model.Off;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SellerEditOff implements Initializable {
    public TextField percentage;
    public DatePicker endDate;
    public DatePicker startDate;
    public Button login;
    public Button logout;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();
    ArrayList<String> products;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginAlert();
        logoutAlert();
        initFields();
    }

    private void initFields() {
        Off off=dataBase.editingOff;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String date = format.format(off.getBeginTime());
        startDate.getEditor().setText(date);
        date = format.format(off.getEndTime());
        endDate.getEditor().setText(date);
        percentage.setText(String.valueOf(off.getOffPercent()));
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
            sellerController.editOff(dataBase.user, dataBase.editingOff.getOffId(), products, getStartDate(), getEndDate(), Integer.parseInt(percentage.getText()));
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
        result.ifPresent(id -> products.add(id));
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

    public void removeProduct(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.getEditor().setPromptText("product ID");
        dialog.setTitle("remove product");
        dialog.setContentText("product ID : ");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(id -> products.remove(id));
    }
}
