package GraphicalView;

import Controller.CustomerController;
import Controller.ProductController;
import Model.Card;
import Model.Product;
import Model.Seller;
import Model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    public Label specialProperties;
    public Button logoutButton;
    public Button loginButton;
    public TextField CommentTitle;
    public TextField CommentContent;
    private Card card;
    private User user;
    private Product product;
    private Seller seller;
    private CustomerController customerController = CustomerController.getInstance();

    public void addThisProductToCard(ActionEvent actionEvent) throws Exception {
        customerController.addProductToCard(user , card , product , seller.getUsername());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logout();
        login();
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

    public void login() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        EventHandler<ActionEvent> event = (e) -> {
            if (DataBase.getInstance().user != null) {
                error.setContentText("You have logged in!");
                error.show();
            } else {
                Runner.getInstance().changeScene("LoginMenu.fxml");
            }
        };
        loginButton.setOnAction(event);

    }

    public void logout() {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        EventHandler<ActionEvent> event = (e) -> {
            message.setContentText("you logged out successfully");
            message.show();
            DataBase.getInstance().logout();
        };
        logoutButton.setOnAction(event);

    }

    public void rateThisProduct(ActionEvent actionEvent) {
        try {
            CustomerController.getInstance().rateProduct(DataBase.getInstance().user , product.getProductId() ,Integer.getInteger(ratePlease.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void commentThisProduct(ActionEvent actionEvent) {
        ProductController.getInstance().addComment(user , product , CommentTitle.getText() , CommentContent.getText() );
    }
}
