package GraphicalView;

import Controller.ManagerController;
import Model.DiscountCode;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditDiscountCode implements Initializable {
    public Button login;
    public Button logout;
    public TextField code;
    public DatePicker startDate;
    public DatePicker endDate;
    public TextField percentage;
    public TextField maximumPrice;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();
    DiscountCode discountCode = dataBase.editingDiscountCode;
    HashMap<String, Integer> usernameAndNumber = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initFields();
        loginAlert();
        logoutAlert();
        validate();
    }

    private void initFields() {
        code.setEditable(false);
        code.setText(discountCode.getDiscountCode());
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String date = format.format(discountCode.getBeginTime());
        startDate.getEditor().setText(date);
        date = format.format(discountCode.getEndTime());
        endDate.getEditor().setText(date);
        percentage.setText(String.valueOf(discountCode.getDiscountPercent()));
        maximumPrice.setText(String.valueOf(discountCode.getMaximumDiscount()));

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

    private void validate() {
        percentage.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[1-9][0-9]?$")) {
                percentage.setText(newValue.replaceAll(".$", ""));
            }
        });
        maximumPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                maximumPrice.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
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

    public void submit(ActionEvent actionEvent) throws Exception {
        if (isValid() != null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please enter a valid " + isValid(), ButtonType.OK);
            error.show();
        } else if (getEndDate().before(getStartDate())) {
            Alert error = new Alert(Alert.AlertType.ERROR, "end date is before start date!", ButtonType.OK);
            error.show();
        } else {
            ManagerController controller = ManagerController.getInstance();
            int percent = Integer.parseInt(percentage.getText());
            int maximum = Integer.parseInt(maximumPrice.getText());
            controller.editDiscountCode(code.getText(), getStartDate(), getEndDate(), percent, maximum, usernameAndNumber);
        }
    }

    private String isValid() {
        if (code.getText().equals(""))
            return "code";
        else if (startDate.getEditor().getText().equals(""))
            return "start date";
        else if (endDate.getEditor().getText().equals(""))
            return "end date";
        else if (percentage.getText().equals(""))
            return "percentage";
        else if (maximumPrice.getText().equals(""))
            return "maximum price";
        return null;
    }

    public void removeCustomer(ActionEvent actionEvent) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("add customer");
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));
        TextField username = new TextField();
        username.setPromptText("customer username"); //useful
        TextField number = new TextField();
        number.setPromptText("number");
        number.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                number.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        gridPane.add(username, 0, 0);
        gridPane.add(number, 2, 0);
        dialog.getDialogPane().setContent(gridPane);
        Platform.runLater(() -> username.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), number.getText());
            }
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(pair -> {
            if (pair.getKey().equals("") || pair.getValue().equals(""))
                return;
            usernameAndNumber.remove(pair.getKey(), Integer.parseInt(pair.getValue()));
        });
    }

    public void addCustomer(ActionEvent actionEvent) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("add customer");
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));
        TextField username = new TextField();
        username.setPromptText("customer username"); //useful
        TextField number = new TextField();
        number.setPromptText("number");
        number.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                number.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        gridPane.add(username, 0, 0);
        gridPane.add(number, 2, 0);
        dialog.getDialogPane().setContent(gridPane);
        Platform.runLater(() -> username.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), number.getText());
            }
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(pair -> {
            if (pair.getKey().equals("") || pair.getValue().equals(""))
                return;
            usernameAndNumber.put(pair.getKey(), Integer.parseInt(pair.getValue()));
        });
    }

}
