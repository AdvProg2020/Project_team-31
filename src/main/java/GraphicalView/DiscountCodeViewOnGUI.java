package GraphicalView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class DiscountCodeViewOnGUI {
    Button showCustomers;
    Button edit;
    String code;
    String beginTime;
    String endTime;
    int discountPercent;
    int maximumDiscount;
    HashMap<String , Integer> customers = new HashMap<>();
    DataBase dataBase=DataBase.getInstance();

    public DiscountCodeViewOnGUI(String code, String beginTime, String endTime, int discountPercent, int maximumDiscount, HashMap<String, Integer> customers) {
        this.code = code;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.discountPercent = discountPercent;
        this.maximumDiscount = maximumDiscount;
        this.customers = customers;
        this.showCustomers = new Button("show customers");
        this.edit = new Button("edit");
        showCustomers.setOnAction(event -> show());
        edit.setOnAction(event -> edit());
    }

    public Button getShowCustomers() {
        return showCustomers;
    }

    public Button getEdit() {
        return edit;
    }

    public void edit() {
      dataBase.editingDiscountCode = code;
        Runner.runner.changeScene("EditDiscountCode.fxml");
    }

    public void show() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("customers");
        String data = "customers and number that they can use code : \n";
        for (String s : customers.keySet()) {
            data += s+ "--->" + customers.get(s) + "\n";
        }
        alert.setContentText(data);
        alert.showAndWait();
    }

    public String getCode() {
        return code;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public int getMaximumDiscount() {
        return maximumDiscount;
    }
}
