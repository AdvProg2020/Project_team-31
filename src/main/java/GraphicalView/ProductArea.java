package GraphicalView;

import Controller.CustomerController;
import Controller.LoginController;

import Controller.ProductController;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    public ChoiceBox sellers;
    public Label sellerPrice;
    public Button rateButton;
    public Button commentButton;
    public ImageView image;
    private User user;
    private Product product;
    private CustomerController customerController = CustomerController.getInstance();
    private ArrayList<String> commentsToString;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        product = ProductsMenu.product;
        if (DataBase.getInstance().user != null) {
            user = DataBase.getInstance().user;
        } else {
            user = DataBase.getInstance().tempUser;
        }
        if (user.getCard() == null)
            user.setCard(new Card());
        if (!(user instanceof Customer)) {
            rateButton.setDisable(true);
            commentButton.setDisable(true);
        }
        available.setText("availability: " + product.getAvailable());
        status.setText("status: " + product.getProductStatus());
        view.setText("views: " + product.getViews());
        productName.setText("name: " + product.getName());
        price.setText("minimum price: " + product.getMinimumPrice());
        information.setText("information: " + product.getInformation());
        update();
        setSpecialProperties();
        ObservableList arrayOfSellers = FXCollections.observableArrayList();
        arrayOfSellers.addAll(product.getSellersOfThisProduct().keySet().stream().map(e -> e.getUsername()).collect(Collectors.toList()));
        sellers.setItems(arrayOfSellers);
        setImage();
    }

    private void setImage() {
        if (product.getImageFile() != null) {
            image.setImage(product.getImage());
            image.setFitWidth(100);
            image.setFitHeight(100);
        }
    }

    private void setSpecialProperties() {
        HBox row = new HBox();
        Label key = new Label("key");
        key.setMinWidth(125);
        Label value = new Label("value");
        value.setMinWidth(125);
        row.getChildren().addAll(key, value);
        specialProperties.getChildren().add(row);
        for (String s : product.getSpecialPropertiesRelatedToCategory().keySet()) {
            HBox r = new HBox();
            Label k = new Label(s);
            k.setMinWidth(125);
            Label v = new Label(product.getSpecialPropertiesRelatedToCategory().get(s));
            v.setMinWidth(125);
            r.getChildren().addAll(k, v);
            specialProperties.getChildren().add(r);
        }
    }


    public void login() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        EventHandler<ActionEvent> event = (e) -> {
            if (DataBase.getInstance().user != null) {
                Runner.buttonSound();
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
            Runner.buttonSound();
            message.setContentText("you logged out successfully");
            message.show();
            DataBase.getInstance().logout();
        };
        logoutButton.setOnAction(event);

    }


    public void update() {
        rate.setText("rate: " + product.getRate());
        commentsList.getItems().clear();
        commentsToString = ProductController.getInstance().showCommentAboutProduct(product);
        if (commentsToString != null) {
            ObservableList<String> comments = FXCollections.observableArrayList();
            comments.addAll(commentsToString);
            commentsList.setItems(comments);
        }
    }

    public void changeSeller(ActionEvent actionEvent) {
        Runner.buttonSound();
        Off productOff = null;
        for (Off off : product.getOffs()) {
            if (off.getSeller().getUsername().equals(sellers.getValue())) {
                productOff = off;
            }
        }

        int p = product.getSellersOfThisProduct().get(LoginController.getUserByUsername(String.valueOf(sellers.getValue())));

        if (productOff == null) {
            sellerPrice.setText("price: " + p + "\nThere is no off");
        } else {
            sellerPrice.setText("price: " + p + "\noff percent: " + productOff.getOffPercent() + ", new price: " + (p * (100 - productOff.getOffPercent()) / 100));
        }
    }

    public void addThisProductToCard(javafx.scene.input.MouseEvent mouseEvent) {
        Runner.buttonSound();
        if (sellers.getValue() == null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please select seller!", ButtonType.OK);
            error.show();
        } else {
            try {
                customerController.addProductToCard(user, DataBase.getInstance().tempUser.getCard(), product, sellers.getValue().toString());
            } catch (Exception e) {
                Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                error.show();
            }
        }
    }

    public void rateThisProduct(javafx.scene.input.MouseEvent mouseEvent) {
        Runner.buttonSound();
        if (ratePlease.getText().equals("")) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please enter number", ButtonType.OK);
            error.show();
        } else {
            try {
                CustomerController.getInstance().rateProduct(user, product.getProductId(), Integer.getInteger(ratePlease.getText()));
                update();
            } catch (Exception e) {
                Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                error.show();
            }
        }

    }

    public void commentThisProduct(javafx.scene.input.MouseEvent mouseEvent) {
        Runner.buttonSound();
        if (commentContent.getText().equals("")) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please  write your comment", ButtonType.OK);
            error.show();
        } else {
            ProductController.getInstance().addComment(user, product, CommentTitle.getText(), commentContent.getText());
            update();
        }
    }

    public void back(MouseEvent mouseEvent) {
        Runner.buttonSound();
        Runner.getInstance().back();
    }
}
