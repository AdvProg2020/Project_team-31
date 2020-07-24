package GraphicalView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class PayMoneyPage implements Initializable {
    public Button pay;
    public Button download;
    DataBase dataBase = DataBase.getInstance();
    Runner runner = Runner.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        download.setDisable(true);
    }

    public void payMoney(MouseEvent mouseEvent) {
        JsonObject output = Runner.getInstance().jsonMaker("customer", "payMoney");
        output.addProperty("id", ReceiveInformationForShopping.buyingLogId);
        try {
            DataBase.getInstance().dataOutputStream.writeUTF(output.toString());
            DataBase.getInstance().dataOutputStream.flush();
            String input = DataBase.getInstance().dataInputStream.readUTF();
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(input);
            if (jsonObject.get("type").getAsString().equals("failed")) {
                Alert error = new Alert(Alert.AlertType.ERROR, jsonObject.get("message").getAsString(), ButtonType.OK);
                error.show();
            } else {
                Alert error = new Alert(Alert.AlertType.INFORMATION, "shopping completed", ButtonType.OK);
                error.show();
                if (dataBase.fileToBuy == null)
                    runner.changeScene("MainMenu.fxml");
                else download.setDisable(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void back(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Runner.getInstance().back();
    }

    public void download(MouseEvent mouseEvent) throws IOException {
        Runner.buttonSound();
        byte[] bytes = dataBase.fileToBuy.getBytes();
        File tmp = new File(dataBase.fileName);
        tmp.createNewFile();
        OutputStream out = new FileOutputStream(tmp);
        out.write(bytes);
        out.flush();
        out.close();
        dataBase.fileToBuy = null;
        runner.changeScene("MainMenu.fxml");
    }
}
