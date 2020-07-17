package GraphicalView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class PayMoneyPage {
    public void payMoney(MouseEvent mouseEvent) {
        JsonObject output = Runner.getInstance().jsonMaker("customer", "payMoney");
        output.addProperty("id", ReceiveInformationForShopping.buyingLogId);
        try {
            DataBase.getInstance().dataOutputStream.writeUTF(output.toString());
            DataBase.getInstance().dataOutputStream.flush();
            String input = DataBase.getInstance().dataInputStream.readUTF();
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(input);
            if(jsonObject.get("type").getAsString().equals("failed")) {
                Alert error = new Alert(Alert.AlertType.ERROR, jsonObject.get("message").getAsString(), ButtonType.OK);
                error.show();
            } else {
                Alert error = new Alert(Alert.AlertType.INFORMATION, "shopping completed", ButtonType.OK);
                error.show();
                Runner.getInstance().changeScene("MainMenu.fxml");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void back(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Runner.getInstance().back();
    }
}
