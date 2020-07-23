package GraphicalView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
import java.util.ResourceBundle;

public class EditProduct implements Initializable {
    public TextField productName;
    public TextField companyName;
    public TextField price;
    public TextArea description;
    public TextField number;
    public VBox choiceBoxContainer;
    public Button categoryFeaturesButton;
    public Button logout;
    HashMap<Label, TextField> data = new HashMap<>();
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();
    String productId = ProductsMenu.productId;
    ChoiceBox<String> choiceBox;
    File photo;
    File file;
    static HashMap<String, ArrayList<String>> allCategories = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoutAlert();
        getAllCategories();
        setCategoryFeatures();
        dropDownListSetUp();
        initValues();
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

    private JsonObject productData() {
        try {
            JsonObject jsonObject = runner.jsonMaker("seller", "getProductData");
            jsonObject.addProperty("productId", productId);
            dataBase.dataOutputStream.writeUTF(jsonObject.toString());
            dataBase.dataOutputStream.flush();
            return runner.jsonParser(dataBase.dataInputStream.readUTF());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initValues() {
        JsonObject jsonObject = productData();
        productName.setText(jsonObject.get("name").getAsString());
        companyName.setText(jsonObject.get("company").getAsString());
        price.setText(jsonObject.get("price").getAsString());
        description.setText(jsonObject.get("information").getAsString());
        number.setText(jsonObject.get("number").getAsString());
    }

    public void back(ActionEvent actionEvent) {
        Runner.buttonSound();
        runner.back();
    }

    public void submit(ActionEvent actionEvent) throws Exception {
        Runner.buttonSound();
        if (isInvalid() != null) {
            Alert error = new Alert(Alert.AlertType.ERROR, "please enter a valid " + isInvalid(), ButtonType.OK);
            error.show();
            return;
        }
        HashMap<String, String> dataToSend = new HashMap<>();
        String id = productId;
        int price = Integer.parseInt(this.price.getText());
        int available = Integer.parseInt(this.number.getText());
        editProduct(id, price, available, description.getText(), dataToSend);
        new Alert(Alert.AlertType.INFORMATION, "product created successfully", ButtonType.OK).show();
        runner.back();
//        if (photo != null) {
//            sellerController.changeProductPhoto(product, photo);
//        }
    }

    private void editProduct(String id, int price, int available, String text, HashMap<String, String> dataToSend) {
        try {
            JsonObject jsonObject = runner.jsonMaker("seller", "editProduct");
            if (file != null) {
                jsonObject.addProperty("fileFlag", "yes");
                String fileContent = new String(Files.readAllBytes(file.toPath()));
                jsonObject.addProperty("file",fileContent);
            } else jsonObject.addProperty("fileFlag", "no");
            jsonObject.addProperty("id", id);
            jsonObject.addProperty("price", price);
            jsonObject.addProperty("available", available);
            jsonObject.addProperty("text", text);
            String[] first = new String[dataToSend.size()];
            String[] second = new String[dataToSend.size()];
            jsonObject.addProperty("first", new Gson().toJson(first));
            jsonObject.addProperty("second", new Gson().toJson(second));
            dataBase.dataOutputStream.writeUTF(jsonObject.toString());
            dataBase.dataOutputStream.flush();
            dataBase.dataInputStream.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String isInvalid() {
        if (productName.getText().equals(""))
            return "product name";
        else if (companyName.getText().equals(""))
            return "company name";
        else if (price.getText().equals("") || !price.getText().matches("^\\d+$"))
            return "price";
        else if (description.getText().equals(""))
            return "description";
        else if (number.getText().equals("") || !number.getText().matches("^\\d+$") && Integer.parseInt(number.getText()) > 0)
            return "number";
        else if (choiceBox.getValue().equals(""))
            return "category name";
        else if (data.size() == 0)
            return "category features";
        return null;
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

    private void dropDownListSetUp() {
        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(allCategories.keySet());
        choiceBoxContainer.getChildren().add(choiceBox);
        choiceBox.setValue(productData().get("category").getAsString());
        choiceBox.setOnAction(event -> data.clear());
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

    public void editFile(ActionEvent actionEvent) {
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
}
