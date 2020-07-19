package GraphicalView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

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
        Runner.buttonSound();
        runner.back();
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

    public void submit(ActionEvent actionEvent) throws Exception {
        Runner.buttonSound();
        if (isInvalid() != null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please enter a valid " + isInvalid(), ButtonType.OK);
            error.show();
        } else if (getEndDate().before(getStartDate())) {
            Alert error = new Alert(Alert.AlertType.ERROR, "end date is before start date!", ButtonType.OK);
            error.show();
        } else {
            addOff(products);
        }
    }

    private void addOff(ArrayList<String> products) {
        JsonObject jsonObject = runner.jsonMaker("seller", "addOff");
        String[] productZ = new String[products.size()];
        for (int i = 0; i < productZ.length; i++)
            productZ[i] = products.get(i);
        jsonObject.addProperty("products", new Gson().toJson(productZ));
        jsonObject.addProperty("startDate", startDate.getEditor().getText() + " 00:00");
        jsonObject.addProperty("endDate", endDate.getEditor().getText() + " 23:59");
        jsonObject.addProperty("percentage", percentage.getText());
        try {
            dataBase.dataOutputStream.writeUTF(jsonObject.toString());
            dataBase.dataOutputStream.flush();
            dataBase.dataInputStream.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
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
        Runner.buttonSound();
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

    public void userArea(MouseEvent mouseEvent) {
        Runner.getInstance().setUserAreaScene();
    }
}
