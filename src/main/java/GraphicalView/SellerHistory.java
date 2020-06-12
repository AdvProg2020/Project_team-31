package GraphicalView;

import javafx.event.ActionEvent;

public class SellerHistory {
    Runner runner = Runner.getInstance();

    public void back(ActionEvent actionEvent) {
        runner.back();
    }
}
