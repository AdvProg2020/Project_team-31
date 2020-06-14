package GraphicalView;

import Controller.CustomerController;
import Controller.LoginController;
import Controller.ProductController;
import Model.*;
import com.sun.org.apache.bcel.internal.generic.LADD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductArea implements Initializable {

    public Button addToCardButton;
    public Label productName;
    public Label price;
    public Label rate;
    public TextField ratePlease;
    public Button logoutButton;
    public Button loginButton;
    public TextField CommentTitle;
    public ListView commentsList;
    public Label information;
    public TextArea commentContent;
    public VBox specialProperties;
    public Label view;
    public Label status;
    public Label available;
    private Card card;
    private User user;
    private Product product;
    private Seller seller;
    private CustomerController customerController = CustomerController.getInstance();
    private ArrayList<String> commentsToString ;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        product = ProductsMenu.product;
        if (DataBase.getInstance().user != null) {
            user = DataBase.getInstance().user;
        } else {
            user = DataBase.getInstance().tempUser;
        }
        if(user.getCard() == null)
            user.setCard(new Card());
        available.setText("availability: " + product.getAvailable());
        status.setText("status: " + product.getProductStatus());
        view.setText("views: " + product.getViews());
        productName.setText("name: " + product.getName());
        price.setText("minimum price: " + product.getMinimumPrice());
        information.setText("information: " + product.getInformation());
        update();
        setSpecialProperties();
    }

    private void setSpecialProperties() {
        HBox row = new HBox();
        Label key = new Label("key");
        key.setMinWidth(125);
        Label value = new Label("value");
        value.setMinWidth(125);
        row.getChildren().addAll(key,value);
        specialProperties.getChildren().add(row);
        for (String s : product.getSpecialPropertiesRelatedToCategory().keySet()) {
            HBox r = new HBox();
            Label k = new Label(s);
            k.setMinWidth(125);
            Label v = new Label(product.getSpecialPropertiesRelatedToCategory().get(s));
            v.setMinWidth(125);
            r.getChildren().addAll(k,v);
            specialProperties.getChildren().add(r);
        }
    }


    public void addThisProductToCard(ActionEvent actionEvent) throws Exception {
        customerController.addProductToCard(user , card , product , seller.getUsername());
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
        if(ratePlease.getText().equals("")) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please enter number", ButtonType.OK);
            error.show();
        }
        try {
            CustomerController.getInstance().rateProduct(user , product.getProductId() ,Integer.getInteger(ratePlease.getText()));
        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            error.show();
        }
        update();
    }

    public void commentThisProduct(ActionEvent actionEvent) {
        ProductController.getInstance().addComment(user , product , CommentTitle.getText() , commentContent.getText() );
        update();
    }

    public void  showProductDetails(){
        rate.setText("rate : " + product.getRate());
        price.setText("price : " + product.getMinimumPrice());
        productName.setText("name : " + product.getName());
    }

    public void update(){
        rate.setText("rate: " + product.getRate());
        commentsList.getItems().clear();
        commentsToString = ProductController.getInstance().showCommentAboutProduct(product);
        if (commentsToString != null) {
            ObservableList<String> comments = FXCollections.observableArrayList();
            comments.addAll(commentsToString);
            commentsList.setItems(comments);
        }
    }
}
