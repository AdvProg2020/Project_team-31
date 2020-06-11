package GraphicalView;

import Model.User;

import java.net.URL;
import java.util.Stack;

public class DataBase {
    public static DataBase dataBase = null;

    public static DataBase getInstance() {
        if (dataBase == null)
            dataBase = new DataBase();
        return dataBase;
    }

    private DataBase() {
    }

    ///////////////////////////////////////////////////////////
    Stack<URL> pages = new Stack();
    User user = null;
}
