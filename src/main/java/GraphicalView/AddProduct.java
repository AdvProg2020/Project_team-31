package GraphicalView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AddProduct implements Initializable {
    public VBox choiceBoxContainer;
    public Button categoryFeaturesButton;
    public TextField productName;
    public TextField companyName;
    public TextField price;
    public TextArea description;
    public TextField number;
    public Button login;
    public Button logout;
    Runner runner = Runner.getInstance();
    ChoiceBox<String> choiceBox;
    File photo;
    File file;
    DataBase dataBase = DataBase.getInstance();
    HashMap<Label, TextField> data = new HashMap<>();
    static HashMap<String, ArrayList<String>> allCategories = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginAlert();
        logoutAlert();
        getAllCategories();
        dropDownListSetUp();
        setCategoryFeatures();
    }

    private void dropDownListSetUp() {
        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(allCategories.keySet());
        choiceBoxContainer.getChildren().add(choiceBox);
        Platform.runLater(() -> {
            SkinBase<ChoiceBox<String>> skin = (SkinBase<ChoiceBox<String>>) choiceBox.getSkin();
            for (Node child : skin.getChildren()) {
                if (child instanceof Label) {
                    Label label = (Label) child;
                    if (label.getText().isEmpty())
                        label.setText("select your category");
                    return;
                }
            }
        });
    }

    private void getAllCategories() {
        try {
            JsonObject jsonObject = runner.jsonMaker("seller", "getAllCategories");
            dataBase.dataOutputStream.writeUTF(jsonObject.toString());
            dataBase.dataOutputStream.flush();
            JsonObject categoryJSon = runner.jsonParser(dataBase.dataInputStream.readUTF());
            JsonArray array = categoryJSon.getAsJsonArray("categories");
            for (JsonElement json : array) {
                String name = json.getAsJsonObject().get("name").getAsString();
                ArrayList<String> features = new ArrayList<>();
                JsonArray featureJSon = json.getAsJsonObject().get("features").getAsJsonArray();
                for (JsonElement element : featureJSon)
                    features.add(element.getAsString());
                allCategories.put(name, features);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void submit(ActionEvent actionEvent) throws Exception {
        Runner.buttonSound();
        if (isInvalid() != null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please enter a valid " + isInvalid(), ButtonType.OK);
            error.show();
            return;
        }
        HashMap<String, String> dataToSend = new HashMap<>();
        String[] generalData = setData(dataToSend);
        addProduct(generalData, dataToSend);
        new Alert(Alert.AlertType.INFORMATION, "product created successfully", ButtonType.OK).show();
        runner.back();
//        if (photo != null) {
//            sellerController.changeProductPhoto(product, photo);
//        }
    }

    private void addProduct(String[] generalData, HashMap<String, String> dataToSend) {
        try {
            String[] first = new String[dataToSend.size()];
            String[] second = new String[dataToSend.size()];
            int iterator = 0;
            for (Map.Entry<String, String> entry : dataToSend.entrySet()) {
                first[iterator] = entry.getKey();
                second[iterator++] = entry.getValue();
            }
            JsonObject send = runner.jsonMaker("seller", "newProduct");
            send.addProperty("generalData", new Gson().toJson(generalData));
            send.addProperty("first", new Gson().toJson(first));
            send.addProperty("second", new Gson().toJson(second));
            if (file != null) {
                send.addProperty("fileFlag", "yes");
                send.addProperty("fileName",file.getName());
                byte[] fileContent = Files.readAllBytes(file.toPath());
                send.addProperty("file",new Gson().toJson(fileContent));
            } else send.addProperty("fileFlag", "no");
            dataBase.dataOutputStream.writeUTF(send.toString());
            dataBase.dataOutputStream.flush();
            dataBase.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] setData(HashMap<String, String> dataToSend) {
        String[] generalData = new String[6];
        generalData[0] = productName.getText();
        generalData[1] = companyName.getText();
        generalData[2] = price.getText();
        generalData[3] = choiceBox.getValue();
        generalData[4] = description.getText();
        generalData[5] = number.getText();
        for (Map.Entry<Label, TextField> entry : data.entrySet()) {
            dataToSend.put(entry.getKey().getText(), entry.getValue().getText());
        }
        return generalData;
    }

    public void back(ActionEvent actionEvent) {
        Runner.buttonSound();
        runner.back();
    }

    public void selectFile(MouseEvent mouseEvent) {
        Runner.buttonSound();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("select photo");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpeg Files", "*.jpeg"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("bmp Files", "*.bmp"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("gif Files", "*.gif"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("png Files", "*.png"));
        photo = fileChooser.showOpenDialog(Runner.stage);
    }

    public void setCategoryFeatures() {
        EventHandler<ActionEvent> event = (e) -> {
            Runner.buttonSound();
            if (choiceBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("you have to select the category");
                alert.show();
            } else {
                try {
                    new GetCategoryInfo().display();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        };
        categoryFeaturesButton.setOnAction(event);
    }

    private String isInvalid() {
        if (productName.getText().equals(""))
            return "product name";
        else if (companyName.getText().equals(""))
            return "company name";
        else if (price.getText().equals("") || !price.getText().matches("^\\d+$") || Integer.parseInt(price.getText()) < 0)
            return "price";
        else if (description.getText().equals(""))
            return "description";
        else if (number.getText().equals("") || !number.getText().matches("^\\d+$") || Integer.parseInt(number.getText()) < 0)
            return "number";
        else if (choiceBox.getValue().equals(""))
            return "category name";
        else if (data.size() == 0)
            return "category features";
        return null;
    }

    public void addFile(ActionEvent actionEvent) {
        Runner.buttonSound();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("select file");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("file", "*.*"));
        file = fileChooser.showOpenDialog(Runner.stage);
    }

    class GetCategoryInfo {
        public void display() throws Exception {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("category info");
            VBox layout = new VBox(10);
            ArrayList<String> features = allCategories.get(choiceBox.getValue());
            Button closeButton = new Button("submit");
            for (String feature : features) {
                Label label = new Label(feature);
                TextField textField = new TextField();
                data.put(label, textField);
                layout.getChildren().addAll(label, textField);
            }
            layout.getChildren().add(closeButton);
            closeButton.setOnAction(e -> {
                Runner.buttonSound();
                for (Node node : layout.getChildren()) {
                    if (node instanceof TextField) {
                        TextField textField = (TextField) node;
                        if (textField.getText().equals("")) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("please fill the blank text fields");
                            alert.show();
                        } else window.close();
                    }
                }
            });
            layout.setAlignment(Pos.CENTER);
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
        }

    }

    public void userArea(MouseEvent mouseEvent) {
        Runner.getInstance().setUserAreaScene();
    }

    public void loginAlert() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        EventHandler<ActionEvent> event = (e) -> {
            if (!DataBase.getInstance().role.equals("none")) {
                error.setContentText("You have logged in!");
                error.show();
            } else {
                Runner.getInstance().changeScene("LoginMenu.fxml");
            }
        };
        login.setOnAction(event);
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
}