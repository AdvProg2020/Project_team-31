package Bank;

import java.util.ArrayList;
import java.util.HashMap;

public class Account {
    String firstName;
    String lastName;
    String username;
    String password;
    public static ArrayList<Account> allAccount;

    static {
        allAccount = new ArrayList<>();
    }

    public static boolean userPassMatch(String username, String password) {
        for (Account account : allAccount)
            if (username.equals(account.username) && password.equals(account.password))
                return true;
        return false;
    }
    public static boolean freeUsername(String username){

    }
}