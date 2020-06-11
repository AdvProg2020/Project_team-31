package GraphicalView;

import Model.User;

import java.net.URL;
import java.util.Stack;

public class DataBase {
    public static DataBase dataBase = null;
    static Runner runner = Runner.getInstance();

    public static DataBase getInstance() {
        if (dataBase == null)
            dataBase = new DataBase();
        return dataBase;
    }

    private DataBase() {
    }

    ///////////////////////////////////////////////////////////
    Stack<String> pages = new Stack();
    User user = null;

    public void logout() {
        user = null;
        pages.clear();
        runner.changeScene("MainMenu.fxml");
    }
}
