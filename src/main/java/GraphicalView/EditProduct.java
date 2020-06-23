package GraphicalView;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class EditProduct implements Initializable {
    public TextField productName;
    public TextField companyName;
    public TextField price;
    public TextArea description;
    public TextField number;
    public VBox choiceBoxContainer;
    public Button categoryFeaturesButton;
    Runner runner = Runner.getInstance();
    DataBase dataBase = DataBase.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initValues();
    }

    private void initValues() {

    }

    public void back(ActionEvent actionEvent) {
        runner.back();
    }

    public void submit(ActionEvent actionEvent) {

    }

    public void selectFile(MouseEvent mouseEvent) {

    }
}
