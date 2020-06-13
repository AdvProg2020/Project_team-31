package GraphicalView;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateDiscountCode implements Initializable {
    public TextField code;
    public Button logout;
    public DatePicker startDate;
    public DatePicker endDate;
    public TextField percentage;
    public TextField maximumPrice;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoutAlert();
        validate();
//        code.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue,
//                                String newValue) {
//                if (!newValue.matches("\\d*")) {
//                    code.setText(newValue.replaceAll("[^\\d]", ""));
//                }
//            }
//        });
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
        runner.back();
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
        gridPane.add(new Label("To:"), 1, 0);
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
            System.out.println("From=" + pair.getKey() + ", To=" + pair.getValue());
        });
    }

    public void submit(ActionEvent actionEvent) {

    }

    public void login(ActionEvent actionEvent) {

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
}
