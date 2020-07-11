package GraphicalView;

import Controller.LoginController;
import Model.Supporter;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AddSupporter {
    public TextField usernameField;
    public TextField passwordField;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField emailField;
    public TextField phoneField;
    Runner runner = Runner.getInstance();

    public void back(MouseEvent mouseEvent) {
        runner.getInstance().back();
    }

    public void registerRequest(MouseEvent mouseEvent) {
        Runner.buttonSound();
        if (!isEmpty().equals("none")) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please enter " + isEmpty(), ButtonType.OK);
            error.show();
        } else if (!LoginController.getInstance().isUsernameFree(usernameField.getText())) {
            Alert error = new Alert(Alert.AlertType.ERROR, "This username is token before", ButtonType.OK);
            error.show();
        } else {
            String information[] = new String[6];
            information[0] = firstNameField.getText();
            information[1] = lastNameField.getText();
            information[2] = emailField.getText();
            information[3] = phoneField.getText();
            information[4] = passwordField.getText();
            try {
                createNewSupporter(usernameField.getText(), information);
                Runner.getInstance().back();
                new Alert(Alert.AlertType.INFORMATION, "new supporter registered successfully!", ButtonType.OK).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void createNewSupporter(String text, String[] information) {
        new Supporter(information[0], information[1], text, information[2], information[3], information[4]);
    }


    private String isEmpty() {
        if (usernameField.getText().equals(""))
            return "username";
        if (passwordField.getText().equals(""))
            return "password";
        if (firstNameField.getText().equals(""))
            return "firstName";
        if (lastNameField.getText().equals(""))
            return "lastName";
        if (emailField.getText().equals(""))
            return "email address";
        if (phoneField.getText().equals(""))
            return "phone number";
        return "none";
    }


}
