package GraphicalView;

import javafx.scene.control.Button;

public class StringAndButtonTable {
    String data;
    Button button;

    public StringAndButtonTable(String data, String button) {
        this.data = data;
        this.button = new Button(button);
    }

    public Button getButton() {
        return button;
    }

    public String getData() {
        return data;
    }
}
