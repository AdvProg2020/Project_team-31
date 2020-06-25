package GraphicalView;

import GraphicalView.DataBase;
import GraphicalView.Runner;
import Model.Customer;
import Model.DiscountCode;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.util.Map;

public class DiscountCodeViewOnGUI {
    DiscountCode discountCode;
    Button showCustomers;
    Button edit;
DataBase dataBase=DataBase.getInstance();
    public Button getShowCustomers() {
        return showCustomers;
    }

    public Button getEdit() {
        return edit;
    }

    public DiscountCodeViewOnGUI(DiscountCode discountCode) {
        this.discountCode = discountCode;
        this.showCustomers = new Button("show customers");
        this.edit = new Button("edit");
        showCustomers.setOnAction(event -> show());
        edit.setOnAction(event -> edit());
    }

    public void edit() {
      dataBase.editingDiscountCode = discountCode;
        Runner.runner.changeScene("EditDiscountCode.fxml");
    }

    public void show() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("customers");
        String data = "customers and number that they can use code : \n";
        for (Map.Entry<Customer, Integer> entry : discountCode.getDiscountTimesForEachCustomer().entrySet())
            data += entry.getKey().getUsername() + "--->" + entry.getValue() + "\n";
        alert.setContentText(data);
        alert.showAndWait();
    }

    public String getCode() {
        return discountCode.getDiscountCode();
    }

    public String getBeginTime() {
        return discountCode.getBeginTime().toString();
    }

    public String getEndTime() {
        return discountCode.getEndTime().toString();
    }

    public String getDiscountPercent() {
        return String.valueOf(discountCode.getDiscountPercent());
    }

    public String getMaximumDiscount() {
        return String.valueOf(discountCode.getMaximumDiscount());
    }
}
