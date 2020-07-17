package GraphicalView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class PutDiscountCode {
    public TextField discount;
    public Button discountButton;

    public void putDiscount(MouseEvent mouseEvent) {
        Runner.buttonSound();
        if(discount.getText().equals("")) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please write code" , ButtonType.OK);
            error.show();
        } else {
            JsonObject output = Runner.getInstance().jsonMaker("customer", "putDiscount");
            output.addProperty("id", ReceiveInformationForShopping.buyingLogId);
            output.addProperty("code", discount.getText());
            try {
                DataBase.getInstance().dataOutputStream.writeUTF(output.toString());
                DataBase.getInstance().dataOutputStream.flush();
                String input = DataBase.getInstance().dataInputStream.readUTF();
                JsonObject jsonObject = (JsonObject) new JsonParser().parse(input);
                if(jsonObject.get("type").getAsString().equals("failed")) {
                    Alert error = new Alert(Alert.AlertType.ERROR, jsonObject.get("message").getAsString(), ButtonType.OK);
                    error.show();
                } else {
                    discountButton.setDisable(true);
                    Alert error = new Alert(Alert.AlertType.INFORMATION, "discount added successfully", ButtonType.OK);
                    error.show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {


            } catch (Exception e) {

            }
        }
    }

    public void nextStep(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Runner.getInstance().changeScene("PayMoney.fxml");
    }

    public void back(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Runner.getInstance().back();
    }
}
