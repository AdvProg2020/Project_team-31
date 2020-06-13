package GraphicalView;

import Controller.LoginController;
import Model.Card;
import Model.Customer;
import Model.Seller;
import Model.User;

import java.util.Calendar;
import java.util.Stack;

public class DataBase {
    public static DataBase dataBase = null;
    static Runner runner = Runner.getInstance();
    LoginController loginController = LoginController.getInstance();

    public static DataBase getInstance() {
        if (dataBase == null)
            dataBase = new DataBase();
        return dataBase;
    }

    private DataBase() {
    }

    ///////////////////////////////////////////////////////////
    Stack<String> pages = new Stack();
    Card card;
    User user = new Seller("mohammadali", "kakavand", "malikakavand", "myemail", "999", "1234","samsung");
    public void logout() {
        user = null;
        pages.clear();
        runner.changeScene("MainMenu.fxml");
    }

    public String[] getUserInfo() {
        //first name
        //last name
        //password
        //email
        //phone number
        //company name (?)
        return loginController.showPersonalInformation(user);
    }
}
