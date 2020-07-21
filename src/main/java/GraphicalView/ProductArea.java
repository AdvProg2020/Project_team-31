package GraphicalView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProductArea implements Initializable {

    public Button addToCardButton;
    public Label productName;
    public Label price;
    public Label rate;
    public TextField ratePlease;
    public Button logout;
    public Button login;
    public TextField CommentTitle;
    public ListView commentsList;
    public Label information;
    public TextArea commentContent;
    public VBox specialProperties;
    public Label view;
    public Label status;
    public Label available;
    public ChoiceBox sellers;
    public Label sellerPrice;
    public Button rateButton;
    public Button commentButton;
    public ImageView image;
    private String productId;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private HashMap<String, Integer> sellerPrices = new HashMap<>();
    private HashMap<String, Integer> sellerOffs = new HashMap<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginAlert();
        logoutAlert();
        if (!(DataBase.getInstance().role.equals("customer"))) {
            rateButton.setDisable(true);
            commentButton.setDisable(true);
        }
        productId = ProductsMenu.productId;
        dataInputStream = DataBase.getInstance().dataInputStream;
        dataOutputStream = DataBase.getInstance().dataOutputStream;
        String input = null;
        try {
            JsonObject jsonObject = Runner.getInstance().jsonMaker("product", "showProduct");
            jsonObject.addProperty("id", productId);
            dataOutputStream.writeUTF(jsonObject.toString());
            dataOutputStream.flush();
            input = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(input);
        analyzeInput(jsonObject);
    }

    private void analyzeInput(JsonObject jsonObject) {
        available.setText("availability: " + jsonObject.get("available").getAsInt());
        status.setText("status: " + jsonObject.get("status").getAsString());
        view.setText("views: " + jsonObject.get("views").getAsInt());
        productName.setText("name: " + jsonObject.get("name").getAsString());
        price.setText("minimum price: " + jsonObject.get("minimumPrice").getAsInt());
        information.setText("information: " + jsonObject.get("information").getAsString());
        rate.setText("rate: " + jsonObject.get("rate").getAsString());
        setSpecialProperties(jsonObject.getAsJsonArray("specialProperties"));
        JsonArray commentArray = jsonObject.getAsJsonArray("comments");
        ObservableList<String> comments = FXCollections.observableArrayList();
        for (JsonElement element : commentArray) {
            comments.add(element.getAsString());
        }
        commentsList.setItems(comments);
        for (JsonElement element : jsonObject.getAsJsonArray("sellers")) {
            JsonObject seller = element.getAsJsonObject();
            sellerPrices.put(seller.get("username").getAsString(), seller.get("price").getAsInt());
            sellerOffs.put(seller.get("username").getAsString(), seller.get("offPercent").getAsInt());
        }
        ObservableList arrayOfSellers = FXCollections.observableArrayList();
        arrayOfSellers.addAll(sellerPrices.keySet());
        sellers.setItems(arrayOfSellers);
    }

    public void userArea(MouseEvent mouseEvent) {
        Runner.getInstance().setUserAreaScene();
    }

    public void loginAlert() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        EventHandler<ActionEvent> event = (e) -> {
            if (!DataBase.getInstance().role.equals("none")) {
                error.setContentText("You have logged in!");
                error.show();
            } else {
                Runner.getInstance().changeScene("LoginMenu.fxml");
            }
        };
        login.setOnAction(event);
    }

    private void logoutAlert() {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        EventHandler<ActionEvent> event = (e) -> {
            Runner.buttonSound();
            if (DataBase.getInstance().role.equals("none")) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("You have not logged in!");
                error.show();
            } else {
                message.setContentText("you logged out successfully");
                message.show();
                DataBase.getInstance().logout();
            }
        };
        logout.setOnAction(event);
    }

