package GraphicalView;

import Controller.LoginController;
import Model.*;

import java.util.Calendar;
import java.util.Stack;

public class DataBase {
    public static DataBase dataBase = null;
    static Runner runner = Runner.getInstance();
    public Card card;
    LoginController loginController = LoginController.getInstance();
    Off editingOff = null;
     public DiscountCode editingDiscountCode = null;
    boolean isAddingManager = false;

    public static DataBase getInstance() {
        if (dataBase == null)
            dataBase = new DataBase();
        return dataBase;
    }

    private DataBase() {
    }

    ///////////////////////////////////////////////////////////
    Stack<String> pages = new Stack();
    User tempUser = new User();

    //    User user = new Customer("mohammadali", "kakavand", "malikakavand", "myemail", "999", "1234");
//    User user = new Seller("mohammadali", "kakavand", "malikakavand", "myemail", "999", "1234", "samsung");
//      User user = new Manager("mohammadali", "kakavand", "malikakavand", "myemail", "999", "1234");
    User user=null;

    public void logout() {
        user = null;
        pages.clear();
        runner.changeScene("MainMenu.fxml");
        runner.changeScene("MainMenu.fxml");
    }

    public String[] getUserInfo() {
        return loginController.showPersonalInformation(user);
    }
}
