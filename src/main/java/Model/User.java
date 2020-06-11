package Model;

import Controller.SaveAndLoadFiles;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable {
    private String name;
    private String lastName;
    private String username;
    private String emailAddress;
    private String phoneNumber;
    private String password;
    private int credit;
    private Card card;
    private HashMap<String, String> filters;
    private static ArrayList<User> allUsers = new ArrayList<>();

    public User(String name, String lastName, String username, String emailAddress, String  phoneNumber, String password) {
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.credit = 0;
        allUsers.add(this);
        filters = new HashMap<>();
    }

    public User(String nullString) {
        name = nullString;
        filters = new HashMap<>();
    }

    public HashMap<String, String> getFilters() {
        return filters;
    }

    public void removeFilter(String filterKey) {
        filters.remove(filterKey);
    }

    public void addFilter(String filterKey, String filterValue) {
        filters.put(filterKey, filterValue);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String[] getPersonalInformation() {
        String[] arrayOfInformation = new String[7];
        arrayOfInformation[0] = name;
        arrayOfInformation[1] = lastName;
        arrayOfInformation[2] = username;
        arrayOfInformation[3] = emailAddress;
        arrayOfInformation[4] = phoneNumber;
        arrayOfInformation[5] = password;
        arrayOfInformation[6] = String.valueOf(credit);
        return arrayOfInformation;
    }

    public void setNewInformation(String name, String lastName, String emailAddress, String  phoneNumber, String password) {
        this.name = name;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public int getCredit() {
        return credit;
    }

    public void payMoney(int payedMoney) {
        credit -= payedMoney;
    }

    public void getMoney(int getMoney) {
        credit += getMoney;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public void deleteUser(){
        allUsers.remove(this);
    }

    public static void logToFile() {
        try{
            FileOutputStream file = new FileOutputStream("src/project files/allUsers.txt");
            ObjectOutputStream allUsers = new ObjectOutputStream(file);

            allUsers.writeObject(getAllUsers());
            allUsers.flush();
            allUsers.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void fileToLog(){
        try{
            FileInputStream file = new FileInputStream("src/project files/allUsers.txt");
            ObjectInputStream allUsers = new ObjectInputStream(file);
            User.allUsers = (ArrayList<User>) allUsers.readObject();
            allUsers.close();
        }catch (IOException | ClassNotFoundException e) {
            SaveAndLoadFiles.end();
        }

    }
}
