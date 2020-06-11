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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        initializeStage();
        setScene();
        primaryStage.show();
    }

    private void setScene() throws IOException {
        URL url = getClass().getClassLoader().getResource("CustomerUserArea.fxml");
        Parent customerMainMenuRoot = FXMLLoader.load(url);
        Scene customerMainMenu = new Scene(customerMainMenuRoot);
        stage.setScene(customerMainMenu);
    }

    private void initializeStage() {
       // stage.setResizable(false);
        stage.setTitle("My shop");
       // stage.setFullScreen(true);
    }
}
