package GraphicalView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
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
    ArrayList<String> products = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loginAlert();
            logoutAlert();
            initFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initFields() throws Exception {
        JsonObject jsonObject = getOffInfoForEdit();
        String[] productZ = new Gson().fromJson(jsonObject.get("products").getAsString(), String[].class);
        products.addAll(Arrays.asList(productZ));
        startDate.getEditor().setText(jsonObject.get("startDate").getAsString());
        endDate.getEditor().setText(jsonObject.get("endDate").getAsString());
        percentage.setText(jsonObject.get("percentage").getAsString());
    }

    private JsonObject getOffInfoForEdit() {
        try {
            JsonObject jsonObject = runner.jsonMaker("seller", "getOffInfoForEdit");
            jsonObject.addProperty("id", dataBase.editingOffId);
            dataBase.dataOutputStream.writeUTF(jsonObject.toString());
            dataBase.dataOutputStream.flush();
            return runner.jsonParser(dataBase.dataInputStream.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void back(ActionEvent actionEvent) {
        Runner.buttonSound();
        runner.back();
    }

    public void loginAlert() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        EventHandler<ActionEvent> event = (e) -> {
            Runner.buttonSound();
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

    public void submit(ActionEvent actionEvent) throws Exception {
        Runner.buttonSound();
        if (isInvalid() != null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please enter a valid " + isInvalid(), ButtonType.OK);
            error.show();
        } else if (getEndDate().before(getStartDate())) {
            Alert error = new Alert(Alert.AlertType.ERROR, "end date is before start date!", ButtonType.OK);
            error.show();
        } else {
            JsonObject jsonObject = runner.jsonMaker("seller", "editOff"); //setOffId
            String[] productz = new String[products.size()];
            for (int i = 0; i < productz.length; i++)
                productz[i] = products.get(i);
            jsonObject.addProperty("products", new Gson().toJson(productz));
            jsonObject.addProperty("id", dataBase.editingOffId);
            jsonObject.addProperty("startDate", startDate.getEditor().getText() + " 00:00");
            jsonObject.addProperty("endDate", endDate.getEditor().getText() + " 23:59");
            jsonObject.addProperty("percentage", percentage.getText());
            dataBase.dataOutputStream.writeUTF(jsonObject.toString());
            dataBase.dataOutputStream.flush();
            dataBase.dataInputStream.readUTF();
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

    public void removeProduct(ActionEvent actionEvent) {
        Runner.buttonSound();
        TextInputDialog dialog = new TextInputDialog();
        dialog.getEditor().setPromptText("product ID");
        dialog.setTitle("remove product");
        dialog.setContentText("product ID : ");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(id -> products.remove(id));
    }

    public void userArea(MouseEvent mouseEvent) {
        Runner.getInstance().setUserAreaScene();
    }
}
