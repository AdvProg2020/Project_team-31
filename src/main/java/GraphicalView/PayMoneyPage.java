package GraphicalView;

import Controller.CustomerController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;

public class PayMoneyPage {
    public void payMoney(MouseEvent mouseEvent) {
        try {
            CustomerController.getInstance().payMoney(DataBase.getInstance().user, ReceiveInformationForShopping.buyingLog);
            Alert error = new Alert(Alert.AlertType.INFORMATION, "shopping completed", ButtonType.OK);
            error.show();
            Runner.getInstance().changeScene("MainMenu.fxml");
        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            error.show();
        }
    }
}