//    private void setImage() {
//        if (product.getImageFile() != null) {
//            image.setImage(product.getImage());
//            image.setFitWidth(100);
//            image.setFitHeight(100);
//        }
//    }

    private void setSpecialProperties(JsonArray jsonArray) {
        HBox row = new HBox();
        Label key = new Label("key");
        key.setMinWidth(125);
        Label value = new Label("value");
        value.setMinWidth(125);
        row.getChildren().addAll(key, value);
        specialProperties.getChildren().add(row);
        for (JsonElement element : jsonArray) {
            JsonObject jsonObject = element.getAsJsonObject();
            HBox r = new HBox();
            Label k = new Label(jsonObject.get("key").getAsString());
            k.setMinWidth(125);
            Label v = new Label(jsonObject.get("value").getAsString());
            v.setMinWidth(125);
            r.getChildren().addAll(k, v);
            specialProperties.getChildren().add(r);
        }
    }

    public void changeSeller(ActionEvent actionEvent) {
        Runner.buttonSound();
        int price = sellerPrices.get(sellers.getValue().toString());
        int offPercent = sellerOffs.get(sellers.getValue().toString());
        if (offPercent == 0) {
            sellerPrice.setText("price: " + price + "\nThere is no off");
        } else {
            sellerPrice.setText("price: " + price + "\noff percent: " + offPercent + ", new price: " + (price * (100 - offPercent) / 100));
        }
    }

    public void addThisProductToCard(javafx.scene.input.MouseEvent mouseEvent) {
        Runner.buttonSound();
        if (sellers.getValue() == null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please select seller!", ButtonType.OK);
            error.show();
        } else {
            JsonObject jsonObject = Runner.getInstance().jsonMaker("customer", "addProductToCart");
            jsonObject.addProperty("id", productId);
            jsonObject.addProperty("seller", sellers.getValue().toString());
            try {
                dataOutputStream.writeUTF(jsonObject.toString());
                dataOutputStream.flush();
                String input = dataInputStream.readUTF();
                JsonObject inJson = (JsonObject) new JsonParser().parse(input);
                if(inJson.get("type").getAsString().equals("failed")) {
                    Alert error = new Alert(Alert.AlertType.ERROR, inJson.get("message").getAsString(), ButtonType.OK);
                    error.show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void rateThisProduct(javafx.scene.input.MouseEvent mouseEvent) {
        Runner.buttonSound();
        if (ratePlease.getText().equals("")) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please enter number", ButtonType.OK);
            error.show();
        } else {
            JsonObject jsonObject = Runner.getInstance().jsonMaker("customer", "rateProduct");
            jsonObject.addProperty("id", productId);
            jsonObject.addProperty("rate", Integer.parseInt(ratePlease.getText()));
            try {
                dataOutputStream.writeUTF(jsonObject.toString());
                dataOutputStream.flush();
                String input = dataInputStream.readUTF();
                JsonObject inJson = (JsonObject) new JsonParser().parse(input);
                if(inJson.get("type").getAsString().equals("failed")) {
                    Alert error = new Alert(Alert.AlertType.ERROR, inJson.get("message").getAsString(), ButtonType.OK);
                    error.show();
                } else {
                    rate.setText("rate: " + inJson.get("newRate").getAsString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void commentThisProduct(javafx.scene.input.MouseEvent mouseEvent) {
        Runner.buttonSound();
        if (commentContent.getText().equals("")) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please  write your comment", ButtonType.OK);
            error.show();
        } else {
            JsonObject jsonObject = Runner.getInstance().jsonMaker("customer", "addComment");
            jsonObject.addProperty("id", productId);
            jsonObject.addProperty("title", CommentTitle.getText());
            jsonObject.addProperty("content", commentContent.getText());
            try {
                dataOutputStream.writeUTF(jsonObject.toString());
                dataOutputStream.flush();
                String input = dataInputStream.readUTF();
                JsonObject inJson = (JsonObject) new JsonParser().parse(input);
                JsonArray commentArray = inJson.getAsJsonArray("comments");
                ObservableList<String> comments = FXCollections.observableArrayList();
                for (JsonElement element : commentArray) {
                    comments.add(element.getAsString());
                }
                commentsList.setItems(comments);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void back(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Runner.getInstance().back();
    }
}
