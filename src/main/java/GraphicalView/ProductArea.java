package GraphicalView;

import Controller.CustomerController;
import Model.Card;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductArea implements Initializable {

    public Button addToCardButton;
    public Label productName;
    public Label price;
    public Label rate;
    public TextField ratePlease;
    public TextField addComment;
    public Label specialProperties;
    public Button logoutButton;
    public Button loginButton;
    private Card card;
    private User user;
    private CustomerController customerController = CustomerController.getInstance();

    public void addThisProductToCard(ActionEvent actionEvent) throws Exception {
        customerController.addProductToCard(user , card , null , null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (DataBase.getInstance().user != null){
            user = DataBase.getInstance().user;
            if (DataBase.getInstance().user.getCard() == null){
                DataBase.getInstance().user.setCard(new Card());
                card = DataBase.getInstance().user.getCard();
            }
            else
                card = DataBase.getInstance().user.getCard();
        }
        else {
            user = DataBase.getInstance().tempUser;
            if (DataBase.getInstance().tempUser.getCard() == null){
                DataBase.getInstance().tempUser.setCard(new Card());
                card = DataBase.getInstance().tempUser.getCard();
            }
            else
                card = DataBase.getInstance().tempUser.getCard();
        }
    }

    public void login(ActionEvent actionEvent) {

    }

    public void logout(ActionEvent actionEvent) {

    }

    public void rateThisProduct(ActionEvent actionEvent) {

    }

    public void commentThisProduct(ActionEvent actionEvent) {
    }
}
