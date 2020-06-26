package GraphicalView;

import Controller.ManagerController;
import Model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RequestArea implements Initializable {
    public VBox detail;
    public Button logout;
    Request request;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logoutAlert();
        request = RequestMenu.request;
        if (request instanceof SellerRequest) {
            setSellerRequest();
        } else if (request instanceof OffRequest) {
            setOffRequest();
        } else if (request instanceof ProductRequest) {
            setProductRequest();
        } else {
            setSellerOfProductRequest();
        }
    }

    public void userArea(MouseEvent mouseEvent) {
        Runner.getInstance().setUserAreaScene();
    }

    private void logoutAlert() {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        EventHandler<ActionEvent> event = (e) -> {
            Runner.buttonSound();
            message.setContentText("you logged out successfully");
            message.show();
            DataBase.getInstance().logout();
        };
        logout.setOnAction(event);
    }

    private void setSellerRequest() {
        SellerRequest sellerRequest = (SellerRequest) request;
        Label type = new Label("Request to be a seller");
        Label username = new Label("username: " + sellerRequest.getUsername());
        Label name = new Label("name: " + sellerRequest.getInformation()[0] + ", " + sellerRequest.getInformation()[1]);
        Label company = new Label("company: " + sellerRequest.getInformation()[5]);
        Label email = new Label("email address: " + sellerRequest.getInformation()[2]);
        Label phone = new Label("phone number: " + sellerRequest.getInformation()[3]);
        detail.getChildren().addAll(type, username, name, company, email, phone);
    }

    private void setOffRequest() {
        OffRequest offRequest = (OffRequest) request;
        if (offRequest.getIsEditing()) {
            Label type = new Label("Request to edit an off");
            Label id = new Label("OffId: " + offRequest.getOff().getOffId());
            Label beginTime = new Label("begin time: " + offRequest.getBeginTime());
            Label endTime = new Label("end time: " + offRequest.getEndTime());
            Label percent = new Label("percent: " + offRequest.getOffPercent());
            Label products = new Label("products are: ");
            detail.getChildren().addAll(type, id, beginTime, endTime, percent, products);
            int i = 1;
            for (Product product : offRequest.getOnSaleProducts()) {
                Label pro = new Label((i++) + ", " + product.getName());
                detail.getChildren().add(pro);
            }
        } else {
            Off off = offRequest.getOff();
            Label type = new Label("Request to create an off");
            Label id = new Label("OffId: " + off.getOffId());
            Label beginTime = new Label("begin time: " + off.getBeginTime());
            Label endTime = new Label("end time: " + off.getEndTime());
            Label percent = new Label("percent: " + off.getOffPercent());
            Label products = new Label("products are: ");
            detail.getChildren().addAll(type, id, beginTime, endTime, percent, products);
            int i = 1;
            for (Product product : off.getOnSaleProducts()) {
                Label pro = new Label((i++) + ", " + product.getName());
                detail.getChildren().add(pro);
            }
        }
    }

    private void setProductRequest() {
        ProductRequest productRequest = (ProductRequest) request;
        if (productRequest.isEditing()) {
            Label type = new Label("Request to edit Product");
            Label product = new Label("Product: " + productRequest.getProduct().getName());
            Label seller = new Label("seller: " + productRequest.getSeller().getUsername());
            Label price = new Label("price: " + productRequest.getPrice());
            Label available = new Label("available: " + productRequest.getAvailable());
            Label information = new Label("information: " + productRequest.getInformation());
            Label features = new Label("special features: ");
            detail.getChildren().addAll(type, product, seller, price, available, information, features);
            for (String s : productRequest.getSpecialPropertiesRelatedToCategory().keySet()) {
                Label f = new Label("key: " + s + ", value: " + productRequest.getSpecialPropertiesRelatedToCategory().get(s));
                detail.getChildren().add(f);
            }
        } else {
            Product productToCreate = productRequest.getProduct();
            Label type = new Label("Request to create Product");
            Label product = new Label("Product: " + productToCreate.getName());
            Seller seller = productToCreate.getSellersOfThisProduct().keySet().stream().collect(Collectors.toList()).get(0);
            Label sellerLabel = new Label("seller: " + seller.getUsername());
            Label price = new Label("price: " + productToCreate.getMinimumPrice());
            Label available = new Label("available: " + productToCreate.getAvailable());
            Label information = new Label("information: " + productToCreate.getInformation());
            Label features = new Label("special features: ");
            detail.getChildren().addAll(type, product, sellerLabel, price, available, information, features);
            for (String s : productToCreate.getSpecialPropertiesRelatedToCategory().keySet()) {
                Label f = new Label("key: " + s + ", value: " + productToCreate.getSpecialPropertiesRelatedToCategory().get(s));
                detail.getChildren().add(f);
            }
        }
    }

    private void setSellerOfProductRequest() {
        SellerOfProductRequest sellerOfProductRequest = (SellerOfProductRequest) request;
        Label type = new Label("Request to add a seller to a product");
        Label product = new Label("Product: " + sellerOfProductRequest.getProduct());
        Label seller = new Label("seller: " + sellerOfProductRequest.getSeller());
        Label price = new Label("price: " + sellerOfProductRequest.getPrice());
        detail.getChildren().addAll(type, product, seller, price);
    }

    public void accept(MouseEvent mouseEvent) {
        ManagerController.getInstance().acceptRequest(request.getRequestId());
        back(mouseEvent);
    }

    public void decline(MouseEvent mouseEvent) {
        ManagerController.getInstance().declineRequest(request.getRequestId());
        back(mouseEvent);
    }

    public void back(MouseEvent mouseEvent) {
        Runner.getInstance().back();
    }

}
