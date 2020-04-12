package Model;

import java.util.ArrayList;

public class Manager extends User {
    public static ArrayList<Manager> allManagers = new ArrayList<Manager>();

    public Manager(String name, String lastname, String username, String emailAddress, int phoneNumber, String password) {
        super(name, lastname, username, emailAddress, phoneNumber, password);
    }
}
