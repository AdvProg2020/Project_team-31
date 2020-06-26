package GraphicalView;

import Controller.LoginController;
import Controller.SaveAndLoadFiles;
import Model.Customer;
import Model.Manager;
import Model.Seller;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Runner extends Application {
    public static Runner runner = null;
    public static Stage stage;
    DataBase dataBase = DataBase.getInstance();
    static Media media = new Media(new File("src/ButtonSound.mp3").toURI().toString());
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
       SaveAndLoadFiles.start();
        stage = primaryStage;
        initializeStage();
        changeScene("MainMenu.fxml");
        changeScene("MainMenu.fxml");
        primaryStage.show();
        if (LoginController.getInstance().isThereAnyManager())
            popup();
        else runner.changeScene("RegisterMenu.fxml");
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                SaveAndLoadFiles.end();
                System.exit(0);
            }
        });
    }

    private void popup() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("hello!");
        window.setMinWidth(350);
        window.setMinHeight(100);
        window.setResizable(false);
        Label label = new Label();
        label.setText("find best products in our app!");
        Button closeButton = new Button("register now!");
        closeButton.setOnAction(e -> {
            changeScene("RegisterMenu.fxml");
            window.close();
        });
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
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

    public static void buttonSound() {
        MediaPlayer player = new MediaPlayer(media);
        player.play();
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
