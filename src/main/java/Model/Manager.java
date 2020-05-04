package Model;

import java.util.ArrayList;

public class Manager extends User {
    private static ArrayList<Manager> allManagers = new ArrayList<>();

    public Manager(String name, String lastName, String username, String emailAddress, String  phoneNumber, String password) {
        super(name, lastName, username, emailAddress, phoneNumber, password);
        allManagers.add(this);
    }

    public void deleteManager(){
        allManagers.remove(this);
    }

    public static ArrayList<Manager> getAllManagers() {
        return allManagers;
    }
}
