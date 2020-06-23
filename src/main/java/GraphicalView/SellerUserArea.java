package GraphicalView;

import Controller.LoginController;
import Controller.ProductController;
import Controller.SellerController;
import Model.Product;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SellerUserArea implements Initializable {
    public Label personalInfo;
    public Button editPersonaInfo;
    public Button logout;
    public Button login;
    public Button addMeButton;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginAlert();
        logoutAlert();
        showPersonalInfo();
        editPersonalInfoAlert();
    }

    private void showPersonalInfo() {
        if (dataBase.user == null) {
            personalInfo.textProperty().setValue("no personal info yet!\n you have to log in first!");
            return;
        }
        String company = "";
        try {
            company = SellerController.getInstance().showCompanyInformation(dataBase.user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        StringBuilder toShow = new StringBuilder("personal information : \n");
        String[] information = LoginController.getInstance().showPersonalInformation(dataBase.user);
        toShow.append("first name : ").append(information[0]).append("\n");
        toShow.append("last name : ").append(information[1]).append("\n");
        toShow.append("username : ").append(information[2]).append("\n");
        toShow.append("email address: ").append(information[3]).append("\n");
        toShow.append("phone number : ").append(information[4]).append("\n");
        toShow.append("credit : ").append(information[6]).append("\n");
        toShow.append("company Info : ").append(company);
        personalInfo.textProperty().setValue(toShow.toString());
    }

    public void back(MouseEvent mouseEvent) {
        runner.back();
    }


    private void editPersonalInfoAlert() {
        if (dataBase.user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            EventHandler<ActionEvent> event = (e) -> alert.show();
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
            if (dataBase.user == null) {
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
        runner.changeScene("SellerHistory.fxml");
    }

    public void showCategories(MouseEvent mouseEvent) {
        runner.changeScene("ShowCategories.fxml");
    }

    public void addProduct(MouseEvent mouseEvent) {
        runner.changeScene("AddProduct.fxml");
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

    public void addMeButtonAlert(ActionEvent actionEvent) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("add seller");
        ButtonType buttonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonType, ButtonType.CANCEL);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));
        TextField productName = new TextField();
        productName.setPromptText("product name"); //useful
        TextField price = new TextField();
        price.setPromptText("price");
        dialog.getDialogPane().lookupButton(buttonType).disableProperty().bind(Bindings.createBooleanBinding(
                () -> productName.getText().equals("") || price.getText().equals("") ||
                        !ProductController.getInstance().isThereAnyProduct(productName.getText())
        ));
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
                for (Product product : Product.allProducts) {
                    System.out.println(product.getName());
                }
            SellerController.getInstance().addSellerToProduct(dataBase.user, productName.getText(), Integer.parseInt(price.getText()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void addOff(ActionEvent actionEvent) {
        runner.changeScene("SellerAddOff.fxml");
    }

    public void sellerOffsShow(ActionEvent actionEvent) {
        runner.changeScene("SellerViewOffs.fxml");
    }
}
