package GraphicalView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateDiscountCode implements Initializable {
    public TextField code;
    public Button logout;
    public DatePicker startDate;
    public DatePicker endDate;
    public TextField percentage;
    public TextField maximumPrice;
    public Button login;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();
    HashMap<String, Integer> usernameAndNumber = new HashMap<>();
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    ArrayList<String> customers = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoutAlert();
        validate();
        loginAlert();
        dataInputStream = DataBase.getInstance().dataInputStream;
        dataOutputStream = DataBase.getInstance().dataOutputStream;
        try {
            dataOutputStream.writeUTF(Runner.getInstance().jsonMaker("manager", "getAllCustomers").toString());
            dataOutputStream.flush();
            String input = dataInputStream.readUTF();
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(input);
            for (JsonElement element : jsonObject.getAsJsonArray("customers")) {
                customers.add(element.getAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void back(ActionEvent actionEvent) {
        Runner.buttonSound();
        runner.back();
    }

    public void addCustomer(ActionEvent actionEvent) {
        Runner.buttonSound();
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
            if (!customers.contains(pair.getKey())) {
                Alert error = new Alert(Alert.AlertType.ERROR, "there is not any customer with this username", ButtonType.OK);
                error.show();
                return;
            }
            usernameAndNumber.put(pair.getKey(), Integer.parseInt(pair.getValue()));
            Alert error = new Alert(Alert.AlertType.INFORMATION, "customer added successfully", ButtonType.OK);
            error.show();
        });
    }

    public void submit(ActionEvent actionEvent) throws Exception {
        Runner.buttonSound();
        if (isValid() != null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please enter a valid " + isValid(), ButtonType.OK);
            error.show();
        } else if (getEndDate().before(getStartDate())) {
            Alert error = new Alert(Alert.AlertType.ERROR, "end date is before start date!", ButtonType.OK);
            error.show();
        } else {
            int percent = Integer.parseInt(percentage.getText());
            int maximum = Integer.parseInt(maximumPrice.getText());
            JsonObject output = Runner.getInstance().jsonMaker("manager", "createDiscountCode");
            output.addProperty("code", code.getText());
            output.addProperty("startDate", getStartDate().toString());
            output.addProperty("endDate", getEndDate().toString());
            output.addProperty("percent", percent);
            output.addProperty("maximum", maximum);
            JsonArray customerUsing = new JsonArray();
            for (String s : usernameAndNumber.keySet()) {
                JsonObject user = new JsonObject();
                user.addProperty("username", s);
                user.addProperty("number", usernameAndNumber.get(s));
                customerUsing.add(user);
            }
            output.add("customers", customerUsing);
            dataOutputStream.writeUTF(output.toString());
            dataOutputStream.flush();
            String input = dataInputStream.readUTF();
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(input);
            Alert error;
            if(jsonObject.get("type").getAsString().equals("failed")){
                error = new Alert(Alert.AlertType.ERROR, jsonObject.get("message").getAsString(), ButtonType.OK);
            } else {
                error = new Alert(Alert.AlertType.INFORMATION, "discount code created successfully!", ButtonType.OK);
                runner.back();
            }
            error.show();
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

    public void loginAlert() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        EventHandler<ActionEvent> event = (e) -> {
            if (!DataBase.getInstance().role.equals("none")) {
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
            Runner.buttonSound();
            message.setContentText("you logged out successfully");
            message.show();
            dataBase.logout();
        };
        logout.setOnAction(event);
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

    public void userArea(MouseEvent mouseEvent) {
        Runner.getInstance().setUserAreaScene();
    }
}
