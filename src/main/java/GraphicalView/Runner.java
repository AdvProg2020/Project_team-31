package GraphicalView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public void setCustomerUserAreaScene() throws IOException {
        URL url = getClass().getClassLoader().getResource("CustomerUserArea.fxml");
        Parent customerUserAreaRoot = FXMLLoader.load(url);
        Scene customerMainMenu = new Scene(customerUserAreaRoot);
        stage.setScene(customerMainMenu);
        dataBase.pages.add(url);
    }
    public void setSellerUserAreaScene() throws IOException {
        URL url = getClass().getClassLoader().getResource("SellerUserArea.fxml");
        Parent sellerUserAreaRoot = FXMLLoader.load(url);
        Scene sellerMainMenu = new Scene(sellerUserAreaRoot);
        stage.setScene(sellerMainMenu);
        dataBase.pages.add(url);
    }
    public void setManagerUserAreaScene() throws IOException {
        URL url = getClass().getClassLoader().getResource("ManagerUserArea.fxml");
        Parent managerUserAreaRoot = FXMLLoader.load(url);
        Scene managerMainMenu = new Scene(managerUserAreaRoot);
        stage.setScene(managerMainMenu);
        dataBase.pages.add(url);
    }
}
