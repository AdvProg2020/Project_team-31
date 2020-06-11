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
        setMainMenuScene();
        primaryStage.show();
    }

    private void initializeStage() {
        stage.setTitle("My shop");
        // stage.setResizable(false);
        // stage.setFullScreen(true);
    }


    public void setMainMenuScene() throws IOException {
        URL url = getClass().getClassLoader().getResource("MainMenu.fxml");
        Parent mainMenuRoot = FXMLLoader.load(url);
        Scene mainMenu = new Scene(mainMenuRoot);
        stage.setScene(mainMenu);
        dataBase.pages.add(url);
    }

    public void setUserAreaScene() throws IOException {
        if (dataBase.user == null || dataBase.user instanceof Customer)
            changeScene("CustomerUserArea.fxml");
        else if (dataBase.user instanceof Seller)
            changeScene("SellerUserArea.fxml");
        else if (dataBase.user instanceof Manager)
            changeScene("ManagerUserArea.fxml");
    }

    public void changeScene(String pageName) throws IOException {
        URL url = getClass().getClassLoader().getResource(pageName);
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        dataBase.pages.add(pageName);
    }

    public void back() throws IOException {
        String pageName = dataBase.pages.pop();
        URL url = getClass().getClassLoader().getResource(pageName);
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

}
