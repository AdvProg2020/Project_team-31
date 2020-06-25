package GraphicalView;

import Model.Customer;
import Model.Manager;
import Model.Seller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Runner extends Application {
    public static Runner runner = null;
    public static Stage stage;
    DataBase dataBase = DataBase.getInstance();

    public static Runner getInstance() {
        if (runner == null)
            runner = new Runner();
        return runner;
    }

    //////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        initializeStage();
        changeScene("MainMenu.fxml");
        primaryStage.show();
    }

    private void initializeStage() {
        stage.setTitle("My shop");
//         stage.setFullScreen(true);
//        stage.setResizable(false);
        stage.setWidth(800);
        stage.setHeight(600);
    }

    public void setUserAreaScene() {
        if (dataBase.user == null || dataBase.user instanceof Customer)
            changeScene("CustomerUserArea.fxml");
        else if (dataBase.user instanceof Seller)
            changeScene("SellerUserArea.fxml");
        else if (dataBase.user instanceof Manager)
            changeScene("ManagerUserArea.fxml");
    }

    public void changeScene(String pageName) {
        try {
            URL url = getClass().getClassLoader().getResource(pageName);
            Parent root = FXMLLoader.load(url);
            stage.setScene(new Scene(root));
            dataBase.pages.add(pageName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void back() {
        try {
            dataBase.pages.pop();
            String pageName = dataBase.pages.pop();
            URL url = getClass().getClassLoader().getResource(pageName);
            Parent root = FXMLLoader.load(url);
            stage.setScene(new Scene(root));
            dataBase.pages.push(pageName);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
