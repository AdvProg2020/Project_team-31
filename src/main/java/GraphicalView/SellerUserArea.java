package GraphicalView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SellerUserArea implements Initializable {
    public Label personalInfo;
    public Button editPersonaInfo;
    public Button logout;
    public Button login;
    public Button addMeButton;
    public Button withdraw;
    public Button charge;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        runner.changeMusic("UserArea");
        chargeDialog();
        loginAlert();
        logoutAlert();
        withdrawDialog();
        showPersonalInfo();
        editPersonalInfoAlert();
    }

    private void chargeDialog() {
        TextInputDialog getNumber = new TextInputDialog();
        getNumber.getEditor().setPromptText("please enter the amount : ");
        Button okButton = (Button) getNumber.getDialogPane().lookupButton(ButtonType.OK);
        TextField inputField = getNumber.getEditor();
        EventHandler<ActionEvent> addBalanceEvent = (e) -> {
            Runner.buttonSound();
            JsonObject jsonObject = runner.jsonMaker("manager", "accountCharge");
            jsonObject.addProperty("amount", inputField.getText());
            try {
                dataBase.dataOutputStream.writeUTF(jsonObject.toString());
                dataBase.dataOutputStream.flush();
                String answer = runner.jsonParser(dataBase.dataInputStream.readUTF()).get("answer").getAsString();
                new Alert(Alert.AlertType.INFORMATION,answer , ButtonType.OK, ButtonType.CANCEL).showAndWait();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
        okButton.setOnAction(addBalanceEvent);
        BooleanBinding isInvalid = Bindings.createBooleanBinding(() -> !inputField.getText().matches("^[0-9]+$"), inputField.textProperty());
        okButton.disableProperty().bind(isInvalid);
        EventHandler<ActionEvent> event = (e) -> {
            Runner.buttonSound();
            getNumber.show();
        };
        charge.setOnAction(event);
    }

    private void withdrawDialog() {
        TextInputDialog getNumber = new TextInputDialog();
        getNumber.getEditor().setPromptText("please enter the amount : ");
        Button okButton = (Button) getNumber.getDialogPane().lookupButton(ButtonType.OK);
        TextField inputField = getNumber.getEditor();
        EventHandler<ActionEvent> addBalanceEvent = (e) -> {
            Runner.buttonSound();
            JsonObject jsonObject = runner.jsonMaker("manager", "accountWithdraw");
            jsonObject.addProperty("amount", inputField.getText());
            try {
                dataBase.dataOutputStream.writeUTF(jsonObject.toString());
                dataBase.dataOutputStream.flush();
                String answer = runner.jsonParser(dataBase.dataInputStream.readUTF()).get("answer").getAsString();
                new Alert(Alert.AlertType.INFORMATION,answer , ButtonType.OK, ButtonType.CANCEL).showAndWait();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
        okButton.setOnAction(addBalanceEvent);
        BooleanBinding isInvalid = Bindings.createBooleanBinding(() -> !inputField.getText().matches("^[0-9]+$"), inputField.textProperty());
        okButton.disableProperty().bind(isInvalid);
        EventHandler<ActionEvent> event = (e) -> {
            Runner.buttonSound();
            getNumber.show();
        };
        withdraw.setOnAction(event);
    }

    private void showPersonalInfo() {
        if (DataBase.getInstance().role.equals("none")) {
            personalInfo.textProperty().setValue("no personal info yet!\n you have to log in first!");
            return;
        }
        String company = "";
        try {
            dataBase.dataOutputStream.writeUTF(runner.jsonMaker("seller", "showCompanyInformation").toString());
            dataBase.dataOutputStream.flush();
            company = runner.jsonParser(dataBase.dataInputStream.readUTF()).get("company").getAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder toShow = new StringBuilder("personal information : \n");
        String[] information = showPersonalInformation();
        toShow.append("first name : ").append(information[0]).append("\n");
        toShow.append("last name : ").append(information[1]).append("\n");
        toShow.append("username : ").append(information[2]).append("\n");
        toShow.append("email address: ").append(information[3]).append("\n");
        toShow.append("phone number : ").append(information[4]).append("\n");
        toShow.append("credit : ").append(information[6]).append("\n");
        toShow.append("company Info : ").append(company);
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

    public void back(MouseEvent mouseEvent) {
        Runner.buttonSound();
        runner.back();
    }


    private void editPersonalInfoAlert() {
        if (DataBase.getInstance().role.equals("none")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            EventHandler<ActionEvent> event = (e) -> {
                Runner.buttonSound();
                alert.show();
            };
            editPersonaInfo.setOnAction(event);
            alert.setContentText("You have to login");
        } else {
            EventHandler<ActionEvent> event = (e) -> runner.changeScene("EditPersonalInfo.fxml");
            editPersonaInfo.setOnAction(event);
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

    public void salesHistory(MouseEvent mouseEvent) {
        Runner.buttonSound();
        runner.changeScene("SellerHistory.fxml");
    }

    public void showCategories(MouseEvent mouseEvent) {
        Runner.buttonSound();
        runner.changeScene("ShowCategories.fxml");
    }

    public void addProduct(MouseEvent mouseEvent) {
        Runner.buttonSound();
        runner.changeScene("AddProduct.fxml");
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

    public void addMeButtonAlert(ActionEvent actionEvent) {
        Runner.buttonSound();
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("add seller");
        ButtonType buttonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonType, ButtonType.CANCEL);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));
        TextField productName = new TextField();
        productName.setPromptText("product Id"); //useful
        TextField price = new TextField();
        price.setPromptText("price");
        price.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                price.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        gridPane.add(productName, 0, 0);
        gridPane.add(price, 2, 0);
        dialog.getDialogPane().setContent(gridPane);
        Platform.runLater(() -> productName.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonType) {
                return new Pair<>(productName.getText(), price.getText());
            }
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(pair -> {
            try {
                addSellerToProduct(productName.getText(), Integer.parseInt(price.getText()));
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });
    }

    private void addSellerToProduct(String productId, int price) throws Exception {
        JsonObject jsonObject = runner.jsonMaker("seller", "addProductToSeller");
        jsonObject.addProperty("id", productId);
        jsonObject.addProperty("price", price);
        dataBase.dataOutputStream.writeUTF(jsonObject.toString());
        dataBase.dataOutputStream.flush();
        String input = dataBase.dataInputStream.readUTF();
        JsonObject json = (JsonObject) new JsonParser().parse(input);
        if (json.get("type").getAsString().equals("failed")) {
            throw new Exception(json.get("message").getAsString());
        }
    }

    public void addOff(ActionEvent actionEvent) {
        Runner.buttonSound();
        runner.changeScene("SellerAddOff.fxml");
    }

    public void sellerOffsShow(ActionEvent actionEvent) {
        Runner.buttonSound();
        runner.changeScene("SellerViewOffs.fxml");
    }

    public void manageProducts(ActionEvent actionEvent) {
        Runner.buttonSound();
        runner.changeScene("ManageProducts.fxml");
    }

    public void createAuction(ActionEvent actionEvent) {
        Runner.buttonSound();
        runner.changeScene("CreateAuction.fxml");
    }
}
