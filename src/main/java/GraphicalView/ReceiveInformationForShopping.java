package GraphicalView;

import Controller.CustomerController;
import Model.BuyingLog;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ReceiveInformationForShopping {
    public TextArea address;
    public TextField phone;
    public static BuyingLog buyingLog;

    public void nextStep(MouseEvent mouseEvent) {
        if(address.getText().equals("") || phone.getText().equals("")) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please complete all data", ButtonType.OK);
            error.show();
        } else {
            String data[] = new String[2];
            data[0] = address.getText();
            data[1] = phone.getText();
            try {
                buyingLog = CustomerController.getInstance().createBuyingLog(DataBase.getInstance().user, data);
            } catch (Exception e) {
                Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                error.show();
            }
        }
    }

    public void back(MouseEvent mouseEvent) {
        Runner.getInstance().back();
    }
}
