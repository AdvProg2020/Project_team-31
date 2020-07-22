package GraphicalView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
        highestPrice.setText(String.valueOf(input.get("highPrice").getAsInt()));
        buyer.setText(input.get("buyer").getAsString());
        minPrice.setText(String.valueOf(input.get("minPrice").getAsInt()));
        endTime.setText(input.get("end").getAsString());
        seller.setText(input.get("seller").getAsString());
        highPrice = input.get("highPrice").getAsInt();
        ObservableList<String> comments = FXCollections.observableArrayList();
        for (JsonElement element : input.getAsJsonArray("comments")) {
            comments.add(element.getAsString());
        }
        commentsList.setItems(comments);
    }

    public void back(ActionEvent actionEvent) {
        Runner.buttonSound();
        Runner.getInstance().back();
    }

    public void comment(MouseEvent mouseEvent) {

    }

    public void refresh(ActionEvent actionEvent) {
    }
}
