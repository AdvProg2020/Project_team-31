package GraphicalView;

import Controller.CustomerController;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class PutDiscountCode {
    public TextField discount;
    public Button discountButton;

    public void putDiscount(MouseEvent mouseEvent) {
        if(discount.getText().equals("")) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please write code" , ButtonType.OK);
            error.show();
        } else {
            try {
                CustomerController.getInstance().putDiscount(DataBase.getInstance().user,ReceiveInformationForShopping.buyingLog,discount.getText());
                discountButton.setDisable(true);
                Alert error = new Alert(Alert.AlertType.INFORMATION, "discount added successfully", ButtonType.OK);
                error.show();
            } catch (Exception e) {
                Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                error.show();
            }
        }
    }

    public void nextStep(MouseEvent mouseEvent) {
    }

    public void back(MouseEvent mouseEvent) {
        Runner.getInstance().back();
    }
}
