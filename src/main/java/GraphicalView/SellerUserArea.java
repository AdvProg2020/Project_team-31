package GraphicalView;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class SellerUserArea {
    Runner runner = Runner.getInstance();

    public void back(MouseEvent mouseEvent) {
        runner.back();
    }

    public void editPersonalInfo(ActionEvent actionEvent) {
        runner.changeScene("EditPersonalInfo.fxml");
    }
}
