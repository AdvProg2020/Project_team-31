package Model;

import java.util.ArrayList;

public abstract class User {
    private String name;
    private String lastname;
    private  String username;
    private String emailAddress;
    private int phoneNumber;
    private  String password;
    private Double credit;
    public static ArrayList<User> allUsers = new ArrayList<User>();


    public User(String name, String lastname, String username, String emailAddress, int phoneNumber, String password) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.credit = 0.0;
    }
}
