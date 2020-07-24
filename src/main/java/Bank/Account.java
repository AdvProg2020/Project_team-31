package Bank;


import java.io.*;
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
    public static ArrayList<Account> allAccount = new ArrayList<>();


    public Account(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.accountNumber = (allAccount.size() + 1);
        allAccount.add(this);
    }

    public static boolean userPassMatch(String username, String password) {
        for (Account account : allAccount)
            if (username.equals(account.username) && password.equals(account.password))
                return true;
        return false;
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

    public static void logToFile() {
        try {
            FileOutputStream file = new FileOutputStream("src/bank data/allAccounts.txt");
            ObjectOutputStream all = new ObjectOutputStream(file);
            all.writeObject(allAccount);
            all.flush();
            all.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileToLog() {
        try {
            FileInputStream file = new FileInputStream("src/bank data/allAccounts.txt");
            ObjectInputStream all = new ObjectInputStream(file);
            allAccount = (ArrayList<Account>) all.readObject();
            all.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}