package GraphicalView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuctionArea implements Initializable {
    public TextField highestPrice;
    public TextField buyer;
    public Label minPrice;
    public TextField newPrice;
    public Label endTime;
    public ListView commentsList;
    public TextArea commentContent;
    public Label seller;
    private int highPrice;
    private String auctionId;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        auctionId = ShowAuctions.auctionToView;
        buyer.setDisable(true);
        highestPrice.setDisable(true);
        dataOutputStream = DataBase.getInstance().dataOutputStream;
        dataInputStream = DataBase.getInstance().dataInputStream;
        JsonObject output = Runner.getInstance().jsonMaker("manager", "getAuction");
        output.addProperty("id", auctionId);
        try {
            dataOutputStream.writeUTF(output.toString());
            dataOutputStream.flush();
            String input = dataInputStream.readUTF();
            analyzeInput((JsonObject) new JsonParser().parse(input));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void analyzeInput(JsonObject input) {
        highPrice = input.get("highPrice").getAsInt();
        highestPrice.setText(String.valueOf(highPrice));
        buyer.setText(input.get("buyer").getAsString());
        minPrice.setText(String.valueOf(input.get("minPrice").getAsInt()));
        endTime.setText(input.get("end").getAsString());
        seller.setText(input.get("seller").getAsString());
        ObservableList<String> comments = FXCollections.observableArrayList();
        for (JsonElement element : input.getAsJsonArray("comments")) {
            comments.add(element.getAsString());
        }
        commentsList.setItems(comments);
    }

    public void back(ActionEvent actionEvent) {
        Runner.buttonSound();
        refresh(actionEvent);
        Runner.getInstance().back();
    }

    public void comment(ActionEvent actionEvent) {
        Runner.buttonSound();
        if(commentContent.getText().equals("")) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please write your comment", ButtonType.OK);
            error.show();
            refresh(actionEvent);
            return;
        }
        JsonObject output = Runner.getInstance().jsonMaker("manager", "commentInAuction");
        output.addProperty("id", auctionId);
        output.addProperty("comment", commentContent.getText());
        JsonObject jsonObject = null;
        try {
            dataOutputStream.writeUTF(output.toString());
            dataOutputStream.flush();
            String input = dataInputStream.readUTF();
            jsonObject = (JsonObject) new JsonParser().parse(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (jsonObject.get("type").getAsString().equals("successful")) {
            setDetail(jsonObject);
            commentContent.setText("");
        } else {
            Alert error = new Alert(Alert.AlertType.INFORMATION, jsonObject.get("message").getAsString(), ButtonType.OK);
            error.show();
            Runner.getInstance().back();
        }
    }

    public void refresh(ActionEvent actionEvent) {
        Runner.buttonSound();
        JsonObject output = Runner.getInstance().jsonMaker("manager", "refreshAuction");
        output.addProperty("id", auctionId);
        JsonObject jsonObject = null;
        try {
            dataOutputStream.writeUTF(output.toString());
            dataOutputStream.flush();
            String input = dataInputStream.readUTF();
            jsonObject = (JsonObject) new JsonParser().parse(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (jsonObject.get("type").getAsString().equals("successful")) {
            setDetail(jsonObject);
        } else {
            Alert error = new Alert(Alert.AlertType.INFORMATION, jsonObject.get("message").getAsString(), ButtonType.OK);
            error.show();
            Runner.getInstance().back();
        }
    }

    private void setDetail(JsonObject input) {
        buyer.setText(input.get("buyer").getAsString());
        highPrice = input.get("highPrice").getAsInt();
        highestPrice.setText(String.valueOf(highPrice));
        ObservableList<String> comments = FXCollections.observableArrayList();
        for (JsonElement element : input.getAsJsonArray("comments")) {
            comments.add(element.getAsString());
        }
        commentsList.setItems(comments);
    }

    public void sendPrice(ActionEvent actionEvent) {
        Runner.buttonSound();
        if(newPrice.getText().equals("")) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please write your price", ButtonType.OK);
            error.show();
            refresh(actionEvent);
            return;
        }
        int price = 0;
        try {
            price = Integer.parseInt(newPrice.getText());
        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please enter a number", ButtonType.OK);
            error.show();
            refresh(actionEvent);
            return;
        }
        if (price <= highPrice || price < Integer.parseInt(minPrice.getText())) {
            Alert error = new Alert(Alert.AlertType.ERROR, "your price should be more than " + Math.max(highPrice, Integer.parseInt(minPrice.getText()) - 1), ButtonType.OK);
            error.show();
            refresh(actionEvent);
            return;
        }
        JsonObject output = Runner.getInstance().jsonMaker("manager", "addNewPrice");
        output.addProperty("id", auctionId);
        output.addProperty("price", price);
        JsonObject jsonObject = null;
        try {
            dataOutputStream.writeUTF(output.toString());
            dataOutputStream.flush();
            String input = dataInputStream.readUTF();
            jsonObject = (JsonObject) new JsonParser().parse(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String type = jsonObject.get("type").getAsString();
        if (type.equals("failed")) {
            Alert error = new Alert(Alert.AlertType.ERROR, jsonObject.get("message").getAsString(), ButtonType.OK);
            error.show();
            setDetail(jsonObject);
        } else if (type.equals("finished")) {
            Alert error = new Alert(Alert.AlertType.INFORMATION, jsonObject.get("message").getAsString(), ButtonType.OK);
            error.show();
            Runner.getInstance().back();
        } else {
            Alert error = new Alert(Alert.AlertType.INFORMATION, "your price entered successfully!", ButtonType.OK);
            error.show();
            newPrice.setText("");
            setDetail(jsonObject);
        }
    }
}
