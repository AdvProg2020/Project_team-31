package Model;

import java.util.ArrayList;

public class Manager extends User {
    public static ArrayList<Manager> allManagers = new ArrayList<Manager>();

    public Manager(String name, String lastName, String username, String emailAddress, String  phoneNumber, String password) {
        super(name, lastName, username, emailAddress, phoneNumber, password);
        allManagers.add(this);
    }
}
