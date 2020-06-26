package GraphicalView;

import Model.Off;
import Model.Product;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class ShowOffsOnGUI {
    Off off;
    Button showButton;
    Button editButton;
    String offId;

    public ShowOffsOnGUI(Off off, Button showButton, Button editButton, String offId) {
        this.off = off;
        this.showButton = showButton;
        this.editButton = editButton;
        this.offId = offId;
        this.showButton.setOnAction(event -> ShowOffsOnGUI.view(off));
        this.editButton.setOnAction(event -> ShowOffsOnGUI.edit(off));
    }

    private static void edit(Off off) {
        Runner.getInstance().changeScene("SellerEditOff.fxml");
        DataBase.getInstance().editingOff = off;
    }

    private static void view(Off off) {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        String data = "off ID : " + off.getOffId() + "\n" +
                "start time : " + off.getBeginTime() + "\n" +
                "end time : " + off.getEndTime() + "\n" +
                "off percentage : " + off.getOffPercent() + "\n" +
                "products : \n";
        for (Product product : off.getOnSaleProducts())
            data += product.getName() + "\n";
        message.setContentText(data);
        message.show();
    }

    public Off getOff() {
        return off;
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
