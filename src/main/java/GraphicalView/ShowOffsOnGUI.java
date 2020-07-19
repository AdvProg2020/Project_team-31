package GraphicalView;

import com.google.gson.JsonObject;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.IOException;

public class ShowOffsOnGUI {
    Button showButton;
    Button editButton;
    String offId;

    public ShowOffsOnGUI(Button showButton, Button editButton, String offId) {
        this.showButton = showButton;
        this.editButton = editButton;
        this.offId = offId;
        this.showButton.setOnAction(event -> ShowOffsOnGUI.view(offId));
        this.editButton.setOnAction(event -> ShowOffsOnGUI.edit(offId));
    }

    private static void edit(String offId) {
        DataBase.getInstance().editingOffId = offId;
        Runner.getInstance().changeScene("SellerEditOff.fxml");
    }

    private static void view(String offId) {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        DataBase dataBase = DataBase.getInstance();
        JsonObject jsonObject = Runner.getInstance().jsonMaker("seller", "getOffInfo");
        jsonObject.addProperty("offId", offId);
        try {
            dataBase.dataOutputStream.writeUTF(jsonObject.toString());
            dataBase.dataOutputStream.flush();
            message.setContentText(Runner.getInstance().jsonParser(dataBase.dataInputStream.readUTF()).get("data").getAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        message.show();
    }

    public Button getShowButton() {
        return showButton;
    }

    public Button getEditButton() {
        return editButton;
    }

    public String getOffId() {
        return offId;
    }
}
