package GraphicalView;

import Controller.CustomerController;
import Controller.ProductController;
import Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
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
    public ListView commentsList;
    private Card card;
    private User user;
    private Product product;
    private Seller seller;
    private CustomerController customerController = CustomerController.getInstance();
    private ArrayList<String> commentsToString = product.getAllCommentsToString();
    public void addThisProductToCard(ActionEvent actionEvent) throws Exception {
        customerController.addProductToCard(user , card , product , seller.getUsername());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logout();
        login();
        if (product.getAllComments() != null)
        commentsList.setItems((ObservableList) commentsToString);
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
        update();
    }

    public void commentThisProduct(ActionEvent actionEvent) {
        ProductController.getInstance().addComment(user , product , CommentTitle.getText() , CommentContent.getText() );
        commentsToString.add(CommentTitle.getText() + "\n" +CommentContent.getText());
        update();
    }

    public void  showProductDetails(){
        rate.setText("rate : " + product.getRate());
        price.setText("price : " + product.getMinimumPrice());
        productName.setText("name : " + product.getName());
        specialProperties.setText("specialProperties : " + product.getSpecialPropertiesRelatedToCategory().keySet().toString());
    }

    public void update(){
        rate.setText("rate : " + product.getRate());
        price.setText("price : " + product.getMinimumPrice());
        if (commentsToString != null)
        commentsList.setItems((ObservableList) commentsToString);
    }
}
