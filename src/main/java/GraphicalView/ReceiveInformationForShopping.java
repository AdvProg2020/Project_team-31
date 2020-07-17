package GraphicalView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ReceiveInformationForShopping {
    public TextArea address;
    public TextField phone;
    public static String buyingLogId;

    public void nextStep(MouseEvent mouseEvent) {
        Runner.buttonSound();
        if(address.getText().equals("") || phone.getText().equals("")) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please complete all data", ButtonType.OK);
            error.show();
        } else {
            JsonObject output = Runner.getInstance().jsonMaker("customer", "createBuyingLog");
            output.addProperty("address", address.getText());
            output.addProperty("phone", phone.getText());
            try {
                DataBase.getInstance().dataOutputStream.writeUTF(output.toString());
                DataBase.getInstance().dataOutputStream.flush();
                String input = DataBase.getInstance().dataInputStream.readUTF();
                JsonObject jsonObject = (JsonObject) new JsonParser().parse(input);
                if(jsonObject.get("type").getAsString().equals("failed")) {
                    Alert error = new Alert(Alert.AlertType.ERROR, jsonObject.get("message").getAsString(), ButtonType.OK);
                    error.show();
                } else {
                    buyingLogId = jsonObject.get("id").getAsString();
                    Runner.getInstance().changeScene("PutDiscountCode.fxml");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void back(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Runner.getInstance().back();
    }
}
