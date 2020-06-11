package GraphicalView;

import javafx.scene.input.MouseEvent;

public class ManagerUserArea {
    Runner runner = Runner.getInstance();

    public void back(MouseEvent mouseEvent) {
        runner.back();
    }
}
