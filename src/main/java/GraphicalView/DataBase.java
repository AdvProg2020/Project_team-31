package GraphicalView;

import Controller.LoginController;
import Model.Card;
import Model.Customer;
import Model.Manager;
import Model.Seller;
import Model.User;

import java.util.Calendar;
import java.util.Stack;

public class DataBase {
    public static DataBase dataBase = null;
    static Runner runner = Runner.getInstance();
    public Card card;
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
    User tempUser = new User();
//    User user = new Customer("mohammadali", "kakavand", "malikakavand", "myemail", "999", "1234");
            User user = new Seller("mohammadali", "kakavand", "malikakavand", "myemail", "999", "1234","samsung");
//    User user = new Manager("mohammadali", "kakavand", "malikakavand", "myemail", "999", "1234");
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
