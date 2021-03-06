package View;

import Model.Card;
import Model.User;
import Controller.*;


import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Menu {
    public static User user;
    public static User tempUser = null;
    public static Scanner scanner;
    public static Card card = null;
    LoginController loginController = LoginController.getInstance();
    SellerController sellerController = SellerController.getInstance();
    ProductController productController = ProductController.getInstance();
    ManagerController managerController = ManagerController.getInstance();
    CustomerController customerController = CustomerController.getInstance();

    static {
        scanner = new Scanner(System.in);
    }

    protected Menu() {
    }

    public abstract void run();

    public Matcher getMatcher(String regex, String check) {
        if (check.equalsIgnoreCase("login"))
            loginAndLogOut(true);
        else if (check.equalsIgnoreCase("logout"))
            loginAndLogOut(false);
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(check);
    }

    public Matcher safeGetMatcher(String regex, String check) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(check);
    }

    public String scanByRegex(String regex, String massage) {
        String check;
        while (!(check = scanner.nextLine().trim()).matches(regex)) {
            if (check.equalsIgnoreCase("login")) {
                loginAndLogOut(true);
            } else if (check.equalsIgnoreCase("logout")) {
                loginAndLogOut(false);
            } else if (check.equalsIgnoreCase("back"))
                return null;
            System.out.println(massage);
        }
        return check;
    }

    public void loginAndLogOut(boolean type) {
        if (user == null)
            if (type)
                LoginMenu.getInstance().run();
            else System.out.println("you aren't logged in!");
        else if (type)
            System.out.println("you already have logged in!");
        else LoginMenu.getInstance().logout();
    }

    void mustLoginAccess() throws Exception {
        if (user == null)
            throw new Exception("you have to login!");
    }

    public static void initialize() {
        tempUser = LoginController.getInstance().createTempUser();
    }
}
