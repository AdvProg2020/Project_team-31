package GraphicalView;

import javafx.scene.input.MouseEvent;

public class SellerUserArea {
    Runner runner = Runner.getInstance();

    public void back(MouseEvent mouseEvent) {
        runner.back();
    }
}
