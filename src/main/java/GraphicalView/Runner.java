package GraphicalView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Runner extends Application {
    public static Stage stage;
    DataBase dataBase = DataBase.getInstance();

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

    private void setMainMenuScene() throws IOException {
        URL url = getClass().getClassLoader().getResource("MainMenu.fxml");
        Parent customerMainMenuRoot = FXMLLoader.load(url);
        Scene customerMainMenu = new Scene(customerMainMenuRoot);
        stage.setScene(customerMainMenu);
        dataBase.pages.add(url);
    }

    private void initializeStage() {
        stage.setTitle("My shop");
        // stage.setResizable(false);
        // stage.setFullScreen(true);
    }
}
