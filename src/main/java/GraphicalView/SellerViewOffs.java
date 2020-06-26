package GraphicalView;

import Controller.ManagerController;
import Controller.SellerController;
import Model.Category;
import Model.Off;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SellerViewOffs implements Initializable {
    public Button logout;
    public Button login;
    public GridPane gridPane;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();
    SellerController sellerController = SellerController.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginAlert();
        logoutAlert();
        showOffs();
    }

    public void back(ActionEvent actionEvent) {
        Runner.buttonSound();
        runner.back();
    }

    public void loginAlert() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        EventHandler<ActionEvent> event = (e) -> {
            Runner.buttonSound();
            if (dataBase.user != null) {
                error.setContentText("You have logged in!");
                error.show();
            } else {
                runner.changeScene("LoginMenu.fxml");
            }
        };
        login.setOnAction(event);
    }

    private void logoutAlert() {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        EventHandler<ActionEvent> event = (e) -> {
            Runner.buttonSound();
            message.setContentText("you logged out successfully");
            message.show();
            dataBase.logout();
        };
        logout.setOnAction(event);
    }

    private void showOffs() {
        TableColumn<BuyingLogShow, String> id = new TableColumn<>("off ID");
        id.setMinWidth(150);
        id.setCellValueFactory(new PropertyValueFactory<>("offId"));

        TableColumn<BuyingLogShow, Button> showButton = new TableColumn<>("show");
        showButton.setMinWidth(150);
        showButton.setCellValueFactory(new PropertyValueFactory<>("showButton"));

        TableColumn<BuyingLogShow, Button> editButton = new TableColumn<>("edit");
        editButton.setMinWidth(150);
        editButton.setCellValueFactory(new PropertyValueFactory<>("editButton"));

        TableView tableView = new TableView();

        ObservableList<ShowOffsOnGUI> list = generateOffsList();
        tableView.getColumns().addAll(id, showButton, editButton);
        tableView.setItems(list);
        gridPane.getChildren().add(tableView);

    }

    private ObservableList generateOffsList() {
        ObservableList<ShowOffsOnGUI> list = FXCollections.observableArrayList();
        ArrayList<Off> allOffs = sellerController.showAllOffsForGUI(dataBase.user);
        for (Off off : allOffs)
            list.add(new ShowOffsOnGUI(off, new Button("show"), new Button("edit"), off.getOffId()));
        return list;
    }

    public void userArea(MouseEvent mouseEvent) {
    }
}

class ShowOffsOnGUI {
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
