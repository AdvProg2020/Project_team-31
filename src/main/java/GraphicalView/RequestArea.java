package GraphicalView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RequestArea implements Initializable {
    public VBox detail;
    public Button logout;
    String requestId;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logoutAlert();
        requestId = RequestMenu.requestToView;
        dataInputStream = DataBase.getInstance().dataInputStream;
        dataOutputStream = DataBase.getInstance().dataOutputStream;
        JsonObject output = Runner.getInstance().jsonMaker("manager", "showRequest");
        output.addProperty("id", requestId);
        JsonObject jsonObject = null;
        try {
            dataOutputStream.writeUTF(output.toString());
            dataOutputStream.flush();
            String input = dataInputStream.readUTF();
            jsonObject = (JsonObject) new JsonParser().parse(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (jsonObject.get("type").getAsString().equals("seller")) {
            setSellerRequest(jsonObject);
        } else if (jsonObject.get("type").getAsString().equals("off")) {
            setOffRequest(jsonObject);
        } else if (jsonObject.get("type").getAsString().equals("product")) {
            setProductRequest(jsonObject);
        } else {
            setSellerOfProductRequest(jsonObject);
        }
    }

    public void userArea(MouseEvent mouseEvent) {
        Runner.getInstance().setUserAreaScene();
    }

    private void logoutAlert() {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        EventHandler<ActionEvent> event = (e) -> {
            Runner.buttonSound();
            message.setContentText("you logged out successfully");
            message.show();
            DataBase.getInstance().logout();
        };
        logout.setOnAction(event);
    }

    private void setSellerRequest(JsonObject input) {
        Label type = new Label("Request to be a seller");
        Label username = new Label("username: " + input.get("username").getAsString());
        Label name = new Label("name: " + input.get("name").getAsString() + ", " + input.get("lastName").getAsString());
        Label company = new Label("company: " + input.get("company").getAsString());
        Label email = new Label("email address: " + input.get("email").getAsString());
        Label phone = new Label("phone number: " + input.get("phone").getAsString());
        detail.getChildren().addAll(type, username, name, company, email, phone);
    }

    private void setOffRequest(JsonObject input) {
        Label type;
        if (input.get("isEditing").getAsBoolean()) {
            type = new Label("Request to edit an off");
        } else {
            type = new Label("Request to create an off");
        }
        Label id = new Label("OffId: " + input.get("offId").getAsString());
        Label beginTime = new Label("begin time: " + input.get("beginTime").getAsString());
        Label endTime = new Label("end time: " + input.get("endTime").getAsString());
        Label percent = new Label("percent: " + input.get("percent").getAsInt());
        Label products = new Label("products are: ");
        detail.getChildren().addAll(type, id, beginTime, endTime, percent, products);
        int i = 1;
        for (JsonElement element : input.getAsJsonArray("products")) {
            Label pro = new Label((i++) + ", " + element.toString());
            detail.getChildren().add(pro);
        }
    }

    private void setProductRequest(JsonObject input) {
        Label type;
        if (input.get("isEditing").getAsBoolean()) {
            type = new Label("Request to edit Product");
        } else {
            type = new Label("Request to create Product");
        }
        Label product = new Label("Product: " + input.get("name").getAsString());
        Label seller = new Label("seller: " + input.get("seller").getAsString());
        Label price = new Label("price: " + input.get("price").getAsInt());
        Label available = new Label("available: " + input.get("available").getAsInt());
        Label information = new Label("information: " + input.get("information").getAsString());
        Label features = new Label("special features: ");
        detail.getChildren().addAll(type, product, seller, price, available, information, features);
        for (JsonElement element : input.getAsJsonArray("special")) {
            JsonObject feature = element.getAsJsonObject();
            Label f = new Label("key: " + feature.get("key").getAsString() + ", value: " + feature.get("value").getAsString());
            detail.getChildren().add(f);
        }
    }

    private void setSellerOfProductRequest(JsonObject input) {
        Label type = new Label("Request to add a seller to a product");
        Label product = new Label("ProductId: " + input.get("product").getAsString());
        Label seller = new Label("seller: " + input.get("seller").getAsString());
        Label price = new Label("price: " + input.get("price").getAsInt());
        detail.getChildren().addAll(type, product, seller, price);
    }

    public void accept(MouseEvent mouseEvent) {
        JsonObject jsonObject = Runner.getInstance().jsonMaker("manager", "acceptRequest");
        jsonObject.addProperty("id", requestId);
        try {
            dataOutputStream.writeUTF(jsonObject.toString());
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        back(mouseEvent);
    }

    public void decline(MouseEvent mouseEvent) {
        JsonObject jsonObject = Runner.getInstance().jsonMaker("manager", "declineRequest");
        jsonObject.addProperty("id", requestId);
        try {
            dataOutputStream.writeUTF(jsonObject.toString());
            dataOutputStream.flush();
            dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        back(mouseEvent);
    }

    public void back(MouseEvent mouseEvent) {
        Runner.getInstance().back();
    }

}
