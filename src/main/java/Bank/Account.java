package Bank;

import java.util.ArrayList;
import java.util.Date;

public class Account {
    String firstName;
    String lastName;
    String username;
    String password;
    int accountNumber;
    String token;
    Date tokenDate;
    int inventory = 0;

    public Account(String firstName, String lastName, String username, String password, int accountNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.accountNumber = accountNumber;
        allAccount.add(this);
    }

    public static int totalAccountNumber = 0;

    public static ArrayList<Account> allAccount = new ArrayList<>();

    public static boolean userPassMatch(String username, String password) {
        for (Account account : allAccount)
            if (username.equals(account.username) && password.equals(account.password))
                return true;
        return false;
    }

    public static boolean freeUsername(String username) {
        for (Account account : allAccount)
            if (account.username.equals(username))
                return false;
        return true;
    }

    public static Account getAccountByUsername(String username) {
        for (Account account : allAccount)
            if (account.username.equals(username))
                return account;
        return null;
    }

    public boolean validityOfToken() {
        return new Date().getTime() - tokenDate.getTime() >= 3_600_000;
    }

    public static Account getAccountByNumber(int accountNumber) {
        for (Account account : allAccount)
            if (account.accountNumber == accountNumber)
                return account;
        return null;
    }

    public static Account getAccountByToken(String token) {
        for (Account account : allAccount)
            if (account.token.equals(token))
                return account;
        return null;
    }
}