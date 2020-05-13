package View;

import Model.Seller;
import Model.User;
import Controller.*;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Menu {
    public static User user;
    public static Scanner scanner;
    LoginController loginController = LoginController.getInstance();
    SellerController sellerController = SellerController.getInstance();

    static {
        scanner = new Scanner(System.in);
    }

    protected Menu() {
    }

    public abstract void run();

    public Matcher getMatcher(String regex, String check) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(check);
    }
}
