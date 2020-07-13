package GraphicalView;

import Model.Customer;
import Model.Manager;
import Model.Seller;
import Model.Supporter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.Socket;
import java.net.URL;

public class Runner extends Application {
    public static Runner runner = null;
    public static Stage stage;
    DataBase dataBase = DataBase.getInstance();
    static Media media = new Media(new File("src/Music/ButtonSound.mp3").toURI().toString());
    Media media1 = new Media(new File("src/Music/1.mp3").toURI().toString());
    Media media2 = new Media(new File("src/Music/2.mp3").toURI().toString());
    Media media3 = new Media(new File("src/Music/3.mp3").toURI().toString());
    Media media4 = new Media(new File("src/Music/4.mp3").toURI().toString());
    MediaPlayer mediaPlayer;
    int musicNumber;

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
        connect();
        stage = primaryStage;
        initializeStage();
        changeScene("MainMenu.fxml");
        changeScene("MainMenu.fxml");
        primaryStage.show();
        if (isThereAnyManager())
            popup();
        else runner.changeScene("RegisterMenu.fxml");
    }

    private boolean isThereAnyManager() {
        return false;
    }

    public void connect() {
        try {
            dataBase.socket = new Socket("127.0.0.1", 8080);
            System.out.println("Successfully connected to server!");
            dataBase.dataInputStream = new DataInputStream(new BufferedInputStream(dataBase.socket.getInputStream()));
            dataBase.dataOutputStream = new DataOutputStream(new BufferedOutputStream(dataBase.socket.getOutputStream()));
        } catch (IOException e) {
            System.err.println("Error starting client!");
        }
    }

    public void changeMusic(String pageName) {
        if (mediaPlayer != null)
            mediaPlayer.stop();
        switch (pageName) {
            case "MainMenu":
                if (musicNumber != 1)
                    mediaPlayer = new MediaPlayer(media1);
                musicNumber = 1;
                break;
            case "OffMenu":
                if (musicNumber != 2)
                    mediaPlayer = new MediaPlayer(media2);
                musicNumber = 2;
                break;
            case "ProductMenu":
                if (musicNumber != 3)
                    mediaPlayer = new MediaPlayer(media3);
                musicNumber = 3;
                break;
            case "UserArea":
                if (musicNumber != 4)
                    mediaPlayer = new MediaPlayer(media4);
                musicNumber = 4;
        }
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        });
        mediaPlayer.play();
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
        else if (dataBase.user instanceof Supporter)
            changeScene("SupporterUserArea.fxml");
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
    public JsonObject jsonMaker(String token, boolean isValid,String controller,String command){
//mamad please implement
    }

}
